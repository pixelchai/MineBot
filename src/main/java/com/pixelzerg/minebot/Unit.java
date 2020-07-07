package com.pixelzerg.minebot;

import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;

public class Unit {
    protected static final Logger LOGGER = LogManager.getLogger();

    private enum Event {
        // events
        FAILURE,
        SUCCESSFUL,
        INTERRUPTED,
        STARTED,
    }

    private final HashMap<Event, ArrayList<Runnable>> topCallbacks = new HashMap<>();
    private final HashMap<Event, ArrayList<Runnable>> bottomCallbacks = new HashMap<>();

    public Unit(){
    }

    private String getDebugPrefix(){
        return "Unit " + this.getClass().getSimpleName() + ": ";
    }

    public void fireDown(Event event){
        LOGGER.debug(this.getDebugPrefix() + "Firing down: " + event);

        for (Runnable callback : bottomCallbacks.getOrDefault(event, new ArrayList<>())){
            callback.run();
        }
    }

    public void fireUp(Event event){
        if (event == Event.STARTED){
            LOGGER.warn(this.getDebugPrefix() + "Firing Event.STARTED in an invalid direction!");
        }
        LOGGER.debug(this.getDebugPrefix() + "Firing up: " + event);

        for (Runnable callback : topCallbacks.getOrDefault(event, new ArrayList<>())){
            callback.run();
        }
    }

    public void hookBottom(Event event, Runnable callback){
        ArrayList<Runnable> callbacks = bottomCallbacks.getOrDefault(event, new ArrayList<>());
        callbacks.add(callback);
        bottomCallbacks.put(event, callbacks);
    }

    public void hookTop(Event event, Runnable callback){
        if (event == Event.STARTED){
            LOGGER.warn(this.getDebugPrefix() + "Hooking Event.STARTED in an invalid direction!");
        }
        ArrayList<Runnable> callbacks = topCallbacks.getOrDefault(event, new ArrayList<>());
        callbacks.add(callback);
        topCallbacks.put(event, callbacks);
    }
}
