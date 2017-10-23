package com.jabzzz.labzzz.ai_skills.follow_target;

import com.badlogic.gdx.math.Vector2;
import com.jabzzz.labzzz.ai_skills.WallDetection;
import com.jabzzz.labzzz.entities.Player;
import com.jabzzz.labzzz.enums.Speed;
import com.jabzzz.labzzz.environment.Labyrinth;

/**
 * Created by Stefan on 06.05.2017.
 */

public class FollowPlayer extends AFollowTargetSkill {

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
