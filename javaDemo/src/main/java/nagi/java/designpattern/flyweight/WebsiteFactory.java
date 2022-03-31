package nagi.java.designpattern.flyweight;

import java.util.HashMap;
import java.util.Map;

public class WebsiteFactory {
    private Map<String, Website> pool = new HashMap<>();

    public Website getWebsite(String type) {
        if (!pool.containsKey(type)) {
            pool.put(type, new ConcreteWebsite(type));
        }
        return pool.get(type);
    }
}
