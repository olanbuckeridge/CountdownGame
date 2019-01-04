package buckero2reynolb6.student.computing.dcu.ie.countdownthegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ResultScreenMultiplayer extends AppCompatActivity {

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
        setContentView(R.layout.activity_result_screen_multiplayer);

        ImageButton nextBtn = (ImageButton) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (HomeActivity.CountM == 1) {
                    Player2Screen();
                    finish();
                }
                else if (HomeActivity.CountM == 2) {
                    Player1Screen();
                    finish();
                }
                else if (HomeActivity.CountM == 3) {
                    Player2Screen();
                    finish();
                }
                else if (HomeActivity.CountM == 4) {
                    Player1Screen();
                    LetterRound.CountF = 0;
                    finish();
                }
                else if (HomeActivity.CountM == 5) {
                    Player2Screen();
                    LetterRound.CountF = 0;
                    finish();
                }
                else {
                    FinalScreen();
                    finish();
                }
            }
        });

        //Result Text
        TextView resultTXT, totalTally1, totalTally2;
        resultTXT = (TextView) findViewById(R.id.resultView);
        totalTally1 = (TextView) findViewById(R.id.totalTally1);
        totalTally2 = (TextView) findViewById(R.id.totalTally2);



        if (LetterRound.CountF == 0) {
            if (HomeActivity.CountM == 1) {
                resultTXT.setText("Congratulations, "+LetterRound.answerL+" scored you " + LetterRound.answerL.length() + " points.");
                HomeActivity.totalTally1 += LetterRound.answerL.length();
                totalTally1.setText("" + HomeActivity.totalTally1);
                totalTally2.setText("" + HomeActivity.totalTally2);
            }
            else if (HomeActivity.CountM == 2) {
                resultTXT.setText("Congratulations, "+LetterRound.answerL+" scored you " + LetterRound.answerL.length() + " points.");
                HomeActivity.totalTally2 += LetterRound.answerL.length();
                totalTally1.setText("" + HomeActivity.totalTally1);
                totalTally2.setText("" + HomeActivity.totalTally2);
            }

            else if (HomeActivity.CountM == 3) {
                if (NumberRound.resultNum <= 10 & NumberRound.answer != 0) {
                    resultTXT.setText("Congratulations, your number has scored you " + NumberRound.numberScore + ".");
                    HomeActivity.totalTally1 += NumberRound.numberScore;
                    totalTally1.setText("" + HomeActivity.totalTally1);
                    totalTally2.setText("" + HomeActivity.totalTally2);
                } else {
                    resultTXT.setText("Unlucky, your number has scored you no points.");
                    HomeActivity.totalTally1 += NumberRound.numberScore;
                    totalTally1.setText("" + HomeActivity.totalTally1);
                    totalTally2.setText("" + HomeActivity.totalTally2);
                }
            }
            else if (HomeActivity.CountM == 4) {
                if (NumberRound.resultNum <= 10 & NumberRound.answer != 0 ) {
                    resultTXT.setText("Congratulations, your number has scored you " + NumberRound.numberScore + ".");
                    HomeActivity.totalTally2 += NumberRound.numberScore;
                    totalTally1.setText("" + HomeActivity.totalTally1);
                    totalTally2.setText("" + HomeActivity.totalTally2);
                }
                else {
                    resultTXT.setText("Unlucky, your number has scored you no points.");
                    HomeActivity.totalTally2 += NumberRound.numberScore;
                    totalTally1.setText("" + HomeActivity.totalTally1);
                    totalTally2.setText("" + HomeActivity.totalTally2);
                }
            }
            else if (HomeActivity.CountM == 5) {
                resultTXT.setText("Congratulations, you got the correct word.");
                HomeActivity.totalTally1 += 10;
                totalTally1.setText("" + HomeActivity.totalTally1);
                totalTally2.setText("" + HomeActivity.totalTally2);
            }
            else if (HomeActivity.CountM == 6) {
                resultTXT.setText("Congratulations, you got the correct word.");
                HomeActivity.totalTally2 += 10;
                totalTally1.setText("" + HomeActivity.totalTally1);
                totalTally2.setText("" + HomeActivity.totalTally2);
            }
        }
        else if (LetterRound.CountF == 1) {
            resultTXT.setText("Incorrect! The correct word was " + ConundrumRound.randomWord + ".");
            totalTally1.setText("" + HomeActivity.totalTally1);
            totalTally2.setText("" + HomeActivity.totalTally2);
        }
        else {
            resultTXT.setText("Sorry, that is an invalid word!");
            LetterRound.CountF = 0;
            totalTally1.setText("" + HomeActivity.totalTally1);
            totalTally2.setText("" + HomeActivity.totalTally2);
        }
    }

    public void Player1Screen() {
        Intent intent = new Intent(this, Player1Screen.class);
        startActivity(intent);
    }

    public void Player2Screen() {
        Intent intent = new Intent(this, Player2Screen.class);
        startActivity(intent);
    }

    public void FinalScreen() {
        Intent intent = new Intent(this, FinalScreenMultiplayer.class);
        startActivity(intent);
    }
}
