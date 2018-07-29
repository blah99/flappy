package com.whitnoise.game.States;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {

    private Stack<State> states;

    public GameStateManager(){
        states = new Stack<State>();
    }

    public void push(State state){
        states.push(state);
    }

    public void pop(){
        states.pop().dispose();
    }

    public void set(State state){
        //this method doesnt work well for static vars that need to be disposed of so i made settoplaystate instead
        states.pop().dispose();
        states.push(state);
    }

    public void setToPlayState(){
        states.pop().dispose();
        states.push(new PlayState(this));
    }

    public void update(float dt){
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }

}
