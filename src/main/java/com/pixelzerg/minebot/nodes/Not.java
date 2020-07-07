package com.pixelzerg.minebot.nodes;

import com.pixelzerg.minebot.Unit;

public class Not extends Unit {
    private final Unit childUnit;

    public Not(Unit childUnit){
        this.childUnit = childUnit;

        this.onStarted(this.childUnit::start);
        this.childUnit.onSuccessful(this::fail);
        this.childUnit.onFailure(this::succeed);
        this.childUnit.onInterrupted(this::interrupt);
        this.onInterrupted(this.childUnit::interrupt);
    }
}
