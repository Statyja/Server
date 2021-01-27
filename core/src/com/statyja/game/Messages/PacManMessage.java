package com.statyja.game.Messages;

import com.badlogic.gdx.math.Vector2;
import com.statyja.game.Enums.MoveDirection;

public class PacManMessage {
    public PacManMessage(Vector2 pacManPosition, float stateTime, MoveDirection direction, int connectionId, boolean isMove) {
        this.pacManPosition = pacManPosition;
        this.stateTime = stateTime;
        this.direction = direction;
        this.connectionId = connectionId;
        this.isMove = isMove;
    }

    public void updateInfo(float x, float y, float stateTime, MoveDirection direction, boolean isMove) {
        this.pacManPosition.x = x;
        this.pacManPosition.y = y;
        this.stateTime = stateTime;
        this.direction = direction;
        this.isMove = isMove;
    }

    private Vector2 pacManPosition;

    private float stateTime;

    private MoveDirection direction;

    private int connectionId;

    private boolean isMove;
}
