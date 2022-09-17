package nagi.asm.tree.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

import static org.objectweb.asm.Opcodes.ASM9;

/**
 * 将core api和tree api组合进一条处理链
 * 会先完成core api的处理，然后进行tree api的处理，最后输出结果
 */
public class ComposeModifyClass {
    public static void main(String[] args) throws IOException {
        ClassNode classNode = new ClassNode();
        String name = "nagi/asm/sample/HelloWorld";
        ClassReader classReader = new ClassReader(name);
        ClassWriter classWriter = new ClassWriter(0);
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(classWriter, new PrintWriter(System.out, true));
        TreeClassVisitor treeClassVisitor = new TreeClassVisitor(ASM9, traceClassVisitor);
        classReader.accept(treeClassVisitor, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);

    }
}

/**
 * 通过继承将tree api的处理封装进ClassVisitor，组合进core api的处理链路
 * treeClassVisitor会截断上游的core api链路处理，等上游链路的所有事件处理完后，再进行tree api处理，最后调用accept继续下游的core api链路处理
 */
class TreeClassVisitor extends ClassNode {

    public TreeClassVisitor(int api, ClassVisitor cv) {
        super(api);
        this.cv = cv;
    }

    // 封装core api -> tree api -> core api的转换过程，就和ModifyClass的main方法做的一样，
    // 这里每一段都是对事件的全量处理，而不是一个事件处理的三个阶段
    @Override
    public void visitEnd() {
        // 在完成TreeClassVisitor之前的core api的处理后进行tree api的处理
        RemoveMethodTransformer removeMethodTransformer = new RemoveMethodTransformer(null, "test", "()V");
        removeMethodTransformer.transform(this);
        // 将visitEed替换成tree api的accept方法，继续执行后续的core api处理
        accept(cv);
    }
}

/**
 * 通过组合的方式将tree api的处理封装进ClassVisitor，组合进core api的处理链路
 */
class TreeClassVisitorCompose extends ClassVisitor {

    // 保存下游的core api处理链路
    ClassVisitor next;

    public TreeClassVisitorCompose(int api) {
        super(api);
    }

    public TreeClassVisitorCompose(int api, ClassVisitor classVisitor) {
        // 使用ClassNode截断上游的core api处理链路，在visitEnd进行tree api链路处理，完成后转发到下游的core api链路处理
        super(api, new ClassNode());
        this.next = classVisitor;
    }

    // 封装core api -> tree api -> core api的转换过程，就和ModifyClass的main方法做的一样，
    // 这里每一段都是对事件的全量处理，而不是一个事件处理的三个阶段
    @Override
    public void visitEnd() {
        // 在完成TreeClassVisitor之前的core api的处理后进行tree api的处理
        RemoveMethodTransformer removeMethodTransformer = new RemoveMethodTransformer(null, "test", "()V");
        removeMethodTransformer.transform((ClassNode)this.cv);
        // 将visitEed替换成tree api的accept方法，继续执行后续的core api处理
        ((ClassNode) this.cv).accept(next);
    }
}
