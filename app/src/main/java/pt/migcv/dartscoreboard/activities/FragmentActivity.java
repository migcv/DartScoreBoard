package pt.migcv.dartscoreboard.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pt.migcv.dartscoreboard.R;
import pt.migcv.dartscoreboard.core.Darts;
import pt.migcv.dartscoreboard.fragments.x01.SelectScoreFragment;

public class FragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        setTitle(Darts.getSelectedGame() + " : Game Details");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FragmentManager fm = getFragmentManager();
        if(Darts.getSelectedGame().equals(Darts.x01)) {
            Fragment fragment = new SelectScoreFragment();
            fm.beginTransaction().add(R.id.activity_fragment, fragment, "SCORE_FRAGMENT").commit();
        }
        else if(Darts.getSelectedGame().equals(Darts.aroundClock)) {

        }
        else if(Darts.getSelectedGame().equals(Darts.cricket)) {

        }
    }
}
