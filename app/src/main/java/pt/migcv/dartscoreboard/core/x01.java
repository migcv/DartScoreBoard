package pt.migcv.dartscoreboard.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Miguel on 06/01/2017.
 */

public class x01 {

    public static final String DOUBLE_OUT = "Double Out";
    public static final String STRAIGHT_OUT = "Straight Out";

    public static int score_start;
    public static String game_mode;

    private static HashMap<String, Integer> playersMap = new HashMap<String, Integer>();

    private static Integer currentPlayer = 0;
    private static ArrayList<Player> playerQueue = new ArrayList<Player>();

    public static Integer[] turnThrows = new Integer[3];
    public static Integer[] turnMultiplier = new Integer[3];

    public static void beginGame() {
        for(int i = 0; i < Darts.getTotalPlayers(); i++) {
            playersMap.put(Darts.getPlayer(i).getName(), score_start);
        }
        for(int i = 0; i < Darts.getTotalPlayers(); i++) {
            Random rand = new Random();
            int n = rand.nextInt(Darts.getTotalPlayers());
            while(playerQueue.contains(Darts.getPlayer(n))) {
                n = rand.nextInt(Darts.getTotalPlayers());
            }
            playerQueue.add(Darts.getPlayer(n));
            System.out.println(i + " - " + playerQueue.get(i));
        }
    }

    public static void endTurn() {
        int turnScore = 0;
        for(int i = 0; i < 3; i++) {
            if(turnMultiplier[i] == null) {
                turnMultiplier[i] = 1;
            }
            System.out.println("Throw " + (i+1) + ": " + turnThrows[i] + " x " + turnMultiplier[i]);
            turnThrows[i] *= turnMultiplier[i];
            turnScore += turnThrows[i];
        }
        int currentScore = playersMap.get(playerQueue.get(currentPlayer).getName()) - turnScore;
        playersMap.put(playerQueue.get(currentPlayer).getName(), currentScore);
        getCurrentPlayer().addTurn(turnThrows, turnMultiplier);
        currentPlayer++;
        if(currentPlayer >= playerQueue.size()) {
            currentPlayer = 0;
        }
        turnThrows = new Integer[3];
        turnMultiplier = new Integer[3];
    }

    public static Player getCurrentPlayer() {
        return playerQueue.get(currentPlayer);
    }

    public static void setStartingScore(int score) {
        score_start = score;
    }

    public static void setMode(String mode) {
        game_mode = mode;
    }

    public static int getPlayerScore(String name) {
        return playersMap.get(name);
    }

}
