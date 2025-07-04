package cainsgl.redis.core.command.processor.string;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.GetSetManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;
import cainsgl.redis.core.storage.redisObj.RedisObj;
import cainsgl.redis.core.storage.redisObj.factory.RedisObjFactory;
import cainsgl.redis.core.storage.share.ExpirableProducer;
import cainsgl.redis.core.storage.share.MainMemory;

import java.util.List;

public class SetProcessor extends AbstractCommandProcessor<GetSetManager> implements ExpirableProducer {

    public SetProcessor()
    {
        super("set", 3, 3);
    }

    String key;
    String value;

    @Override
    public RESP2Response execute() throws RedisException
    {
        GetSetManager manager = getManager();
        //这里key开始的，后面的参数都会被传入del里
        RedisObj<?> produce = RedisObjFactory.produce(RedisObjFactory.ObjType.STRING, value, 60000, this, key);
        manager.redisObjMap.put(key, produce);
        MainMemory.put(key, produce);
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


    @Override
    public void del(RedisObj<?> r, Object... param) {
        String key = (String) param[0];
        getManager().redisObjMap.remove(key);
    }
}
