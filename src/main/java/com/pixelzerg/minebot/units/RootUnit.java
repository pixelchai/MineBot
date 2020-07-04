package com.pixelzerg.minebot.units;

import com.pixelzerg.minebot.Unit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RootUnit extends Unit {
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        Minecraft mc = Minecraft.getInstance();

        if (player != null) {
            String msg = event.getMessage().getString();
            if (msg.contentEquals("<" + player.getDisplayName().getString() + "> start")) {
                new MoveToUnit(player.getPosition().add(new Vec3i(10, 0, 0))).start();
            }
        }
    }
}
