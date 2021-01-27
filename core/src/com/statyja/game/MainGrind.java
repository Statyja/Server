package com.statyja.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.statyja.game.Enums.MoveDirection;
import com.statyja.game.Messages.CellMessage;
import com.statyja.game.Messages.MovePacket;
import com.statyja.game.Messages.PacArrayMessage;
import com.statyja.game.Messages.PacManMessage;
import com.statyja.game.Messages.RemovePacket;
import com.statyja.game.Objects.Cell;
import com.statyja.game.Objects.PacMan;
import com.statyja.game.Tools.MapLoader;

import java.util.ArrayList;


public class MainGrind implements ApplicationListener {

    private PacArrayMessage pacArrayMessage;

    private Server server;

    private ArrayList<PacMan> pacMans;

    private CellMessage cellMessage;

    private Cell[][] cells;

    private ArrayList<Vector2> heroSpawn;

    @Override
    public void create() {
        Vector2 worldSize = new Vector2();
        server = new Server();
        kryoRegister();

        pacMans = new ArrayList<>();

        pacArrayMessage = new PacArrayMessage();

        heroSpawn = new ArrayList<>();
        cellMessage = new CellMessage();
        cellMessage.setCells(MapLoader.getMap("pacman_field.txt", worldSize, heroSpawn));
        System.err.println(cellMessage.getCells().toString());

        cells = new Cell[Math.round(worldSize.x)][Math.round(worldSize.y)];
        for (int i = 0; i < worldSize.x; i++)
            for (int j = 0; j < worldSize.y; j++) {
                cells[i][j] = new Cell(new Vector2(i, j), false, -1);
            }

        for (Cell cell :
                cellMessage.getCells()) {
            cells[Math.round(cell.getPosition().x)][Math.round(cell.getPosition().y)] = cell;
        }

        server.start();
        try {
            server.bind(54554, 54776);
        } catch (Exception e) {
            System.err.println("Failed to bind to port!");
        }
        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof MovePacket) {
                    try {
                        PacMan pacMan = findPacManBId(connection.getID());
                        assert pacMan != null;
                        pacMan.changeAction(((MovePacket) object).getDirection(), ((MovePacket) object).getSubDirection());
                    } catch (Exception ex) {
                        Gdx.app.log("ERROR", ex.getMessage());
                    }

                }
            }
        });

        server.addListener(new Listener() {
            @Override
            public void disconnected(Connection connection) {
                super.disconnected(connection);
                try {
                    PacMan pacMan = findPacManBId(connection.getID());
                    assert pacMan != null;
                    pacArrayMessage.removePacMan(pacMan.getPacManMessage());
                    pacMans.remove(pacMan);
                    RemovePacket removePacket = new RemovePacket(connection.getID());
                    server.sendToAllTCP(removePacket);
                } catch (Exception ex) {
                    Gdx.app.log("ERROR", ex.getMessage());
                }
            }

            @Override
            public void connected(Connection connection) {
                super.connected(connection);
                server.sendToAllTCP(cellMessage);
                PacMan pacMan = new PacMan(MoveDirection.RIGHT, heroSpawn.get((connection.getID() - 1) % heroSpawn.size()), 1, connection.getID(), cells, MainGrind.this);
                pacMans.add(pacMan);
                pacArrayMessage.addPacMan(pacMan.getPacManMessage());
                server.sendToAllTCP(pacArrayMessage);
            }
        });
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
    }

    public void sendMessage(PacManMessage pacManMessage) {
        server.sendToAllTCP(pacManMessage);
    }

    @Override
    public void render() {
        for (PacMan pacman :
                pacMans) {
            pacman.act(Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }


    private void kryoRegister() {
        Kryo kryo = server.getKryo();
        kryo.register(Vector2.class);
        kryo.register(String.class);
        kryo.register(boolean.class);

        kryo.register(ArrayList.class);
        kryo.register(float.class);

        kryo.register(PacMan.class);

        kryo.register(Cell.class);
        kryo.register(Cell[].class);
        kryo.register(Cell[][].class);
        kryo.register(CellMessage.class);

        kryo.register(MovePacket.class);
        kryo.register(Array.class);
        kryo.register(PacManMessage.class);
        kryo.register(PacArrayMessage.class);
        kryo.register(MoveDirection.class);
        kryo.register(RemovePacket.class);
    }

    private PacMan findPacManBId(int id) {
        for (PacMan pacMan :
                pacMans) {
            if (pacMan.getConnectionId() == id)
                return pacMan;
        }
        return null;
    }
}
