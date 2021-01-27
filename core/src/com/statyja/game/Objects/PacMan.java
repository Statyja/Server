package com.statyja.game.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.statyja.game.Enums.MoveDirection;
import com.statyja.game.MainGrind;
import com.statyja.game.Messages.PacManMessage;

public class PacMan extends Actor {
    private MoveDirection subDirection;
    private MoveDirection direction;
    private float r;

    transient private Cell[][] cells;
    transient private int nextX, nextY;

    public int getConnectionId() {
        return connectionId;
    }

    transient private int connectionId;
    transient private MainGrind game;
    transient private float speed;

    public PacManMessage getPacManMessage() {
        pacManMessage.updateInfo(getX(), getY(), stateTimer, direction, isMove);
        return pacManMessage;
    }

    transient private PacManMessage pacManMessage;

    private boolean isMove;

    private float stateTimer = 0;

    public PacMan(MoveDirection direction, Vector2 position, float r, int connectionId, Cell[][] cells, MainGrind game) {
        this.direction = direction;
        this.r = r;
        this.connectionId = connectionId;
        this.subDirection = MoveDirection.NONE;
        this.setPosition(position.x, position.y);
        this.cells = cells;
        nextX = Math.round(getX());
        nextY = Math.round(getY());
        this.game = game;
        speed = 0.2f;
        pacManMessage = new PacManMessage(new Vector2(getX(), getY()), 0, direction, connectionId, false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTimer += delta;
        if ((getY() + getX()) % 1 != 0) {
            pacManMessage.updateInfo(getX(), getY(), stateTimer, direction, isMove);
            game.sendMessage(pacManMessage);
        }
        if (getActions().size == 0 && isMove) {
            isMove = false;
            pacManMessage.updateInfo(getX(), getY(), stateTimer, direction, isMove);
            game.sendMessage(pacManMessage);
        }
    }

    public void changeAction(MoveDirection direction, MoveDirection subDirection) {
        if (getActions().size == 0) {
            if (!((direction == this.direction && subDirection == this.subDirection) ||
                    (subDirection == this.direction && direction == this.subDirection))) {
                this.direction = direction;
                this.subDirection = subDirection;
            }

            switch (this.direction) {
                case UP:
                    if (isWall(nextX, nextY + 1)) {
                        this.addAction(Actions.moveTo(nextX, nextY + 1, speed));
                        isMove = true;
                        nextY++;
                    } else if (this.subDirection != MoveDirection.NONE) {
                        MoveDirection localDirection = MoveDirection.valueOf(this.subDirection.name());
                        this.subDirection = MoveDirection.valueOf(direction.name());
                        this.direction = MoveDirection.valueOf(localDirection.name());
                    }
                    break;
                case DOWN:
                    if (isWall(nextX, nextY - 1)) {
                        this.addAction(Actions.moveTo(nextX, nextY - 1, speed));
                        isMove = true;
                        nextY--;
                    } else if (this.subDirection != MoveDirection.NONE) {
                        MoveDirection localDirection = MoveDirection.valueOf(this.subDirection.name());
                        this.subDirection = MoveDirection.valueOf(this.direction.name());
                        this.direction = MoveDirection.valueOf(localDirection.name());
                    }
                    break;
                case RIGHT:
                    if (isWall(nextX + 1, nextY)) {
                        this.addAction(Actions.moveTo(nextX + 1, nextY, speed));
                        isMove = true;
                        nextX++;
                    } else if (this.subDirection != MoveDirection.NONE) {
                        MoveDirection localDirection = MoveDirection.valueOf(this.subDirection.name());
                        this.subDirection = MoveDirection.valueOf(this.direction.name());
                        this.direction = MoveDirection.valueOf(localDirection.name());
                    }
                    break;
                case LEFT:
                    if (isWall(nextX - 1, nextY)) {
                        this.addAction(Actions.moveTo(nextX - 1, nextY, speed));
                        isMove = true;
                        nextX--;
                    } else if (this.subDirection != MoveDirection.NONE) {
                        MoveDirection localDirection = MoveDirection.valueOf(this.subDirection.toString());
                        this.subDirection = MoveDirection.valueOf(this.direction.toString());
                        this.direction = MoveDirection.valueOf(localDirection.toString());
                    }
                    break;
            }
        }
    }

    private boolean isWall(int x, int y) {
        return !cells[x][y].isWall();
    }
}
