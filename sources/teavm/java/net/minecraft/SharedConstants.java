package net.minecraft;

import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.world.level.storage.DataVersion;

/**
 * PATCHED: SharedConstants for browser/EaglerCraft environment.
 * Provides a hard-coded WorldVersion so DetectedVersion.tryDetectVersion()
 * (which reads /version.json via Class.getResourceAsStream, unavailable in TeaVM)
 * is bypassed entirely.
 */
public class SharedConstants {
    public static final boolean SNAPSHOT = false;
    public static final int WORLD_VERSION = 4789;
    public static final int PROTOCOL_VERSION = 1073742131;
    public static final int DATA_VERSION = 4789;
    public static final int RESOURCE_PACK_FORMAT = 84;
    public static final int DATA_PACK_FORMAT = 101;

    // Debug fields (all false for production)
    public static final boolean IS_RUNNING_IN_IDE = false;
    public static final boolean DEBUG_SYNCHRONOUS_GL_LOGS = false;
    public static final boolean DEBUG_ALLOW_LOW_SIM_DISTANCE = false;
    public static final boolean DEBUG_SUBTITLES = false;
    public static final boolean DEBUG_PATHFINDING = false;
    public static final boolean DEBUG_NEIGHBORSUPDATE = false;
    public static final boolean DEBUG_EXPERIMENTAL_REDSTONEWIRE_UPDATE_ORDER = false;
    public static final boolean DEBUG_STRUCTURES = false;
    public static final boolean DEBUG_VILLAGE_SECTIONS = false;
    public static final boolean DEBUG_BRAIN = false;
    public static final boolean DEBUG_BEES = false;
    public static final boolean DEBUG_RAIDS = false;
    public static final boolean DEBUG_POI = false;
    public static final boolean DEBUG_GOAL_SELECTOR = false;
    public static final boolean DEBUG_GAME_EVENT_LISTENERS = false;
    public static final boolean DEBUG_SHAPES = false;
    public static final boolean DEBUG_SHOW_SERVER_DEBUG_VALUES = false;
    public static final boolean DEBUG_SOCIAL_INTERACTIONS = false;
    public static final boolean DEBUG_HOTKEYS = false;
    public static final boolean DEBUG_CURSOR_POS = false;
    public static final boolean DEBUG_RENDER_UI_LAYERING_RECTANGLES = false;
    public static final boolean DEBUG_SHUFFLE_MODELS = false;
    public static final boolean DEBUG_SHUFFLE_UI_RENDERING_ORDER = false;
    public static final boolean DEBUG_ACTIVE_TEXT_AREAS = false;
    public static final boolean DEBUG_ENTITY_BLOCK_INTERSECTION = false;
    public static final boolean DEBUG_BREEZE_MOB = false;
    public static final boolean DEBUG_OPEN_INCOMPATIBLE_WORLDS = false;
    public static final boolean DEBUG_FORCE_ONBOARDING_SCREEN = false;
    public static final boolean DEBUG_FORCE_TELEMETRY = false;
    public static final boolean DEBUG_DONT_SEND_TELEMETRY_TO_BACKEND = false;
    public static final boolean DEBUG_BYPASS_REALMS_VERSION_CHECK = false;
    public static final boolean DEBUG_WORLD_RECREATE = false;
    public static final boolean DEBUG_VALIDATE_RESOURCE_PATH_CASE = false;
    public static final boolean DEBUG_DISABLE_BELOW_ZERO_RETROGENERATION = false;
    public static final boolean DEBUG_UI_NARRATION = false;
    public static final boolean DEBUG_DEFAULT_SKIN_OVERRIDE = false;
    public static final boolean DEBUG_SHOW_LOCAL_SERVER_ENTITY_HIT_BOXES = false;
    public static final boolean CHECK_DATA_FIXER_SCHEMA = false;

    public static final char[] ILLEGAL_FILE_CHARACTERS = new char[]{'/', '\n', '\r', '\t', '\0', '\\', ':', '*', '?', '"', '<', '>', '|'};

    /**
     * Hard-coded WorldVersion for Minecraft 26.1.2-rc-1.
     * Avoids DetectedVersion.tryDetectVersion() which requires /version.json
     * via Class.getResourceAsStream (not available in TeaVM browser environment).
     */
    private static final WorldVersion CURRENT_VERSION = new WorldVersion() {
        @Override
        public DataVersion dataVersion() {
            return new DataVersion(4789, "main");
        }
        @Override
        public String name() {
            return "26.1.2-rc-1";
        }
        @Override
        public int protocolVersion() {
            return 1073742131;
        }
        @Override
        public PackFormat packVersion(PackType type) {
            if (type == PackType.SERVER_DATA) {
                return PackFormat.create(101, 1);
            }
            // CLIENT_RESOURCES and default
            return PackFormat.create(84, 0);
        }
        @Override
        public java.util.Date buildTime() {
            return new java.util.Date(1744112783000L); // 2026-04-08T12:46:23+00:00
        }
        @Override
        public boolean stable() {
            return false;
        }
    };

    public static WorldVersion getCurrentVersion() {
        return CURRENT_VERSION;
    }

    public static void setVersion(WorldVersion version) {
        // no-op: version is hard-coded for TeaVM
    }

    public static String getCurrentVersionName() {
        return "26.1.2-rc-1";
    }

    public static boolean isSnapshot() {
        return SNAPSHOT;
    }

    public static int getProtocolVersion() {
        return PROTOCOL_VERSION;
    }

    public static int getDataVersion() {
        return DATA_VERSION;
    }

    public static int getWorldVersion() {
        return WORLD_VERSION;
    }

    public static int getResourcePackFormat() {
        return RESOURCE_PACK_FORMAT;
    }

    public static int getDataPackFormat() {
        return DATA_PACK_FORMAT;
    }

    /** Checks if a char is an illegal filename character. */
    public static boolean isIllegalFilenameChar(char c) {
        for (char illegal : ILLEGAL_FILE_CHARACTERS) {
            if (c == illegal) return true;
        }
        return false;
    }
}
