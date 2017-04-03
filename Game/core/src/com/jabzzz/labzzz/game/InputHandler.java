package com.jabzzz.labzzz.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.jabzzz.labzzz.states.State;

import java.util.Stack;

/**
 * Created by Useless on 03.04.2017.
 */

public class InputHandler implements InputProcessor
{

    private Stack<State> theStateStack = null;

    public InputHandler(Stack<State> theStateStack)
    {

        this.theStateStack = theStateStack;
        Gdx.input.setInputProcessor(this);
        System.out.println("UpdateThread got constructed!");
    }


    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown "+keycode);

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp "+keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped "+character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        theStateStack.peek().input(screenX, screenY);
        System.out.println("touchDown");
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged");
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        System.out.println("mouseMoved "+screenX+" "+screenX);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled "+amount);
        return false;
    }
}
