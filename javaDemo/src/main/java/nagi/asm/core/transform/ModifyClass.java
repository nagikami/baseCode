package nagi.asm.core.transform;

import org.objectweb.asm.*;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

import static org.objectweb.asm.Opcodes.*;

/**
 * 使用core api转换类时需要按照一定的顺序处理元素
 * ClassReader获取输入流并读取类文件到字节数组
 * ClassVisitor从字节数组加载类文件结构描述对象，并对描述对象进行修改
 * ClassWriter将新的描述对象转化为字节数组
 * ClassReader的accept方法通过一定顺序调用ClassVisitor的一系列visit方法从ClassReader的
 * 字节数组加载字节数组的不同部分到类文件结构描述对象，每次调用最外层的visit方法发起加载就会产生一个事件
 * 事件包含从字节数组读取的数据，以参数的形式在ClassVisitor间传递，最终最下游的ClassVisitor使用接收到的数据创建描述对象
 * ClassVisitor通过组合下游的ClassVisitor完成对下游visit方法的包装，当前visit方法可以对事件进行
 * 过滤（是否调用下游visit方法）和处理（在调用下游visit方法前后增加处理逻辑）
 */
public class ModifyClass {
    public static void main(String[] args) throws IOException {
        String name = "nagi/asm/sample/HelloWorld";
        byte[] b = modifyClassCommon(name);

        // b = modifyClassImprove(name);

        // print HelloWorld
        int paringOptions = ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG;
        boolean asmCode = true;
        Printer printer = asmCode ? new ASMifier() : new Textifier();
        PrintWriter printWriter = new PrintWriter(System.out, true);
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, printer, printWriter);
        new ClassReader(b).accept(traceClassVisitor, paringOptions);
    }

    /**
     * 适合减少性转换
     */
    public static byte[] modifyClassCommon(String name) throws IOException {
        ClassReader classReader = new ClassReader(name);
        ClassWriter classWriter = new ClassWriter(0);
        // 创建ClassVisitor，组合ClassWriter（作为ClassVisitor链的最后一个ClassVisitor）
        //CustomClassVisitor classVisitor = new CustomClassVisitor(ASM9, classWriter);
        CustomClassVisitor classVisitor = new CustomClassVisitor(ASM9, classWriter, "test", "()V", "attach", "I", ACC_PUBLIC);
        classReader.accept(classVisitor, 0);
        return classWriter.toByteArray();
    }

    /**
     * 当类文件结构描述对象最终由ClassWriter返回时，代表中间未有ClassVisitor过滤或者修改，
     * 则不会产生事件，而是直接复制ClassReader字节数组的相应内容到ClassWriter的字节数组
     * 当ClassWriter未组合ClassReader时，有ASM实现该优化，例如modifyClassCommon()
     * 当ClassWriter组合ClassReader时自动实现该优化，但执行该优化时需要复制类中的所有常量，
     * 对于要增加字段、方法或者指令的转换没有问题，但是对于要删除或者重命名字段的转换则无法提高效率，
     * 因此建议对增加性转换使用此优化
     */
    public static byte[] modifyClassImprove(String name) throws IOException {
        ClassReader classReader = new ClassReader(name);
        // 创建ClassWriter并组合ClassReader，用于数组复制
        ClassWriter classWriter = new ClassWriter(classReader,0);
        // 创建ClassVisitor，组合ClassWriter（作为ClassVisitor链的最后一个ClassVisitor）
        CustomClassVisitor classVisitor = new CustomClassVisitor(ASM9, classWriter);
        classReader.accept(classVisitor, 0);
        return classWriter.toByteArray();
    }
}

class CustomClassVisitor extends ClassVisitor {

    // 指定要删除的方法名和方法描述符
    private String mName;
    private String mDesc;

    // 指定要添加的字段的字段名、类型描述符、修饰符
    private String fName;
    private String fDesc;
    private int fAcc;
    // 要添加的字段是否存在
    private boolean isFiledPresent;

    public CustomClassVisitor(int api) {
        super(api);
    }

    public CustomClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    public CustomClassVisitor(int api, ClassVisitor classVisitor, String mName, String mDesc,
                              String fName, String fDesc, int fAcc) {
        super(api, classVisitor);
        this.mName = mName;
        this.mDesc = mDesc;
        this.fName = fName;
        this.fDesc = fDesc;
        this.fAcc = fAcc;
    }

    // 修改版本号并将事件转发给下游的ClassVisitor的visit方法
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(V1_8, access, name, signature, superName, interfaces);
    }

    // 删除指定的方法
    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals(mName) && descriptor.equals(mDesc)) {
            // 不转发到下游ClassVisitor，这样最后的ClassWriter就不会加载此方法的对象，
            // 也不会将其转为字节数组的一部分，达到删除此方法的目的
            return null;
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        // 如果要添加的字段已经存在，则标识
        if (name.equals(fName)) {
            isFiledPresent = true;
        }
        return super.visitField(access, name, descriptor, signature, value);
    }

    // 在visitEnd里添加字段或者方法，既保证visit访问顺序，也保证只执行一次
    @Override
    public void visitEnd() {
        // 要添加的字段在当前类不存在，则添加
        if (!isFiledPresent) {
            FieldVisitor fieldVisitor = visitField(fAcc, fName, fDesc, null, null);
            // 如果fieldVisitor为null，则表示添加该属性的事件被过滤，否则添加成功，调用visitEnd
            if (fieldVisitor != null) {
                fieldVisitor.visitEnd();
            }
        }
        super.visitEnd();
    }
}
