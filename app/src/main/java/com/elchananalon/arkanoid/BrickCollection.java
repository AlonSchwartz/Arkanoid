package com.elchananalon.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;

// NOT READY YET
public class BrickCollection {

    Brick brick = new Brick(100,50,200,50, Color.RED);

    private static final int ROWS = 5, COLS= 5;
    private ArrayList<Brick> bricks;

    //Constructor. We can also do a constructor that gets ROWS, COLS & Brick
    public BrickCollection(float width, float height, float startingXPosition, float startingYPosition, int padding, int color) {

      bricks = new ArrayList<>();

      if (padding<=0){
          padding =(int) width/10;
      }

        for (int i=0; i<ROWS ; i++)
      {
          Brick brick = new Brick(width,height,startingXPosition+padding,startingYPosition, Color.RED);
          bricks.add(brick);
          padding+=padding;

      }
    }
    public void draw(Canvas canvas) {
       for (int i =0; i<bricks.size();i++)
           bricks.get(i).draw(canvas);
    }
}


