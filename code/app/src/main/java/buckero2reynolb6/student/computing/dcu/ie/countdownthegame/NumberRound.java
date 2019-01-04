package buckero2reynolb6.student.computing.dcu.ie.countdownthegame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NumberRound extends AppCompatActivity {

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    View rootLayout;

    private int revealX;
    private int revealY;


    Random random = new Random();
    public static int answer = 0;
    public static int numberScore = 0;
    private int randNum = 0;
    private int submit = 0;
    private int countE = 0;
    private static String strNum = "0123456789";
    private static String strOpr = "+-*/";
    public static int resultNum = 0;
    private Button ScoreAndLeaderboards;
    private Button largeNumBtn;
    private Button smallNumBtn;
    private ImageButton resetBtn;
    private ImageButton startBtn, submitBtn;
    private TextView plusBtn, minusBtn, multiplyBtn, divideBtn;
    private static String exp = null;
    private int Num1 = 0;
    private int Count = 0;
    private int CountO = 0;
    private double result = 0;
    private String[] randOpr = {"+","-","*","/"};
    private List<Integer> GetNumbers = new ArrayList<Integer>(6);
    private List<Integer> LargeNumbers = new ArrayList<Integer>(4);
    private List<Integer> SmallNumbers = new ArrayList<Integer>(20);
    TextView N1, N2, N3, N4, N5, N6;
    TextView E1, E2, E3, E4, E5, E6;
    TextView O1, O2, O3, O4, O5;
    Button equalsOne, equalsTwo, equalsThree, equalsFour, equalsFive;
    TextView Result2, Result3, Result4, Result5, Result6;
    TextView resultTXT, timerTXT, resultOne, resultTwo, resultThree,resultFour;


    private MediaPlayer mp;
    VideoView clock;
    ImageView clockIMG;
    private int stopPosition;

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
        //if (mp != null) {
        //    mp.start();
        //    clock.seekTo(stopPosition);
        //    clock.start();
        //}
        super.onResume();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_round);

        final Intent intent = getIntent();

        rootLayout = findViewById(R.id.root_layout);

        clock = (VideoView) findViewById(R.id.clock);
        clockIMG = (ImageView) findViewById(R.id.clockIMG);

        clock.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.clock);

        clock.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
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

        LargeNumbers.add(25);
        LargeNumbers.add(50);
        LargeNumbers.add(75);
        LargeNumbers.add(100);

        SmallNumbers.add(1);
        SmallNumbers.add(1);
        SmallNumbers.add(2);
        SmallNumbers.add(2);
        SmallNumbers.add(3);
        SmallNumbers.add(3);
        SmallNumbers.add(4);
        SmallNumbers.add(4);
        SmallNumbers.add(5);
        SmallNumbers.add(5);
        SmallNumbers.add(6);
        SmallNumbers.add(6);
        SmallNumbers.add(7);
        SmallNumbers.add(7);
        SmallNumbers.add(8);
        SmallNumbers.add(8);
        SmallNumbers.add(9);
        SmallNumbers.add(9);
        SmallNumbers.add(10);
        SmallNumbers.add(10);


        // Numbers
        N1 = (TextView) findViewById(R.id.Number1);
        N2 = (TextView) findViewById(R.id.Number2);
        N3 = (TextView) findViewById(R.id.Number3);
        N4 = (TextView) findViewById(R.id.Number4);
        N5 = (TextView) findViewById(R.id.Number5);
        N6 = (TextView) findViewById(R.id.Number6);
        // Empty Number Slots
        E1 = (TextView) findViewById(R.id.Empty1);
        E2 = (TextView) findViewById(R.id.Empty2);
        E3 = (TextView) findViewById(R.id.Empty3);
        E4 = (TextView) findViewById(R.id.Empty4);
        E5 = (TextView) findViewById(R.id.Empty5);
        E6 = (TextView) findViewById(R.id.Empty6);
        // Operands
        O1 = (TextView) findViewById(R.id.Operand1);
        O2 = (TextView) findViewById(R.id.Operand2);
        O3 = (TextView) findViewById(R.id.Operand3);
        O4 = (TextView) findViewById(R.id.Operand4);
        O5 = (TextView) findViewById(R.id.Operand5);
        // Results
        resultOne = (TextView) findViewById(R.id.resultOne);
        resultTwo = (TextView) findViewById(R.id.resultTwo);
        resultThree = (TextView) findViewById(R.id.resultThree);
        resultFour = (TextView) findViewById(R.id.resultFour);
        Result2 = (TextView) findViewById(R.id.Result2);
        Result3 = (TextView) findViewById(R.id.Result3);
        Result4 = (TextView) findViewById(R.id.Result4);
        Result5 = (TextView) findViewById(R.id.Result5);
        Result6 = (TextView) findViewById(R.id.Result6);

        Result2.setOnTouchListener(new ChoiceTouchListener());
        Result3.setOnTouchListener(new ChoiceTouchListener());
        Result4.setOnTouchListener(new ChoiceTouchListener());
        Result5.setOnTouchListener(new ChoiceTouchListener());
        Result6.setOnTouchListener(new ChoiceTouchListener());

        N1.setOnTouchListener(new ChoiceTouchListener());
        N2.setOnTouchListener(new ChoiceTouchListener());
        N3.setOnTouchListener(new ChoiceTouchListener());
        N4.setOnTouchListener(new ChoiceTouchListener());
        N5.setOnTouchListener(new ChoiceTouchListener());
        N6.setOnTouchListener(new ChoiceTouchListener());

        resultOne.setOnDragListener(new ChoiceDragListener());
        resultTwo.setOnDragListener(new ChoiceDragListener());
        resultThree.setOnDragListener(new ChoiceDragListener());
        resultFour.setOnDragListener(new ChoiceDragListener());

        E1.setOnDragListener(new ChoiceDragListener());
        E2.setOnDragListener(new ChoiceDragListener());
        E3.setOnDragListener(new ChoiceDragListener());
        E4.setOnDragListener(new ChoiceDragListener());
        E5.setOnDragListener(new ChoiceDragListener());
        E6.setOnDragListener(new ChoiceDragListener());

        O1.setOnDragListener(new ChoiceDragListenerO());
        O2.setOnDragListener(new ChoiceDragListenerO());
        O3.setOnDragListener(new ChoiceDragListenerO());
        O4.setOnDragListener(new ChoiceDragListenerO());
        O5.setOnDragListener(new ChoiceDragListenerO());

        resultTXT = (TextView) findViewById(R.id.resultTXT);
        //timerTXT = (TextView) findViewById(R.id.timerTXT);

        plusBtn = (TextView) findViewById(R.id.plusBtn);
        plusBtn.setOnTouchListener(new ChoiceTouchListener());

        minusBtn = (TextView) findViewById(R.id.minusBtn);
        minusBtn.setOnTouchListener(new ChoiceTouchListener());


        multiplyBtn = (TextView) findViewById(R.id.multiplyBtn);
        multiplyBtn.setOnTouchListener(new ChoiceTouchListener());


        divideBtn = (TextView) findViewById(R.id.divideBtn);
        divideBtn.setOnTouchListener(new ChoiceTouchListener());


        largeNumBtn = (Button) findViewById(R.id.largeNumBtn);
        largeNumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickLargeNum();
            }
        });

        smallNumBtn = (Button) findViewById(R.id.smallNumBtn);
        smallNumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickSmallNum();
            }
        });

        equalsOne = (Button) findViewById(R.id.equalsOne);
        equalsOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate();
                equalsOne.setVisibility(View.INVISIBLE);
            }
        });

        equalsTwo = (Button) findViewById(R.id.equalsTwo);
        equalsTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate();
                equalsTwo.setVisibility(View.INVISIBLE);
            }
        });

        equalsThree = (Button) findViewById(R.id.equalsThree);
        equalsThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate();
                equalsThree.setVisibility(View.INVISIBLE);
            }
        });

        equalsFour = (Button) findViewById(R.id.equalsFour);
        equalsFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate();
                equalsFour.setVisibility(View.INVISIBLE);
            }
        });

        equalsFive = (Button) findViewById(R.id.equalsFive);
        equalsFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluate();
                equalsFive.setVisibility(View.INVISIBLE);
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

        startBtn = (ImageButton) findViewById(R.id.startBtn);
        startBtn.setVisibility(View.INVISIBLE);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRound();
                resetBtn.setVisibility(View.VISIBLE);
                submitBtn.setVisibility(View.VISIBLE);
                startBtn.setVisibility(View.GONE);
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

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
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
                    if (tag != null) {
                        //the tag is the view id already dropped here
                        int existingID = (Integer) tag;
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

    /**
     * DragListener will handle dragged views being dropped on the drop area
     * - only the drop action will have processing added to it as we are not
     * - amending the default behavior for other parts of the drag process
     *
     */
    @SuppressLint("NewApi")
    private class ChoiceDragListenerO implements View.OnDragListener {

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
                    view.setVisibility(View.VISIBLE);
                    //update the text in the target view to reflect the data being dropped
                    dropTarget.setText(dropped.getText().toString());
                    //make it bold to highlight the fact that an item has been dropped
                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    //if an item has already been dropped here, there will be a tag
                    Object tag = dropTarget.getTag();
                    //if there is already an item here, set it back visible in its original place
                    if (tag != null) {
                        //the tag is the view id already dropped here
                        int existingID = (Integer) tag;
                        //set the original view visible again
                        findViewById(existingID).setVisibility(View.VISIBLE);
                    }
                    //set the tag in the target view being dropped on - to the ID of the view being dropped
                    dropTarget.setTag(dropped.getId());
                    //remove setOnDragListener by setting OnDragListener to null, so that no further drag & dropping on this TextView can be done
                    //dropTarget.setOnDragListener(null);

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


    public int randInt(int min, int max) {
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = random.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void startRound() {
        randNum = randInt(100,999);
        resultTXT.setText("" + randNum);



        mp = MediaPlayer.create(this, R.raw.timer);
        mp.start();
        clock.setVisibility(View.VISIBLE);
        clock.start();
        int seconds = 31;
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
                    getResult();
                    finish();
                }
            }
        }.start();

        //for (int k = 0; k < GetNumbers.size(); k++) {
         //   int j = 0;
           // while (j < (GetNumbers.size()) - 1) {
             //   String operator = getRandomO();
               // if (exp == null) {
                 //   exp = GetNumbers.get(j) + operator + GetNumbers.get(++j);
                //} else {
                  //  exp = exp + operator + GetNumbers.get(++j);
                //}
                //result = eval(exp);
                //resultTXT.setText("" + result);
            //}
        //}
    }

    public void openScoreAndLeaderboard(){
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }
    public void evaluate() {
        StringBuffer result = new StringBuffer();
        String ResultE1 = resultOne.getText().toString();
        String ResultE2 = resultTwo.getText().toString();
        String ResultE3 = resultThree.getText().toString();
        String ResultE4 = resultFour.getText().toString();
        String R1 = E1.getText().toString();
        String R3 = E2.getText().toString();
        String R5 = E3.getText().toString();
        String R7 = E4.getText().toString();
        String R9 = E5.getText().toString();
        String R11 = E6.getText().toString();
        String R2 = O1.getText().toString();
        String R4 = O2.getText().toString();
        String R6 = O3.getText().toString();
        String R8 = O4.getText().toString();
        String R10 = O5.getText().toString();
        Character R1c = R1.charAt(0);
        Character R3c = R3.charAt(0);
        Character E1c = ResultE1.charAt(0);
        Character R5c = R5.charAt(0);
        Character E2c = ResultE2.charAt(0);
        Character R7c = R7.charAt(0);
        Character E3c = ResultE3.charAt(0);
        Character R9c = R9.charAt(0);
        Character E4c = ResultE4.charAt(0);
        Character R11c = R11.charAt(0);

        if (countE == 0) {
            if (strNum.contains(R1c.toString()) & strNum.contains(R3c.toString()) & strOpr.contains(R2)) {
                result.append(R1+R2+R3);
                String r = result.toString();
                double equals = eval(r);
                answer = (int) Math.round(equals);
                Result2.setText("" + answer);
                countE++;
            }
            else {
                Toast.makeText(this, String.format("Error, expression incomplete. Please reset."), Toast.LENGTH_SHORT).show();
            }
        }
        else if (countE == 1) {
            if (strNum.contains(E1c.toString()) & strNum.contains(R5c.toString()) & strOpr.contains(R4)) {
                result.append(ResultE1+R4+R5);
                String r = result.toString();
                double equals = eval(r);
                answer = (int) Math.round(equals);
                Result3.setText("" + answer);
                countE++;
            }
            else {
                Toast.makeText(this, String.format("Error, expression incomplete. Please reset."), Toast.LENGTH_SHORT).show();
            }

        }
        else if (countE == 2) {
            if (strNum.contains(E2c.toString()) & strNum.contains(R7c.toString()) & strOpr.contains(R6)) {
                result.append(ResultE2+R6+R7);
                String r = result.toString();
                double equals = eval(r);
                answer = (int) Math.round(equals);
                Result4.setText("" + answer);
                countE++;
            }
            else {
                Toast.makeText(this, String.format("Error, expression incomplete. Please reset."), Toast.LENGTH_SHORT).show();
            }
        }
        else if (countE == 3) {
            if (strNum.contains(E3c.toString()) & strNum.contains(R9c.toString()) & strOpr.contains(R8)) {
                result.append(ResultE3+R8+R9);
                String r = result.toString();
                double equals = eval(r);
                answer = (int) Math.round(equals);
                Result5.setText("" + answer);
                countE++;
            }
            else {
                Toast.makeText(this, String.format("Error, expression incomplete. Please reset."), Toast.LENGTH_SHORT).show();
            }
        }
        else if (countE == 4) {
            if (strNum.contains(E4c.toString()) & strNum.contains(R11c.toString()) & strOpr.contains(R10)) {
                result.append(ResultE4+R10+R11);
                String r = result.toString();
                double equals = eval(r);
                answer = (int) Math.round(equals);
                Result6.setText("" + answer);
            }
            else {
                Toast.makeText(this, String.format("Error, expression incomplete. Please reset."), Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void getResult()
    {
        if (HomeActivity.CountMulti == 0) {
            resultNum = randNum - answer;
            if (resultNum == 0 & answer != 0) {
                numberScore = 10;
            }
            else if (resultNum < 5 & resultNum > -5 & answer != 0) {
                numberScore = 7;
            }
            else if (resultNum < 10 & resultNum > -10 & answer != 0){
                numberScore = 5;
            }
            else {
                numberScore = 0;
            }
            HomeActivity.CountM += 1;
            resultScreen();
            finish();
        }
        else {
            resultNum = randNum - answer;
            if (resultNum == 0 & answer != 0 ) {
                numberScore = 10;
            }
            else if (resultNum < 5 & resultNum > -5 & answer != 0) {
                numberScore = 7;
            }
            else if (resultNum < 10 & resultNum > -10 & answer != 0){
                numberScore = 5;
            }
            else {
                numberScore = 0;
            }
            HomeActivity.CountM += 1;
            resultScreenMulti();
            finish();
        }

    }

    public void resultScreenMulti() {
        Intent intent = new Intent(this, ResultScreenMultiplayer.class);
        startActivity(intent);
    }

    public void resultScreen() {
        Intent intent = new Intent(this, ResultScreen.class);
        startActivity(intent);
    }

    public void pickLargeNum() {
        if (Count == 0) {
            N1.setText("" + getLargeNum());
        } else if (Count == 1) {
            N2.setText("" + getLargeNum());
        } else if (Count == 2) {
            N3.setText("" + getLargeNum());
        } else if (Count == 3) {
            N4.setText("" + getLargeNum());
        } else if (Count == 4) {
            N5.setText("" + getLargeNum());
        } else if (Count == 5) {
            N6.setText("" + getLargeNum());
            largeNumBtn.setVisibility(View.GONE);
            smallNumBtn.setVisibility(View.GONE);
            startBtn.setVisibility(View.VISIBLE);

        }
    }

    private int getLargeNum() {
        if (LargeNumbers.size() != 1) {
            int num = getRandomNumberL();
            int numL = LargeNumbers.get(num);
            LargeNumbers.remove(num);
            Count++;
            return numL;
        }
        else {
            int num = getRandomNumberL();
            int numL = LargeNumbers.get(num);
            Count++;
            largeNumBtn.setVisibility(View.GONE);
            return numL;
        }
    }


    private int getRandomNumberL() {
        int randomInt = 0;
        randomInt = random.nextInt(LargeNumbers.size());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }


    public void pickSmallNum() {
        if (Count == 0) {
            N1.setText("" + getSmallNum());
        } else if (Count == 1) {
            N2.setText("" + getSmallNum());
        } else if (Count == 2) {
            N3.setText("" + getSmallNum());
        } else if (Count == 3) {
            N4.setText("" + getSmallNum());
        } else if (Count == 4) {
            N5.setText("" + getSmallNum());
        } else if (Count == 5) {
            N6.setText("" + getSmallNum());
            largeNumBtn.setVisibility(View.GONE);
            smallNumBtn.setVisibility(View.GONE);
            startBtn.setVisibility(View.VISIBLE);

        }
    }

    private int getSmallNum(){
        if(Count < 6); {
            int num =  getRandomNumberS();
            int numL = SmallNumbers.get(num);
            SmallNumbers.remove(num);
            Count ++;
            return numL;
        }

    }

    private int getRandomNumberS() {
        int randomInt = 0;
        randomInt = random.nextInt(SmallNumbers.size());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }

    public void reset()
    {
        N1.setVisibility(TextView.VISIBLE);
        N2.setVisibility(TextView.VISIBLE);
        N3.setVisibility(TextView.VISIBLE);
        N4.setVisibility(TextView.VISIBLE);
        N5.setVisibility(TextView.VISIBLE);
        N6.setVisibility(TextView.VISIBLE);

        Result2.setVisibility(TextView.VISIBLE);
        Result3.setVisibility(TextView.VISIBLE);
        Result4.setVisibility(TextView.VISIBLE);
        Result5.setVisibility(TextView.VISIBLE);
        Result6.setVisibility(TextView.VISIBLE);

        Result2.setText("_");
        Result3.setText("_");
        Result4.setText("_");
        Result5.setText("_");
        Result6.setText("_");

        plusBtn.setVisibility(TextView.VISIBLE);
        minusBtn.setVisibility(TextView.VISIBLE);
        multiplyBtn.setVisibility(TextView.VISIBLE);
        divideBtn.setVisibility(TextView.VISIBLE);

        equalsOne.setVisibility(View.VISIBLE);
        equalsTwo.setVisibility(View.VISIBLE);
        equalsThree.setVisibility(View.VISIBLE);
        equalsFour.setVisibility(View.VISIBLE);
        equalsFive.setVisibility(View.VISIBLE);

        CountO = 0;
        E1.setText("_");
        E2.setText("_");
        E3.setText("_");
        E4.setText("_");
        E5.setText("_");
        E6.setText("_");

        resultOne.setText("_");
        resultTwo.setText("_");
        resultThree.setText("_");
        resultFour.setText("_");

        O1.setText("-");
        O2.setText("-");
        O3.setText("-");
        O4.setText("-");
        O5.setText("-");

        countE = 0;
        E1.setTag(null);
        E2.setTag(null);
        E3.setTag(null);
        E4.setTag(null);
        E5.setTag(null);
        E6.setTag(null);

        resultOne.setTag(null);
        resultTwo.setTag(null);
        resultThree.setTag(null);
        resultFour.setTag(null);

        O1.setTag(null);
        O2.setTag(null);
        O3.setTag(null);
        O4.setTag(null);
        O5.setTag(null);

        E1.setTypeface(Typeface.DEFAULT);
        E2.setTypeface(Typeface.DEFAULT);
        E3.setTypeface(Typeface.DEFAULT);
        E4.setTypeface(Typeface.DEFAULT);
        E5.setTypeface(Typeface.DEFAULT);
        E6.setTypeface(Typeface.DEFAULT);

        O1.setTypeface(Typeface.DEFAULT);
        O2.setTypeface(Typeface.DEFAULT);
        O3.setTypeface(Typeface.DEFAULT);
        O4.setTypeface(Typeface.DEFAULT);
        O5.setTypeface(Typeface.DEFAULT);

        resultOne.setTypeface(Typeface.DEFAULT);
        resultTwo.setTypeface(Typeface.DEFAULT);
        resultThree.setTypeface(Typeface.DEFAULT);
        resultFour.setTypeface(Typeface.DEFAULT);

        E1.setOnDragListener(new ChoiceDragListener());
        E2.setOnDragListener(new ChoiceDragListener());
        E3.setOnDragListener(new ChoiceDragListener());
        E4.setOnDragListener(new ChoiceDragListener());
        E5.setOnDragListener(new ChoiceDragListener());
        E6.setOnDragListener(new ChoiceDragListener());

        resultOne.setOnDragListener(new ChoiceDragListener());
        resultTwo.setOnDragListener(new ChoiceDragListener());
        resultThree.setOnDragListener(new ChoiceDragListener());
        resultFour.setOnDragListener(new ChoiceDragListener());

        O1.setOnDragListener(new ChoiceDragListenerO());
        O2.setOnDragListener(new ChoiceDragListenerO());
        O3.setOnDragListener(new ChoiceDragListenerO());
        O4.setOnDragListener(new ChoiceDragListenerO());
        O5.setOnDragListener(new ChoiceDragListenerO());

    }


}
