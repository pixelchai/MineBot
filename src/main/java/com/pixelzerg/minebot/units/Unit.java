package com.pixelzerg.minebot.units;

import java.util.ArrayList;
import java.util.HashMap;

public class Unit {
    private final HashMap<String, ArrayList<Runnable>> eventHooks = new HashMap<>();

    private void hookEvent(String eventName, Runnable callback){
        ArrayList<Runnable> callbacks = eventHooks.getOrDefault(eventName, new ArrayList<>());
        callbacks.add(callback);
        eventHooks.put(eventName, callbacks);
    }

    private void fireEvent(String eventName){
        for (Runnable callback : eventHooks.getOrDefault(eventName, new ArrayList<>())){
            callback.run();
        }
    }

    public void tick(){

    }
}
