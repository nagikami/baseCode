package nagi.asm.core.generate;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.objectweb.asm.Opcodes.*;

/**
 * 使用core api在运行时生成类的字节数组，元素生成需要按照一定的顺序
 * 可以将生成的类（字节数组）存储到硬盘的类文件（.class）中，之后通过类加载器的
 * findClass方法通过类文件加载类（编译器）
 * 也可通过类加载器的defineClass方法加载生成的字节数组直接加载类（动态代理、AOP）
 */
public class GenerateClass {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException, IOException {
        /**
         * 创建ClassWriter，参数为0，代表不自动计算任何东西，必须显式调用visitMaxs计算局部变量表和操作数栈的大小，
         * 如需计算栈映射帧则调用visitFrame
         * 传入ClassWriter.COMPUTE_FRAMES自动计算栈映射帧，ClassWriter效率下降50%
         * ClassWriter.COMPUTE_MAXS自动计算局部变量表和操作数栈的大小，ClassWriter效率下降10%
         * 需要比较自动计算和手动计算的效率，决定是否使用自动计算
         * 使用自动计算后，仍然需要调用visitMaxs，但是参数会被忽略
         */
        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        MethodVisitor methodVisitor;
        classWriter.visit(V11, ACC_PUBLIC | ACC_SUPER, "nagi/asm/core/generate/Generate_stub", null, "java/lang/Object", null);
        {
            // 这里需要添加static修饰符，否则通过反射无法获取到此时设置的默认值
            // （推测通过反射对生成类实例化时只进行了类变量初始化，没有进行成员变量初始化）
            fieldVisitor = classWriter.visitField(ACC_PUBLIC | ACC_STATIC | ACC_FINAL, "value", "I", null, 10);
            fieldVisitor.visitEnd();
        }
        {
            // 生成初始化方法
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            // 只调用一次
            methodVisitor.visitCode();
            // 为方法加载一个带一个参数的指令（Instruction）
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            // 为方法加载一个无参指令（Instruction）/操作码（opcode）
            methodVisitor.visitInsn(RETURN);
            // 手动计算局部变量表和操作数栈的大小
            methodVisitor.visitMaxs(1, 1);
            // 只调用一次
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();
        byte[] b = classWriter.toByteArray();
        //System.out.println(new String(b, StandardCharsets.UTF_8));
        Class<?> aClass = new MyClassLoader().defineClass("nagi.asm.writer.Generate_stub", b);
        Object o = aClass.getDeclaredConstructor().newInstance();
        Field value = aClass.getDeclaredField("value");
        System.out.println(value.get(o));

        // print Generate_stub
        int paringOptions = ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG;
        boolean asmCode = true;
        Printer printer = asmCode ? new ASMifier() : new Textifier();
        PrintWriter printWriter = new PrintWriter(System.out, true);
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, printer, printWriter);
        new ClassReader(b).accept(traceClassVisitor, paringOptions);
    }
}

/**
 * 直接调用defineClass加载ClassWriter生成的字节数组
 */
class MyClassLoader extends ClassLoader {
    public Class<?> defineClass(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }
}

/**
 * 在findClass添加逻辑，ClassWriter生成字节数组后使用defineClass加载
 */
class MyClassLoader2 extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (name.endsWith("_stub")) {
            ClassWriter classWriter = new ClassWriter(0);
            FieldVisitor fieldVisitor;
            MethodVisitor methodVisitor;
            classWriter.visit(V11, ACC_PUBLIC | ACC_SUPER, name.replace(".", "/"), null, "java/lang/Object", null);
            {
                fieldVisitor = classWriter.visitField(ACC_PUBLIC, "value", "I", null, new Integer(10));
                fieldVisitor.visitEnd();
            }
            {
                // 生成初始化方法
                methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
                methodVisitor.visitCode();
                methodVisitor.visitVarInsn(ALOAD, 0);
                methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
                methodVisitor.visitInsn(RETURN);
                methodVisitor.visitMaxs(1, 1);
                methodVisitor.visitEnd();
            }
            classWriter.visitEnd();
            byte[] b = classWriter.toByteArray();
            return defineClass(name, b, 0, b.length);
        }
        return super.findClass(name);
    }
}
