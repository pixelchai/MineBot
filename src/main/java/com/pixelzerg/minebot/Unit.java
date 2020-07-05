package com.pixelzerg.minebot;

import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;

public class Unit {
    protected static final Logger LOGGER = LogManager.getLogger();

    // events
    private static final int EVENT_FAILURE = 0;
    private static final int EVENT_SUCCESSFUL = 1;
    private static final int EVENT_INTERRUPTED = 2;

    // helper events
    private static final int EVENT_UNSUCCESSFUL = 3; // = failure OR interrupted
    private static final int EVENT_DONE = 4; // = failure OR interrupted OR successful
    private static final int EVENT_STARTED = 5;

    private final HashMap<Integer, ArrayList<Runnable>> eventHooks = new HashMap<>();

    public Unit(){
        // helper events hooking
        onFailure(() -> fireEvent(EVENT_UNSUCCESSFUL));
        onInterrupted(() -> fireEvent(EVENT_UNSUCCESSFUL));

        onUnsuccessful(() -> fireEvent(EVENT_DONE));
        onSuccessful(() -> fireEvent(EVENT_DONE));

        // forge registering/unregistering
        onStarted(this::registerForge);
        onDone(this::unregisterForge);
    }

    private void hookEvent(int eventCode, Runnable callback){
        ArrayList<Runnable> callbacks = eventHooks.getOrDefault(eventCode, new ArrayList<>());
        callbacks.add(callback);
        eventHooks.put(eventCode, callbacks);
    }

    private void fireEvent(int eventCode){
        for (Runnable callback : eventHooks.getOrDefault(eventCode, new ArrayList<>())){
            callback.run();
        }
    }

    // region event hooking methods
    public Unit onFailure(Runnable callback){
        hookEvent(EVENT_FAILURE, callback);
        return this;
    }

    public Unit onSuccessful(Runnable callback){
        hookEvent(EVENT_SUCCESSFUL, callback);
        return this;
    }

    public Unit onInterrupted(Runnable callback){
        hookEvent(EVENT_INTERRUPTED, callback);
        return this;
    }

    public Unit onUnsuccessful(Runnable callback){
        hookEvent(EVENT_UNSUCCESSFUL, callback);
        return this;
    }

    public Unit onDone(Runnable callback){
        hookEvent(EVENT_DONE, callback);
        return this;
    }

    public Unit onStarted(Runnable callback){
        hookEvent(EVENT_STARTED, callback);
        return this;
    }
    // endregion

    // region event firing methods
    protected void fail(){
        fireEvent(EVENT_FAILURE);
    }

    protected void succeed(){
        fireEvent(EVENT_SUCCESSFUL);
    }

    public void interrupt() {
        fireEvent(EVENT_INTERRUPTED);
    }

    public void start(){
        fireEvent(EVENT_STARTED);
    }
    // endregion

    private void registerForge(){
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.debug("Unit: Started "+this.getClass().getSimpleName());
    }

    private void unregisterForge(){
        MinecraftForge.EVENT_BUS.unregister(this);
        LOGGER.debug("Unit: Done "+this.getClass().getName());
    }
}
