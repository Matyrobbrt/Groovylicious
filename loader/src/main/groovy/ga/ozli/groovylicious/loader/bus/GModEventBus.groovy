package ga.ozli.groovylicious.loader.bus

import groovy.transform.Canonical
import groovy.transform.CompileStatic
import net.minecraftforge.eventbus.api.IEventBus

@Canonical
@CompileStatic
final class GModEventBus implements IEventBus {
    @Delegate
    final IEventBus delegate
}
