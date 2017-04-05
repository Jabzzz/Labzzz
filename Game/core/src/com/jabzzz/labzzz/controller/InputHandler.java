package com.jabzzz.labzzz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.states.AState;

import java.util.Stack;

/**
 * Created by Useless on 03.04.2017.
 */

public class InputHandler implements InputProcessor
{

    private Stack<AState> theStateStack = null;
    private int refPoints[][] = new int[10][2];
    private Speed kbSpeed = null;

    public InputHandler(Stack<AState> theStateStack)
    {

        this.theStateStack = theStateStack;
        Gdx.input.setInputProcessor(this);
        System.out.println("InputHandler got constructed!");
        kbSpeed = Speed.SLOW;

        for(int i=0; i<refPoints.length; i++)
        {
            for(int j=0; j<refPoints[0].length; j++)
            refPoints[i][j] = 0;
        }

    }

    @Override
    public boolean keyDown(int keycode) {

        //System.out.println("keyDown "+keycode);

        InputSystem is = InputSystem.KEYBOARDMOVE;

        Direction d = getDirection(keycode);

        if(d == Direction.NONE)
        {
            if(keycode==31)
                kbSpeed = Speed.SLOW;
            if(keycode==59)
                kbSpeed = Speed.FAST;
        }
        else
        {
            System.out.println("Input(Keyboard move): speed: " +kbSpeed+ ", direction: " + d);
            theStateStack.peek().input(kbSpeed, d, is, 0, 0);
        }
        return false;
    }

    private Direction getDirection(int keycode) {
        Direction d = Direction.NONE;;
        switch(keycode)
        {
            case 51: d = Direction.UP;
                break;
            case 19: d = Direction.UP;
                break;
            case 29: d = Direction.LEFT;
                break;
            case 21: d = Direction.LEFT;
                break;
            case 47: d = Direction.DOWN;
                break;
            case 20: d = Direction.DOWN;
                break;
            case 32: d = Direction.RIGHT;
                break;
            case 22: d = Direction.RIGHT;
                break;
        }
        return d;
    }

    @Override
    public boolean keyUp(int keycode) {
        //System.out.println("keyUp "+keycode);

        InputSystem is = InputSystem.KEYBOARDSTOP;

        Direction d = getDirection(keycode);

        if(keycode==31)
            kbSpeed = Speed.NORMAL;
        if(keycode==59)
            kbSpeed = Speed.NORMAL;

        System.out.println("Input(Keyboard stop): speed: "+kbSpeed+", direction: "+d);
        theStateStack.peek().input(kbSpeed, d, is, 0 ,0);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        //wont be used
        //System.out.println("keyTyped "+character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //System.out.println("touchDown at:\t"+screenX+", "+screenY+" from Pointer: "+pointer);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //System.out.println("touchUp at:\t"+screenX+", "+screenY+" from Pointer: "+pointer);

        refPoints[pointer][0] = 0;
        refPoints[pointer][1] = 0;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //System.out.println("touchDragged at:"+screenX+", "+screenY+" from Pointer: "+pointer);

        if (refPoints[pointer][0]==0)
        {
            refPoints[pointer][0] = screenX;
            refPoints[pointer][1] = screenY;
        }
        else
        {
            //on display left upper corner is 0/0 so we have y has to be -y
            float vecX = (float) screenX-refPoints[pointer][0];
            float vecY = -((float) screenY-refPoints[pointer][1]);

            Direction d = null;
            InputSystem is = InputSystem.TOUCH;

            //get direction
            if(vecX > vecY)
            {
                if(vecX > -vecY)
                {
                    d = Direction.RIGHT;
                }
                if(vecX <= -vecY)
                {
                    d = Direction.DOWN;
                }
            }
            if(vecX <= vecY)
            {
                if(vecX > -vecY)
                {
                    d = Direction.UP;
                }
                if(vecX <= -vecY)
                {
                    d = Direction.LEFT;
                }
            }

            double abs = Math.sqrt(Math.pow(vecX,2)+Math.pow(vecY,2));

            Speed s = Speed.FAST;

            if(abs<100)
                s = Speed.SLOW;
            else if(abs<200)
                s = Speed.NORMAL;


            System.out.println("Input: s: "+s+", direction: "+d);
            theStateStack.peek().input(s, d, is, vecX, vecY);
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //System.out.println("mouseMoved "+screenX+" "+screenX);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        //System.out.println("scrolled "+amount);
        return false;
    }
}
