package buckero2reynolb6.student.computing.dcu.ie.countdownthegame;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ConundrumRound extends AppCompatActivity {

    ImageButton startBtn, resetBtn, submitBtn;
    TextView E1,E2,E3,E4,E5,E6,E7,E8,E9;
    TextView L1,L2,L3,L4,L5,L6,L7,L8,L9,testTXT,timerTXT;
    Random random = new Random();
    private List<String> list;
    private int count9 = 0;
    private String word = "";
    private int submit = 0;
    private int seconds = 31;
    public static String randomWord = "";
    public static int CountF = 0;
    private int Count = 0;
    VideoView clock;
    ImageView clockIMG;
    private int stopPosition;

    private MediaPlayer mp;

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
    public void onPause ()
    {
        if (mp != null){
            mp.pause();
            stopPosition = clock.getCurrentPosition(); //stopPosition is an int
            clock.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume ()
    {
        if (mp != null) {
            mp.start();
            clock.seekTo(stopPosition);
            clock.start();
        }
        super.onResume();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conundrum_round);


        clock = (VideoView) findViewById(R.id.clock);
        clockIMG = (ImageView) findViewById(R.id.clockIMG);

        clock.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.clock);

        clock.setVisibility(View.INVISIBLE);

        //Random Letters
        L1 = (TextView) findViewById(R.id.Letter1);
        L2 = (TextView) findViewById(R.id.Letter2);
        L3 = (TextView) findViewById(R.id.Letter3);
        L4 = (TextView) findViewById(R.id.Letter4);
        L5 = (TextView) findViewById(R.id.Letter5);
        L6 = (TextView) findViewById(R.id.Letter6);
        L7 = (TextView) findViewById(R.id.Letter7);
        L8 = (TextView) findViewById(R.id.Letter8);
        L9 = (TextView) findViewById(R.id.Letter9);
        //Empty Buttons
        E1 = (TextView) findViewById(R.id.Empty1);
        E2 = (TextView) findViewById(R.id.Empty2);
        E3 = (TextView) findViewById(R.id.Empty3);
        E4 = (TextView) findViewById(R.id.Empty4);
        E5 = (TextView) findViewById(R.id.Empty5);
        E6 = (TextView) findViewById(R.id.Empty6);
        E7 = (TextView) findViewById(R.id.Empty7);
        E8 = (TextView) findViewById(R.id.Empty8);
        E9 = (TextView) findViewById(R.id.Empty9);

        //timerTXT = (TextView) findViewById(R.id.timerTXT);
        //testTXT = (TextView) findViewById(R.id.testTXT);

        L1.setOnTouchListener(new ConundrumRound.ChoiceTouchListener());
        L2.setOnTouchListener(new ConundrumRound.ChoiceTouchListener());
        L3.setOnTouchListener(new ConundrumRound.ChoiceTouchListener());
        L4.setOnTouchListener(new ConundrumRound.ChoiceTouchListener());
        L5.setOnTouchListener(new ConundrumRound.ChoiceTouchListener());
        L6.setOnTouchListener(new ConundrumRound.ChoiceTouchListener());
        L7.setOnTouchListener(new ConundrumRound.ChoiceTouchListener());
        L8.setOnTouchListener(new ConundrumRound.ChoiceTouchListener());
        L9.setOnTouchListener(new ConundrumRound.ChoiceTouchListener());

        E1.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E2.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E3.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E4.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E5.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E6.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E7.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E8.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E9.setOnDragListener(new ConundrumRound.ChoiceDragListener());


        startBtn = (ImageButton) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectWord();
                resetBtn.setVisibility(View.VISIBLE);
                submitBtn.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.GONE);
            }
        });

        resetBtn = (ImageButton) findViewById(R.id.resetBtn);
        resetBtn.setVisibility(View.INVISIBLE);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        submitBtn = (ImageButton) findViewById(R.id.submitBtn);
        submitBtn.setVisibility(View.INVISIBLE);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit = 1;
                submitBtn.setVisibility(View.INVISIBLE);
                mp.stop();
                getResult();
            }
        });
    }

    /**
     * ChoiceTouchListener will handle touch events on draggable views
     *
     */
    private final class ChoiceTouchListener implements View.OnTouchListener {
        @SuppressLint("NewApi")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            /*
             * Drag details: we only need default behavior
             * - clip data could be set to pass data as part of drag
             * - shadow can be tailored
             */
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }
    /**
     * DragListener will handle dragged views being dropped on the drop area
     * - only the drop action will have processing added to it as we are not
     * - amending the default behavior for other parts of the drag process
     *
     */
    @SuppressLint("NewApi")
    private class ChoiceDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:

                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;
                    //checking whether first character of dropTarget equals first character of dropped
                    //stop displaying the view where it was before it was dragged
                    view.setVisibility(View.INVISIBLE);
                    //update the text in the target view to reflect the data being dropped
                    dropTarget.setText(dropped.getText().toString());
                    //make it bold to highlight the fact that an item has been dropped
                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    //if an item has already been dropped here, there will be a tag
                    Object tag = dropTarget.getTag();
                    //if there is already an item here, set it back visible in its original place
                    if(tag!=null)
                    {
                        //the tag is the view id already dropped here
                        int existingID = (Integer)tag;
                        //set the original view visible again
                        findViewById(existingID).setVisibility(View.VISIBLE);
                    }
                    //set the tag in the target view being dropped on - to the ID of the view being dropped
                    dropTarget.setTag(dropped.getId());
                    //remove setOnDragListener by setting OnDragListener to null, so that no further drag & dropping on this TextView can be done
                    dropTarget.setOnDragListener(null);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public void openScoreAndLeaderboard(){
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    public void resultScreen() {
        Intent intent = new Intent(this, ResultScreen.class);
        startActivity(intent);
    }

    public void resultScreenMulti() {
        Intent intent = new Intent(this, ResultScreenMultiplayer.class);
        startActivity(intent);
    }

    public void pickWord(){
        list = new ArrayList<String>();
        String text = "";
        try {
            InputStream inputStream = getAssets().open("dictionary.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
            String[] arr = text.split("\\r?\\n");
            for (int i=0;i<arr.length;i++){
                if (arr[i].length() == 9) {
                    list.add(arr[i]);
                }
            }
        }
        catch (IOException e) {
        }
    }

    private int getRandomNumber() {
        int randomInt = 0;
        randomInt = random.nextInt(list.size());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }

    public static String shuffleString(String string)
    {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        String shuffled = "";
        for (String letter : letters) {
            shuffled += letter;
        }
        return shuffled;
    }

    public void selectWord() {
        pickWord();
        randomWord = list.get(getRandomNumber());
        String newRandomWord = shuffleString(randomWord);

        StringBuffer randStr1 = new StringBuffer();
        StringBuffer randStr2 = new StringBuffer();
        StringBuffer randStr3 = new StringBuffer();
        StringBuffer randStr4 = new StringBuffer();
        StringBuffer randStr5 = new StringBuffer();
        StringBuffer randStr6 = new StringBuffer();
        StringBuffer randStr7 = new StringBuffer();
        StringBuffer randStr8 = new StringBuffer();
        StringBuffer randStr9 = new StringBuffer();

        char c1 = newRandomWord.charAt(0);
        randStr1.append(c1);
        L1.setText(randStr1.toString().toUpperCase());
        char c2 = newRandomWord.charAt(1);
        randStr2.append(c2);
        L2.setText(randStr2.toString().toUpperCase());
        char c3 = newRandomWord.charAt(2);
        randStr3.append(c3);
        L3.setText(randStr3.toString().toUpperCase());
        char c4 = newRandomWord.charAt(3);
        randStr4.append(c4);
        L4.setText(randStr4.toString().toUpperCase());
        char c5 = newRandomWord.charAt(4);
        randStr5.append(c5);
        L5.setText(randStr5.toString().toUpperCase());
        char c6 = newRandomWord.charAt(5);
        randStr6.append(c6);
        L6.setText(randStr6.toString().toUpperCase());
        char c7 = newRandomWord.charAt(6);
        randStr7.append(c7);
        L7.setText(randStr7.toString().toUpperCase());
        char c8 = newRandomWord.charAt(7);
        randStr8.append(c8);
        L8.setText(randStr8.toString().toUpperCase());
        char c9 = newRandomWord.charAt(8);
        randStr9.append(c9);
        L9.setText(randStr9.toString().toUpperCase());
        //testTXT.setText(randomWord);

        mp = MediaPlayer.create(this, R.raw.timer);
        mp.start();
        clock.setVisibility(View.VISIBLE);
        clock.start();
        CountDownTimer countDownTimer = new CountDownTimer(seconds *1000,250) {
            @Override
            public void onTick(long millis) {
                if (submit == 1) {
                    mp.stop();
                    clock.stopPlayback();
                }
                else if (millis / 250 == 119) {
                    clockIMG.setVisibility(View.INVISIBLE);
                }
                //timerTXT.setText("Time Remaining: " + (int)(millis /1000));
            }

            @Override
            public void onFinish() {
                //timerTXT.setText("Finished!");
                if (submit != 1) {
                    submitBtn.setVisibility(View.INVISIBLE);
                    submit = 0;
                    getResult();
                    finish();
                }
            }
        }.start();
    }
    public void getResult(){
        StringBuffer result = new StringBuffer();
        String R1 = E1.getText().toString();
        String R2 = E2.getText().toString();
        String R3 = E3.getText().toString();
        String R4 = E4.getText().toString();
        String R5 = E5.getText().toString();
        String R6 = E6.getText().toString();
        String R7 = E7.getText().toString();
        String R8 = E8.getText().toString();
        String R9 = E9.getText().toString();
        result.append(R1+R2+R3+R4+R5+R6+R7+R8+R9);
        String userR = result.toString().toUpperCase();
        String r = randomWord.toUpperCase();
        if (HomeActivity.CountMulti == 0) {
            if (userR.equals(r)) {
                HomeActivity.CountM += 1;
                resultScreen();
                finish();
            }
            else {
                HomeActivity.CountM += 1;
                LetterRound.CountF = 1;
                resultScreen();
                finish();
            }
        }
        else {
            if (userR.equals(r)) {
                HomeActivity.CountM += 1;
                resultScreenMulti();
                finish();
            }
            else {
                HomeActivity.CountM += 1;
                LetterRound.CountF = 1;
                resultScreenMulti();
                finish();
            }
        }
    }


    public void reset()
    {
        L1.setVisibility(TextView.VISIBLE);
        L2.setVisibility(TextView.VISIBLE);
        L3.setVisibility(TextView.VISIBLE);
        L4.setVisibility(TextView.VISIBLE);
        L5.setVisibility(TextView.VISIBLE);
        L6.setVisibility(TextView.VISIBLE);
        L7.setVisibility(TextView.VISIBLE);
        L8.setVisibility(TextView.VISIBLE);
        L9.setVisibility(TextView.VISIBLE);

        E1.setText("_");
        E2.setText("_");
        E3.setText("_");
        E4.setText("_");
        E5.setText("_");
        E6.setText("_");
        E7.setText("_");
        E8.setText("_");
        E9.setText("_");

        E1.setTag(null);
        E2.setTag(null);
        E3.setTag(null);
        E4.setTag(null);
        E5.setTag(null);
        E6.setTag(null);
        E7.setTag(null);
        E8.setTag(null);
        E9.setTag(null);

        E1.setTypeface(Typeface.DEFAULT);
        E2.setTypeface(Typeface.DEFAULT);
        E3.setTypeface(Typeface.DEFAULT);
        E4.setTypeface(Typeface.DEFAULT);
        E5.setTypeface(Typeface.DEFAULT);
        E6.setTypeface(Typeface.DEFAULT);
        E7.setTypeface(Typeface.DEFAULT);
        E8.setTypeface(Typeface.DEFAULT);
        E9.setTypeface(Typeface.DEFAULT);

        E1.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E2.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E3.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E4.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E5.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E6.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E7.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E8.setOnDragListener(new ConundrumRound.ChoiceDragListener());
        E9.setOnDragListener(new ConundrumRound.ChoiceDragListener());

    }
}
