package com.elchananalon.arkanoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class GameView extends View {
    private SoundPlayer sound;
    // states
    private enum State {GET_READY, PLAYING, GAME_OVER};
    // objects
    private Ball movingBall;
    private int canvasWidth;
    private int canvasHeight;
    private int countLives, countScore;

    private Paddle paddle;
    private BrickCollection bricks;
    private float fx, fy;       // for finger touch location
    private Paint penMsg , penInfo;  // messages on screen

    // current state
    private State state;


    public GameView(Context context,  AttributeSet attrs) {
        super(context, attrs);

    }
    private void initGame(){
        // init score and lives
        countLives = 3;
        countScore = 0;

        // brick breaking effect setup
        sound = new SoundPlayer(getContext());

        initCanvas();
        float padding = canvasWidth*((float) 5/1000);// 0.5% of screen width
        float col = BrickCollection.getCOLS();
        float row = BrickCollection.getROWS();
        float adjustHeight = (canvasHeight/3.0f);
        if(row > 6){
            adjustHeight = (canvasHeight/2.0f);
        }
        // init brick collection
        bricks = new BrickCollection((canvasWidth/col)-padding, (adjustHeight/row) - padding,padding/2,canvasHeight*(9.0f/100.0f)+padding, padding);


        // paint for info text
        penInfo = new Paint(Paint.ANTI_ALIAS_FLAG);
        penInfo.setColor(Color.BLACK);
        penInfo.setTextSize(canvasHeight/17.0f);//45
        penInfo.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));


        // paint for messages text
        penMsg = new Paint(Paint.ANTI_ALIAS_FLAG);
        penMsg.setTextAlign(Paint.Align.CENTER);
        penMsg.setColor(Color.WHITE);
        penMsg.setStyle(Paint.Style.STROKE);
        penMsg.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        penMsg.setTextSize(canvasHeight/13.0f);//55

    }

    private void initCanvas()
    {
        state = State.GET_READY;

        // Initialize paddle
        float paddleWidth = canvasWidth*(15.0f/100.0f);// 15%
        float paddleHeight = canvasHeight*(3.0f/100.0f);// 3%
        paddle = new Paddle(paddleWidth ,paddleHeight);
        paddle.setXPosition((canvasWidth / 2)-(paddle.getWidth())/2.0f);
        paddle.setYPosition(canvasHeight-45);

        /*randomize ball direction*/
        Random ran = new Random();
        int direction[]={1,-1};
        int lor = direction[ran.nextInt(2)];
        // initialize ball
        movingBall = new Ball(paddle.getXPosition()+paddle.getWidth()/2, paddle.getYPosition()-20, 20);
        movingBall.setDx(canvasWidth/(lor*(169)));//nexus 4 will get -7, but bigger screen will adjust
        movingBall.setDy(canvasHeight/153);//nexus 4 will get 5, but bigger screen will adjust

        paddle.setStartingSpeed(canvasWidth/118);

        System.out.println("width = " + canvasWidth + " height = " + canvasHeight);
        System.out.println("dx = " + movingBall.getDx() + " dy = " + movingBall.getDy());
        System.out.println("speed = "+ paddle.getStartingSpeed());
        System.out.println("radius = "+ movingBall.getRadius());
        System.out.println("(canvasWidth/canvasHeight)*(25/2) = " + canvasWidth/canvasHeight + " * " + 25/2 );

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        // Background init
        Drawable d = getResources().getDrawable(R.drawable.brick);
        //getDrawable(int drawable,Theme null) is better, does'nt match API 15
        d.setBounds(0, 0, canvasWidth, canvasHeight);
        d.draw(canvas);
        // draw objects on canvas
        movingBall.draw(canvas);
        paddle.draw(canvas);
        bricks.draw(canvas);
        penInfo.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Lives: " + countLives, canvasWidth-50, canvasHeight*(7.0f/100.0f), penInfo); //y axis 7%
        penInfo.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Score: " + countScore, 50, canvasHeight*(7.0f/100.0f), penInfo); //y axis 7%



        switch (state)
        {
            case GET_READY:
                canvas.drawText("Click to PLAY!", canvasWidth/2, canvasHeight/2, penMsg);
                break;

            case PLAYING:

                if(state == State.PLAYING)
                {
                    if(fx > canvasWidth/2 )
                        paddle.move(canvasWidth,canvasHeight,0);
                    else if(fx <= canvasWidth/2)
                        paddle.move(canvasWidth,canvasHeight,1);
                }

                // move the movingBall
                movingBall.move(canvasWidth, canvasHeight);
                // check bricks and paddle collision with the ball
                movingBall.collideWith(paddle);

                    for(int i =0 ; i<bricks.getBricks().size();i++){
                    if (movingBall.collideWith(bricks.getBricks().get(i))) {
                        bricks.remove(i);
                        sound.playSound();

                        countScore+=5*countLives;
                    }
                }

                // paddle misses the ball
                if(movingBall.getyPosition()-movingBall.getRadius() >= paddle.getYPosition()){
                    countLives--;
                    if(countLives == 0){
                        state = State.GAME_OVER;
                    }
                    else{// restart paddle and ball
                        initCanvas();
                    }
                }
                ///all bricks removed
                if(bricks.getBricks().size() == 0)
                    state = State.GAME_OVER;
                break;

            case GAME_OVER:
                sound.releaseSP();
                if(bricks.getBricks().size() == 0){//WINN
                    canvas.drawText("WELL DONE! - You Win ", canvasWidth/2, canvasHeight/2, penMsg);
                    canvas.drawText("Touch the screen to start new game", canvasWidth/2, canvasHeight/2 + penMsg.getTextSize()+5, penMsg);
                }
                else{//loss
                    canvas.drawText("GAME OVER - You Loss!", canvasWidth/2, canvasHeight/2, penMsg);
                }

                break;
        }
        invalidate();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        fx = event.getX();
        fy = event.getY();


        if (event.getAction() == MotionEvent.ACTION_DOWN){
                if(state == State.GET_READY)
                    state = State.PLAYING;
                else
                {
                    if(state == State.PLAYING)
                    {
                        paddle.setSpeed(paddle.getStartingSpeed());
                    }
                    else
                    {
                        initGame();
                    }
                }
        }
         if ( event.getAction() == MotionEvent.ACTION_UP) {
            paddle.setSpeed(0);
            }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        canvasWidth = w;
        canvasHeight = h;

        initGame();
    }
}
