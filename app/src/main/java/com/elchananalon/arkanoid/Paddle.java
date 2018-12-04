package com.elchananalon.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Paddle {

    private float width, height, xPosition, yPosition;
    private static final float xStartingPosition=500, yStartingPosition=650, RIGHT=0, LEFT=1;
    private Paint pen;
    private int color = Color.BLUE, speed = 5;


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

    public int getSpeed() {return speed; }

    public void setSpeed(int speed) {this.speed = speed; }

    /**********************************************************/

    public void draw(Canvas canvas){
        pen.setColor(color);
        canvas.drawRect(xPosition, yPosition, xPosition+width,yPosition+height-5, pen);
        /*
        *   left: The X coordinate of the left side of the rectangle
            top: The Y coordinate of the top of the rectangle
            right: The X coordinate of the right side of the rectangle
            bottom: The Y coordinate of the bottom of the rectangle

        // A= X_ScreenSize / 2,B= Y_ScreenSize - 40,A+width, B+height
        */
    }

    /**********************************************************/
    public void move(int w, int h, int direction){

        //Move to left
        if (xPosition+width >= width && direction == LEFT)
        {
            xPosition -= speed;
        }
        //Move to right
        if (xPosition+width < w && direction == RIGHT)
        {
            xPosition += speed;
        }

    }

}
