package com.pixelzerg.minebot.units;

import com.pixelzerg.minebot.Unit;
import com.pixelzerg.minebot.game.InputController;
import com.pixelzerg.minebot.game.PlayerControl;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class JumpUnit extends Unit {
    private int elapsedTicks = 0;

    @SubscribeEvent
    public void onTickEvent(TickEvent event) {
        if (elapsedTicks <= 0){
            InputController.set(PlayerControl.JUMP, true);
            LOGGER.info("JUMP KEYDOWN");
        } else if (elapsedTicks == 10){
            InputController.set(PlayerControl.JUMP, false);
            LOGGER.info("JUMP KEYUP");
        } else if (elapsedTicks >= 40){
            LOGGER.info("JUMP DONE");
            elapsedTicks = 0;
            this.succeed();
        }
        elapsedTicks++;
    }
}
