package cainsgl.redis.core.storage;

import cainsgl.redis.core.network.response.resp.RESP2Response;

public interface RedisObj<T>
{
    T getData();

    RESP2Response getRes();


}
