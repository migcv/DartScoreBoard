package pt.migcv.dartscoreboard.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pt.migcv.dartscoreboard.R;
import pt.migcv.dartscoreboard.core.Darts;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button x01Button = (Button) findViewById(R.id.x01Button);
        Button aroundClockButton = (Button) findViewById(R.id.aroundClockButton);
        Button cricketButton = (Button) findViewById(R.id.cricketButton);

        x01Button.setText(Darts.x01);
        aroundClockButton.setText(Darts.aroundClock);
        cricketButton.setText(Darts.cricket);
    }

    public void selectGame(View view) {
        Button gameButton = (Button) view;
        Darts.selectGame(gameButton.getText().toString());
        System.out.println("GAME SELECTED WAS: <" + Darts.getSelectedGame() + ">");
        Intent activity = new Intent(this, PlayersActivity.class);
        startActivity(activity);
    }

    public void rulesGame(View view) {
        final Dialog dialog = new Dialog(view.getContext());
        if(view.getId() == R.id.x01RulesButton) {
            System.out.println("RULES OF X01 SELECTED");
            dialog.setContentView(R.layout.dialog_x01_rules);
            dialog.show();
        }
        else if(view.getId() == R.id.aroundClockRulesButton) {
            System.out.println("RULES OF X01 SELECTED");
            dialog.setContentView(R.layout.dialog_around_clock_rules);
            dialog.show();
        }
        else if(view.getId() == R.id.cricketRulesButton) {
            System.out.println("RULES OF X01 SELECTED");
            dialog.setContentView(R.layout.dialog_cricket_rules);
            dialog.show();
        }
    }
}
