package com.jabzzz.labzzz.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.ai_skills.*;
import com.jabzzz.labzzz.ai_skills.WallDetection;
import com.jabzzz.labzzz.controller.InputData;
import com.jabzzz.labzzz.controller.MainGame;
import com.jabzzz.labzzz.game.Labyrinth;
import com.jabzzz.labzzz.states.GameState;

import java.util.ArrayList;

/**
 * Created by Stefan on 18.04.2017.
 */

public class Enemy extends ACharacter {


    private int roleAI;
    private Player player;

    private int currentFollowTargetSkill = -1;
    private ArrayList<FollowTargetSkill> followTargetSkills = new ArrayList<FollowTargetSkill>();



    private int halfDisplayX = (int) (MainGame.WIDTH / 2);
    private int halfDisplayY = (int) (MainGame.HEIGHT / 2);

    public Enemy(Vector2 position, Labyrinth labyrinth, Player player)
    {
        super(labyrinth);

        this.player = player;
        this.position = new Vector2(position);

        WallDetection wallDetection = new WallDetection();
        followTargetSkills.add(new RandomWalk(labyrinth, wallDetection, this.position));
        followTargetSkills.add(new FollowPlayer(labyrinth, this.position, wallDetection, player));
        currentFollowTargetSkill = 1;

        texture = new Texture("gamestate/entities/enemy.gif");
    }

    public void render(SpriteBatch theBatch, OrthographicCamera theCam)
    {
        theBatch.begin();
        int colStart, colEnd, rowStart, rowEnd;
        colStart = MathUtils.floor((theCam.position.x - halfDisplayX) / labyrinth.getWidth());
        colEnd = MathUtils.floor((theCam.position.x + halfDisplayX) / labyrinth.getWidth());
        rowStart = MathUtils.floor((theCam.position.y - halfDisplayY) / labyrinth.getHeight());
        rowEnd = MathUtils.floor((theCam.position.y + halfDisplayY) / labyrinth.getHeight());

        for(int row = rowStart; row <= rowEnd; row++)
        {
            for (int colum = colStart; colum <= colEnd; colum++)
            {
                Vector2 virtualPos = new Vector2(position).add(colum * labyrinth.getWidth(), row * labyrinth.getWidth());
                theBatch.draw(texture, virtualPos.x - 25, virtualPos.y - 25, 50, 50);
            }
        }

        theBatch.end();

        drawDebug(getPosition());
    }

    private void drawDebug(Vector2 pos)
    {
        GameState.shapeRenderer.setAutoShapeType(true);
        GameState.shapeRenderer.begin();

        GameState.shapeRenderer.setColor(Color.BLUE);
        GameState.shapeRenderer.line(player.position, position);
        ArrayList<Vector2> intersections = labyrinth.getIntersectionsWithGrid(player.position, position);
        if(labyrinth.isLineWithoutBlocks(player.position, position))
            GameState.shapeRenderer.setColor(Color.GREEN);
        else
            GameState.shapeRenderer.setColor(Color.RED);
        for(Vector2 intersection : intersections)
        {
            GameState.shapeRenderer.circle(intersection.x, intersection.y, 3);
        }

        if(currentFollowTargetSkill >= 0)
        {
            FollowTargetSkill targetSkill = followTargetSkills.get(currentFollowTargetSkill);



            GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
            GameState.shapeRenderer.circle(targetSkill.getTarget().x, targetSkill.getTarget().y, 10);
            GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.BLUE);
            GameState.shapeRenderer.circle(position.x, position.y, 10);

            float scale = 20f;
            GameState.shapeRenderer.setColor(Color.RED);
            GameState.shapeRenderer.line(position, new Vector2(position).add(new Vector2(velocity).setLength(velocity.len() * scale)));

            GameState.shapeRenderer.setColor(Color.GREEN);
            GameState.shapeRenderer.line(position, new Vector2(position).add(new Vector2(acceleration).setLength(acceleration.len() * scale)));

            if(targetSkill.getWallDetection().getBlock(0))
                GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
            else
                GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.GREEN);
            GameState.shapeRenderer.rect(position.x - 5 + 20, position.y - 5, 10, 10);

            if(targetSkill.getWallDetection().getBlock(1))
                GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
            else
                GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.GREEN);
            GameState.shapeRenderer.rect(position.x - 5, position.y - 5 + 20, 10, 10);

            if(targetSkill.getWallDetection().getBlock(2))
                GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
            else
                GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.GREEN);
            GameState.shapeRenderer.rect(position.x - 5 - 20, position.y - 5, 10, 10);

            if(targetSkill.getWallDetection().getBlock(3))
                GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
            else
                GameState.shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.GREEN);
            GameState.shapeRenderer.rect(position.x - 5, position.y - 25, 10, 10);


        }
        GameState.shapeRenderer.end();

    }

    @Override
    public void update(float delta)
    {
        //Calculate Input
        calcInputData();

        //Set movement
        movement(delta);
    }

    @Override
    public void calcInputData()
    {
        float distanceEnemyPlayer = FollowTargetSkill.searchPathThroughBorder(position, player.position, labyrinth).len();
        if(distanceEnemyPlayer < MainGame.BLOCK_SIZE * 1.5)
        {
            if(currentFollowTargetSkill != 1)
            {
                currentFollowTargetSkill = 1;
                followTargetSkills.get(1).reset();
            }
        }
        else if(distanceEnemyPlayer > MainGame.BLOCK_SIZE * 3)
        {
            if(currentFollowTargetSkill != 0)
            {
                currentFollowTargetSkill = 0;
                followTargetSkills.get(0).reset();
            }
        }
        //currentFollowTargetSkill = 0;

        if(currentFollowTargetSkill >= 0)
        {
            InputData id = followTargetSkills.get(currentFollowTargetSkill).update(this);

            acceleration = getAccelerationFrom(id.getSpeed(), id.getDirection(), id.getInputSystem());
        }
    }

    @Override
    public void animate()
    {

    }



}
