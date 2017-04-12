package com.jabzzz.labzzz.controller;

import com.jabzzz.labzzz.enums.Direction;
import com.jabzzz.labzzz.enums.InputSystem;
import com.jabzzz.labzzz.enums.Speed;

import javax.print.DocFlavor;

/**
 * Created by Stefan on 12.04.2017.
 */

public class InputData {
    private Speed speed;
    private Direction direction;
    private InputSystem inputSystem;

    public InputData(Speed speed, Direction direction, InputSystem inputSystem)
    {
        this.speed = speed;
        this.direction = direction;
        this.inputSystem = inputSystem;
    }

    public Speed getSpeed()
    {
        return speed;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public InputSystem getInputSystem()
    {
        return inputSystem;
    }
}
