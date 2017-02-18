package pt.migcv.dartscoreboard.core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Miguel on 07/01/2017.
 */

public class Player {
    private String name;

    private ArrayList<Integer[]> scoreTurns = new ArrayList<Integer[]>();
    private ArrayList<Integer[]> multiplierTurns = new ArrayList<Integer[]>();
    private ArrayList<Integer> finalScoreTurns = new ArrayList<Integer>();

    private int totalThrows = 0;
    private int throwsMissed = 0;
    private int bestTurn = 0;
    private int worstTurn = 181;
    private int x1Hits = 0;
    private int x2Hits = 0;
    private int x3Hits = 0;

    private int bestHouse = 0;
    private Integer[] bestHouseArray;

    private float scorePerTurn = 0;

    public Player(String name) {
        this.name = name;
    }

    public void addTurn(int turn, Integer[] scoreTurn, Integer[] multiplierTurn, int finalScore) {
        scoreTurns.add(turn, scoreTurn);
        multiplierTurns.add(turn, multiplierTurn);
        finalScoreTurns.add(turn, finalScore);

        int totalScore = 0;
        x1Hits = 0;
        x2Hits = 0;
        x3Hits = 0;
        throwsMissed = 0;
        bestHouseArray = new Integer[25];

        for(int i = 0; i < scoreTurns.size(); i++) {
            for(int j = 0; j < 3; j++) {
                /* Misses Count */
                if(scoreTurns.get(i)[j] == 0) {
                    throwsMissed++;
                }
                /* Multiplier Counter */
                if(multiplierTurns.get(i)[j] == 1 && scoreTurns.get(i)[j] != 0) {
                    x1Hits++;
                }
                else if(multiplierTurns.get(i)[j] == 2) {
                    x2Hits++;
                }
                else if(multiplierTurns.get(i)[j] == 2) {
                    x3Hits++;
                }
                /* Best House */
                if(scoreTurns.get(i)[j] != 0 && bestHouseArray[scoreTurns.get(i)[j]-1] == null) {
                    bestHouseArray[scoreTurns.get(i)[j]-1] = 1;
                }
                else if(scoreTurns.get(i)[j] != 0) {
                    bestHouseArray[scoreTurns.get(i)[j]-1]++;
                }
            }
            int score = scoreTurns.get(i)[0] * multiplierTurns.get(i)[0] + scoreTurns.get(i)[1] * multiplierTurns.get(i)[1] + scoreTurns.get(i)[2] * multiplierTurns.get(i)[2];
            if(score > bestTurn) {
                bestTurn = score;
            }
            if(score < worstTurn) {
                worstTurn = score;
            }
            totalScore += score;
        }
        scorePerTurn = totalScore / scoreTurns.size();
        totalThrows = scoreTurns.size() * 3;
        bestHouse = findBestHouse();
    }

    public Object[] getTurnScore(int turn) {
        if(scoreTurns.size() <= turn) {
            return null;
        }
        int finalScore = finalScoreTurns.get(turn) + (scoreTurns.get(turn)[0] * multiplierTurns.get(turn)[0] + scoreTurns.get(turn)[1] * multiplierTurns.get(turn)[1] + scoreTurns.get(turn)[2] * multiplierTurns.get(turn)[2]);
        Object[] res = {scoreTurns.get(turn), multiplierTurns.get(turn), finalScore};
        return res;
    }

    public ArrayList<Integer[]> getMultiplierTurns() {
        return multiplierTurns;
    }

    public ArrayList<Integer[]> getScoreTurns() {
        return scoreTurns;
    }

    public ArrayList<Integer> getFinalScoreTurns() {
        return finalScoreTurns;
    }

    public int getThrowsMissed() {
        return throwsMissed;
    }

    public String getName() {
        return name;
    }

    public int getBestHouse() {
        return bestHouse;
    }

    public int getBestTurn() {
        return bestTurn;
    }

    public int getWorstTurn() {
        return worstTurn > 180? 0 : worstTurn;
    }

    public int getX1Hits() {
        return x1Hits;
    }

    public int getX2Hits() {
        return x2Hits;
    }

    public int getX3Hits() {
        return x3Hits;
    }

    public float getScorePerTurn() {
        return scorePerTurn;
    }

    public int getTotalThrows() {
        return totalThrows;
    }

    private int findBestHouse() {
        int bestHouse = 0, timesHitted = 0;
        for(int i = 0; i < bestHouseArray.length; i++) {
            if(bestHouseArray[i] != null) {
                if(timesHitted < bestHouseArray[i]) {
                    bestHouse = i;
                    timesHitted = bestHouseArray[i];
                }
            }
        }
        return bestHouse;
    }
}
