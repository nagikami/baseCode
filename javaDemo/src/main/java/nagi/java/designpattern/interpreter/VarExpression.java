package nagi.java.designpattern.interpreter;

import java.util.HashMap;

/**
 * 非终结符表达式
 * 变量解释器
 */
public class VarExpression extends Expression{

    //变量名
    private String key;

    public VarExpression(String key) {
        this.key = key;
    }

    //根据变量名称返回对应的值
    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return var.get(key);
    }
}
