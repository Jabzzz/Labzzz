package com.jabzzz.labzzz.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.controller.MainGame;
import com.jabzzz.labzzz.game.Labyrinth;

/**
 * Created by Stefan on 18.04.2017.
 */

public class Enemy extends AEntity {

    private Vector2 acceleration = new Vector2();
    private Vector2 velocity = new Vector2();

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Texture texture;

    private int colStart = 0;
    private int colEnd = 0;
    private int rowStart = 0;
    private int rowEnd = 0;
    private int halfDisplayX = (int) (MainGame.WIDTH / 2);
    private int halfDisplayY = (int) (MainGame.HEIGHT / 2);

    public Enemy(Vector2 position, Labyrinth labyrinth)
    {
        super(labyrinth);

        this.position = new Vector2(position);

        texture = new Texture("gamestate/entities/enemy.gif");
    }

    @Override
    public void render(SpriteBatch theBatch)
    {

        theBatch.begin();

        theBatch.draw(texture, getPosition().x - 25, getPosition().y - 25, 50, 50);

        // shapeRenderer.circle(getPosition().x, getPosition().y, 10);

        //shapeRenderer.rect(getCollisionRectangle().x, getCollisionRectangle().y, getCollisionRectangle().width, getCollisionRectangle().height);

        theBatch.end();

        /*
        theBatch.begin();
        Vector2 position = new Vector2();

        colStart = MathUtils.floor(theCam.position.x - halfDisplayX);
        colEnd = MathUtils.ceil(theCam.position.x + halfDisplayX);
        rowStart = MathUtils.floor(theCam.position.y - halfDisplayY);
        rowEnd = MathUtils.ceil(theCam.position.y + halfDisplayY);

        //if()

        theBatch.end();*/
    }

    public void update(float delta)
    {
        
    }
}
