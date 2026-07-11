package net.minecraft.client;

import net.lax1dude.eaglercraft.v2_6.internal.PlatformRuntime;
import net.lax1dude.eaglercraft.v2_6.internal.teavm.ClientMain;
import net.minecraft.client.main.GameConfig;

/**
 * EaglerCraft 26.1.2 browser stub for net.minecraft.client.Minecraft.
 *
 * This stub overrides the real Minecraft class at javac compile time,
 * providing public tick() and render(boolean) for EaglerCraft's rAF loop.
 * The real MC JAR's Minecraft is excluded from the teavm source classpath
 * because our teavm/java source takes precedence.
 */
public class Minecraft {

    private static Minecraft instance;
    protected final GameConfig gameConfig;

    // FPS
    private int frameCount;
    private long lastFpsTime;
    public int currentFps;
    public long currentTime;

    public Minecraft(GameConfig config) {
        Minecraft.instance = this;
        this.gameConfig = config;
        ClientMain.log("[Minecraft] Constructing Minecraft instance...");
        ClientMain.log("[Minecraft] User: " + (config.user != null ? config.user.user.getName() : "Player"));
        ClientMain.log("[Minecraft] Construction complete");
    }

    public static Minecraft getInstance() { return instance; }

    /** Updates input and FPS counter. Called per frame. */
    public void tick() {
        try {
            this.currentTime = PlatformRuntime.getCurrentTimeMillis();
            this.frameCount++;
            long now = PlatformRuntime.getCurrentTimeMillis();
            if (now - this.lastFpsTime >= 1000L) {
                this.currentFps = this.frameCount;
                this.frameCount = 0;
                this.lastFpsTime = now;
            }
        } catch (Throwable t) {
            ClientMain.warn("[Minecraft] tick() error: " + t.getMessage());
        }
    }

    /** Renders one frame. Overridden by real MC or subclass renderers. */
    public void render(boolean renderLevel) {}

    public void run() {}
    public void stop() {}
    public boolean isSameThread() { return true; }
    public void execute(Runnable r) { r.run(); }
}
