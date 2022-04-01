package nagi.java.designpattern.interpreter;

import java.util.HashMap;

/**
 * 符号解释器基类 终结符表达式（不可分割字符，即在文法中不包含对其他符号的引用）
 * 每个运算符都只和左右两个数字有关系
 * 但左右两个数字有可能是一个解析的结果，无论何种类型都是Expression类的实现类
 */
public class SymbolExpression extends Expression{

    protected Expression left;
    protected Expression right;

    public SymbolExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    //默认空实现，由子类实现
    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return 0;
    }
}
