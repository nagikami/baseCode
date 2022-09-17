package nagi.asm.tree.generate;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import static org.objectweb.asm.Opcodes.*;

/**
 * tree api生成类时会比core api多耗时30%，但是可以按照任意顺序生成元素
 */
public class GenerateClass {
    public static void main(String[] args) {
        ClassNode classNode = new ClassNode();
        classNode.version = V11;
        classNode.access = ACC_PUBLIC;
        classNode.name = "nagi/asm/core/generate/Generate_stub";
        classNode.superName = "java/lang/Object";
        classNode.fields.add(new FieldNode(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "value", "I", null, 10));
        classNode.methods.add(new MethodNode(ACC_PUBLIC, "test", "()V", null, null));
    }
}
