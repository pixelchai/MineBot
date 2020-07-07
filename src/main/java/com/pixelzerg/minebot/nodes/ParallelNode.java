package com.pixelzerg.minebot.nodes;

import com.pixelzerg.minebot.Unit;

public class ParallelNode extends Unit {

    private final Unit[] childrenUnits;
    private int numSucceeded = 0;

    public ParallelNode(Unit... childrenUnits){
        this.childrenUnits = childrenUnits;

        this.hookBottom(Event.START, () -> {
           for(Unit childUnit : childrenUnits){
               childUnit.start();
           }
        });

        for(Unit childUnit : childrenUnits){
            childUnit.hookTop(new Event[]{Event.FAIL, Event.INTERRUPT}, () ->
                    this.fireUp(Event.FAIL));

            childUnit.hookTop(Event.SUCCESS, () -> {
                numSucceeded++;

                if (numSucceeded >= childrenUnits.length){
                    this.fireUp(Event.SUCCESS);
                }
            });
        }

        this.hookTop(new Event[]{Event.SUCCESS, Event.FAIL, Event.INTERRUPT}, () -> {
            for(Unit childUnit : childrenUnits){
                childUnit.fireDown(Event.INTERRUPT);
            }
        });

        this.hookBottom(Event.INTERRUPT, () -> {
            for(Unit childUnit : childrenUnits){
                childUnit.fireDown(Event.INTERRUPT);
            }
        });
    }
}
