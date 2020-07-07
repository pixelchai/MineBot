package com.pixelzerg.minebot.nodes;

import com.pixelzerg.minebot.Unit;

public class NotNode extends Unit {
    private final Unit childUnit;

    public NotNode(Unit childUnit){
        this.childUnit = childUnit;

        this.hookBottom(Event.START, () -> {
            this.childUnit.start();
        });

        this.childUnit.hookTop(Event.FAIL, () -> {
            this.fireUp(Event.SUCCESS);
        });
        this.childUnit.hookTop(Event.SUCCESS, () -> {
            this.fireUp(Event.FAIL);
        });
        this.childUnit.hookTop(Event.INTERRUPT, () -> {
            this.fireUp(Event.INTERRUPT);
        });

        this.hookBottom(Event.INTERRUPT, () -> {
            this.childUnit.fireDown(Event.INTERRUPT);
        });
    }
}
