package cainsgl.redis.core.command.processor;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.PingManager;
import cainsgl.redis.core.network.response.Response;
import cainsgl.redis.core.network.response.ResponseEnum;

import java.util.List;

public class PingProcessor extends AbstractCommandProcessor<PingManager>
{
    public PingProcessor()
    {
        super("ping");
    }

    @Override
    public int maxArgsCount()
    {
        return 2;
    }
    String result;
    @Override
    public Response execute()
    {
        if(result==null)
        {
           return new Response("PONG", ResponseEnum.SIMPLE_STRING);
        }else
        {
            return new Response(result, ResponseEnum.STRING);
        }
    }

    @Override
    public void processArgs(List<String > args)
    {
        if(args.isEmpty())
        {
            return;
        }
        result=args.get(0);
    }
}
