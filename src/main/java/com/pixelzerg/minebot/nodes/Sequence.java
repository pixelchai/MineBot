package com.pixelzerg.minebot.nodes;

import com.pixelzerg.minebot.Unit;

public class Sequence extends Selector {
    private Unit[] childrenUnits;

    public void Selector(Unit... childrenUnits) {
        this.childrenUnits = childrenUnits;

        // wire up children Units to fire sequentially
        for(int i = 0; i < childrenUnits.length - 1; i++){
            final int childIndex = i;
            this.childrenUnits[i].onSuccessful(() -> this.childrenUnits[childIndex+1].start());

            this.childrenUnits[i].onUnsuccessful(this::fail); // if any child is unsuccessful, Sequence fails
        }

        // the last child Unit succeeded => the sequence as a whole succeeded
        this.childrenUnits[childrenUnits.length - 1].onSuccessful(this::succeed);
    }

    @Override
    protected void startChildren() {
        // start first child unit
        this.childrenUnits[0].start();
    }

    @Override
    protected void interruptChildren() {
        // interrupt all children
        for(Unit childUnit : this.childrenUnits){
            childUnit.interrupt();
        }
    }
}
