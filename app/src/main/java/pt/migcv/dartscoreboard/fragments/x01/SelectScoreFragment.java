package pt.migcv.dartscoreboard.fragments.x01;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pt.migcv.dartscoreboard.R;
import pt.migcv.dartscoreboard.core.Darts;
import pt.migcv.dartscoreboard.core.x01;

public class SelectScoreFragment extends Fragment {
    View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_x01_select_score, container, false);

        view.findViewById(R.id.button301).setOnClickListener(selectStartingScore);
        view.findViewById(R.id.button401).setOnClickListener(selectStartingScore);
        view.findViewById(R.id.button501).setOnClickListener(selectStartingScore);
        view.findViewById(R.id.button601).setOnClickListener(selectStartingScore);
        view.findViewById(R.id.button1001).setOnClickListener(selectStartingScore);

        view.findViewById(R.id.straigthButton).setOnClickListener(selectMode);
        view.findViewById(R.id.doubleButton).setOnClickListener(selectMode);

        view.findViewById(R.id.continueButton).setOnClickListener(startGame);

        return view;
    }

    private View.OnClickListener selectStartingScore = new View.OnClickListener() {
        public void onClick(View v) {
            Button scoreButton = (Button) v;
            view.findViewById(R.id.button301).setBackgroundTintList(getResources().getColorStateList(R.color.normalButton));
            view.findViewById(R.id.button401).setBackgroundTintList(getResources().getColorStateList(R.color.normalButton));
            view.findViewById(R.id.button501).setBackgroundTintList(getResources().getColorStateList(R.color.normalButton));
            view.findViewById(R.id.button601).setBackgroundTintList(getResources().getColorStateList(R.color.normalButton));
            view.findViewById(R.id.button1001).setBackgroundTintList(getResources().getColorStateList(R.color.normalButton));
            scoreButton.setBackgroundTintList(getResources().getColorStateList(R.color.appTheme));
            Integer score = Integer.parseInt(scoreButton.getText().toString());
            x01.setStartingScore(score);
        }
    };

    private View.OnClickListener selectMode = new View.OnClickListener() {
        public void onClick(View v) {
            Button modeButton = (Button) v;
            view.findViewById(R.id.straigthButton).setBackgroundTintList(getResources().getColorStateList(R.color.normalButton));
            view.findViewById(R.id.doubleButton).setBackgroundTintList(getResources().getColorStateList(R.color.normalButton));
            modeButton.setBackgroundTintList(getResources().getColorStateList(R.color.appTheme));
            if(modeButton.getText().toString().equals(x01.DOUBLE_OUT)) {
                x01.setMode(x01.DOUBLE_OUT);
            }
            else if(modeButton.getText().toString().equals(x01.STRAIGHT_OUT)) {
                x01.setMode(x01.STRAIGHT_OUT);
            }
        }
    };

    private View.OnClickListener startGame = new View.OnClickListener() {
        public void onClick(View v) {
            x01.beginGame();
            FragmentManager fm = getFragmentManager();
            Fragment fragment = new GameFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.activity_fragment, fragment, "GAME_FRAGMENT");
            ft.commit();
        }
    };

}
