package ga.ozli.groovylicious.loader

import groovy.transform.Canonical
import groovy.transform.CompileStatic
import net.minecraftforge.eventbus.api.IEventBus

@Canonical
@CompileStatic
final class ExtendedModBus implements IEventBus {
    @Delegate
    final IEventBus delegate
}
