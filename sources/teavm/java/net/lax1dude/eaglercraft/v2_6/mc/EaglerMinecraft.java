package net.lax1dude.eaglercraft.v2_6.mc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;

/**
 * PATCHED: Bridge class that extends Minecraft to expose the private runTick(boolean)
 * method as a public method callable from EaglerCraft's game loop.
 *
 * In vanilla MC 26.1.2, runTick(boolean) is private. This subclass promotes
 * visibility so EaglerCraft can drive the MC tick+render loop per animation frame.
 */
public class EaglerMinecraft extends Minecraft {

    public EaglerMinecraft(GameConfig config) {
        super(config);
    }

    /**
     * Public bridge to Minecraft's private runTick(boolean isPaused) method.
     * Called once per animation frame by EaglerCraft.renderFrame().
     */
    public void eaglerRunTick(boolean isPaused) {
        runTick(isPaused);
    }
}
