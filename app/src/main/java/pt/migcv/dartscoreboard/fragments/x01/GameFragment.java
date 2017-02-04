package pt.migcv.dartscoreboard.fragments.x01;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

import pt.migcv.dartscoreboard.R;
import pt.migcv.dartscoreboard.activities.MainActivity;
import pt.migcv.dartscoreboard.core.Darts;
import pt.migcv.dartscoreboard.core.Player;
import pt.migcv.dartscoreboard.core.x01;

/**
 * Created by Miguel on 06/01/2017.
 */

public class GameFragment extends Fragment {

    View view;
    EditText throw1;
    EditText throw2;
    EditText throw3;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_x01_game, container, false);
        getActivity().setTitle(Darts.getSelectedGame() + " : " + x01.game_mode + " : " + x01.score_start);

        throw1 = (EditText) view.findViewById(R.id.throw1);
        throw2 = (EditText) view.findViewById(R.id.throw2);
        throw3 = (EditText) view.findViewById(R.id.throw3);

        if(x01.getCurrentPlayer().getTurnScore(x01.getTurn() - 1) != null) {
            // {THROWS, MULTIPLIER, SCORE}
            Object[] previousTurnScore = x01.getCurrentPlayer().getTurnScore(x01.getTurn() - 1);

            ((TextView) view.findViewById(R.id.playerScore)).setText("" + previousTurnScore[2]);
            throw1.setText("1".toString(), TextView.BufferType.EDITABLE);
            throw2.setText("" + ((Integer[])previousTurnScore[0])[1]);
            throw3.setText("" + ((Integer[])previousTurnScore[0])[2]);
        }
        else {
            ((TextView) view.findViewById(R.id.playerScore)).setText("" + x01.getPlayerScore(x01.getCurrentPlayer().getName()));
        }

        ((TextView) view.findViewById(R.id.playerName)).setText("" + x01.getCurrentPlayer().getName().toUpperCase());
        ((TextView) view.findViewById(R.id.turn)).setText("" + x01.getTurn());
        ((TextView) view.findViewById(R.id.totalPlayers)).setText("" + x01.getTotalPlayers());
        ((TextView) view.findViewById(R.id.currentPlayer)).setText("" + (x01.getCurrentPlayerIndex() + 1));

        final Dialog turnStart = new Dialog(view.getContext());

        turnStart.setContentView(R.layout.dialog_player_turn);
        turnStart.setTitle("Player's Turn");

        ((TextView)turnStart.findViewById(R.id.playerName)).setText("" + x01.getCurrentPlayer().getName().toUpperCase());
        ((TextView)turnStart.findViewById(R.id.turn)).setText("" + x01.getTurn());

        turnStart.show();

        // Hide after some seconds
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (turnStart.isShowing()) {
                    turnStart.dismiss();
                }
            }
        };

        turnStart.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 3000);

        view.findViewById(R.id.missedButton1).setOnClickListener(missedButton);
        view.findViewById(R.id.missedButton2).setOnClickListener(missedButton);
        view.findViewById(R.id.missedButton3).setOnClickListener(missedButton);

        view.findViewById(R.id.x2Button1).setOnClickListener(multiplierButton1);
        view.findViewById(R.id.x3Button1).setOnClickListener(multiplierButton1);
        view.findViewById(R.id.x2Button2).setOnClickListener(multiplierButton2);
        view.findViewById(R.id.x3Button2).setOnClickListener(multiplierButton2);
        view.findViewById(R.id.x2Button3).setOnClickListener(multiplierButton3);
        view.findViewById(R.id.x3Button3).setOnClickListener(multiplierButton3);

        view.findViewById(R.id.backButton).setOnClickListener(previousTurn);
        view.findViewById(R.id.quitButton).setOnClickListener(quitGame);
        view.findViewById(R.id.endTurn).setOnClickListener(endTurn);

        view.findViewById(R.id.playerName).setOnClickListener(playerStats);
        view.findViewById(R.id.score_layout).setOnClickListener(playersScores);
        view.findViewById(R.id.turn_layout).setOnClickListener(allThrows);
        view.findViewById(R.id.current_turn_layout).setOnClickListener(turnThrows);

        return view;
    }
    /*
     *  DIALOG - Player Stats
     */
    View.OnClickListener playerStats = new View.OnClickListener() {
        public void onClick(View v) {
            final Dialog dialog = new Dialog(view.getContext());

            dialog.setContentView(R.layout.dialog_x01_player_stats);
            dialog.setTitle("Player Stats");
            ((TextView)dialog.findViewById(R.id.playerName)).setText("" + x01.getCurrentPlayer().getName().toUpperCase() + "'s Stats");
            ((TextView)dialog.findViewById(R.id.bestTurn)).setText("" + x01.getCurrentPlayer().getBestTurn());
            ((TextView)dialog.findViewById(R.id.worstTurn)).setText("" + x01.getCurrentPlayer().getWorstTurn());
            ((TextView)dialog.findViewById(R.id.scoreTurn)).setText("" + x01.getCurrentPlayer().getScorePerTurn());
            ((TextView)dialog.findViewById(R.id.throwsMissed)).setText("" + x01.getCurrentPlayer().getThrowsMissed());
            ((TextView)dialog.findViewById(R.id.preferedHouse)).setText("" + x01.getCurrentPlayer().getBestHouse());
            ((TextView)dialog.findViewById(R.id.totalThrows)).setText("" + x01.getCurrentPlayer().getTotalThrows());
            ((TextView)dialog.findViewById(R.id.x1Hits)).setText("" + x01.getCurrentPlayer().getX1Hits());
            ((TextView)dialog.findViewById(R.id.x2Hits)).setText("" + x01.getCurrentPlayer().getX2Hits());
            ((TextView)dialog.findViewById(R.id.x3Hits)).setText("" + x01.getCurrentPlayer().getX3Hits());

            dialog.show();
        }
    };
    /*
     *  DIALOG - Players Scores
     */
    View.OnClickListener playersScores = new View.OnClickListener() {
        public void onClick(View v) {
            final Dialog dialog = new Dialog(view.getContext());

            dialog.setContentView(R.layout.dialog_x01_players_scores);
            dialog.setTitle("Players Scores");

            LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.score_layout);

            ArrayList<Player> leaderBoard = x01.getPlayerQueue();

            for(int i = 0; i < leaderBoard.size(); i++) {
                int playerLowerScore;
                for (int j = i + 1; j < leaderBoard.size(); j++) {
                    if (x01.getPlayerScore(leaderBoard.get(i).getName()) > x01.getPlayerScore(leaderBoard.get(j).getName())) {
                        Player aux = leaderBoard.get(i);
                        leaderBoard.set(i, leaderBoard.get(j));
                        leaderBoard.set(j, aux);
                    }
                }
                System.out.println(i + " - " + leaderBoard.get(i).getName() + " - " + x01.getPlayerScore(leaderBoard.get(i).getName()));

                LinearLayout newll = new LinearLayout(dialog.getContext());
                newll.setOrientation(LinearLayout.HORIZONTAL);
                newll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                TextView playerName = new TextView(dialog.getContext());
                playerName.setText((i + 1) + "   " + leaderBoard.get(i).getName().toUpperCase());
                playerName.setTypeface(null, Typeface.BOLD);
                playerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                newll.addView(playerName, new LayoutParams(500, LayoutParams.MATCH_PARENT));

                TextView playerScore = new TextView(dialog.getContext());
                playerScore.setText("" + x01.getPlayerScore(leaderBoard.get(i).getName()));
                playerScore.setTypeface(null, Typeface.BOLD);
                playerScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                playerScore.setGravity(Gravity.END | Gravity.RIGHT);
                playerScore.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                newll.addView(playerScore, new LayoutParams(150, LayoutParams.MATCH_PARENT));

                ll.addView(newll, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }

            dialog.show();
        }
    };
    /*
     *  DIALOG - Current Turn Throws
     */
    View.OnClickListener turnThrows = new View.OnClickListener() {
        public void onClick(View v) {
            final Dialog dialog = new Dialog(view.getContext());

            dialog.setContentView(R.layout.dialog_x01_current_turn_throws);
            dialog.setTitle("Turn Throws");

            ((TextView) dialog.findViewById(R.id.turn)).setText("" + x01.getTurn());

            LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.turn_throws_layout);

            ArrayList<Player> playerQueue = x01.getPlayerQueue();

            for(int i = 0; i < playerQueue.size(); i++) {
                LinearLayout newll = new LinearLayout(dialog.getContext());
                newll.setOrientation(LinearLayout.HORIZONTAL);
                newll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                TextView playerName = new TextView(dialog.getContext());
                playerName.setText((i + 1) + "   " + playerQueue.get(i).getName().toUpperCase());
                playerName.setTypeface(null, Typeface.BOLD);
                playerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                newll.addView(playerName, new LayoutParams(500, LayoutParams.MATCH_PARENT));

                if(playerQueue.get(i).getScoreTurns().size() >= x01.getTurn()) {
                    TextView playerScore = new TextView(dialog.getContext());
                    playerScore.setText("" + playerQueue.get(i).getScoreTurns().get(x01.getTurn() - 1)[0] + " x" + playerQueue.get(i).getMultiplierTurns().get(x01.getTurn() - 1)[0] + "\n" + playerQueue.get(i).getScoreTurns().get(x01.getTurn() - 1)[1] + " x" + playerQueue.get(i).getMultiplierTurns().get(x01.getTurn() - 1)[1] + "\n" + playerQueue.get(i).getScoreTurns().get(x01.getTurn() - 1)[2] + " x" + playerQueue.get(i).getMultiplierTurns().get(x01.getTurn() - 1)[2]);
                    playerScore.setTypeface(null, Typeface.BOLD);
                    playerScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    playerScore.setGravity(Gravity.END | Gravity.RIGHT);
                    playerScore.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                    newll.addView(playerScore, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                }
                else if(x01.getCurrentPlayerIndex() == i) {
                    TextView playerScore = new TextView(dialog.getContext());
                    playerScore.setText("Playing...");
                    playerScore.setTypeface(null, Typeface.BOLD);
                    playerScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    playerScore.setGravity(Gravity.END | Gravity.RIGHT);
                    playerScore.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                    newll.addView(playerScore, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                }
                else {
                    TextView playerScore = new TextView(dialog.getContext());
                    playerScore.setText("Waiting...");
                    playerScore.setTypeface(null, Typeface.BOLD);
                    playerScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    playerScore.setGravity(Gravity.END | Gravity.RIGHT);
                    playerScore.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                    newll.addView(playerScore, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                }

                ll.addView(newll, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }

            dialog.show();
        }
    };
    /*
     *  DIALOG - All Throws of Player
     */
    View.OnClickListener allThrows = new View.OnClickListener() {
        public void onClick(View v) {
            final Dialog dialog = new Dialog(view.getContext());

            dialog.setContentView(R.layout.dialog_x01_all_turn_throws);
            dialog.setTitle("All Throws");

            ((TextView) dialog.findViewById(R.id.playerName)).setText(x01.getCurrentPlayer().getName().toUpperCase());

            LinearLayout ll = (LinearLayout) dialog.findViewById(R.id.throws_layout);

            for(int i = 0; i < x01.getTurn() - 1; i++) {
                LinearLayout newll = new LinearLayout(dialog.getContext());
                newll.setOrientation(LinearLayout.HORIZONTAL);
                newll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                if(x01.getCurrentPlayer().getScoreTurns().size() > 0) {
                    TextView playerScore = new TextView(dialog.getContext());
                    playerScore.setText((i + 1) + "   " + x01.getCurrentPlayer().getScoreTurns().get(i)[0] + " x" + x01.getCurrentPlayer().getMultiplierTurns().get(i)[0] + "   " + x01.getCurrentPlayer().getScoreTurns().get(i)[1] + " x" + x01.getCurrentPlayer().getMultiplierTurns().get(i)[1] + "   " + x01.getCurrentPlayer().getScoreTurns().get(i)[2] + " x" + x01.getCurrentPlayer().getMultiplierTurns().get(i)[2] + "   " + x01.getCurrentPlayer().getFinalScoreTurns().get(i));
                    playerScore.setTypeface(null, Typeface.BOLD);
                    playerScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    playerScore.setGravity(Gravity.END | Gravity.RIGHT);
                    playerScore.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                    newll.addView(playerScore, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                } else {
                    TextView playerScore = new TextView(dialog.getContext());
                    playerScore.setText("Haven't played yet!");
                    playerScore.setTypeface(null, Typeface.BOLD);
                    playerScore.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    playerScore.setGravity(Gravity.END | Gravity.RIGHT);
                    playerScore.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                    newll.addView(playerScore, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
                }

                ll.addView(newll, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }

            dialog.show();
        }
    };

    private View.OnClickListener previousTurn = new View.OnClickListener() {
        public void onClick(View v) {
            if(x01.getTurn() >= 1 && x01.getCurrentPlayerIndex() == 0) {
                Snackbar.make(view, "There's no previous turn!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return;
            }
            x01.previousTurn();

            Fragment frg = null;
            frg = getFragmentManager().findFragmentByTag("GAME_FRAGMENT");
            final FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();
        }
    };

    private View.OnClickListener endTurn = new View.OnClickListener() {
        public void onClick(View v) {
            String throw1 = ((EditText) view.findViewById(R.id.throw1)).getText().toString();
            if(throw1.isEmpty()) {
                Snackbar.make(view, "1st throw is empty", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return;
            }
            String throw2 = ((EditText) view.findViewById(R.id.throw2)).getText().toString();
            if(throw2.isEmpty()) {
                Snackbar.make(view, "2nd throw is empty", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return;
            }
            String throw3 = ((EditText) view.findViewById(R.id.throw3)).getText().toString();
            if(throw3.isEmpty()) {
                Snackbar.make(view, "3rd throw is empty", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return;
            }
            // Turn Score String to Integer
            x01.turnThrows[0] = Integer.parseInt(throw1);
            x01.turnThrows[1] = Integer.parseInt(throw2);
            x01.turnThrows[2] = Integer.parseInt(throw3);
            System.out.println("Player: " + x01.getCurrentPlayer().getName());
            System.out.println("Throws: " + x01.turnThrows[0] + " (*" + x01.turnMultiplier[0] + "), " + x01.turnThrows[1] + " (*" + x01.turnMultiplier[0] + "), " + x01.turnThrows[2] + " (*" + x01.turnMultiplier[0] + ")");
            for(int i = 0; i < 3; i++) {
                if((x01.turnThrows[i] > 20 || x01.turnThrows[i] < 0) && x01.turnThrows[i] != 25) {
                    Snackbar.make(view, (i+1) + "st throw is invalid", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }
            }
            x01.endTurn();
            if(x01.gameEnded()) {
                FragmentManager fm = getFragmentManager();
                Fragment fragment = new FinalScore();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.activity_fragment, fragment, "FINAL_SCORE_FRAGMENT");
                ft.commit();
            }
            else {
                ((EditText) view.findViewById(R.id.throw1)).setText("");
                ((EditText) view.findViewById(R.id.throw2)).setText("");
                ((EditText) view.findViewById(R.id.throw3)).setText("");
                Fragment frg = null;
                frg = getFragmentManager().findFragmentByTag("GAME_FRAGMENT");
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(frg);
                ft.attach(frg);
                ft.commit();
            }
        }
    };

    private View.OnClickListener quitGame = new View.OnClickListener() {
        public void onClick(View v) {
            quitGameVerification();
            /*x01.clean();
            Intent activity = new Intent(view.getContext(), MainActivity.class);
            startActivity(activity);*/
        }
    };

    private void quitGameVerification() {
        final Dialog dialog = new Dialog(view.getContext());

        dialog.setContentView(R.layout.dialog_x01_quit_game);
        dialog.setTitle("Quit");

        dialog.findViewById(R.id.yesButton).setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    x01.clean();
                    Intent activity = new Intent(view.getContext(), MainActivity.class);
                    startActivity(activity);
                }
            }
        );
        dialog.findViewById(R.id.noButton).setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            }
        );

        dialog.show();
    }

    private View.OnClickListener missedButton = new View.OnClickListener() {
        public void onClick(View v) {
            if(v.getId() == R.id.missedButton1) {
                if(throw1.getText().toString().isEmpty()) {
                    throw1.setText("0");
                }
                else if(throw1.getText().toString().equals("0")) {
                    Snackbar.make(view, "1st throw already missed", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }
                else {
                    Snackbar.make(view, "1st throw has already a score", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
            else if(v.getId() == R.id.missedButton2) {
                if(throw2.getText().toString().isEmpty()) {
                    throw2.setText("0");
                }
                else if(throw2.getText().toString().equals("0")) {
                    Snackbar.make(view, "2nd throw already missed", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }
                else {
                    Snackbar.make(view, "2nd throw has already a score", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
            else if(v.getId() == R.id.missedButton3) {
                if(throw3.getText().toString().isEmpty()) {
                    throw3.setText("0");
                }
                else if(throw3.getText().toString().equals("0")) {
                    Snackbar.make(view, "3rd throw already missed", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    return;
                }
                else {
                    Snackbar.make(view, "3rd throw has already a score", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        }
    };

    private View.OnClickListener multiplierButton1 = new View.OnClickListener() {
        public void onClick(View v){
            Button button = (Button) v;
            if(v.getBackgroundTintList() == getResources().getColorStateList(R.color.appTheme)) {
                view.findViewById(R.id.x2Button1).setBackgroundTintList(getResources().getColorStateList(R.color.blueButton));
                view.findViewById(R.id.x3Button1).setBackgroundTintList(getResources().getColorStateList(R.color.redButton));
                x01.turnMultiplier[0] = 1;
            }
            else {
                view.findViewById(R.id.x2Button1).setBackgroundTintList(getResources().getColorStateList(R.color.blueButton));
                view.findViewById(R.id.x3Button1).setBackgroundTintList(getResources().getColorStateList(R.color.redButton));
                v.setBackgroundTintList(getResources().getColorStateList(R.color.appTheme));
                if (button.getText().toString().equals("x2")) {
                    x01.turnMultiplier[0] = 2;
                } else if (button.getText().toString().equals("x3")) {
                    x01.turnMultiplier[0] = 3;
                }
            }
        }
    };

    private View.OnClickListener multiplierButton2 = new View.OnClickListener() {
        public void onClick(View v){
            Button button = (Button) v;
            if(v.getBackgroundTintList() == getResources().getColorStateList(R.color.appTheme)) {
                view.findViewById(R.id.x2Button2).setBackgroundTintList(getResources().getColorStateList(R.color.blueButton));
                view.findViewById(R.id.x3Button2).setBackgroundTintList(getResources().getColorStateList(R.color.redButton));
                x01.turnMultiplier[1] = 1;
            }
            else {
                view.findViewById(R.id.x2Button2).setBackgroundTintList(getResources().getColorStateList(R.color.blueButton));
                view.findViewById(R.id.x3Button2).setBackgroundTintList(getResources().getColorStateList(R.color.redButton));
                v.setBackgroundTintList(getResources().getColorStateList(R.color.appTheme));
                if (button.getText().toString().equals("x2")) {
                    x01.turnMultiplier[1] = 2;
                } else if (button.getText().toString().equals("x3")) {
                    x01.turnMultiplier[1] = 3;
                }
            }
        }
    };

    private View.OnClickListener multiplierButton3 = new View.OnClickListener() {
        public void onClick(View v){
            Button button = (Button) v;
            if(v.getBackgroundTintList() == getResources().getColorStateList(R.color.appTheme)) {
                view.findViewById(R.id.x2Button3).setBackgroundTintList(getResources().getColorStateList(R.color.blueButton));
                view.findViewById(R.id.x3Button3).setBackgroundTintList(getResources().getColorStateList(R.color.redButton));
                x01.turnMultiplier[2] = 1;
            }
            else {
                view.findViewById(R.id.x2Button3).setBackgroundTintList(getResources().getColorStateList(R.color.blueButton));
                view.findViewById(R.id.x3Button3).setBackgroundTintList(getResources().getColorStateList(R.color.redButton));
                v.setBackgroundTintList(getResources().getColorStateList(R.color.appTheme));
                if (button.getText().toString().equals("x2")) {
                    x01.turnMultiplier[2] = 2;
                } else if (button.getText().toString().equals("x3")) {
                    x01.turnMultiplier[2] = 3;
                }
            }
        }
    };
}
