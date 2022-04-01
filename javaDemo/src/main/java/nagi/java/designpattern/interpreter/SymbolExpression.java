package nagi.java.designpattern.interpreter;

import java.util.HashMap;

/**
 * 符号解释器基类
 * 每个运算符都只和左右两个数字有关系
 * 但左右两个数字有可能是一个解析的结果，无论何种类型都是Expression类的实现类
 */
public class SymbolExpression extends Expression{
    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return 0;
    }
}
