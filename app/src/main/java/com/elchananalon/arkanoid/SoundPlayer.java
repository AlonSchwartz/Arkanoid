package com.elchananalon.arkanoid;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool sound;
    private int hitSound;

    public SoundPlayer(Context context){
        sound =new SoundPool(1,AudioManager.STREAM_MUSIC,0);

        hitSound = sound.load(context,R.raw.concrete_break,1);
    }
    public void playSound(){
        System.out.println("Sound played");
        sound.play(hitSound,1.0f,1.0f,1,0,1.0f);//play sound
    }
}
