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

        // helper events
        UNSUCCESSFUL, // = failure OR interrupted
        DONE, // = failure OR interrupted OR successful
        STARTED,
    }

    private final HashMap<Event, ArrayList<Runnable>> eventHooks = new HashMap<>();

    public Unit(){
        // helper events hooking
        initInternalHooks();

        // registering/unregistering
        onStarted(this::register);
        onDone(this::unregister);
    }

    private void initInternalHooks(){
        onFailure(() -> fireEvent(Event.UNSUCCESSFUL));
        onInterrupted(() -> fireEvent(Event.UNSUCCESSFUL));

        onUnsuccessful(() -> fireEvent(Event.DONE));
        onSuccessful(() -> fireEvent(Event.DONE));
    }

    private void hookEvent(Event event, Runnable callback){
        ArrayList<Runnable> callbacks = eventHooks.getOrDefault(event, new ArrayList<>());
        callbacks.add(callback);
        eventHooks.put(event, callbacks);
    }

    private void fireEvent(Event event){
        LOGGER.debug("Unit: "+this.getClass().getSimpleName()+ ": Firing: " + event);
        for (Runnable callback : eventHooks.getOrDefault(event, new ArrayList<>())){
            callback.run();
        }
    }

    // region event hooking methods
    public Unit onFailure(Runnable callback){
        hookEvent(Event.FAILURE, callback);
        return this;
    }

    public Unit onSuccessful(Runnable callback){
        hookEvent(Event.SUCCESSFUL, callback);
        return this;
    }

    public Unit onInterrupted(Runnable callback){
        hookEvent(Event.INTERRUPTED, callback);
        return this;
    }

    public Unit onUnsuccessful(Runnable callback){
        hookEvent(Event.UNSUCCESSFUL, callback);
        return this;
    }

    public Unit onDone(Runnable callback){
        hookEvent(Event.DONE, callback);
        return this;
    }

    public Unit onStarted(Runnable callback){
        hookEvent(Event.STARTED, callback);
        return this;
    }
    // endregion

    // region event firing methods
    protected void fail(){
        fireEvent(Event.FAILURE);
    }

    protected void succeed(){
        fireEvent(Event.SUCCESSFUL);
    }

    public void interrupt() {
        fireEvent(Event.INTERRUPTED);
    }

    public void start(){
        fireEvent(Event.STARTED);
    }
    // endregion

    private void register(){
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.debug("Unit: Registered "+this.getClass().getSimpleName());
    }

    private void unregister(){
//        // reset internal hooks
//        eventHooks.clear();
//        initInternalHooks();

        // unregister from Forge
        MinecraftForge.EVENT_BUS.unregister(this);
        LOGGER.debug("Unit: Unregistered "+this.getClass().getName());
    }
}
