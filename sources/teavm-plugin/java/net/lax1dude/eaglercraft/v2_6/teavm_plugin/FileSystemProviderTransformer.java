package net.lax1dude.eaglercraft.v2_6.teavm_plugin;

import org.teavm.model.*;

/**
 * No-op transformer — installedProviders() is now implemented directly
 * in the Java patch source (sources/teavm/patch/.../FileSystemProvider.java)
 * to avoid a TeaVM IR codegen bug where var$0 is emitted without a 'let'
 * declaration in static methods, causing ReferenceError at runtime.
 */
public class FileSystemProviderTransformer implements ClassHolderTransformer {
    @Override
    public void transformClass(ClassHolder cls, ClassHolderTransformerContext context) {
        // intentionally empty — static method defined in Java source
    }
}
