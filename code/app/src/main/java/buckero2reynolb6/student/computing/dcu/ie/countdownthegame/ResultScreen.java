package buckero2reynolb6.student.computing.dcu.ie.countdownthegame;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ResultScreen extends AppCompatActivity {



    @Override
    public void onPause ()
    {
        super.onPause();
    }



    private Toast toast;
    private long lastBackPressTime = 0;

    @Override
    public void onBackPressed() {
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            toast = Toast.makeText(this, "Press back again to return to the home screen.", Toast.LENGTH_SHORT);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            LetterRound.CountF = 0;
            HomeActivity.totalTally1 = 0;
            HomeActivity.totalTally2 = 0;
            HomeActivity.CountM = 0;
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        ImageButton nextBtn = (ImageButton) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HomeActivity.CountM == 1) {
                    NumberScreen(view);
                    finish();
                }
                else if (HomeActivity.CountM == 2) {
                    ConundrumScreen(view);
                    LetterRound.CountF = 0;
                    finish();
                }
                else {
                    FinalScreen(view);
                    finish();
                }
            }
        });

        //Result Text
        TextView resultTXT, totalTally;
        resultTXT = (TextView) findViewById(R.id.resultView);
        totalTally = (TextView) findViewById(R.id.totalTally);



        if (LetterRound.CountF == 0) {
            if (HomeActivity.CountM == 1) {
                resultTXT.setText("Congratulations, "+LetterRound.answerL+" scored you " + LetterRound.answerL.length() + " points.");;
                HomeActivity.totalTally1 += LetterRound.answerL.length();
                totalTally.setText("" + HomeActivity.totalTally1);
            }
            else if (HomeActivity.CountM == 2) {
                if (NumberRound.resultNum <= 10 & NumberRound.answer != 0 ) {
                    resultTXT.setText("Congratulations, your number has scored you " + NumberRound.numberScore + ".");
                    HomeActivity.totalTally1 += NumberRound.numberScore;
                    totalTally.setText("" + HomeActivity.totalTally1);
                }
                else {
                    resultTXT.setText("Unlucky, your number has scored you no points.");
                    HomeActivity.totalTally1 += NumberRound.numberScore;
                    totalTally.setText("" + HomeActivity.totalTally1);
                }
            }
            else if (HomeActivity.CountM == 3) {
                resultTXT.setText("Congratulations, you got the correct word.");
                HomeActivity.totalTally1 += 10;
                totalTally.setText("" + HomeActivity.totalTally1);
            }
        }
        else if (LetterRound.CountF == 1) {
            resultTXT.setText("Incorrect! The correct word was " + ConundrumRound.randomWord + ".");
            totalTally.setText("" + HomeActivity.totalTally1);
        }
        else {
            resultTXT.setText("Sorry, that is an invalid word!");
            totalTally.setText("" + HomeActivity.totalTally1);
            LetterRound.CountF = 0;
        }
    }

    public void NumberScreen(View view) {
        Intent homeIntent = new Intent(ResultScreen.this, NumberRound.class);
        startActivity(homeIntent);
    }

    public void ConundrumScreen(View view) {
        Intent homeIntent = new Intent(ResultScreen.this, ConundrumRound.class);
        startActivity(homeIntent);
    }

    public void FinalScreen(View view) {
        Intent homeIntent = new Intent(ResultScreen.this, FinalScreen.class);
        startActivity(homeIntent);
    }
}
