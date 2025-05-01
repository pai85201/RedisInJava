package cainsgl.redis.core.command.processor;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.GetSetManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.network.response.Response;

import java.util.List;

public class SetProcessor extends AbstractCommandProcessor<GetSetManager>
{
    public SetProcessor()
    {
        super("set");
    }

    @Override
    public int maxArgsCount()
    {
        return 3;
    }
    String key=null;
    String value=null;
    @Override
    public Response execute()
    {
         getManager().test.put(key,value);
        return Response.OK;
    }

    @Override
    public void processArgs(List<String> args)
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
