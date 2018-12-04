package com.elchananalon.arkanoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
    private Ball movingBall;
    private int canvasWidth;
    private int canvasHeight;
    private Paint pen;

    private Paddle paddle = new Paddle(180 ,40);
    private Brick brick = new Brick(180,50,20,50, Color.RED);
    private Brick brick2 = new Brick(180,50,410,50, Color.RED);
    private Brick brick3 = new Brick(180,50,180+20+10,110, Color.RED);
    private Brick brick4 = new Brick(180,50,210+180+10,110, Color.RED);

    private BrickCollection bricks = new BrickCollection(180,50,20,50, 10, Color.RED);


    public GameView(Context context,  AttributeSet attrs) {
        super(context, attrs);

    }
    private void initGame(){
        movingBall = new Ball(canvasWidth / 2.0f, canvasHeight / 2.0f, 50);
        movingBall.setDx(-7);
        movingBall.setDy(5);



    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        movingBall.draw(canvas);
        movingBall.move(canvasWidth, canvasHeight);
        paddle.draw(canvas);
       //brick.draw(canvas);
      //  brick2.draw(canvas);
       // brick3.draw(canvas);
       // brick4.draw(canvas);
        //paddle.move(1180,768,1);
       bricks.draw(canvas);

        movingBall.collideWith(paddle);
        if (movingBall.collideWith(bricks.getBricks().get(23)))
            System.out.println("HIT!");

        invalidate();
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
