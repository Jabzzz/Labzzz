package com.jabzzz.labzzz.ai_skills;

import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.entities.Player;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.game.Labyrinth;

/**
 * Created by Stefan on 06.05.2017.
 */

public class FollowPlayer extends FollowTargetSkill {

    private Player player;

    public FollowPlayer(Labyrinth labyrinth, Vector2 position, WallDetection wallDetection, Player player)
    {
        super(labyrinth, position, wallDetection);
        this.player = player;
    }

    public void reset()
    {

    }

    @Override
    public void updateTarget() {
        speed = Speed.NORMAL;
        setTarget(player.getPosition());
    }
}
