package com.pixelzerg.minebot.units;

import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.HashMap;

public class Unit {
    public static final int EVENT_DONE = 0;
    public static final int EVENT_STARTED = 1;

    private final HashMap<Integer, ArrayList<Runnable>> eventHooks = new HashMap<>();

    public Unit on(int eventCode, Runnable callback){
        ArrayList<Runnable> callbacks = eventHooks.getOrDefault(eventCode, new ArrayList<>());
        callbacks.add(callback);
        eventHooks.put(eventCode, callbacks);
        return this;
    }

    private void fireEvent(int eventCode){
        for (Runnable callback : eventHooks.getOrDefault(eventCode, new ArrayList<>())){
            callback.run();
        }
    }

    public void start(){
        MinecraftForge.EVENT_BUS.register(this);
    }
}
