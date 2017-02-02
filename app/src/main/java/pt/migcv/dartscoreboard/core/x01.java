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

    private static final int[][] POSSIBLE_PLAYS = {{0, 1, 2}, {0, 1}, {1, 2}, {0, 2}, {0}, {1}, {2}};

    public static int score_start;
    public static String game_mode;

    private static boolean gameEnded = false;
    private static String winner;

    private static HashMap<String, Integer> playersMap = new HashMap<String, Integer>(); // <player_name : player_score>

    private static Integer currentPlayer = 0;
    private static ArrayList<Player> playerQueue = new ArrayList<Player>();

    private static Integer turn = 1;

    public static Integer[] turnThrows = new Integer[3];
    public static Integer[] turnMultiplier = {1, 1, 1};

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
        int lowerScore = playersMap.get(getCurrentPlayer().getName());
        int[] turnScore = new int[3];
        turnScore[0] = turnThrows[0] * turnMultiplier[0];
        turnScore[1] = turnThrows[1] * turnMultiplier[1];
        turnScore[2] = turnThrows[2] * turnMultiplier[2];
        System.out.println("Throws for player: " + getCurrentPlayer().getName());
        for(int j = 0; j < POSSIBLE_PLAYS.length; j++) {
            int scoreTry = 0;
            for(int i = 0; i < POSSIBLE_PLAYS[j].length; i++) {
                scoreTry +=  turnScore[POSSIBLE_PLAYS[j][i]];
            }
            scoreTry = playersMap.get(getCurrentPlayer().getName()) - scoreTry;
            if(scoreTry >= 0 && lowerScore > scoreTry) {
                lowerScore = scoreTry;
            }
        }
        if(lowerScore == 0) { // GAME HAS ENDED!!!
            System.out.println("Player: " + getCurrentPlayer().getName() + " won!!!");
            gameEnded = true;
            winner = getCurrentPlayer().getName();
        }
        System.out.println("Player: " + getCurrentPlayer().getName() + " score is " + lowerScore);
        playersMap.put(getCurrentPlayer().getName(), lowerScore);
        getCurrentPlayer().addTurn(turn - 1, turnThrows, turnMultiplier, lowerScore);
        currentPlayer++;
        if(currentPlayer >= playerQueue.size()) {
            currentPlayer = 0;
            turn++;
        }
        turnThrows = new Integer[3];
        turnMultiplier = new Integer[]{1, 1, 1};
    }

    public static void previousTurn() {
        currentPlayer--;
        if(currentPlayer < 0) {
            currentPlayer = playerQueue.size() - 1;
            turn--;
        }
        Object[] aux = getCurrentPlayer().getTurnScore(turn - 1);
        playersMap.put(getCurrentPlayer().getName(), (int) aux[2]);
    }

    public static Player getCurrentPlayer() {
        return playerQueue.get(currentPlayer);
    }

    public static int getCurrentPlayerIndex() {
        return currentPlayer;
    }

    public static int getTotalPlayers() {
        return playerQueue.size();
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

    public static int getTurn() {return turn; }

    public static boolean gameEnded() { return gameEnded; }

    public static String getWinner() { return winner; }

}
