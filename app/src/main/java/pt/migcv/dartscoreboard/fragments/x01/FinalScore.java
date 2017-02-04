package pt.migcv.dartscoreboard.fragments.x01;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.migcv.dartscoreboard.R;
import pt.migcv.dartscoreboard.activities.FragmentActivity;
import pt.migcv.dartscoreboard.activities.MainActivity;
import pt.migcv.dartscoreboard.activities.PlayersActivity;
import pt.migcv.dartscoreboard.core.Darts;
import pt.migcv.dartscoreboard.core.x01;

/**
 * Created by Miguel on 06/01/2017.
 */

public class FinalScore extends Fragment {

    View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_x01_final_score, container, false);

        ((TextView) view.findViewById(R.id.winner)).setText(x01.getWinner().toUpperCase());
        view.findViewById(R.id.menuButton).setOnClickListener(menuButton);

        for(int i = 0; i < Darts.getTotalPlayers(); i++) {

            LinearLayout allPlayersLayout = (LinearLayout)view.findViewById(R.id.playersLayout);

            LinearLayout playersLayout = new LinearLayout(view.getContext());
            playersLayout.setLayoutParams(new LinearLayout.LayoutParams(AppBarLayout.LayoutParams.MATCH_PARENT, AppBarLayout.LayoutParams.WRAP_CONTENT));
            playersLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView place = new TextView(view.getContext());
            place.setText("" + (i+1));
            place.setTypeface(null, Typeface.BOLD);
            place.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            playersLayout.addView(place, new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.WRAP_CONTENT, AppBarLayout.LayoutParams.WRAP_CONTENT));

            TextView playerName = new TextView(view.getContext());
            playerName.setText("PlayerName");
            playerName.setTypeface(null, Typeface.BOLD);
            playerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            playersLayout.addView(playerName, new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.WRAP_CONTENT, AppBarLayout.LayoutParams.WRAP_CONTENT));

            TextView playerScore = new TextView(view.getContext());
            playerName.setText("##");
            playerName.setTypeface(null, Typeface.BOLD);
            playerName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            playersLayout.addView(playerScore, new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.WRAP_CONTENT, AppBarLayout.LayoutParams.WRAP_CONTENT));

            allPlayersLayout.addView(playersLayout);
        }

        return view;
    }

    private View.OnClickListener menuButton = new View.OnClickListener() {
        public void onClick(View v) {
            x01.clean();
            Intent activity = new Intent(view.getContext(), MainActivity.class);
            startActivity(activity);
        }
    };
}
