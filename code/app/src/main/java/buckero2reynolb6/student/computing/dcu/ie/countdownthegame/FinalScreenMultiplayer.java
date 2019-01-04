package buckero2reynolb6.student.computing.dcu.ie.countdownthegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class FinalScreenMultiplayer extends AppCompatActivity {

    @Override
    public void onPause ()
    {
        super.onPause();
    }

    @Override
    public void onResume ()
    {
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen_multiplayer);

        ImageButton homeBtn = (ImageButton) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreen();
            }
        });
        TextView totalTally1, totalTally2, resultView;
        resultView = (TextView) findViewById(R.id.resultView);
        totalTally1 = (TextView) findViewById(R.id.totalTally1);
        totalTally1.setText(""+ HomeActivity.totalTally1);
        totalTally2 = (TextView) findViewById(R.id.totalTally2);
        totalTally2.setText(""+ HomeActivity.totalTally2);

        if (HomeActivity.totalTally1 > HomeActivity.totalTally2) {
            resultView.setText("Congratulations Player 1, you are the winner with " + HomeActivity.totalTally1 + " points.");
        }
        else if (HomeActivity.totalTally1 == HomeActivity.totalTally2) {
            resultView.setText("It's been a DRAW!");
        }
        else {
            resultView.setText("Congratulations Player 2, you are the winner with " + HomeActivity.totalTally2 + " points.");
        }
    }

    public void HomeScreen() {
        LetterRound.CountF = 0;
        HomeActivity.totalTally1 = 0;
        HomeActivity.totalTally2 = 0;
        HomeActivity.CountM = 0;
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
