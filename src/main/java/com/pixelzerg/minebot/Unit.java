package com.pixelzerg.minebot;

import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;

public class Unit {
    protected static final Logger LOGGER = LogManager.getLogger();

    public enum Event {
        // events
        FAIL,
        SUCCESS,
        INTERRUPT,
        START,
    }

    private final HashMap<Event, ArrayList<Runnable>> topCallbacks = new HashMap<>();
    private final HashMap<Event, ArrayList<Runnable>> bottomCallbacks = new HashMap<>();

    public Unit(){
        this.hookBottom(Event.START, this::register);

        this.hookTop(new Event[]{Event.SUCCESS, Event.FAIL, Event.INTERRUPT}, () -> {
            // this Unit finished => unhook all signals coming from below, apart from Interrupt
            bottomCallbacks.put(Event.SUCCESS, new ArrayList<>());
            bottomCallbacks.put(Event.FAIL, new ArrayList<>());
            this.unregister();
        });
    }

    private String getDebugPrefix(){
        return "Unit " + this.getClass().getSimpleName() + ": ";
    }

    public void fireDown(Event event){
        LOGGER.debug(this.getDebugPrefix() + "Fired down: " + event);

        for (Runnable callback : bottomCallbacks.getOrDefault(event, new ArrayList<>())){
            callback.run();
        }
    }

    public void fireUp(Event event){
        if (event == Event.START){
            LOGGER.warn(this.getDebugPrefix() + "Fired Event.STARTED in an invalid direction!");
        }
        LOGGER.debug(this.getDebugPrefix() + "Fired up: " + event);

        for (Runnable callback : topCallbacks.getOrDefault(event, new ArrayList<>())){
            callback.run();
        }
    }

    public void hookBottom(Event event, Runnable callback){
        hookBottom(new Event[]{event}, callback);
    }

    public void hookTop(Event event, Runnable callback){
        hookTop(new Event[]{event}, callback);
    }

    public void hookBottom(Event[] events, Runnable callback){
        for (Event event : events){
            ArrayList<Runnable> callbacks = bottomCallbacks.getOrDefault(event, new ArrayList<>());
            callbacks.add(callback);
            bottomCallbacks.put(event, callbacks);
        }
    }

    public void hookTop(Event[] events, Runnable callback){
        for (Event event : events) {
            if (event == Event.START) {
                LOGGER.warn(this.getDebugPrefix() + "Hooking Event.STARTED in an invalid direction!");
            }
            ArrayList<Runnable> callbacks = topCallbacks.getOrDefault(event, new ArrayList<>());
            callbacks.add(callback);
            topCallbacks.put(event, callbacks);
        }
    }

    public void start(){
        this.fireDown(Event.START);
    }

    private void register(){
        // register Forge
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.debug(this.getDebugPrefix() + "Registered");
    }

    private void unregister(){
        // unregister from Forge
        MinecraftForge.EVENT_BUS.unregister(this);
        LOGGER.debug(this.getDebugPrefix() + "Unregistered");
    }
}
