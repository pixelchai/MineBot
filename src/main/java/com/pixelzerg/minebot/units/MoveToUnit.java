package com.pixelzerg.minebot.units;
import com.pixelzerg.minebot.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// basic movement Unit just for debugging purposes

public class MoveToUnit extends Unit {
    private static final Logger LOGGER = LogManager.getLogger();
    private BlockPos targetPos;

    public MoveToUnit(BlockPos targetPos){
        this.targetPos = targetPos;
    }

    @SubscribeEvent
    public void onTickEvent(TickEvent event){
        LOGGER.info("tiCK!!");
    }
}
