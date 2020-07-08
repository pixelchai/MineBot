package com.pixelzerg.minebot.units;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalXZ;
import com.pixelzerg.minebot.Unit;
import com.pixelzerg.minebot.nodes.NotNode;
import com.pixelzerg.minebot.nodes.ParallelNode;
import com.pixelzerg.minebot.nodes.UntilUnsuccessful;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
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
//                getTree().start();
                IBaritone ba = BaritoneAPI.getProvider().getPrimaryBaritone();
                ba.getCustomGoalProcess().setGoalAndPath(new GoalXZ(0, 50));
            }
        }
    }

    private Unit getTree(){
        return new ParallelNode(
                new UntilUnsuccessful(new NotNode(new WoolDetectorUnit()))
//                new UntilUnsuccessful(new JumpUnit())
        );
    }
}
