package java.nio.file.spi;

import java.util.Collections;
import java.util.List;

public abstract class FileSystemProvider {
    public abstract String getScheme();
    public abstract java.nio.file.FileSystem newFileSystem(java.net.URI uri, java.util.Map<String,?> env);
    public abstract java.nio.file.FileSystem getFileSystem(java.net.URI uri);
    public abstract java.nio.file.Path getPath(java.net.URI uri);
    public abstract java.nio.channels.SeekableByteChannel newByteChannel(java.nio.file.Path path, java.util.Set<? extends java.nio.file.OpenOption> options, java.nio.file.attribute.FileAttribute<?>... attrs);

    /**
     * Returns a singleton list with our JAR provider.
     * JarFileSystemProvider is loaded dynamically to avoid a compile-time
     * dependency from teavm/patch on teavm/java sources.
     * This also avoids the TeaVM IR Transformer codegen bug where var$0
     * is emitted without a 'let' declaration in static methods.
     */
    public static List<FileSystemProvider> installedProviders() {
        try {
            Class<?> cls = Class.forName("net.lax1dude.eaglercraft.v2_6.patch.JarFileSystemProvider");
            FileSystemProvider provider = (FileSystemProvider) cls.getDeclaredConstructor().newInstance();
            return Collections.singletonList(provider);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
