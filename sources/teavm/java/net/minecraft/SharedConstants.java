package net.minecraft;

import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.world.level.storage.DataVersion;
import java.util.Date;

/**
 * SharedConstants stub for EaglerCraft 26.1.2.
 * Provides a hard-coded WorldVersion so DetectedVersion.tryDetectVersion()
 * (which reads /version.json, unavailable in TeaVM) is bypassed.
 */
public class SharedConstants {

    public static final boolean IS_RUNNING_IN_IDE = false;
    public static final boolean SNAPSHOT = false;

    private static final WorldVersion CURRENT_VERSION = new EaglerWorldVersion();

    /** Hard-coded WorldVersion for Minecraft 26.1.2-rc-1. */
    private static final class EaglerWorldVersion implements WorldVersion {
        @Override
        public DataVersion dataVersion() {
            return new DataVersion(4789, "main");
        }
        @Override
        public String id() { return "26.1.2-rc-1"; }
        @Override
        public String name() { return "26.1.2-rc-1"; }
        @Override
        public int protocolVersion() { return 1073742131; }
        @Override
        public PackFormat packVersion(PackType type) {
            if (type == PackType.SERVER_DATA) return PackFormat.create(101, 1);
            return PackFormat.create(84, 0);
        }
        @Override
        public Date buildTime() { return new Date(1744112783000L); }
        @Override
        public boolean stable() { return false; }
    }

    public static WorldVersion getCurrentVersion() {
        return CURRENT_VERSION;
    }

    public static void setVersion(WorldVersion version) {
        // no-op: version is hard-coded for browser
    }

    public static boolean isSnapshot() { return SNAPSHOT; }
    public static String getCurrentVersionName() { return "26.1.2-rc-1"; }
    public static int getProtocolVersion() { return 1073742131; }
    public static int getDataVersion() { return 4789; }
    public static int getWorldVersion() { return 4789; }
    public static int getResourcePackFormat() { return 84; }
    public static int getDataPackFormat() { return 101; }

    public static char[] ILLEGAL_FILE_CHARACTERS =
        new char[]{'/', '\n', '\r', '\t', '\0', '\\', ':', '*', '?', '"', '<', '>', '|'};

    public static boolean isIllegalFilenameChar(char c) {
        for (char ch : ILLEGAL_FILE_CHARACTERS) if (c == ch) return true;
        return false;
    }
}
