package com.pixelzerg.minebot.units;

import com.pixelzerg.minebot.Unit;
import com.pixelzerg.minebot.nodes.Not;
import com.pixelzerg.minebot.nodes.ParallelNode;
import com.pixelzerg.minebot.nodes.UntilUnsuccessful;
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
                getTree().start();
            }
        }
    }

    private Unit getTree(){
        return new ParallelNode(
                new UntilUnsuccessful(new Not(new WoolDetectorUnit())),
                new UntilUnsuccessful(new JumpUnit())
        );
    }
}
