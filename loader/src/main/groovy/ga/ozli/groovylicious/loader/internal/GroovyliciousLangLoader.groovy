package ga.ozli.groovylicious.loader.internal

import groovy.transform.Canonical
import groovy.transform.CompileStatic
import groovy.transform.PackageScope
import groovy.util.logging.Slf4j
import net.minecraftforge.forgespi.language.IModInfo
import net.minecraftforge.forgespi.language.IModLanguageProvider
import net.minecraftforge.forgespi.language.ModFileScanData

@Slf4j
@Canonical
@PackageScope
@CompileStatic
final class GroovyliciousLangLoader implements IModLanguageProvider.IModLanguageLoader {
    final String className, modId
    @Override
    public <T> T loadMod(IModInfo info, ModFileScanData modFileScanResults, ModuleLayer layer) {
        final gContainer = Class.forName('ga.ozli.groovylicious.loader.GModContainer', true, Thread.currentThread().contextClassLoader)
        final ctor = gContainer.getDeclaredConstructor(IModInfo, String, ModFileScanData, ModuleLayer)
        if (gContainer.classLoader != Thread.currentThread().contextClassLoader) {
            log.warn('Attempting to load GModContainer from {}: actually resulted in {}', Thread.currentThread().contextClassLoader, gContainer.classLoader)
        }
        return ctor.newInstance(info, className, modFileScanResults, layer) as T
    }
}
