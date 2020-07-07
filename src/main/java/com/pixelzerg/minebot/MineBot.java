package com.pixelzerg.minebot;

import com.pixelzerg.minebot.units.RootUnit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("minebot")
public class MineBot
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    private final Unit rootUnit = new RootUnit();

    public MineBot() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // register root unit
        MinecraftForge.EVENT_BUS.register(this.rootUnit);
    }

//    @SubscribeEvent
//    public void onChat(ClientChatReceivedEvent event) {
//        ClientPlayerEntity player = Minecraft.getInstance().player;
//        Minecraft mc = Minecraft.getInstance();
//        String reply = null;
//
//        if (player != null) {
//            String msg = event.getMessage().getString();
//            if (msg.contentEquals("<" + player.getDisplayName().getString() + "> start")) {
//                reply = "How about no :)";
////                KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKey(), true);
//            } else {
//                LOGGER.debug("<" + player.getDisplayName().getString() + "> start");
//                LOGGER.debug(msg);
//            }
//
//            if (reply != null) {
//                player.sendMessage(new StringTextComponent(reply));
//                event.setCanceled(true);
//            }
//        }
//    }
}
