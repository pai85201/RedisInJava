package cainsgl.redis.core.network.response.resp.impl;

import cainsgl.redis.core.network.response.resp.RESP2Response;
import io.netty.util.concurrent.Future;

import java.util.concurrent.ExecutionException;

@FunctionalInterface
public interface FutureListener<T>
{
    RESP2Response process(T t)  throws InterruptedException, ExecutionException; ;
}
