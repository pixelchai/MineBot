package com.pixelzerg.minebot.nodes;

import com.pixelzerg.minebot.Unit;

public class InterruptPoller extends Selector {
    private Unit pollUnit;
    private Unit mainUnit;

    public InterruptPoller(Unit pollUnit, Unit mainUnit){
        this.pollUnit = pollUnit;
        this.mainUnit = mainUnit;

        this.pollUnit.onFailure(this::interrupt);
        this.pollUnit.onSuccessful(() -> this.pollUnit.start());

        this.mainUnit.onUnsuccessful(this::fail);
        this.mainUnit.onSuccessful(this::succeed);

        this.onDone(() -> this.pollUnit.interrupt());
    }

    @Override
    protected void startChildren() {
        pollUnit.start();
        mainUnit.start();
    }

    @Override
    protected void interruptChildren() {
        pollUnit.interrupt();
        mainUnit.interrupt();
    }
}
