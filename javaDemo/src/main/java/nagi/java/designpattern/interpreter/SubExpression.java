package nagi.java.designpattern.interpreter;

import java.util.HashMap;

/**
 * 减法解释器
 */
public class SubExpression extends SymbolExpression {
    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpreter(HashMap<String, Integer> var) {
        //返回左右表达式值的差
        return left.interpreter(var) - right.interpreter(var);
    }
}
