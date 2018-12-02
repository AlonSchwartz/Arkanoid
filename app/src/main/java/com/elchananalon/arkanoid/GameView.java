package com.elchananalon.arkanoid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
    private Ball movingBall;
    private int canvasWidth;
    private int canvasHeight;
    private Paint pen;

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
