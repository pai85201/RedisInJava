package cainsgl.redis.core.command.processor.string;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.GetSetManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;
import cainsgl.redis.core.storage.RedisObj;
import cainsgl.redis.core.storage.share.MainMemory;

import java.util.List;
import java.util.Map;

public class SetProcessor extends AbstractCommandProcessor<GetSetManager>
{
    public SetProcessor()
    {
        super("set", 3, 3);
    }

    String key;
    String value;

    @Override
    public RESP2Response execute() throws RedisException
    {
        Map<String, RedisObj<Object>> map = getManager().redisObjMap;
        RedisObj<Object> redisObj;
        try
        {
            int i = Integer.parseInt(value);
            redisObj = new RedisObj<>(key, 30, i);
        } catch (Exception e)
        {
            redisObj = new RedisObj<>(key, 30, value);
        }
        map.put(key, redisObj);
        MainMemory.put(key, redisObj);
        //原来的直接不要了
        return EnumResponse.ok;
    }

    @Override
    public void processArgs(List<String> args) throws RedisException
    {
        key = args.get(0);
        value = args.get(1);
    }

    @Override
    public List<RedisParameter> getParameter()
    {
        return List.of(new RedisParameter(String.class, List.of()), new RedisParameter(String.class, List.of()));
    }


}
