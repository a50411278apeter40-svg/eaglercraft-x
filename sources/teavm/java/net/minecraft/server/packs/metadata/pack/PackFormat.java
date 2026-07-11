package net.minecraft.server.packs.metadata.pack;

/**
 * PATCHED: PackFormat stub for TeaVM browser environment.
 * Replaces the MC Record-based PackFormat which TeaVM cannot properly compile.
 */
public final class PackFormat {
    private final int major;
    private final int minor;

    private PackFormat(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    /** Static factory used by WorldVersion impl and DetectedVersion. */
    public static PackFormat create(int major, int minor) {
        return new PackFormat(major, minor);
    }

    public int major() { return major; }
    public int minor() { return minor; }

    /** Returns the inclusive range of supported format versions (just major..major for now). */
    public net.minecraft.util.InclusiveRange<Integer> minorRange() {
        return new net.minecraft.util.InclusiveRange<>(minor, minor);
    }

    @Override
    public String toString() {
        return "PackFormat[major=" + major + ", minor=" + minor + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PackFormat)) return false;
        PackFormat pf = (PackFormat) o;
        return major == pf.major && minor == pf.minor;
    }

    @Override
    public int hashCode() {
        return 31 * major + minor;
    }
}
