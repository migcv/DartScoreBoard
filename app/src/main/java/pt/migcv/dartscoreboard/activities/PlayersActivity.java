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

    private boolean players_unique = false;

    private EditText[] playersEditText = new EditText[6];

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

        for(int i = 0; i < 2; i++) {
            ++number_of_players;

            LinearLayout ll = (LinearLayout)findViewById(R.id.playersLayout);

            TextView textView = new TextView(this);
            textView.setText("Player " + number_of_players);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            ll.addView(textView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            EditText editText = new EditText(this);
            playersEditText[i] = editText;
            ll.addView(editText, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        }
        totalPlayers.setText(""+ number_of_players);
    }

    public void addNewPlayer(View view) {
        if(isPlayersEditTextEmpty(number_of_players)) {
            Snackbar.make(view, "Fill all the names first", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        else if(number_of_players >= 6) {
            Snackbar.make(view, "No more players allowed!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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
            playersEditText[number_of_players-1] = editText;
            ll.addView(editText, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

            ((TextView) findViewById(R.id.total_players)).setText("" + number_of_players);
        }
    }

    public void allPlayersSet(View view) {
        for (int i = 0; i < playersEditText.length; i++) {
            if (playersEditText[i] != null) {
                try {
                    Darts.addPlayer(playersEditText[i].getText().toString());
                } catch (PlayerAlreadyExistsException e) {
                    Snackbar.make(view, "Players with same name", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Darts.cleanPlayersList();
                    return;
                }
            }
        }
        if (Darts.getTotalPlayers() >= 2) {
            Intent scanner = new Intent(this, FragmentActivity.class);
            startActivity(scanner);
        }
        else {
            Snackbar.make(view, "Not enough players", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

       /*if(Darts.getTotalPlayers() >= 2) {
            Darts.preparePlayers();
            if(players_unique) {
                Intent scanner = new Intent(this, FragmentActivity.class);
                startActivity(scanner);
            }
            else {
                Snackbar.make(view, "Players with same name", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
        else {
            Snackbar.make(view, "Not enough players", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }*/
    }

    private boolean isPlayersEditTextEmpty(int n_players) {
        for(int i = 0; i < n_players; i++) {
            if(playersEditText[i] == null || playersEditText[i].getText().toString().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
