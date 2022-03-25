package nagi.spring.spring;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext {
    private Class configClass;
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private Map<String, Object> singletonObjectMap = new ConcurrentHashMap<>();

    public MyApplicationContext(Class configClass) {
        this.configClass = configClass;

        //扫描
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            //扫描路径
            String path = componentScanAnnotation.value();
            path = path.replace(".", "/");
            ClassLoader classLoader = MyApplicationContext.class.getClassLoader();
            //从编译完的classPath获取相对路径为path的资源
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());
            if (file.isDirectory()) {
                File[] files = file.listFiles();

                for (File f : files) {
                    String filePath = f.getAbsolutePath();
                    //通过类的class文件加载类
                    if (filePath.endsWith(".class")) {
                        //从文件路径获取类的全限定名称
                        String fileName = filePath.substring(filePath.indexOf("nagi\\spring"), filePath.indexOf(".class"));
                        fileName = fileName.replace("\\", ".");
                        try {
                            //加载类
                            Class<?> aClass = classLoader.loadClass(fileName);
                            //处理所有包含component注解的类
                            if (aClass.isAnnotationPresent(Component.class)) {
                                Component component = aClass.getAnnotation(Component.class);
                                String beanName = component.value();
                                //创建beanDefinition
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(aClass);
                                if (aClass.isAnnotationPresent(Scope.class)) {
                                    //获取scope值，判断是范围是单例还是多例
                                    Scope scope = aClass.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scope.value());
                                } else {
                                    //默认单例
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }

        //初始化容器时创建所有单例bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if ("singleton".equals(beanDefinition.getScope())) {
                singletonObjectMap.put(beanName, createBean(beanDefinition));
            }
        }
    }

    private Object createBean(BeanDefinition beanDefinition) {
        return null;
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        //beanDefinition不存在
        if (null == beanDefinition) {
            throw new NullPointerException();
        } else {
            String scope = beanDefinition.getScope();
            //获取单例bean，没有择创建
            if ("singleton".equals(scope)) {
                Object bean = singletonObjectMap.get(beanName);
                if (bean == null) {
                    Object o = createBean(beanDefinition);
                    singletonObjectMap.put(beanName, o);
                    bean = o;
                }
                return bean;
            } else {
                //返回创建的多实例bean
                return createBean(beanDefinition);
            }
        }
    }
}
