package ga.ozli.groovylicious.loader.internal

import ga.ozli.groovylicious.loader.GMod
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import net.minecraftforge.forgespi.language.ILifecycleEvent
import net.minecraftforge.forgespi.language.IModLanguageProvider
import net.minecraftforge.forgespi.language.ModFileScanData
import org.objectweb.asm.Type

import java.util.function.Consumer
import java.util.function.Supplier

@Slf4j
@CompileStatic
final class GroovyliciousLang implements IModLanguageProvider {
    private static final Type GMOD_TYPE = Type.getType(GMod)

    @Override
    String name() {
        return 'groovylicious'
    }

    @Override
    Consumer<ModFileScanData> getFileVisitor() {
        return { ModFileScanData scanData ->
            final Map<String, IModLanguageLoader> mods = scanData.annotations
                .findAll { it.annotationType() == GMOD_TYPE }
                .collect { new GroovyliciousLangLoader(it.clazz().className, it.annotationData()['value'] as String) }
                .each { log.debug('Found GMod entry-point class {} for mod {}', it.className, it.modId) }
                .collectEntries { [it.modId, it] }
            scanData.addLanguageLoader mods
        }
    }

    @Override
    <R extends ILifecycleEvent<R>> void consumeLifecycleEvent(final Supplier<R> consumeEvent) {


    }
}
