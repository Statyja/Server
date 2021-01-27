package com.statyja.game.Objects;

import com.badlogic.gdx.math.Vector2;

public class Cell {
    private Vector2 position;

    private boolean isWall;

    private int spr;

    public Cell(Vector2 position, boolean isWall, int spr) {
        this.position = position;
        this.isWall = isWall;
        this.spr=spr;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isWall() {
        return isWall;
    }

}
