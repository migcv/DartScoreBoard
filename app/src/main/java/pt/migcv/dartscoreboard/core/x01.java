package pt.migcv.dartscoreboard.core;

import java.util.HashMap;

/**
 * Created by Miguel on 06/01/2017.
 */

public class x01 {

    private static int score_start;
    private static HashMap<String, Integer> playersMap = new HashMap<String, Integer>();
    private static String currentPlayer;

    public static Integer[] turnThrows = new Integer[3];
    public static Integer[] turnMultiplier = new Integer[3];

    public static void beginGame(int score) {
        score_start = score;
        for(int i = 0; i < Darts.getTotalPlayers(); i++) {
            playersMap.put(Darts.getPlayer(i), score_start);
        }
        currentPlayer = Darts.getPlayer(0);
    }

    public static void endTurn() {
        int turnScore = 0;
        for(int i = 0; i < 3; i++) {
            turnThrows[i] *= turnMultiplier[i];
            turnScore += turnThrows[i];
        }

    }

    public static String getCurrentPlayer() {
        return currentPlayer;
    }

    public static int getPlayerScore(String name) {
        return playersMap.get(name);
    }

}
