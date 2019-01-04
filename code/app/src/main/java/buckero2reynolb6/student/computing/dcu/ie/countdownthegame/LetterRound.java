package buckero2reynolb6.student.computing.dcu.ie.countdownthegame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class LetterRound extends AppCompatActivity {

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    View rootLayout;
    private int revealX;
    private int revealY;

    public static int CountF = 0;
    public static String answerL = "";
    Random random = new Random();
    private Button consonantBtn;
    private Button vowelBtn;
    private ImageButton resetBtn;
    private ImageButton submitBtn;
    private String word = "";
    private int submit = 0;
    private int seconds = 31;
    private static final String Consonant = "BCDFGHJKLMNPQRSTVWXYZ";
    private static final String vowel = "AEIOU";
    private static final int Random_Word = 9;
    private int Count = 0;
    private MediaPlayer mp;
    private int stopPosition;
    TextView E1,E2,E3,E4,E5,E6,E7,E8,E9;
    TextView L1,L2,L3,L4,L5,L6,L7,L8,L9;
    TextView resultTXT,timerTXT;
    MediaPlayer theme;
    VideoView clock;
    ImageView clockIMG;

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
        setContentView(R.layout.activity_letters_round);


        final Intent intent = getIntent();

        rootLayout = findViewById(R.id.root_layout);if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);

            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else{
            rootLayout.setVisibility(View.VISIBLE);
        }

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
        resultTXT = (TextView) findViewById(R.id.resultTXT);

        L1.setOnTouchListener(new ChoiceTouchListener());
        L2.setOnTouchListener(new ChoiceTouchListener());
        L3.setOnTouchListener(new ChoiceTouchListener());
        L4.setOnTouchListener(new ChoiceTouchListener());
        L5.setOnTouchListener(new ChoiceTouchListener());
        L6.setOnTouchListener(new ChoiceTouchListener());
        L7.setOnTouchListener(new ChoiceTouchListener());
        L8.setOnTouchListener(new ChoiceTouchListener());
        L9.setOnTouchListener(new ChoiceTouchListener());

        E1.setOnDragListener(new ChoiceDragListener());
        E2.setOnDragListener(new ChoiceDragListener());
        E3.setOnDragListener(new ChoiceDragListener());
        E4.setOnDragListener(new ChoiceDragListener());
        E5.setOnDragListener(new ChoiceDragListener());
        E6.setOnDragListener(new ChoiceDragListener());
        E7.setOnDragListener(new ChoiceDragListener());
        E8.setOnDragListener(new ChoiceDragListener());
        E9.setOnDragListener(new ChoiceDragListener());

        consonantBtn = (Button) findViewById(R.id.consonantBtn);
        consonantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickConsonant();
            }
        });

        vowelBtn = (Button) findViewById(R.id.vowelBtn);
        vowelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickvowel();
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



    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    rootLayout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });


            circularReveal.start();
        }
    }

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

    public void pickConsonant() {
        if (Count == 0){
            L1.setText(getRandomConsonant());
        }
        else if (Count == 1){
            L2.setText(getRandomConsonant());
        }
        else if (Count == 2){
            L3.setText(getRandomConsonant());
        }
        else if (Count == 3){
            L4.setText(getRandomConsonant());
        }
        else if (Count == 4){
            L5.setText(getRandomConsonant());
        }
        else if (Count == 5){
            L6.setText(getRandomConsonant());
        }
        else if (Count == 6){
            L7.setText(getRandomConsonant());
        }
        else if (Count == 7){
            L8.setText(getRandomConsonant());
        }
        else if (Count == 8){
            L9.setText(getRandomConsonant());
            vowelBtn.setVisibility(View.GONE);
            consonantBtn.setVisibility(View.GONE);
            submitBtn.setVisibility(View.VISIBLE);
            resetBtn.setVisibility(View.VISIBLE);

            mp = MediaPlayer.create(this,R.raw.timer);
            mp.start();
            clock.setVisibility(View.VISIBLE);
            clock.start();
            CountDownTimer countDownTimer = new CountDownTimer(seconds *1000,250) {
                @Override
                public void onTick(long millis) {
                    if (submit == 1) {
                        mp.stop();
                        //mp.release();
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
                        getResult();
                        finish();
                    }
                }
            }.start();
        }
    }
    public String getRandomConsonant(){
        StringBuffer randStr = new StringBuffer();
        if (Count < Random_Word) {
            int number = getRandomNumberC();
            char ch = Consonant.charAt(number);
            randStr.append(ch);
            Count += 1;
        }
        return randStr.toString();
    }

    private int getRandomNumberC() {
        int randomInt = 0;
        randomInt = random.nextInt(Consonant.length());
        return randomInt;
    }


    public void pickvowel() {
        if (Count == 0){
            L1.setText(getRandomvowel());
        }
        else if (Count == 1){
            L2.setText(getRandomvowel());
        }
        else if (Count == 2){
            L3.setText(getRandomvowel());
        }
        else if (Count == 3){
            L4.setText(getRandomvowel());
        }
        else if (Count == 4){
            L5.setText(getRandomvowel());
        }
        else if (Count == 5){
            L6.setText(getRandomvowel());
        }
        else if (Count == 6){
            L7.setText(getRandomvowel());
        }
        else if (Count == 7){
            L8.setText(getRandomvowel());
        }
        else if (Count == 8){
            L9.setText(getRandomvowel());
            vowelBtn.setVisibility(View.GONE);
            consonantBtn.setVisibility(View.GONE);
            resetBtn.setVisibility(View.VISIBLE);
            submitBtn.setVisibility(View.VISIBLE);

            mp = MediaPlayer.create(this,R.raw.timer);
            mp.start();
            clock.setVisibility(View.VISIBLE);
            clock.start();
            CountDownTimer countDownTimer = new CountDownTimer(seconds *1000,250) {
                @Override
                public void onTick(long millis) {
                    if (submit == 1) {
                        mp.stop();
                        //mp.release();
                        clock.stopPlayback();
                    }
                    else if (millis / 250 == 119) {
                        clockIMG.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onFinish() {
                    if (submit != 1) {
                        submitBtn.setVisibility(View.INVISIBLE);
                        getResult();
                        finish();
                    }
                }
            }.start();
        }
    }
    public String getRandomvowel(){
        StringBuffer randStr = new StringBuffer();
        if (Count < Random_Word) {
            int number = getRandomNumberV();
            char ch = vowel.charAt(number);
            randStr.append(ch);
            Count += 1;
        }
        return randStr.toString();
    }

    private int getRandomNumberV() {
        int randomInt;
        randomInt = random.nextInt(vowel.length());
        return randomInt;
    }


    public boolean  wordCheck(String word) {

        String text;
        try {
            InputStream inputStream = getAssets().open("dictionary.txt");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);
            String[] arr = text.split("\\r?\\n");
            for (int i = 0; i < arr.length;i++)
                if (arr[i].equals(word)) {
                    return true;
                }
        }
        catch (IOException e) {
        }
        return false;
    }

    public void resultScreen() {
        Intent intent = new Intent(this, ResultScreen.class);
        startActivity(intent);
    }
    public void resultScreenMulti() {
        Intent intent = new Intent(this, ResultScreenMultiplayer.class);
        startActivity(intent);
    }

    public void getResult()
    {
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
        String r = result.toString();
        answerL = r.replaceAll("_","");
        if (HomeActivity.CountMulti == 0){
            if (wordCheck(answerL.toLowerCase())) {
                HomeActivity.CountM += 1;
                resultScreen();
                finish();
            }
            else {
                HomeActivity.CountM += 1;
                CountF = 2;
                resultScreen();
                finish();
            }
        }
        else {
            if (wordCheck(answerL.toLowerCase())) {
                HomeActivity.CountM += 1;
                resultScreenMulti();
                finish();
            }
            else {
                HomeActivity.CountM += 1;
                CountF = 2;
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

        E1.setOnDragListener(new ChoiceDragListener());
        E2.setOnDragListener(new ChoiceDragListener());
        E3.setOnDragListener(new ChoiceDragListener());
        E4.setOnDragListener(new ChoiceDragListener());
        E5.setOnDragListener(new ChoiceDragListener());
        E6.setOnDragListener(new ChoiceDragListener());
        E7.setOnDragListener(new ChoiceDragListener());
        E8.setOnDragListener(new ChoiceDragListener());
        E9.setOnDragListener(new ChoiceDragListener());

    }



}
