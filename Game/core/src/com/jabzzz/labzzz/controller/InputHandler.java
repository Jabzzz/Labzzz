package com.jabzzz.labzzz.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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

    private final int SLOWBOARDER = 100;
    private final int FASTBOARDER = 200;

    private Stack<AState> theStateStack = null;

    private int refPoints[][] = new int[10][2];
    //array for directions: 0=up 1=left 2=down 3=right
    private boolean dir[] = new boolean[4];
    //array for speedkey 0= key c; 1= key shift;
    private boolean speed[] = new boolean[2];

    private Speed kbSpeed = null;

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public InputHandler(Stack<AState> theStateStack)
    {

        this.theStateStack = theStateStack;
        Gdx.input.setInputProcessor(this);
        //System.out.println("InputHandler got constructed!");
        kbSpeed = Speed.SLOW;

        for(int i=0; i<refPoints.length; i++)
        {
            for(int j=0; j<refPoints[0].length; j++)
            refPoints[i][j] = 0;
        }

    }

    public void render(int height)
    {
        //zeichnet nur für einen Finger
        if ((refPoints[0][0] != 0)&&(refPoints[0][1] != 0))
        {
            shapeRenderer.setAutoShapeType(true);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 0, 0.5f);
            shapeRenderer.circle(refPoints[0][0], height-refPoints[0][1], 5);
            /*shapeRenderer.end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);*/
            shapeRenderer.setColor(1, 0, 0, 0.1f);
            shapeRenderer.circle(refPoints[0][0], height-refPoints[0][1], SLOWBOARDER);
            shapeRenderer.setColor(1, 0, 0, 0.1f);
            shapeRenderer.circle(refPoints[0][0], height-refPoints[0][1], FASTBOARDER);
            shapeRenderer.end();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        //System.out.println("keyDown "+keycode);

        InputSystem is = InputSystem.KEYBOARDMOVE;

        Direction d = getDirection(keycode, true);

        if(keycode==31)
            speed[0]=true;
        if(keycode==59)
            speed[1]=true;

        getSpeed();

        System.out.println("Input: Keyboard move with speed: " +kbSpeed+ ", direction: " + d);
        theStateStack.peek().input(kbSpeed, d, is, 0, 0);

        return false;
    }

    private void getSpeed() {
        if ((speed[0])&&(speed[1]))
                kbSpeed = Speed.NORMAL;
        else if (speed[0])
            kbSpeed = Speed.SLOW;
        else if (speed[1])
            kbSpeed = Speed.FAST;
        else
            kbSpeed = Speed.NORMAL;
    }

    private Direction getDirection(int keycode, boolean keyDown) {
        Direction d;
        switch(keycode)
        {
            case 51: dir[0] = keyDown;
                break;
            case 19: dir[0] = keyDown;
                break;
            case 29: dir[1] = keyDown;
                break;
            case 21: dir[1] = keyDown;
                break;
            case 47: dir[2] = keyDown;
                break;
            case 20: dir[2] = keyDown;
                break;
            case 32: dir[3] = keyDown;
                break;
            case 22: dir[3] = keyDown;
                break;
        }

        if (dir[0])
        {
            if (dir[1])
                d = Direction.UPLEFT;
            else if (dir[2])
                d = Direction.NONE;
            else if (dir[3])
                d = Direction.UPRIGHT;
            else
                d = Direction.UP;
        }
        else if (dir[1])
        {
            if (dir[3])
                d = Direction.NONE;
            else
                d = Direction.LEFT;
        }
        else if (dir[2])
        {
            if (dir[1])
                d = Direction.DOWNLEFT;
            else if (dir[3])
                d = Direction.DOWNRIGHT;
            else
                d = Direction.DOWN;
        }
        else if (dir[3])
            d = Direction.RIGHT;
        else
            d = Direction.NONE;
        return d;
    }

    @Override
    public boolean keyUp(int keycode) {
        //System.out.println("keyUp "+keycode);

        InputSystem is = InputSystem.KEYBOARDSTOP;

        Direction d = getDirection(keycode, false);

        if(keycode==31)
            speed[0]=false;
        if(keycode==59)
            speed[1]=false;

        getSpeed();

        //System.out.println("Input: Keyboard stop: new speed: "+kbSpeed+", direction: "+d);
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

        //System.out.println("Input: Click at: x: "+screenX+", "+screenX);
        theStateStack.peek().input(Speed.NONE, Direction.NONE, InputSystem.CLICK, screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //System.out.println("touchUp at:\t"+screenX+", "+screenY+" from Pointer: "+pointer);

        refPoints[pointer][0] = 0;
        refPoints[pointer][1] = 0;

        //System.out.println("Input: Click stop at: x: "+screenX+", "+screenX);
        theStateStack.peek().input(Speed.NONE, Direction.NONE, InputSystem.CLICKSTOP, screenX, screenY);
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
        else {

            //on display left upper corner is 0/0 so we have y has to be -y
            Vector2 vec = new Vector2((float) screenX - refPoints[pointer][0], -((float) screenY - refPoints[pointer][1]));
            float abs = vec.len();

            //zu kleine Impulse filtern
            if (abs > 25) {
                Direction d = null;
                InputSystem is = InputSystem.DRAGORMOUSE;

                //get Direction

                if (337.5 <= vec.angle() || vec.angle() < 22.5)
                    d = Direction.RIGHT;
                else if (vec.angle() < 67.5)
                    d = Direction.UPRIGHT;
                else if (vec.angle() < 112.5)
                    d = Direction.UP;
                else if (vec.angle() < 157.5)
                    d = Direction.UPLEFT;
                else if (vec.angle() < 202.5)
                    d = Direction.LEFT;
                else if (vec.angle() < 247.5)
                    d = Direction.DOWNLEFT;
                else if (vec.angle() < 292.5)
                    d = Direction.DOWN;
                else if (vec.angle() < 337.5)
                    d = Direction.DOWNRIGHT;

                Speed s = Speed.FAST;

                if (abs < SLOWBOARDER)
                    s = Speed.SLOW;
                else if (abs < FASTBOARDER)
                    s = Speed.NORMAL;


                //System.out.println("Input: Drag or mouse with speed:" + s + ", direction: " + d);
                theStateStack.peek().input(s, d, is, screenX, screenY);
            }
            /*else
                System.out.println("Skipped");*/
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        //wont be used
        //System.out.println("mouseMoved "+screenX+" "+screenX);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        //wont be used
        //System.out.println("scrolled "+amount);
        return false;
    }
}
