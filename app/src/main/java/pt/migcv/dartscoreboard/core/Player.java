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

    private int totalThrows = 0;
    private int throwsMissed = 0;
    private int bestHouse = 0;
    private int bestTurn = 0;
    private int worstTurn = 181;
    private int x1Hits = 0;
    private int x2Hits = 0;
    private int x3Hits = 0;

    private float scorePerTurn = 0;

    public Player(String name) {
        this.name = name;
    }

    public void addTurn(Integer[] scoreTurn, Integer[] multiplierTurn) {
        scoreTurns.add(scoreTurn);
        multiplierTurns.add(multiplierTurn);
        int totalScore = 0;
        x1Hits = 0;
        x2Hits = 0;
        x3Hits = 0;
        for(int i = 0; i < scoreTurns.size(); i++) {
            for(int j = 0; j < 3; j++) {
                if(scoreTurns.get(i)[j] > bestHouse) {
                    bestHouse = scoreTurns.get(i)[j];
                }
                if(scoreTurns.get(i)[j] == 0) {
                    throwsMissed++;
                }
                if(multiplierTurns.get(i)[j] == 1 && scoreTurns.get(i)[j] != 0) {
                    x1Hits++;
                }
                else if(multiplierTurns.get(i)[j] == 2) {
                    x2Hits++;
                }
                else if(multiplierTurns.get(i)[j] == 2) {
                    x3Hits++;
                }
            }
            int score = scoreTurns.get(i)[0] + scoreTurns.get(i)[1] + scoreTurns.get(i)[2];
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
    }

    public ArrayList<Integer[]> getMultiplierTurns() {
        return multiplierTurns;
    }

    public ArrayList<Integer[]> getScoreTurns() {
        return scoreTurns;
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
}
