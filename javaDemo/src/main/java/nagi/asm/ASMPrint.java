package nagi.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * print ASM code representation of specify class
 */
public class ASMPrint {
    public static void main(String[] args) throws IOException {
        // config
        String className = "nagi.asm.sample.HelloWorld";
        int paringOptions = ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG;
        boolean asmCode = true;

        // print
        // specify representation of class file
        Printer printer = asmCode ? new ASMifier() : new Textifier();
        // get output stream
        PrintWriter printWriter = new PrintWriter(System.out, true);
        // traceClassVisitor without wrapping next classVisitor
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, printer, printWriter);
        // Get input stream and read byte array from input stream with ClassReader.
        // Construct ClassVisitor(memory structure of class file) with byte array.
        // Write ClassVisitor to output stream with ClassWriter(specify representation of class file).
        new ClassReader(className).accept(traceClassVisitor, paringOptions);
    }
}
