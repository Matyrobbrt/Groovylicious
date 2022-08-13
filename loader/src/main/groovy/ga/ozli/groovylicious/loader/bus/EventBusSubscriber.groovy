package ga.ozli.groovylicious.loader.bus

import ga.ozli.groovylicious.loader.bus.type.BusType
import ga.ozli.groovylicious.loader.bus.type.ForgeBus
import net.minecraftforge.api.distmarker.Dist

@interface EventBusSubscriber {
    Class<? extends BusType> value() default ForgeBus
    String modId() default ''
    Dist[] dist() default [Dist.CLIENT, Dist.DEDICATED_SERVER]
}