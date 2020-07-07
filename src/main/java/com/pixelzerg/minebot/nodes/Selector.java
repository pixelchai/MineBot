package com.pixelzerg.minebot.nodes;

import com.pixelzerg.minebot.Unit;

public abstract class Selector extends Unit {
    protected abstract void startChildren();
    protected abstract void interruptChildren();

    @Override
    public void start() {
        startChildren();
        super.start();
    }

    @Override
    public void interrupt() {
        interruptChildren();
        super.interrupt();
    }
}
