package pt.migcv.dartscoreboard.fragments.x01;

import android.app.Fragment;
import android.app.FragmentManager;
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
import pt.migcv.dartscoreboard.core.x01;

import static android.support.v7.appcompat.R.attr.colorButtonNormal;

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

        ((TextView) view.findViewById(R.id.playerName)).setText("" + x01.getCurrentPlayer());
        ((TextView) view.findViewById(R.id.playerScore)).setText("" + x01.getPlayerScore(x01.getCurrentPlayer()));

        view.findViewById(R.id.missedButton1).setOnClickListener(missedButton);
        view.findViewById(R.id.missedButton2).setOnClickListener(missedButton);
        view.findViewById(R.id.missedButton3).setOnClickListener(missedButton);

        throw1 = (EditText) view.findViewById(R.id.throw1);
        throw2 = (EditText) view.findViewById(R.id.throw2);
        throw3 = (EditText) view.findViewById(R.id.throw3);

        view.findViewById(R.id.x1Button1).setOnClickListener(multiplierButton1);
        view.findViewById(R.id.x2Button1).setOnClickListener(multiplierButton1);
        view.findViewById(R.id.x3Button1).setOnClickListener(multiplierButton1);
        view.findViewById(R.id.x1Button2).setOnClickListener(multiplierButton2);
        view.findViewById(R.id.x2Button2).setOnClickListener(multiplierButton2);
        view.findViewById(R.id.x3Button2).setOnClickListener(multiplierButton2);
        view.findViewById(R.id.x1Button3).setOnClickListener(multiplierButton3);
        view.findViewById(R.id.x2Button3).setOnClickListener(multiplierButton3);
        view.findViewById(R.id.x3Button3).setOnClickListener(multiplierButton3);

        return view;
    }

    private View.OnClickListener endTurn = new View.OnClickListener() {
        public void onClick(View v) {

        }
    };

    private View.OnClickListener missedButton = new View.OnClickListener() {
        public void onClick(View v) {
            if(v.getId() == R.id.missedButton1) {
                if(throw1.getText().toString().isEmpty()) {
                    throw1.setText("0");
                }
                else {
                    Snackbar.make(view, "1st throw has already a score", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
            else if(v.getId() == R.id.missedButton2) {
                if(throw2.getText().toString().isEmpty()) {
                    throw2.setText("0");
                }
                else {
                    Snackbar.make(view, "2nd throw has already a score", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
            else if(v.getId() == R.id.missedButton3) {
                if(throw3.getText().toString().isEmpty()) {
                    throw3.setText("0");
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
            view.findViewById(R.id.x1Button1).setBackgroundTintList(getResources().getColorStateList(R.color.normalButton));
            view.findViewById(R.id.x2Button1).setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
            view.findViewById(R.id.x3Button1).setBackgroundTintList(getResources().getColorStateList(R.color.redButton));
            button.setBackgroundTintList(getResources().getColorStateList(R.color.appTheme));
            if(button.getText().toString().equals("x1")) {
                x01.turnMultiplier[0] = 1;
            }
            else if(button.getText().toString().equals("x2")) {
                x01.turnMultiplier[0] = 2;
            }
            else if(button.getText().toString().equals("x3")) {
                x01.turnMultiplier[0] = 3;
            }
        }
    };

    private View.OnClickListener multiplierButton2 = new View.OnClickListener() {
        public void onClick(View v){
            Button button = (Button) v;
            view.findViewById(R.id.x1Button2).setBackgroundTintList(getResources().getColorStateList(R.color.normalButton));
            view.findViewById(R.id.x2Button2).setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
            view.findViewById(R.id.x3Button2).setBackgroundTintList(getResources().getColorStateList(R.color.redButton));
            v.setBackgroundTintList(getResources().getColorStateList(R.color.appTheme));
            if(button.getText().toString().equals("x1")) {
                x01.turnMultiplier[1] = 1;
            }
            else if(button.getText().toString().equals("x2")) {
                x01.turnMultiplier[1] = 2;
            }
            else if(button.getText().toString().equals("x3")) {
                x01.turnMultiplier[1] = 3;
            }
        }
    };

    private View.OnClickListener multiplierButton3 = new View.OnClickListener() {
        public void onClick(View v){
            Button button = (Button) v;
            view.findViewById(R.id.x1Button3).setBackgroundTintList(getResources().getColorStateList(R.color.normalButton));
            view.findViewById(R.id.x2Button3).setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
            view.findViewById(R.id.x3Button3).setBackgroundTintList(getResources().getColorStateList(R.color.redButton));
            v.setBackgroundTintList(getResources().getColorStateList(R.color.appTheme));
            if(button.getText().toString().equals("x1")) {
                x01.turnMultiplier[2] = 1;
            }
            else if(button.getText().toString().equals("x2")) {
                x01.turnMultiplier[2] = 2;
            }
            else if(button.getText().toString().equals("x3")) {
                x01.turnMultiplier[2] = 3;
            }
        }
    };
}
