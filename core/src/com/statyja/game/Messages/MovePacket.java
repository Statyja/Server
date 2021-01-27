package com.statyja.game.Messages;

import com.statyja.game.Enums.MoveDirection;

public class MovePacket {
    public MoveDirection getDirection() {
        return direction;
    }

    private MoveDirection direction;

    public MoveDirection getSubDirection() {
        return subDirection;
    }

    private MoveDirection subDirection;
}
