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
        return view;
    }

    private View.OnClickListener selectStartingScore = new View.OnClickListener() {
        public void onClick(View v) {
            Button scoreButton = (Button) v;
            Integer score = Integer.parseInt(scoreButton.getText().toString());
            x01.beginGame(score);
            FragmentManager fm = getFragmentManager();
            Fragment fragment = new GameFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.activity_fragment, fragment, "GAME_FRAGMENT");
            ft.commit();
        }
    };

}
