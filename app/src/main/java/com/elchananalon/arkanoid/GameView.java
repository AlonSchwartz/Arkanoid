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

public class GameView extends View {
    //private MediaPlayer mediaPlayer;
    private SoundPlayer sound;
    // states
    private enum State {GET_READY, PLAYING, GAME_OVER}
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
        // brick breaking effect setup
        sound = new SoundPlayer(getContext());

    }
    private void initGame(){
        // init score and lives
        countLives = 3;
        countScore = 0;

        initCanvas();
        float padding = canvasWidth*((float) 5/1000);// 0.5% of screen width
        float col = BrickCollection.getCOLS();
        float row = BrickCollection.getROWS();
        // init brick collection
        bricks = new BrickCollection((canvasWidth/col)-padding, ((canvasHeight/(float)2)/row) - padding,padding/2,canvasWidth/12, padding);


        // paint for info text
        penInfo = new Paint(Paint.ANTI_ALIAS_FLAG);
        penInfo.setColor(Color.YELLOW);
        penInfo.setTextSize(55);

        // paint for messages text
        penMsg = new Paint(Paint.ANTI_ALIAS_FLAG);
        penMsg.setTextAlign(Paint.Align.CENTER);
        penMsg.setColor(Color.GREEN);
        penMsg.setStyle(Paint.Style.STROKE);
        penMsg.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        penMsg.setTextSize(55);

    }

    private void initCanvas()
    {
        state = State.GET_READY;
        // initialize ball
        movingBall = new Ball(canvasWidth /2, canvasHeight-85, 50);
        movingBall.setDx(1); // was -7
        movingBall.setDy(1); // was 5
        // Initialize paddle
        paddle = new Paddle(180 ,20);
        paddle.setXPosition((canvasWidth / 2.0f)-(paddle.getWidth())/2);
        paddle.setYPosition(canvasHeight-45);

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Drawable d = getResources().getDrawable(R.drawable.brick);
        //getDrawable(int drawable,Theme null) is better, does'nt match API 15
        d.setBounds(0, 0, canvasWidth, canvasHeight);
        d.draw(canvas);
        // draw objects on canvas
        movingBall.draw(canvas);
        paddle.draw(canvas);
        bricks.draw(canvas);
        penInfo.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Lives: " + countLives, canvasWidth-50, 100, penInfo);
        penInfo.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Score: " + countScore, 50, 100, penInfo);


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
                if (movingBall.collideWith(paddle)){
                   // movingBall.setDy(-(movingBall.getDy()));
                   // movingBall.setDx(-(movingBall.getDx()));

                    // just from checking...
                    movingBall.setDy(0);
                    movingBall.setDx(0);

                }

                    for(int i =0 ; i<bricks.getBricks().size();i++){
                    if (movingBall.collideWith(bricks.getBricks().get(i))) {
                        // the following logic should be adjusted
                       // movingBall.setDx(-movingBall.getDx());
                        movingBall.setDy(-movingBall.getDy());
                        bricks.remove(i);
                        sound.playSound();

                        countScore+=5*countLives;
                    }
                }

                // paddle misses the ball
                if(movingBall.getyPosition() > paddle.getYPosition() && !movingBall.collideWith(paddle)){
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
