package com.pixelzerg.minebot.nodes;

import com.pixelzerg.minebot.Unit;

public class UntilUnsuccessful extends Unit {
    private final Unit childUnit;

    public UntilUnsuccessful(Unit childUnit){
        this.childUnit = childUnit;

        this.onStarted(this.childUnit::start);
        this.childUnit.onSuccessful(this.childUnit::start);
        this.childUnit.onFailure(this::fail);
        this.childUnit.onInterrupted(this::interrupt);
        this.onInterrupted(this.childUnit::interrupt);
    }
}
