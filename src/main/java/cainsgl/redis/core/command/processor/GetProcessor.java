package cainsgl.redis.core.command.processor;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.GetSetManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.network.response.Response;
import cainsgl.redis.core.network.response.ResponseEnum;

import java.util.List;

public class GetProcessor extends AbstractCommandProcessor<GetSetManager>
{
    public GetProcessor()
    {
        super("get");
    }

    @Override
    public int maxArgsCount()
    {
        return 2;
    }
    String key=null;


    @Override
    public Response execute()
    {
        return new Response(getManager().test.get(key), ResponseEnum.STRING);
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
}
