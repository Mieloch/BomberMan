package local.oop.ai;

import local.oop.model.player.PlayerId;

public abstract class AbstractAI implements AI{
    protected PlayerId id;
    public AbstractAI(PlayerId id){
        this.id = id;
    }
    @Override
    public PlayerId getPlayerId() {
       return this.id;
    }
}
