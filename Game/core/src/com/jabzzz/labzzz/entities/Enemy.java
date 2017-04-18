package com.jabzzz.labzzz.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Stefan on 18.04.2017.
 */

public class Enemy extends AEntity {

    private Vector2 acceleration = new Vector2();
    private Vector2 velocity = new Vector2();

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Texture texture;

    public Enemy(Vector2 position)
    {
        this.position = position;

        texture = new Texture("enemy.gif");
    }

    @Override
    public void render(SpriteBatch theBatch)
        {
            
            theBatch.begin();

            theBatch.draw(texture, getPosition().x, getPosition().y, 50, 50);

           // shapeRenderer.circle(getPosition().x, getPosition().y, 10);

            //shapeRenderer.rect(getCollisionRectangle().x, getCollisionRectangle().y, getCollisionRectangle().width, getCollisionRectangle().height);

            theBatch.end();
        }
}
