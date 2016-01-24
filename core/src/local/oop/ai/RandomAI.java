package local.oop.ai;

import local.oop.model.ArenaState;
import local.oop.model.Command;
import local.oop.model.player.PlayerId;

import java.util.Random;

/**
 * Created by Echomil on 2016-01-22.
 */
public class RandomAI extends AbstractAI implements  AI{

 public    RandomAI(PlayerId id) {
        super(id);

    }

    @Override
    public Command makeMove(ArenaState state){
        return Command.getRandomMove();
    }

}
