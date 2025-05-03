package cainsgl.redis.core.storage.redisObj.impl;

import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.StringResponse;
import cainsgl.redis.core.storage.redisObj.RedisObj;

import java.util.LinkedList;

public class RedisListObj implements RedisObj<LinkedList<String>> {

    LinkedList<String> value;

    public RedisListObj(LinkedList<String> value){
        this.value = value;
    }

    @Override
    public LinkedList<String> getData() {
        return value;
    }

    @Override
    public RESP2Response getRes() {
        return new StringResponse(value.toString());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
