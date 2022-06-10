package nagi.java.func;

import java.util.HashMap;
import java.util.function.Consumer;

public class ConsumerTest {
    public static void main(String[] args) {
        HashMap<String, Consumer<String>> stringConsumerHashMap = new HashMap<>();
        User user= new User();
        stringConsumerHashMap.put("name", user::setName);
        stringConsumerHashMap.get("name").accept("nagi");
        System.out.println(user.getName());
    }
}
