package com.pixelzerg.minebot.nodes;

import com.pixelzerg.minebot.Unit;

public class ParallelNode extends Unit {

    private final Unit[] childrenUnits;
    private int numSucceeded = 0;

    public ParallelNode(Unit... childrenUnits){
        this.childrenUnits = childrenUnits;

        this.onStarted(() -> {
           for(Unit childUnit : childrenUnits){
               childUnit.start();
           }
        });

        for(Unit childUnit : childrenUnits){
            childUnit.onUnsuccessful(this::fail);
            childUnit.onSuccessful(() -> {
                numSucceeded++;

                if (numSucceeded >= childrenUnits.length){
                    this.succeed();
                }
            });
        }

        this.onDone(() -> {
            for(Unit childUnit : childrenUnits){
                childUnit.interrupt();
            }
        });
    }
}
