package ga.ozli.groovylicious.loader

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import net.minecraftforge.eventbus.EventBusErrorMessage
import net.minecraftforge.eventbus.api.BusBuilder
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.fml.ModContainer
import net.minecraftforge.fml.ModLoadingException
import net.minecraftforge.fml.ModLoadingStage
import net.minecraftforge.fml.config.IConfigEvent
import net.minecraftforge.fml.event.IModBusEvent
import net.minecraftforge.forgespi.language.IModInfo
import net.minecraftforge.forgespi.language.ModFileScanData

import java.lang.reflect.Constructor
import java.util.function.Consumer

@Slf4j
@CompileStatic
final class GModContainer extends ModContainer {
    private final Class modClass
    private Object mod
    private final ExtendedModBus modBus

    GModContainer(final IModInfo info, final String className, final ModFileScanData scanData, final ModuleLayer layer) {
        super(info)

        activityMap[ModLoadingStage.CONSTRUCT] = this.&constructMod
        this.configHandler = Optional.of(this::postConfigEvent as Consumer<IConfigEvent>)
        this.contextExtension = { null }

        modBus = new ExtendedModBus(BusBuilder.builder()
                .setExceptionHandler {bus, event, listeners, i, cause -> log.error('Failed to process mod event: {}', new EventBusErrorMessage(event, i, listeners, cause)) }
                .setTrackPhases(false)
                .markerType(IModBusEvent)
                .build())

        final module = layer.findModule(info.owningFile.moduleName()).orElseThrow()
        modClass = Class.forName(module, className)
        log.debug('Loaded GMod class {} on loader {} and module {}', className, modClass.classLoader, module)
    }

    private void constructMod() {
        try {
            log.debug('Loading mod class {} for {}', modClass.name, this.modId)
            def modCtor = resolveCtor()
            this.mod = modCtor.newInstance(this)
            log.debug('Successfully loaded mod {}', this.modId)
        } catch (final Throwable t) {
            log.error('Failed to create mod from class {} for modid {}', modClass.name, modId, t)
            throw new ModLoadingException(this.modInfo, ModLoadingStage.CONSTRUCT, 'fml.modloading.failedtoloadmod', t, this.modClass)
        }
    }

    private Constructor resolveCtor() {
        try {
            return modClass.getDeclaredConstructor(GModContainer)
        } catch (NoSuchMethodException ignored) {
            return modClass.getDeclaredConstructor()
        }
    }

    ExtendedModBus getModBus() {
        modBus
    }

    @Override
    boolean matches(Object mod) {
        return mod.is(this.mod)
    }

    @Override
    Object getMod() {
        return mod
    }

    @Override
    protected <T extends Event & IModBusEvent> void acceptEvent(T e) {
        try {
            modBus.post(e)
        } catch (Throwable t) {
            log.error('Caught exception in mod \'{}\' during event dispatch for {}', modId, e, t)
            throw new ModLoadingException(this.modInfo, this.modLoadingStage, 'fml.modloading.errorduringevent', t)
        }
    }

    private void postConfigEvent(final IConfigEvent event) {
        modBus.post(event.self())
    }
}
