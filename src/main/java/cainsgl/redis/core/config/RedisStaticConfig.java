package cainsgl.redis.core.config;

import cainsgl.redis.core.command.AbstractCommandManager;
import cainsgl.redis.core.network.RedisServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class RedisStaticConfig implements Config
{
    private static final Logger log = LoggerFactory.getLogger(RedisServer.class);

    @Override
    public void autoConfig() throws Exception
    {
        final String packagePath = "cainsgl.redis.core.command.manager";
        String path = packagePath.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<Class<?>> classes = new ArrayList<>();
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements())
        {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("file"))
            {
                // 处理文件系统
                classes.addAll(findClassesInDirectory(
                        new File(resource.getFile()),
                        packagePath
                ));
            }
        }
        log.debug("load all processor");
        for(Class<?> clazz : classes)
        {
            Class<?> superclass = clazz.getSuperclass();
            if(superclass == AbstractCommandManager.class)
            {
                log.debug("found CommandProcessor:{}", clazz.getSimpleName());
                Constructor<?> constructor = clazz.getConstructor();
                constructor.setAccessible(true);
               constructor.newInstance();
               log.info("success load CommandProcessor:{}", clazz.getSimpleName());
            }
        }
    }

    private static List<Class<?>> findClassesInDirectory(File directory, String packageName) throws ClassNotFoundException
    {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {return classes;}

        File[] files = directory.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                if (file.isDirectory())
                {
                    classes.addAll(findClassesInDirectory(
                            file,
                            packageName + "." + file.getName()
                    ));
                } else if (file.getName().endsWith(".class"))
                {
                    String className = packageName + '.'
                            + file.getName().substring(0, file.getName().length() - 6);
                    classes.add(Class.forName(className));
                    log.debug("load class {}", className);
                }
            }
        }
        return classes;
    }
}
