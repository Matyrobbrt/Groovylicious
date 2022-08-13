package ga.ozli.groovylicious.loader

import ga.ozli.groovylicious.loader.bus.GModEventBus
import groovy.transform.CompileStatic
import net.minecraftforge.eventbus.api.IEventBus

@CompileStatic
interface BaseGMod {
    default GModEventBus getModBus() {
        throw new IllegalArgumentException('Transformer failed injection')
    }
    default IEventBus getForgeBus() {
        throw new IllegalArgumentException('Transformer failed injection')
    }
}