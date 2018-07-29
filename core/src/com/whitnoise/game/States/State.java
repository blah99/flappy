package com.whitnoise.game.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm; //gsm is a way to manage state so we can put a state on top of a state e.g. pausing

    protected State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();



    }

    protected abstract void handleInput();
    public abstract void update(float dt); //dt(delta time)=difference between one frame rendered and the next
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();


}
