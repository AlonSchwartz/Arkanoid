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
        this.color = Color.BLACK;

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
        this.xPosition+= dx;
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

        // top left corner
        float temp = paddle.getXPosition()+paddle.getSpeed()-this.xPosition;
        float temp2 = paddle.getYPosition() - this.yPosition;

        double a = Math.pow(temp, 2);
        double b = Math.pow(temp2, 2);

        double dist = (int)Math.sqrt(a+b);

        if (dist-radius <= 0) {
          //  System.out.println(dist +" " +radius);
           // System.out.println(dist -radius);
//            System.out.println("HI 1");

            if (this.xPosition+radius > paddle.getXPosition())
            {
                dy=-dy;
            }
            else {
                dy = -dy;
                dx = -dx;
            }
            this.yPosition-=paddle.getHeight()+10;//needs more testing with the ball
             return true;
        }

        // top right corner
        float temp3 = paddle.getXPosition()+paddle.getWidth()+paddle.getSpeed()-this.xPosition;
        float temp4 = paddle.getYPosition() - this.yPosition;

        double c = Math.pow(temp3, 2);
        double d = Math.pow(temp4, 2);

        double dist2 = (int)Math.sqrt(c+d);

        if (dist2-radius <= 0) {
            System.out.println("HI 2");
            System.out.println(dist2 -radius);

            if (this.xPosition-radius < paddle.getXPosition()+paddle.getWidth())
            {
                dy=-dy;
            }
            else{
                dy = -dy;
                dx = -dx;
            }
            this.yPosition-=paddle.getSpeed()+10;//needs more testing with the ball
            return true;
        }

        float dis = Math.abs(-this.yPosition+paddle.getYPosition());
        if (dis-radius <= 0 && this.xPosition > paddle.getXPosition() && this.xPosition < paddle.getXPosition()+paddle.getWidth()) {
         //  System.out.println("boom");
            dy=-dy;
            //dx=-dx;
            this.yPosition-=paddle.getSpeed()+10;//needs more testing with the ball
            return true;
        }



/*
// does the same thing, but working a bit different. each version of some problems, need to check which one is with less problems

        if ((paddle.getXPosition() <= this.xPosition-radius) && (paddle.getXPosition()+paddle.getWidth() > this.xPosition+radius))
        {
            if ((this.yPosition+radius == paddle.getYPosition()) && this.yPosition-radius < paddle.getYPosition())
            {
                // Hit from up only changes the y position of the ball
                System.out.println("HIT FROM UP");
                //dx=-dx;
                dy=-dy;
                //this.yPosition-=paddle.getSpeed();

                return true;
            }
        }

        // If there is a hit on the right side of the paddle
        if (this.xPosition-radius <= paddle.getXPosition()+paddle.getWidth() && this.xPosition+radius > paddle.getXPosition()+paddle.getWidth())
        {
            if (this.yPosition+radius >= paddle.getYPosition() && this.yPosition-radius < paddle.getYPosition())
            {
                System.out.println("HIT FROM RIGHT");
                // hit at the corners will change the x position too
                dx = -dx;
               dy=-dy;
                this.yPosition-=paddle.getSpeed();

                return true;
            }
        }

        // If there is a hit on the left side of the paddle
        if (this.xPosition+radius >= paddle.getXPosition() && this.xPosition-radius < paddle.getXPosition())
        {
           if (this.yPosition+radius >= paddle.getYPosition() && this.yPosition-radius < paddle.getYPosition())
            {
                System.out.println("HIT FROM LEFT");
                // hit at the corners will change the x position too

                dx = -dx;
               dy=-dy;
                this.yPosition-=paddle.getSpeed();
                return true;
            }
        }
*/

        return false;


    }

    public boolean collideWith(Brick brick)
    {
        // FULL hit up or down
        if (this.xPosition+radius >= brick.getxPosition() && this.xPosition+radius <= brick.getxPosition()+brick.getWidth()){
            if (this.yPosition-radius <= brick.getyPosition()+brick.getHeight() && (this.yPosition+radius > brick.getyPosition())) {
               dy=-dy;
                return true;
            }
        }

        // PARTLY hit from left up or down, including from the side
        if ((brick.getxPosition() >= this.xPosition-radius) && (brick.getxPosition() <= this.xPosition+radius  ) ){
            if (this.yPosition-radius < brick.getyPosition()+brick.getHeight() && this.yPosition+radius > brick.getyPosition()) {
                dy=-dy;
                return true;
            }
        }

        // PARTLY hit from right up or down, including from the side
        if (this.xPosition-radius <= brick.getxPosition()+brick.getWidth() && this.xPosition+radius >= brick.getxPosition()+brick.getWidth()) {
            if (this.yPosition - radius <= brick.getyPosition() + brick.getHeight() && (this.yPosition + radius > brick.getyPosition())) {
                dy=-dy;
                return true;
            }
        }

        return false;
    }

}


