package com.statyja.game.Messages;

public class RemovePacket {
    public RemovePacket(int connectionId) {
        this.connectionId = connectionId;
    }

    private int connectionId;

}
