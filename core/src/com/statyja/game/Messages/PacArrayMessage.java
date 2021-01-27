package com.statyja.game.Messages;

import java.util.ArrayList;

public class PacArrayMessage {

    public void addPacMan(PacManMessage pacManMessage){
        pacManMessages.add(pacManMessage);
    }

    public void removePacMan(PacManMessage pacManMessage){
        pacManMessages.remove(pacManMessage);
    }

    public PacArrayMessage(){
        pacManMessages= new ArrayList<>();
    }

    private ArrayList<PacManMessage> pacManMessages;
}
