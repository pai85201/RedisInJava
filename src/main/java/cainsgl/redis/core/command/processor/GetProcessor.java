package cainsgl.redis.core.command.processor;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.GetSetManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.network.response.Response;
import cainsgl.redis.core.network.response.ResponseEnum;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.StringResponse;
import cainsgl.redis.core.storage.RedisObj;
import cainsgl.redis.core.storage.share.ShareGet;

import java.util.List;

public class GetProcessor extends AbstractCommandProcessor<GetSetManager> implements ShareGet
{
    public GetProcessor()
    {
        super("get",2,2);
    }


    String key=null;


    @Override
    public RESP2Response execute()
    {
        return new StringResponse(getManager().test.get(key));
    }

    @Override
    public void processArgs(List<String> args)
    {
        key= args.get(0);
    }

    @Override
    public List<RedisParameter> getParameter()
    {
       return List.of(new RedisParameter(String.class,List.of() )) ;
    }

    //该方法由主内存调用，返回给主内存RedisObj
    @Override
    public RedisObj<?> get(String key)
    {
        return null;
    }
}
