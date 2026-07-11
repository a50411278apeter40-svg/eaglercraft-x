package java.nio.file;

/**
 * TeaVM-compatible stub for java.nio.file.Paths.
 */
public final class Paths {
    private Paths() {}

    /**
     * Single-argument overload needed for File.toPath() bytecode injection.
     * TeaVM cannot resolve varargs (String, String...) from bytecode that
     * calls (String) directly.
     */
    public static Path get(String first) {
        return Path.of(first, new String[0]);
    }

    public static Path get(String first, String... more) {
        return Path.of(first, more);
    }

    public static Path get(java.net.URI uri) {
        return Path.of(uri);
    }
}
