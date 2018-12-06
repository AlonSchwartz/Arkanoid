package com.elchananalon.arkanoid;

import android.graphics.Canvas;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Random;

public class BrickCollection {

    private static final int ROWS = 10, COLS= 5;
    private ArrayList<Brick> bricks;

    public static int getROWS() {
        return ROWS;
    }

    public static int getCOLS() {
        return COLS;
    }

    // Constructor. Will build a set of bricks at width X height size, with padding between them
    public BrickCollection(float width, float height, float startingXPosition, float startingYPosition, float padding) {

        bricks = new ArrayList<>();

        if (padding <= 0) {
            padding = width / 10; // To make sure there will always be padding, even if someone tried to put 0 padding
        }
        float prevX = 0; //To store previous block X position
        float shiftRight = 0;

        for (int j = 0; j < ROWS; j++) {
            for (int i = 0; i < COLS; i++) {
                if (i == 0) {
                    bricks.add(new Brick(width, height, startingXPosition, startingYPosition, setBrickColor()));
                    shiftRight = padding + width; // We need to shift right from the first brick only
                }
                else{
                    bricks.add(new Brick(width, height, prevX + shiftRight, startingYPosition, setBrickColor()));
                }
                prevX = bricks.get(i).getxPosition();
            }
            startingYPosition += padding + height;
            shiftRight = 0;
            prevX=0;
        }
    }

    // to get the arrayList
    public ArrayList<Brick> getBricks() {
        return bricks;
    }
    /**********************************************************/

    public void draw(Canvas canvas) {
       for (int i =0; i<bricks.size();i++)
           bricks.get(i).draw(canvas);

    }

    public void remove(int i){
        bricks.remove(i);
    }

    private int setBrickColor(){
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(27,18,88));
        colors.add(Color.rgb(84,16,16));
        colors.add(Color.rgb(50,87,53));
        colors.add(Color.rgb(124,47,38));
        colors.add(Color.rgb(84,16,16));
        colors.add(Color.rgb(172,163,33));
        Random rand = new Random();
        return colors.get(rand.nextInt(colors.size()));
    }
}


