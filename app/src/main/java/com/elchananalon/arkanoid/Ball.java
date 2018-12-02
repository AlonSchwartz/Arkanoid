package com.elchananalon.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {

    private float dx,dy;// Moving direction
    private float xPosition, yPosition, radius;
    private int color;
    private Paint pen;

    // Constructor
    public Ball(float xPosition, float yPosition, float radius) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.radius = radius;
        this.dx = 0;
        this.dy = 0;
        this.pen = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.color = Color.WHITE;
    }

    /**********************************************************/

    // Getters & Setters
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

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
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
        canvas.drawCircle(xPosition,yPosition,radius,pen);
    }

    public void setLocation(float xNewLocation, float yNewLocation)
    {
        setxPosition(xNewLocation);
        setyPosition(yNewLocation);
    }
    public void move(int w, int h)
    {
        this.xPosition+= dx;
        this.yPosition+= dy;

        // check if ball out of left or right side
        if((xPosition-radius)<=0 || (xPosition+radius)>=w)
        {
            dx = -dx;
        }

        // check if ball out of bottom or up side
        if((yPosition+radius)>=h || (yPosition-radius)<=0)
        {
            dy = -dy;
        }

    }

}


