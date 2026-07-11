package net.minecraft.client;

/**
 * PATCHED: DeltaTracker stub for TeaVM browser environment.
 * Provides timing deltas for MC's render/tick system.
 */
public interface DeltaTracker {

    DeltaTracker ZERO = new DefaultValue(0.0f);
    DeltaTracker ONE = new DefaultValue(1.0f);

    float getGameTimeDeltaTicks();
    float getGameTimeDeltaPartialTick(boolean ignoreFrozenGame);
    float getRealtimeDeltaTicks();

    final class DefaultValue implements DeltaTracker {
        private final float value;

        public DefaultValue(float value) {
            this.value = value;
        }

        @Override
        public float getGameTimeDeltaTicks() { return value; }

        @Override
        public float getGameTimeDeltaPartialTick(boolean ignoreFrozenGame) { return value; }

        @Override
        public float getRealtimeDeltaTicks() { return value; }
    }

    /** A simple timer-based DeltaTracker that can be updated each frame. */
    final class Timer implements DeltaTracker {
        private float partialTick;
        private float realtimeDelta;
        private float targetTps;

        public Timer(float targetTps) {
            this.targetTps = targetTps;
            this.partialTick = 0.0f;
            this.realtimeDelta = 0.0f;
        }

        public void updateTimer(long nowMs, boolean frozen) {
            // Simple passthrough — actual timing managed by EaglerCraft game loop
            this.realtimeDelta = 1.0f / targetTps;
        }

        @Override
        public float getGameTimeDeltaTicks() { return partialTick; }

        @Override
        public float getGameTimeDeltaPartialTick(boolean ignoreFrozenGame) { return partialTick; }

        @Override
        public float getRealtimeDeltaTicks() { return realtimeDelta; }

        public void setPartialTick(float v) { this.partialTick = v; }
    }
}
