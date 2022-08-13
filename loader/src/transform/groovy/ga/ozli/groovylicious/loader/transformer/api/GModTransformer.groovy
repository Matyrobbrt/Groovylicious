package ga.ozli.groovylicious.loader.transformer.api

import groovy.transform.CompileStatic
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.control.SourceUnit

@CompileStatic
interface GModTransformer {
    void transform(ClassNode classNode, AnnotationNode annotationNode, SourceUnit source)
}
