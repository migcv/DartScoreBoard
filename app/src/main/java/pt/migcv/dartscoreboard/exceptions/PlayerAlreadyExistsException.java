package pt.migcv.dartscoreboard.exceptions;

import pt.migcv.dartscoreboard.activities.PlayersActivity;

/**
 * Created by Miguel on 06/01/2017.
 */

public class PlayerAlreadyExistsException extends DartsException {

    public PlayerAlreadyExistsException(String playerName) {
        super(playerName);
    }

    @Override
    public String getMessage() {
        return "<" + this.getClass().getName() + ">: Player <" + element + "> already exists!";
    }
}
