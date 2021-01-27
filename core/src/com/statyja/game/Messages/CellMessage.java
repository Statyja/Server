package com.statyja.game.Messages;

import com.statyja.game.Objects.Cell;

import java.util.ArrayList;

public class CellMessage {
    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    private ArrayList<Cell> cells;


}
