package com.elchananalon.arkanoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    // states
    private enum State {GET_READY, PLAYING, GAME_OVER};
    // objects
    private Ball movingBall;
    private Paddle paddle = new Paddle(180 ,40);
    private Brick brick = new Brick(180,50,20,50, Color.RED);
    private Brick brick2 = new Brick(180,50,410,50, Color.RED);
    private Brick brick3 = new Brick(180,50,20,110, Color.RED);
    private BrickCollection bricks = new BrickCollection(180,50,20,50, 20, Color.RED);

    private float fx, fy;       // for finger touch location
    private int canvasWidth;
    private int canvasHeight;
    private Paint penMsg;

    // current state
    private State state;

    public GameView(Context context,  AttributeSet attrs) {
        super(context, attrs);

    }
    private void initGame(){
        state = State.GET_READY;
        // initialize ball
        movingBall = new Ball(canvasWidth / 2.0f, canvasHeight-80, 50);
        movingBall.setDx(-7);
        movingBall.setDy(5);
        // Initialize paddle
        paddle = new Paddle(180 ,40);
        paddle.setXPosition(canvasWidth / 2.0f);
        paddle.setYPosition(canvasHeight-45);

        // paint for messages text
        penMsg = new Paint(Paint.ANTI_ALIAS_FLAG);
        penMsg.setTextAlign(Paint.Align.CENTER);
        penMsg.setColor(Color.GREEN);
        penMsg.setStyle(Paint.Style.STROKE);
        penMsg.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        penMsg.setTextSize(55);

    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        movingBall.draw(canvas);

        paddle.draw(canvas);
        //brick.draw(canvas);
        //brick2.draw(canvas);
        // brick3.draw(canvas);
        //paddle.move(1180,768,1);
        bricks.draw(canvas);

        switch (state)
        {
            case GET_READY:
                canvas.drawText("Click to PLAY!", canvasWidth/2, canvasHeight/2, penMsg);
                break;

            case PLAYING:

                // move the movingBall
                movingBall.move(canvasWidth, canvasHeight);

                // check collision
                movingBall.collideWith(paddle);
                if (movingBall.collideWith(brick2))
                    System.out.println("HIT!");
                break;

            case GAME_OVER:
                canvas.drawText("GAME OVER - You Loss!", canvasWidth/2, canvasHeight/2, penMsg);
                break;
        }
        invalidate();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        fx = event.getX();
        fy = event.getY();

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:

                if(state == State.GET_READY)
                    state = State.PLAYING;
                else
                {
                    if(state == State.PLAYING)
                    {

                    }
                    else
                    {
                        initGame();
                    }
                }

                break;

            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_UP:
                if(fx > canvasWidth/2 )
                    paddle.move(canvasWidth,canvasHeight,0);
                else if(fx <= canvasWidth/2)
                    paddle.move(canvasWidth,canvasHeight,1);
                break;
        }

        invalidate();

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