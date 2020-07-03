package com.pixelzerg.minebot.game;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;

public class PlayerController {
    private static Minecraft mc;

    static {
        Minecraft mc = Minecraft.getInstance();

        // set up default options
        mc.gameSettings.autoJump = false;
        mc.gameSettings.viewBobbing = false;
    }

    private static void setKeyBindState(InputMappings.Input input, boolean held){
        KeyBinding.setKeyBindState(input, held);
    }

//    public static void pressInput(InputMappings.Input input, int duration){
//        mc.gameSettings.
//    }

    public static void set(PlayerControl control, boolean held){
        InputMappings.Input input = null;
        switch (control){
            case FORWARD:
                input = mc.gameSettings.keyBindForward.getKey();
                break;
            case LEFT:
                input = mc.gameSettings.keyBindLeft.getKey();
                break;
            case BACK:
                input = mc.gameSettings.keyBindBack.getKey();
                break;
            case RIGHT:
                input = mc.gameSettings.keyBindRight.getKey();
                break;
            case JUMP:
                input = mc.gameSettings.keyBindJump.getKey();
                break;
            case SNEAK:
                input = mc.gameSettings.keyBindSneak.getKey();
                break;
            case SPRINT:
                input = mc.gameSettings.keyBindSprint.getKey();
                break;
            case INVENTORY:
                input = mc.gameSettings.keyBindInventory.getKey();
                break;
            case SWAP_HANDS:
                input = mc.gameSettings.keyBindSwapHands.getKey();
                break;
            case DROP:
                input = mc.gameSettings.keyBindDrop.getKey();
                break;
            case USE:
                input = mc.gameSettings.keyBindUseItem.getKey();
                break;
            case ATTACK:
                input = mc.gameSettings.keyBindAttack.getKey();
                break;
            case PICK:
                input = mc.gameSettings.keyBindPickBlock.getKey();
                break;
            case CHAT:
                input = mc.gameSettings.keyBindChat.getKey();
                break;
            case SCREENSHOT:
                input = mc.gameSettings.keyBindScreenshot.getKey();
                break;
        }
        setKeyBindState(input, held);
    }

    public static void selectHotbar(int i){
        setKeyBindState(mc.gameSettings.keyBindsHotbar[i].getKey(), true);
    }

}