package com.jabzzz.labzzz.ai_skills.attack_skills;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Stefan on 07.05.2017.
 */

public class Bullet {

    private Texture bullet;
    private Vector2 position;
    private Vector2 velocity;
    private boolean firedAndActive = false;

    public Bullet()
    {
        bullet = new Texture("gamestate/entities/bullet1.png");
    }

    public void fire(Vector2 position, Vector2 velocity)
    {
        this.position = position;
        this.velocity = velocity;
        firedAndActive = true;
    }

    public void render(SpriteBatch theBatch, OrthographicCamera theCam)
    {
        if(firedAndActive)
        {
            theBatch.draw(bullet, position.x - bullet.getWidth()/2, position.y - bullet.getHeight()/2);
        }
    }

    public void update()
    {
        position.add(velocity);
    }
}
