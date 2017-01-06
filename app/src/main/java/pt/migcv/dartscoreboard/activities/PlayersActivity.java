package pt.migcv.dartscoreboard.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout.LayoutParams;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.migcv.dartscoreboard.R;
import pt.migcv.dartscoreboard.core.Darts;
import pt.migcv.dartscoreboard.exceptions.PlayerAlreadyExistsException;

public class PlayersActivity extends AppCompatActivity {

    public int number_of_players = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Players");
        setContentView(R.layout.activity_players);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        TextView gameSelected = (TextView) findViewById(R.id.game_selected);
        gameSelected.setText(Darts.getSelectedGame());
        TextView totalPlayers = (TextView) findViewById(R.id.total_players);
        totalPlayers.setText(""+ Darts.getTotalPlayers());
        for(int i = 0; i < 2; i++) {
            ++number_of_players;

            LinearLayout ll = (LinearLayout)findViewById(R.id.playersLayout);

            TextView textView = new TextView(this);
            textView.setText("Player " + number_of_players);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            ll.addView(textView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            EditText editText = new EditText(this);
            editText.addTextChangedListener(new PlayerNameWatcher(this, number_of_players));
            ll.addView(editText, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        }
    }

    public void addNewPlayer(View view) {
        if(number_of_players != Darts.getTotalPlayers()) {
            Snackbar.make(view, "Fill all the names first", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        else {
            ++number_of_players;

            LinearLayout ll = (LinearLayout)findViewById(R.id.playersLayout);

            TextView textView = new TextView(this);
            textView.setText("Player " + number_of_players);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            ll.addView(textView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            EditText editText = new EditText(this);
            editText.addTextChangedListener(new PlayerNameWatcher(this, number_of_players));
            ll.addView(editText, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        }
    }

    public void allPlayersSet(View view) {
        if(Darts.getTotalPlayers() >= 2) {
            Intent scanner = new Intent(this, FragmentActivity.class);
            startActivity(scanner);
        }
        else {
            Snackbar.make(view, "Not enough players", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }



    private class PlayerNameWatcher implements TextWatcher {

        private Activity activity;
        private int playerID;

        public PlayerNameWatcher(Activity activity, int playerID) {
            this.activity = activity;
            this.playerID = playerID;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                Darts.addPlayer(charSequence.toString(), playerID);
                ((TextView) activity.findViewById(R.id.total_players)).setText(""+Darts.getTotalPlayers());
            }
            catch (PlayerAlreadyExistsException e) {
                Snackbar.make(activity.findViewById(R.id.total_players), "Player " + playerID + " name already exists", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }


}
