package buckero2reynolb6.student.computing.dcu.ie.countdownthegame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.gigamole.library.PulseView;

public class SplashScreen extends AppCompatActivity {

    VideoView clock;
    ImageView clockIMG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        clockIMG = (ImageView) findViewById(R.id.clockIMG);

        PulseView pulseView = (PulseView) findViewById(R.id.PV);
        pulseView.startPulse();
        int seconds = 3;
        CountDownTimer countDownTimer = new CountDownTimer(seconds *1000,1000) {
            @Override
            public void onTick(long millis) {

            }
            @Override
            public void onFinish() {
                Intent homeIntent = new Intent(SplashScreen.this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }.start();
    }
}
