package cainsgl.redis.core.command.processor.string;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.GetSetManager;
import cainsgl.redis.core.command.parameter.RedisParameter;

import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;
import cainsgl.redis.core.network.response.resp.impl.NumberResponse;
import cainsgl.redis.core.network.response.resp.impl.StringResponse;
import cainsgl.redis.core.storage.RedisObj;


import java.util.List;

public class GetProcessor extends AbstractCommandProcessor<GetSetManager>
{
    public GetProcessor()
    {
        super("get", 2, 2);
    }


    String key;

    @Override
    public RESP2Response execute()
    {
        RedisObj<?> objectRedisObj = getManager().redisObjMap.get(key);
        if(objectRedisObj == null)
        {
            return EnumResponse.nil;
        }
        key=null;
        return objectRedisObj.getRes();
    }

    @Override
    public void processArgs(List<String> args)
    {
        key = args.get(0);
    }

    @Override
    public List<RedisParameter> getParameter()
    {
        return List.of(new RedisParameter(String.class, List.of()));
    }

    //该方法由主内存调用，返回给主内存RedisObj
}
