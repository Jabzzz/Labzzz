package com.jabzzz.labzzz.ai_skills.attack_skills;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Stefan on 07.05.2017.
 */

public class ShootingSkill extends AAttackSkill {

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    Vector2 characterPosition;
    Vector2 velocityBullets;

    public ShootingSkill(Vector2 characterPosition, Vector2 velocityBullets)
    {
        this.characterPosition = characterPosition;
        this.velocityBullets = velocityBullets;
    }

    public void fire()
    {
        Bullet newBullet = new Bullet();
        newBullet.fire(characterPosition, velocityBullets);
        bullets.add(newBullet);
    }

    public void update()
    {
        for(Bullet bullet : bullets)
            bullet.update();
    }

    public void render(SpriteBatch theBatch, OrthographicCamera theCam)
    {
        for(Bullet bullet : bullets)
            bullet.render(theBatch, theCam);
    }
}
