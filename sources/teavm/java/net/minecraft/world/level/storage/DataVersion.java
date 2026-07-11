package net.minecraft.world.level.storage;

/**
 * PATCHED: DataVersion stub for TeaVM browser environment.
 * Replaces the MC Record-based DataVersion.
 */
public final class DataVersion {
    public static final String MAIN_SERIES = "main";

    private final int version;
    private final String series;

    public DataVersion(int version, String series) {
        this.version = version;
        this.series = series == null ? MAIN_SERIES : series;
    }

    public DataVersion(int version) {
        this(version, MAIN_SERIES);
    }

    public int getVersion() { return version; }
    public int version() { return version; }
    public String getSeries() { return series; }
    public String series() { return series; }

    public boolean isSameSeriesAs(DataVersion other) {
        return this.series.equals(other.series);
    }

    @Override
    public String toString() {
        return "DataVersion[version=" + version + ", series=" + series + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataVersion)) return false;
        DataVersion dv = (DataVersion) o;
        return version == dv.version && series.equals(dv.series);
    }

    @Override
    public int hashCode() {
        return 31 * version + series.hashCode();
    }
}
