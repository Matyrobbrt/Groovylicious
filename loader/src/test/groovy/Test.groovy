import ga.ozli.groovylicious.loader.BaseGMod
import ga.ozli.groovylicious.loader.GMod
import groovy.transform.CompileStatic

@GMod('yep')
@CompileStatic
class Test implements BaseGMod {
    {
        println modBus
    }
}
