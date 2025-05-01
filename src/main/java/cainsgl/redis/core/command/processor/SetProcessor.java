package cainsgl.redis.core.command.processor;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.GetSetManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.Response;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;
import cainsgl.redis.core.network.response.resp.impl.OkResponse;
import cainsgl.redis.core.storage.RedisObj;
import cainsgl.redis.core.storage.share.ShareSet;

import java.util.List;

public class SetProcessor extends AbstractCommandProcessor<GetSetManager>
{
    public SetProcessor()
    {
        super("set",3,3);
    }


    String key=null;
    String value=null;
    @Override
    public RESP2Response execute()  throws RedisException
    {
         getManager().test.put(key,value);
        return EnumResponse.ok;
    }

    @Override
    public void processArgs(List<String> args)  throws RedisException
    {
        key= args.get(0);
        value= args.get(1);
    }

    @Override
    public List<RedisParameter> getParameter()
    {
        return List.of(new RedisParameter(String.class,List.of() ),new RedisParameter(String.class,List.of() ));
    }


}
