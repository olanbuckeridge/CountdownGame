package buckero2reynolb6.student.computing.dcu.ie.countdownthegame;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.media.Image;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gigamole.library.PulseView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;


public class HomeActivity extends AppCompatActivity
        implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    public static int CountMulti = 0;
    public static int CountM = 0;
    public static int totalTally1 = 0;
    public static int totalTally2 = 0;
    private Button showLeaderboardBtn;
    private Button signInBtn;
    private Button signOutbtn;
    public static int stopPositionA;
    public GoogleApiClient mGoogleApiClient;


    private Toast toast;
    private long lastBackPressTime = 0;

    PulseView pulseView;


    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            toast = Toast.makeText(this, "Press back again to exit game.", Toast.LENGTH_SHORT);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pulseView = (PulseView) findViewById(R.id.PV);
        pulseView.startPulse();


        SharedPreferences settings = getSharedPreferences("CountdownTheGame", Context.MODE_PRIVATE);
        //Buttons
        showLeaderboardBtn = (Button) findViewById(R.id.showLeaderboardBtn);
        signInBtn = (Button) findViewById(R.id.signInBtn);
        signOutbtn = (Button) findViewById(R.id.signOutBtn);

        //hide buttons
        showLeaderboardBtn.setVisibility(View.INVISIBLE);
        signInBtn.setVisibility(View.VISIBLE);
        signOutbtn.setVisibility(View.INVISIBLE);

        // set onClickLister
        showLeaderboardBtn.setOnClickListener(this);
        signInBtn.setOnClickListener(this);
        signOutbtn.setOnClickListener(this);

        // initialize google Api Client.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();


        ImageButton singlePlayerBtn = (ImageButton) findViewById(R.id.singlePlayerBtn);
        singlePlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.CountM = 0;
                HomeActivity.CountMulti = 0;
                SinglePlayer(view);
            }
        });

        ImageButton multiPlayerBtn = (ImageButton) findViewById(R.id.multiPlayerBtn);
        multiPlayerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.CountM = 0;
                NumberRound.answer = 0;
                HomeActivity.totalTally1 = 0;
                HomeActivity.totalTally2 = 0;
                HomeActivity.CountMulti = 1;
                MultiPlayer(view);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Disconnect
        if (mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Get player's information.
        Player player = Games.Players.getCurrentPlayer(mGoogleApiClient);

        // Display Name.
        String displayName = "???";
        if (player != null){
            displayName = player.getDisplayName();
        }
        Toast.makeText(this, String.format("Hello %s!", displayName), Toast.LENGTH_SHORT).show();

        showLeaderboardBtn.setVisibility(View.VISIBLE);
        signInBtn.setVisibility(View.INVISIBLE);
        signOutbtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            if(resultCode != RESULT_OK){
                return;
            }
            if(!mGoogleApiClient.isConnected()){
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed( ConnectionResult connectionResult) {
        int errorCode = connectionResult.getErrorCode();
        //Not signed in.
        if (errorCode == connectionResult.SIGN_IN_REQUIRED){
            try{
                connectionResult.startResolutionForResult(this, 100);
            } catch(IntentSender.SendIntentException e){
                mGoogleApiClient.connect();
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signInBtn:
                mGoogleApiClient.connect();
                break;
            case R.id.signOutBtn:
                Games.signOut(mGoogleApiClient);

                if (mGoogleApiClient.isConnected()){
                    mGoogleApiClient.disconnect();
                }

                //Show & Hide Buttons.
                showLeaderboardBtn.setVisibility(View.INVISIBLE);
                signInBtn.setVisibility(View.VISIBLE);
                signOutbtn.setVisibility(View.INVISIBLE);

                //Message
                Toast.makeText(this, "Sign Out", Toast.LENGTH_SHORT).show();
                break;

            case R.id.submitScoreBtn:
                //Submit score
                Games.Leaderboards.submitScore(mGoogleApiClient,
                        getString(R.string.leaderboard_id), HomeActivity.totalTally1);
                Toast.makeText(this, "Submit!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.showLeaderboardBtn:
                //show leaderboard
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
                        mGoogleApiClient, getString(R.string.leaderboard_id)), 1);
                break;
        }
    }

    public void SinglePlayer(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, LetterRound.class);
        intent.putExtra(LetterRound.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(LetterRound.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    public void MultiPlayer(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, Player1Screen.class);
        intent.putExtra(Player1Screen.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(Player1Screen.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

}

