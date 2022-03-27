package nagi.spring.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyApplicationContext {
    private Class configClass;
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    //单例池
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private ArrayList<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

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
                                //添加所有的beanPostProcessor
                                if (BeanPostProcessor.class.isAssignableFrom(aClass)) {
                                    BeanPostProcessor instance = (BeanPostProcessor) aClass.getConstructor().newInstance();
                                    beanPostProcessorList.add(instance);
                                }

                                Component component = aClass.getAnnotation(Component.class);
                                String beanName = component.value();

                                //没有配置bean名称，则使用默认名称：首字母小写的类名
                                if ("".equals(beanName)) {
                                    beanName = Introspector.decapitalize(aClass.getSimpleName());
                                }

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
                        } catch (ClassNotFoundException | NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }

        //初始化容器时创建所有单例bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

            //bean为单例且未通过属性注入调用getBean提前创建，则创建该bean
            if ("singleton".equals(beanDefinition.getScope()) && !singletonObjects.containsKey(beanName)) {
                singletonObjects.put(beanName, createBean(beanName, beanDefinition));
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class aClass = beanDefinition.getType();

        try {
            Object instance = aClass.getConstructor().newInstance();

            //依赖注入
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(AutoWired.class)) {
                    declaredField.setAccessible(true);
                    declaredField.set(instance, getBean(declaredField.getName()));
                }
            }

            //aware回调，检查是否需要传递beanName给实例
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            //执行所有的beanPostProcessor的before方法
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                beanPostProcessor.postProcessBeforeInitialization(beanName, instance);
            }

            //初始化回调
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            //执行所有的beanPostProcessor的after方法
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(beanName, instance);
            }
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        //beanDefinition不存在
        if (null == beanDefinition) {
            throw new NullPointerException();
        } else {
            String scope = beanDefinition.getScope();
            //获取单例bean，没有则创建
            if ("singleton".equals(scope)) {
                Object bean = singletonObjects.get(beanName);
                //依赖bean不存在时创建依赖的bean
                if (bean == null) {
                    Object o = createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName, o);
                    bean = o;
                }
                return bean;
            } else {
                //返回创建的多实例bean
                return createBean(beanName, beanDefinition);
            }
        }
    }
}
