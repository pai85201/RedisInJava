package cainsgl.redis.core.config;

import java.util.concurrent.ExecutionException;

public interface Stopable
{
    void stop() throws InterruptedException, ExecutionException;
}
