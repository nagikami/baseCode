package nagi.asm.tree.analysis;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.objectweb.asm.tree.analysis.BasicVerifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

import static org.objectweb.asm.Opcodes.ASM9;

/**
 * 数据流分析
 * 使用数据集模拟字节码指令的执行，执行状态保存在栈映射帧，包括正向分析（指令执行前的栈映射帧计算指令执行后的栈映射帧）、
 * 反向分析（指令执行后的栈映射帧计算指令执行前的栈映射帧）
 * 要模拟指令i的执行，就是要对于其操作数取值集合中的所有组合形式，找出i的所有可能结果集。
 * 例如，如果整数由以下三个集合表示:P=“正整 数或 null”，N=“负整数或 null”，A=“所有整数”，
 * 要模拟IADD指令，就意味着当两个操作数均为P时返回P，当两个操作数均为N时返回N，在所有其他情况下返回A。
 * 通过对栈映射帧的分析检查可能存在的bug，例如对指令前的执行帧的操作数类型进行校验，判断是否是指令所需的类型，
 * 即类型是否合法；执行帧返回null则代表指令不可到达为废代码；执行帧未变化代表为无效指令序列
 * 控制流分析
 * 模拟所有可能的执行分支，可以计算代码复杂度（流程的圈复杂度）
 */
public class DataFlowAnalysis {
    public static void main(String[] args) throws IOException {
        ClassWriter classWriter = new ClassWriter(0);
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(classWriter, new PrintWriter(System.out, true));
        AnalyzeClassVisitor analyzeClassVisitor = new AnalyzeClassVisitor(ASM9, traceClassVisitor);
        new ClassReader("nagi.asm.sample.HelloWorld").accept(analyzeClassVisitor, 0);
    }
}

/**
 * 组合方法分析器链到MethodVisitor链
 */
class AnalyzeMethodVisitor extends MethodVisitor {

    String owner;
    MethodVisitor next;

    public AnalyzeMethodVisitor(int api) {
        super(api);
    }

    public AnalyzeMethodVisitor(int api, MethodVisitor methodVisitor, String owner, int access, String name, String desc) {
        super(api, new MethodNode(access, name, desc, null, null));
        this.owner = owner;
        this.next = methodVisitor;
    }

    @Override
    public void visitEnd() {
        // 完成上游MethodVisitor链所有事件后执行分析链，对指令前的执行帧的数据进行类型校验
        Analyzer<BasicValue> basicValueAnalyzer = new Analyzer<>(new BasicVerifier());
        try {
            basicValueAnalyzer.analyze(owner, (MethodNode) mv);
        } catch (AnalyzerException e) {
            e.printStackTrace();
        }
        // 转发到下游MethodVisitor链
        ((MethodNode) mv).accept(next);
    }
}

class AnalyzeClassVisitor extends ClassVisitor {

    public AnalyzeClassVisitor(int api) {
        super(api);
    }

    public AnalyzeClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (methodVisitor != null) {
            methodVisitor = new AnalyzeMethodVisitor(ASM9, methodVisitor, "nagi/asm/sample/HelloWorld", access, name, descriptor);
        }
        return methodVisitor;
    }
}
