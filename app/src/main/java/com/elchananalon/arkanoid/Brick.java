package com.elchananalon.arkanoid;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Brick {

    private float width, height, xPosition, yPosition;
    private int color;
    private Paint pen;

    // Constructor
    public Brick(float width, float height, float xPosition, float yPosition, int color) {
        this.width = width;
        this.height = height;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.color = color;
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

    public float getxPosition() {
        return xPosition;
    }

    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    /**********************************************************/

    public void draw(Canvas canvas)
    {
        pen.setColor(color);
        canvas.drawRect(xPosition, yPosition, xPosition+width, yPosition+height, pen);

         /*
        *   left: The X coordinate of the left side of the rectangle
            top: The Y coordinate of the top of the rectangle
            right: The X coordinate of the right side of the rectangle
            bottom: The Y coordinate of the bottom of the rectangle
        */
    }

}
