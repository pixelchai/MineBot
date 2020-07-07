package com.pixelzerg.minebot.units;

import com.pixelzerg.minebot.Unit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WoolDetectorUnit extends Unit {
    private static boolean isBlockInRange(World world, Block blockToFind, int x, int y, int z, int radius) {
        for (int xSearch = x - radius; xSearch <= x + radius; xSearch++) {
            for (int ySearch = y - radius; ySearch <= y + radius; ySearch++) {
                for (int zSearch = z - radius; zSearch <= z + radius; zSearch++) {
                    BlockPos blockPos = new BlockPos(xSearch, ySearch, zSearch);
                    if (world.getBlockState(blockPos).getBlock() == blockToFind) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SubscribeEvent
    public void onTickEvent(TickEvent event){
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        if(isBlockInRange(mc.world, Blocks.RED_WOOL, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), 20)){
            this.succeed();
        }
    }
}
