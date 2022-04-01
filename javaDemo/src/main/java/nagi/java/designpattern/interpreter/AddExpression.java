package nagi.java.designpattern.interpreter;

import java.util.HashMap;

/**
 * 加法解释器
 */
public class AddExpression extends SymbolExpression {
    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpreter(HashMap<String, Integer> var) {
        //返回左右表达式值的和
        return left.interpreter(var) + right.interpreter(var);
    }
}
