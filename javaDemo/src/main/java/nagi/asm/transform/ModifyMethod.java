package nagi.asm.transform;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

import static org.objectweb.asm.Opcodes.*;

/**
 * 可以使用AnalyzerAdapter为方法添加新指令，它会自动计算栈映射帧，并且提供包含操作数栈大小在内的栈信息，
 * 但是效率通常没有COMPUTE_MAXS高
 * 可以使用LocalVariablesSorter为方法添加新的局部变量，通过newLocal创建局部变量，
 * 将局部变量有序添加到局部变量表
 * 可以使用AdviceAdapter为方法添加新指令，它对构造器生效，且继承自LocalVariablesSorter，可以创建局部变量
 * 需要使用链将这些适配器连接起来
 */
public class ModifyMethod {
    public static void main(String[] args) throws IOException {

        String name = "nagi/asm/sample/HelloWorld";
        //删除指定方法的指定指令（操作码）
        removeOpcode(name);
    }

    public static void removeOpcode(String name) throws IOException {
        ClassReader classReader = new ClassReader(name);
        ClassWriter classWriter = new ClassWriter(0);
        Printer printer = new ASMifier();
        PrintWriter printWriter = new PrintWriter(System.out, true);
        // 打印上游到TraceClassVisitor时的字节数组内容
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(classWriter, printer, printWriter);
        RemoveOpcodeClassVisitor customMethodClassVisitor = new RemoveOpcodeClassVisitor(ASM9, traceClassVisitor);
        classReader.accept(customMethodClassVisitor, 0);
    }
}

class RemoveOpcodeClassVisitor extends ClassVisitor {

    public RemoveOpcodeClassVisitor(int api) {
        super(api);
    }

    public RemoveOpcodeClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor =  super.visitMethod(access, name, descriptor, signature, exceptions);
        // 如果方法名为test，返回删除NOP指令的RemoveOpcodeMethodVisitor
        if (methodVisitor != null && name.equals("test")) {
            methodVisitor = new RemoveOpcodeMethodVisitor(ASM9, methodVisitor);
        }
        return methodVisitor;
    }
}

class RemoveOpcodeMethodVisitor extends MethodVisitor {
    public RemoveOpcodeMethodVisitor(int api) {
        super(api);
    }

    public RemoveOpcodeMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    // 删除方法的NOP指令
    @Override
    public void visitInsn(int opcode) {
        if (opcode != NOP) {
            super.visitInsn(opcode);
        }
    }
}
