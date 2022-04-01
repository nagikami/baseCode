package nagi.java.designpattern.interpreter;

import java.util.HashMap;
import java.util.Stack;

public class Calculator {

    //定义表达式
    private Expression expression;

    public Calculator(String expStr) {
        //安排运算顺序
        Stack<Expression> stack = new Stack<>();
        //拆分成字符数组
        char[] chars = expStr.toCharArray();

        Expression left = null;
        Expression right = null;
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case '+':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(chars[++i]));
                    stack.push(new AddExpression(left, right));
                    break;
                case '-':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(chars[++i]));
                    stack.push(new SubExpression(left, right));
                    break;
                default:
                    stack.push(new VarExpression(String.valueOf(chars[i])));
                    break;
            }
        }
        //弹出最终的嵌套表达式
        this.expression = stack.pop();
    }

    public int run(HashMap<String, Integer> var) {
        //绑定表达式和变量值
        return this.expression.interpreter(var);
    }

}
