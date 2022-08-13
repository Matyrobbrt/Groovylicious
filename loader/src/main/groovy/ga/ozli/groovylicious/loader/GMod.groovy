package ga.ozli.groovylicious.loader

import groovy.transform.CompileStatic
import org.codehaus.groovy.transform.GroovyASTTransformationClass

@CompileStatic
@GroovyASTTransformationClass(value = 'ga.ozli.groovylicious.loader.transformer.GModASTTransformer')
@interface GMod {
    String value()
}
