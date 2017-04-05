package com.jabzzz.labzzz.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Stefan on 04.04.2017.
 */

public class Player extends AEntity {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private OrthographicCamera theCam;

    public Player(float x, float y, OrthographicCamera theCam)
    {
        position.set(x,y);
        this.theCam = theCam;
    }

    public void render(SpriteBatch theBatch)
    {
        theCam.position.set(getPosition(), 0);
        theCam.update();
        shapeRenderer.setProjectionMatrix(theCam.combined);
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 1, 0, 1);

        shapeRenderer.circle(getPosition().x, getPosition().y, 10);

        shapeRenderer.end();
    }
}
