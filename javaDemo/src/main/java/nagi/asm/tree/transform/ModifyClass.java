package nagi.asm.tree.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * ClassNode继承了ClassVisitor，可以通过ClassReader的accept读取类到ClassNode
 * 同时ClassNode实现了accept方法，可以使用accept方法将类文件结构描述对象转发给ClassWriter转换为字节数组
 * tree api需要将类文件内容完整读取到内存，在读取或修改部分内容时效率比core api低，但是可以按任意顺序处理元素
 * 当前类将reader、transformer(tree api)、writer分成了三条链，也可以将tree api组合进core api的处理链，合并成一条链，参考ComposeModifyClass
 */
public class ModifyClass {
    public static void main(String[] args) throws IOException {
        ClassNode classNode = new ClassNode();
        String name = "nagi/asm/sample/HelloWorld";
        ClassReader classReader = new ClassReader(name);
        // 读取输入类到ClassNode
        classReader.accept(classNode, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
        // 添加对ClassNode的转换链
        RemoveMethodTransformer removeMethodTransformer = new RemoveMethodTransformer(null, "test", "()V");
        removeMethodTransformer.transform(classNode);
        ClassWriter classWriter = new ClassWriter(0);
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(classWriter, new PrintWriter(System.out, true));
        // 打印ClassNode并输出ClassNode到ClassWriter
        classNode.accept(traceClassVisitor);
    }
}

/**
 * 参考core api使用责任链模式封装对ClassNode的处理
 */
class ClassTransformer {
    protected ClassTransformer transformer;
    public ClassTransformer(ClassTransformer classTransformer) {
        this.transformer = classTransformer;
    }

    public void transform(ClassNode classNode) {
        if (transformer != null) {
            transformer.transform(classNode);
        }
    }
}

class RemoveMethodTransformer extends ClassTransformer {

    private String mName;
    private String mDesc;

    public RemoveMethodTransformer(ClassTransformer classTransformer, String mName, String mDesc) {
        super(classTransformer);
        this.mName = mName;
        this.mDesc = mDesc;
    }

    @Override
    public void transform(ClassNode classNode) {
        classNode.methods.removeIf(methodNode -> methodNode.name.equals(mName) && methodNode.desc.equals(mDesc));
        super.transform(classNode);
    }
}
