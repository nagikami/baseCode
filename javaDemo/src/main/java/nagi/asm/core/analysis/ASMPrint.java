package nagi.asm.core.analysis;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

import static org.objectweb.asm.Opcodes.ASM9;

/**
 * 分析类结构
 * 使用ClassVisitor扫描类结构（分部分读取字节数组），返回分析结果，处理结果（e.g.打印）
 * 分析类结构并以ASM code或者文本形式输出分析结果
 */
public class ASMPrint {
    public static void main(String[] args) throws IOException {
        // config
        String className = "nagi.asm.sample.HelloWorld";
        // 跳过栈映射帧（记录栈帧在执行某条指令前局部变量表和操作数栈中数据的类型，通常在跳转指令(GOTO、ATHROW)后生成，可以用于数据流分析）和
        // debug内容（字节码指令偏移量和源码行号的映射，局部变量表序号和源码变量名的映射）的读取
        int paringOptions = ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG;
        boolean asmCode = true;

        // 指定分析结果输出形式
        Printer printer = asmCode ? new ASMifier() : new Textifier();
        // 获取输出流
        PrintWriter printWriter = new PrintWriter(System.out, true);
        // 创建TraceClassVisitor（打印当前类，追踪类的转换过程）但是不指定内部ClassVisitor，
        // 表示TraceClassVisitor是最后一个ClassVisitor，相当于生成或转换中的ClassWriter
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, printer, printWriter);
        // ClassReader读取类到字节数组，调用traceClassVisitor分析类结构（按一定顺序调用visit方法），并打印分析结果
        new ClassReader(className).accept(traceClassVisitor, paringOptions);

        // 打印source信息
        PrintClassVisitor printClassVisitor = new PrintClassVisitor(ASM9, null);
        new ClassReader(className).accept(printClassVisitor, 0);
    }
}

class PrintClassVisitor extends ClassVisitor {

    public PrintClassVisitor(int api) {
        super(api);
    }

    public PrintClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visitSource(String source, String debug) {
        System.out.println(String.format("source: %s, debug: %s", source, debug));
        super.visitSource(source, debug);
    }
}
