package pss.www.platform.cache;

import java.lang.reflect.Field;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CacheShutdownListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DistCache c = CacheProvider.maybeGet();
        if (c instanceof ReplicatingCache) {
            try {
                Field f = ReplicatingCache.class.getDeclaredField("remote");
                f.setAccessible(true);
                Object remote = f.get(c);
                if (remote instanceof AutoCloseable) {
                    ((AutoCloseable) remote).close();
                }
            } catch (Exception ignore) {
            }
        }
    }
}
