package nagi.java.jndi;

import javax.naming.*;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.ldap.LdapContext;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Java Naming and Directory Interface (JNDI)使用Java API提供一致的资源naming和directory服务
 * JNDI可以用来绑定、查找对象和检测对象的变化
 */
public class Main {
    public static void main(String[] args) {
        nameInterface();
    }

    /**
     * naming服务使用Name接口管理组件命名，在具有继承关系的命名空间中为对象命名，并提供基于名称的单点访问
     * directory服务（例如LDAP server）仅仅是特殊的命名服务，每个directory entry为named entry（对象）绑定属性
     * 且具有继承关系（在JNDI中，通过父子上下文表示）
     */
    public static void nameInterface() {
        try {
            // java:comp代表全局上下文，后面跟着以/分隔的子上下文
            Name objectName = new CompositeName("java:comp/env/jdbc");
            Enumeration<String> elements = objectName.getAll();
            while (elements.hasMoreElements()) {
                System.out.println(elements.nextElement());
            }
        } catch (InvalidNameException e) {
            e.printStackTrace();
        }
    }

    /**
     * Context接口保存用于naming和directory服务的属性
     */
    public static void LDAPContextInterface() {
        Hashtable<String, Object> env = new Hashtable<>(11);
        // 指定Context工厂类
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // 指定目录服务的url
        env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=JNDITutorial");
        try {
            InitialContext context = new InitialContext(env);
            // 通过name查找包含named entry（对象）和directory entry（属性）的DirContext
            LdapContext target = (LdapContext) context.lookup("cn=Rosanna Lee,ou=People");
            System.out.println(target);
            context.close();
        } catch (NamingException e) {
            System.out.println("Lookup failed");
            e.printStackTrace();
        }
    }

    public static void DNSContextInterface() {
        Hashtable<String, Object> env = new Hashtable<>(11);
        // 指定Context工厂类
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        // 指定目录服务的url，不指定则使用操作系统默认的dns服务
        env.put(Context.PROVIDER_URL,  "dns://114.114.114.114");
        try {
            // 初始化包含named entry和directory entry的上下文
            InitialDirContext context = new InitialDirContext(env);
            // 通过name获取属性
            Attributes attributes = context.getAttributes("baidu.com");
            NamingEnumeration<? extends Attribute> attributesAll = attributes.getAll();
            while (attributesAll.hasMoreElements()) {
                System.out.println(attributesAll.nextElement());
            }
            context.close();
        } catch (NamingException e) {
            System.out.println("Lookup failed");
            e.printStackTrace();
        }
    }
}
