package net.minecraft;

import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.world.level.storage.DataVersion;
import java.util.Date;

/**
 * WorldVersion interface stub for EaglerCraft 26.1.2.
 */
public interface WorldVersion {
    DataVersion dataVersion();
    String id();
    String name();
    int protocolVersion();
    PackFormat packVersion(PackType packType);
    Date buildTime();
    boolean stable();
}
