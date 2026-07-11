package java.nio.file.spi;

import net.lax1dude.eaglercraft.v2_6.patch.JarFileSystemProvider;
import java.util.Collections;
import java.util.List;

public abstract class FileSystemProvider {
    public abstract String getScheme();
    public abstract java.nio.file.FileSystem newFileSystem(java.net.URI uri, java.util.Map<String,?> env);
    public abstract java.nio.file.FileSystem getFileSystem(java.net.URI uri);
    public abstract java.nio.file.Path getPath(java.net.URI uri);
    public abstract java.nio.channels.SeekableByteChannel newByteChannel(java.nio.file.Path path, java.util.Set<? extends java.nio.file.OpenOption> options, java.nio.file.attribute.FileAttribute<?>... attrs);

    /** Returns only our JAR provider — avoids TeaVM IR codegen bug with var$0 */
    public static List<FileSystemProvider> installedProviders() {
        return Collections.<FileSystemProvider>singletonList(new JarFileSystemProvider());
    }
}
