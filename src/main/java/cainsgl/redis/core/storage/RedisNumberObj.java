package cainsgl.redis.core.storage;

import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;
import cainsgl.redis.core.network.response.resp.impl.NumberResponse;

public class RedisNumberObj implements RedisObj<Number>
{
    Number value;
    public RedisNumberObj(Number value)
    {
        this.value = value;

    }

    @Override
    public Number getData()
    {
        return value;
    }

    @Override
    public RESP2Response getRes()
    {
        return new NumberResponse(value);
    }

    @Override
    public int hashCode()
    {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof RedisNumberObj r)
        {
            return r.value.equals(value);
        }else
        {
            return false;
        }
    }
}
