package cainsgl.redis.core.storage.share;

import cainsgl.redis.core.storage.RedisObj;

@FunctionalInterface
public interface GetCallBack
{
    boolean call(RedisObj<?> r);
}
