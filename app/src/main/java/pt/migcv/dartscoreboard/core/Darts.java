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
    private static ArrayList<String> playersList = new ArrayList<String>();

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

    public static void addPlayer(String name, int id) throws PlayerAlreadyExistsException {
        if(!playersList.contains(name)) { // See if name exists
            if(playersList.size() <= id-1) { // See if ID already exists
                playersList.add(name);
            }
            else {
                playersList.set(id-1, name);
            }
        }
        else {
            throw new PlayerAlreadyExistsException(name);
        }
    }

    public static int getTotalPlayers() {
        return playersList.size();
    }

    public static String getPlayer(int i) {
        return playersList.get(i);
    }
}
