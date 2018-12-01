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
        canvas.drawRect(123, 456, 500, 100, pen);
    }

}
