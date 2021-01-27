package com.statyja.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.statyja.game.Objects.Cell;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapLoader {

    public static ArrayList<Cell> getMap(String name, Vector2 size, ArrayList<Vector2> spawns) {
        FileHandle file = Gdx.files.internal(name);
        BufferedReader reader = file.reader(2048);
        String s;
        int state = 0;

        ArrayList<Cell> cellArrayList = new ArrayList<>();

        try {
            while ((s = reader.readLine()) != null) {
                if (s.isEmpty() || s.startsWith("//")) continue;
                if ((s = s.replaceAll(" ", "")).equals("walls:")) {
                    state = 1;
                    continue;
                } else if ((s = s.replaceAll(" ", "")).equals("spawn:")) {
                    state = 2;
                    continue;
                }
                switch (state){
                    case 0:
                        String[] chunks = s.split(";");
                        size.set(Float.parseFloat(chunks[0]), Float.parseFloat(chunks[1]));
                        break;
                    case 1:
                        String[] chunks1 = s.split(";");

                        cellArrayList.add(new Cell(new Vector2(Float.parseFloat(chunks1[0]), Float.parseFloat(chunks1[1])), true, Integer.parseInt(chunks1[2])));
                        break;
                    case 2:
                        String[] chunks2 = s.split(";");
                        spawns.add(new Vector2(Float.parseFloat(chunks2[0]), Float.parseFloat(chunks2[1])));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cellArrayList;
    }

}

