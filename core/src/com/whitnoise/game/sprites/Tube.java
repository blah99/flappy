package com.whitnoise.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {
    public static final int TUBE_WIDTH = 52;

    private static final int FLUCTUATION = 130;
    private static final int TUBE_GAP = 100;
    private static final int LOWEST_OPENING = 120;
    private static Texture topTube;
    private static Texture bottomTube;
    private Vector2 posTopTube, posBotTube;
    private Rectangle boundsTop, boundsBot;
    private Random rand;

    //fix how you handle the tubes and if static....

    public Tube(float x){
        //topTube = new Texture("toptube.png"); //note better would be to create 2 static tube textures and reuse them
        //bottomTube = new Texture("bottomtube.png");
        rand = new Random();

        //added for static
//        if (topTube == null) {
//            System.out.println("topnull");
//            topTube = new Texture("toptube.png");
//        }

        //
        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop = new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        boundsBot =  new Rectangle(posBotTube.x, posBotTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    public static void setTextures(String topTubeTextureName, String botTubeTextureName){
       // if (topTube == null)
        System.out.println("setting tube textures");
            topTube = new Texture(topTubeTextureName);
       // if (bottomTube == null)
            bottomTube = new Texture(botTubeTextureName);
    }

    public static Texture getTopTube() {
        return topTube;
    }

    public static Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBotTube() {
        return posBotTube;
    }

    public void reposition(float x){
        posTopTube.set(x, rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING);
        posBotTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        boundsTop.setPosition(posTopTube.x, posTopTube.y);
        boundsBot.setPosition(posBotTube.x, posBotTube.y);
    }

    public boolean collides(Rectangle player) {
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public static void dispose() {
        System.out.println("tubes disposed of... .");
        topTube.dispose();
        bottomTube.dispose();
    }
}
