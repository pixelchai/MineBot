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
        } else if (elapsedTicks >= 20){
            InputController.set(PlayerControl.JUMP, false);
            this.succeed();
        }
        elapsedTicks++;
    }
}
