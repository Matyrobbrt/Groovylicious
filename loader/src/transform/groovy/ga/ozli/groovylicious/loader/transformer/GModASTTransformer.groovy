//file:noinspection GrMethodMayBeStatic
package ga.ozli.groovylicious.loader.transformer

import ga.ozli.groovylicious.loader.bus.GModEventBus
import ga.ozli.groovylicious.loader.transformer.api.GModTransformer
import groovy.transform.CompileStatic
import net.minecraftforge.eventbus.api.IEventBus
import org.codehaus.groovy.ast.*
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AbstractASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.objectweb.asm.Opcodes

@CompileStatic
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class GModASTTransformer extends AbstractASTTransformation {
    @Override
    void visit(ASTNode[] nodes, SourceUnit source) {
        init(nodes, source)
        final annotation = nodes[0] as AnnotationNode
        final node = nodes[1] as AnnotatedNode
        if (!(node instanceof ClassNode)) throw new IllegalArgumentException('@GMod annotation can only be applied to classes!')
        final cNode = node as ClassNode
        ServiceLoader.load(GModTransformer, getClass().classLoader).each { it.transform(cNode, annotation, source) }
    }

    @CompileStatic
    static final class BusTransformer implements GModTransformer {
        @Override
        void transform(ClassNode classNode, AnnotationNode annotationNode, SourceUnit source) {
            classNode.addProperty(
                    'modBus', Opcodes.ACC_PUBLIC, ClassHelper.make(GModEventBus),
                    null, null, null
            )
            classNode.addProperty(
                    'forgeBus', Opcodes.ACC_PUBLIC, ClassHelper.make(IEventBus),
                    null, null, null
            )
        }
    }
}
