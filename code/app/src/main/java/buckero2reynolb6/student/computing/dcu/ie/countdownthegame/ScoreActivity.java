package buckero2reynolb6.student.computing.dcu.ie.countdownthegame;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;

public class ScoreActivity extends AppCompatActivity
        implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private Button submitScoreBtn;
    private Button showLeaderboardBtn;
    private Button signInBtn;
    private Button signOutbtn;

    private int totalScore;

    public GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences settings = getSharedPreferences("CountdownTheGame", Context.MODE_PRIVATE);
        totalScore = settings.getInt("totalScore", 0);
        ((TextView) findViewById(R.id.totalScoreLabel)).setText(HomeActivity.totalTally1 + "");
        //Buttons
        submitScoreBtn = (Button) findViewById(R.id.submitScoreBtn);
        showLeaderboardBtn = (Button) findViewById(R.id.showLeaderboardBtn);
        signInBtn = (Button) findViewById(R.id.signInBtn);
        signOutbtn = (Button) findViewById(R.id.signOutBtn);

        //hide buttons
        submitScoreBtn.setVisibility(View.INVISIBLE);
        showLeaderboardBtn.setVisibility(View.INVISIBLE);
        signInBtn.setVisibility(View.VISIBLE);
        signOutbtn.setVisibility(View.INVISIBLE);

        // set onClickLister
        submitScoreBtn.setOnClickListener(this);
        showLeaderboardBtn.setOnClickListener(this);
        signInBtn.setOnClickListener(this);
        signOutbtn.setOnClickListener(this);

        // initialize google Api Client.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

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

        submitScoreBtn.setVisibility(View.VISIBLE);
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
                submitScoreBtn.setVisibility(View.INVISIBLE);
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

}
