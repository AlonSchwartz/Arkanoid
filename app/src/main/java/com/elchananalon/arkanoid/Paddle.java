package com.elchananalon.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Paddle {

    private float width, height, xPosition, yPosition;
    private static final float xStartingPosition=5, yStartingPosition=3;
    private Paint pen;
    private int color = Color.BLUE;


    // Constructor
    public Paddle(float width, float height) {
        this.width = width;
        this.height = height;
        this.xPosition = xStartingPosition;
        this.yPosition = yStartingPosition;
        this.pen = new Paint(Paint.ANTI_ALIAS_FLAG);


    }

    /**********************************************************/

    // Getters & Setters
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getXPosition() {
        return xPosition;
    }

    public void setXPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public void setYPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    /**********************************************************/

    public void draw(Canvas canvas){
        pen.setColor(color);
        canvas.drawRect(210, 220, 500, 100, pen);

    }

    /**********************************************************/
    public void move(){



    }

}
