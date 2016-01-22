package local.oop.ai;

import local.oop.model.ArenaState;
import local.oop.model.Command;
import local.oop.model.Player;
import local.oop.model.player.PlayerId;

/**
 * Created by Echomil on 2016-01-22.
 */
public abstract class AbstractAI implements AI{
    protected PlayerId id;

    AbstractAI(PlayerId id){
        this.id = id;
    }




    @Override
    public PlayerId getPlayerId() {
       return this.id;
    }
}
