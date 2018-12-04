package com.elchananalon.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {

    private float dx,dy;// Moving direction
    private float xPosition, yPosition, radius;
    private int color, speed=5;
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
        this.radius = 20;
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

    public int getSpeed() {return speed; }

    public void setSpeed(int speed) {this.speed = speed;  }

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
        //this.xPosition+= dx;
        this.yPosition+= -dy;

        // check if ball out of left or right side
        if((xPosition-radius)<=0 || (xPosition+radius)>=w)
        {
            dx = -dx;
        }


        // check if ball out of bottom or up side
        if(yPosition-radius<=0)
        {
            dy = -dy;
        }

    }

    public boolean collideWith(Paddle paddle)
    {
        // If the x position of the ball is between the paddle x boundaries
        if ((this.xPosition+this.radius >= paddle.getXPosition()) && (this.xPosition+this.radius <= paddle.getXPosition()+paddle.getWidth()) )
        {
            // and also y position of the ball is touching the paddle
            if ((this.yPosition+this.radius >= paddle.getYPosition()) && (this.yPosition + this.radius<=paddle.getYPosition()+paddle.getHeight()))
            {
                // HIT! Change the ball direction
                dy = -dy;
                return true;
            }
        }
        return false;
    }

    public boolean collideWith(Brick brick)
    {
        if (this.xPosition+radius >= brick.getxPosition() && this.xPosition+radius <= brick.getxPosition()+brick.getWidth()){
            if (this.yPosition+radius <= brick.getyPosition()+brick.getHeight() &&(this.yPosition+radius > brick.getyPosition())) {
                return true; // HIT
            }
        }
        return false;
    }

}


