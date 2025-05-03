package cainsgl.redis.core.storage.redisObj.impl;

import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.StringResponse;
import cainsgl.redis.core.storage.redisObj.RedisObj;

public class RedisStringObj implements RedisObj<String> {
    String str;
    public RedisStringObj(String s)
    {
        this.str = s;

    }

    @Override
    public String getData()
    {
        return str;
    }

    @Override
    public RESP2Response getRes()
    {
        return new StringResponse(str);
    }

    @Override
    public int hashCode()
    {
        return str.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof RedisStringObj r)
        {
            return r.str.equals(str);
        }else
        {
            return false;
        }
    }
}
