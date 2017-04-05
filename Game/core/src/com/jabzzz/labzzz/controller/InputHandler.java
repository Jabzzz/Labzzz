package com.jabzzz.labzzz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.states.AState;

import java.util.Stack;

/**
 * Created by Useless on 03.04.2017.
 */

public class InputHandler implements InputProcessor
{

    private Stack<AState> theStateStack = null;
    private int refPoints[][] = new int[10][2];

    public InputHandler(Stack<AState> theStateStack)
    {

        this.theStateStack = theStateStack;
        Gdx.input.setInputProcessor(this);
        System.out.println("InputHandler got constructed!");
<<<<<<< HEAD
    }
=======


        for(int i=0; i<refPoints.length; i++)
        {
            for(int j=0; j<refPoints[0].length; j++)
            refPoints[i][j] = 0;
        }
>>>>>>> master

        System.out.println(refPoints);

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
        System.out.println("touchDown at:\t"+screenX+", "+screenY+" from Pointer: "+pointer);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp at:\t"+screenX+", "+screenY+" from Pointer: "+pointer);

        refPoints[pointer][0] = 0;
        refPoints[pointer][1] = 0;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged at:"+screenX+", "+screenY+" from Pointer: "+pointer);

        if (refPoints[pointer][0]==0)
        {
            refPoints[pointer][0] = screenX;
            refPoints[pointer][1] = screenY;
        }
        else
        {
            //on display left upper corner is 0/0 so we have y has to be -y
            double vecX = (double) screenX-refPoints[pointer][0];
            double vecY = -((double) screenY-refPoints[pointer][1]);

            Direction d = null;

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

            System.out.println("Input: abs: "+abs+", direction: "+d);
            theStateStack.peek().input(abs, d);
        }
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
