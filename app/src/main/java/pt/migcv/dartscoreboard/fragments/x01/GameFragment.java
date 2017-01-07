package pt.migcv.dartscoreboard.fragments.x01;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pt.migcv.dartscoreboard.R;
import pt.migcv.dartscoreboard.activities.FragmentActivity;
import pt.migcv.dartscoreboard.core.Darts;
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
        ((FragmentActivity) getActivity()).setTitle(Darts.getSelectedGame() + " : " + x01.game_mode + " : " + x01.score_start);

        ((TextView) view.findViewById(R.id.playerName)).setText("" + x01.getCurrentPlayer().getName());
        ((TextView) view.findViewById(R.id.playerName)).setOnClickListener(playerStats);
        ((TextView) view.findViewById(R.id.playerScore)).setText("" + x01.getPlayerScore(x01.getCurrentPlayer().getName()));

        view.findViewById(R.id.missedButton1).setOnClickListener(missedButton);
        view.findViewById(R.id.missedButton2).setOnClickListener(missedButton);
        view.findViewById(R.id.missedButton3).setOnClickListener(missedButton);

        throw1 = (EditText) view.findViewById(R.id.throw1);
        throw2 = (EditText) view.findViewById(R.id.throw2);
        throw3 = (EditText) view.findViewById(R.id.throw3);

        view.findViewById(R.id.x2Button1).setOnClickListener(multiplierButton1);
        view.findViewById(R.id.x3Button1).setOnClickListener(multiplierButton1);
        view.findViewById(R.id.x2Button2).setOnClickListener(multiplierButton2);
        view.findViewById(R.id.x3Button2).setOnClickListener(multiplierButton2);
        view.findViewById(R.id.x2Button3).setOnClickListener(multiplierButton3);
        view.findViewById(R.id.x3Button3).setOnClickListener(multiplierButton3);

        view.findViewById(R.id.endTurn).setOnClickListener(endTurn);

        return view;
    }

    View.OnClickListener playerStats = new View.OnClickListener() {
        public void onClick(View v) {
            final Dialog dialog = new Dialog(view.getContext());

            dialog.setContentView(R.layout.dialog_player_stats);
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
            if(x01.turnThrows[0] > 20 || x01.turnThrows[0] < 0) {
                Snackbar.make(view, "1st throw is invalid", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return;
            }
            x01.turnThrows[1] = Integer.parseInt(throw2);
            if(x01.turnThrows[1] > 20 && x01.turnThrows[1] < 0) {
                Snackbar.make(view, "2nd throw is invalid", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return;
            }
            x01.turnThrows[2] = Integer.parseInt(throw3);
            if(x01.turnThrows[2] > 20 && x01.turnThrows[2] < 0) {
                Snackbar.make(view, "3rd throw is invalid", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return;
            }
            x01.endTurn();
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
    };

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
