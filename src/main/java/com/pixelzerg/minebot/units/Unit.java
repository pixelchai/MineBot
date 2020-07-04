package com.pixelzerg.minebot.units;

import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class Unit {
    protected static final Logger LOGGER = LogManager.getLogger();

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
        this.fireEvent(EVENT_STARTED);
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.debug("Unit: Started "+this.getClass().getSimpleName());
    }

    private void done(){
        MinecraftForge.EVENT_BUS.unregister(this);
        this.fireEvent(EVENT_DONE);
        LOGGER.debug("Unit: Done "+this.getClass().getName());
    }
}
