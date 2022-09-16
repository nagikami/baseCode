package nagi.asm.transform;

import org.objectweb.asm.*;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

import static org.objectweb.asm.Opcodes.*;

/**
 * 有状态转换
 * 删除一个指令序列，需要依赖依赖之前的指令
 */
public class ModifyMethodStateful {
    public static void main(String[] args) throws IOException {
        String name = "nagi/asm/sample/HelloWorld";
        removeOpcodes(name);
    }

    // 删除无效的ICONST_0->IADD序列
    public static void removeOpcodes(String name) throws IOException {
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

class RemoveOpcodesClassVisitor extends ClassVisitor {

    public RemoveOpcodesClassVisitor(int api) {
        super(api);
    }

    public RemoveOpcodesClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor =  super.visitMethod(access, name, descriptor, signature, exceptions);
        // 返回RemoveOpcodesMethodVisitor，删除无效的ICONST_0->IADD序列
        if (methodVisitor != null) {
            methodVisitor = new RemoveOpcodesMethodVisitor(ASM9, methodVisitor);
        }
        return methodVisitor;
    }
}

/**
 * 模板类
 * 在所有的指令执行方法前添加visitOpcodes调用，保证在不是ICONST_0->IADD序列时可以执行ICONST_0指令
 */
abstract class PatternMethodVisitor extends MethodVisitor {
    protected static final int SEEN_NOTHING = 0;
    protected int state;

    public PatternMethodVisitor(int api) {
        super(api);
    }

    public PatternMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitInsn(int opcode) {
        visitOpcodes();
        super.visitInsn(opcode);
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        visitOpcodes();
        super.visitIntInsn(opcode, operand);
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        visitOpcodes();
        super.visitVarInsn(opcode, var);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        visitOpcodes();
        super.visitTypeInsn(opcode, type);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        visitOpcodes();
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        visitOpcodes();
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        visitOpcodes();
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        visitOpcodes();
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitLdcInsn(Object value) {
        visitOpcodes();
        super.visitLdcInsn(value);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        visitOpcodes();
        super.visitIincInsn(var, increment);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        visitOpcodes();
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        visitOpcodes();
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override
    public void visitMultiANewArrayInsn(String descriptor, int numDimensions) {
        visitOpcodes();
        super.visitMultiANewArrayInsn(descriptor, numDimensions);
    }

    // 处理ICONST_0在栈映射帧之前的情况
    @Override
    public void visitFrame(int type, int numLocal, Object[] local, int numStack, Object[] stack) {
        visitOpcodes();
        super.visitFrame(type, numLocal, local, numStack, stack);
    }

    // 处理ICONST_0在跳转命令之前的情况
    @Override
    public void visitLabel(Label label) {
        visitOpcodes();
        super.visitLabel(label);
    }

    // 处理ICONST_0在方法最后的位置的情况
    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        visitOpcodes();
        super.visitMaxs(maxStack, maxLocals);
    }

    public abstract void visitOpcodes();
}

/**
 * 删除无效的ICONST_0->IADD序列
 */
class RemoveOpcodesMethodVisitor extends PatternMethodVisitor {
    private static final int SEEN_ICONST_0 = 1;

    public RemoveOpcodesMethodVisitor(int api) {
        super(api);
    }

    public RemoveOpcodesMethodVisitor(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    @Override
    public void visitInsn(int opcode) {
        // 上一条指令是ICONST_0且本条指令是IADD时，满足ICONST_0->IADD序列，不执行此序列直接返回
        if (state == SEEN_ICONST_0) {
            if (opcode == IADD) {
                return;
            }
        }
        // 只有一个ICONST_0，不满足序列，执行此前未执行的ICONST_0
        visitOpcodes();
        // 当前为ICONST_0指令，更新状态，不执行直接返回
        if (opcode == ICONST_0) {
            state = SEEN_ICONST_0;
            return;
        }
        super.visitInsn(opcode);
    }

    // 如果上一条指令是ICONST_0，则执行缓存的ICONST_0指令，并将状态置空
    @Override
    public void visitOpcodes() {
        if (state == SEEN_ICONST_0) {
            super.visitInsn(ICONST_0);
        }
        state = SEEN_NOTHING;
    }
}
