package pt.migcv.dartscoreboard.core;

import java.util.ArrayList;
import java.util.HashMap;

import pt.migcv.dartscoreboard.exceptions.PlayerAlreadyExistsException;

/**
 * Created by Miguel on 06/01/2017.
 */

public class Darts {
    public static final String x01 = "x01";
    public static final String aroundClock = "Around The Clock";
    public static final String cricket = "Cricket";

    private static String selectedGame = null;

    private static ArrayList<Player> playersList = new ArrayList<Player>();

    public static void selectGame(String game) {
        if(game.equals(x01))
            selectedGame = x01;
        else if(game.equals(aroundClock))
            selectedGame = aroundClock;
        else if(game.equals(cricket))
            selectedGame = cricket;
        else
            selectedGame = null;
    }

    public static String getSelectedGame() {
        return selectedGame;
    }

    public static void addPlayer(String name) throws PlayerAlreadyExistsException {
        for(int i = 0; i < playersList.size(); i++) {
            System.out.println( i +" Name: <" + name + "> <" + playersList.get(i).getName() + ">");
            if(playersList.get(i).getName().equals(name)) {
                System.out.println("JA EXISTE!!!");
                throw new PlayerAlreadyExistsException(name);
            }
        }
        if(!name.isEmpty()) {
            playersList.add(new Player(name));
        }
    }

    public static int getTotalPlayers() {
        return playersList.size();
    }

    public static Player getPlayer(int i) {
        return playersList.get(i);
    }

    public static void cleanPlayersList() {
        playersList = new ArrayList<Player>();
    }
}
