package cainsgl.redis.core.command.processor.common;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.SimpleManager;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.SimpleStringResponse;
import cainsgl.redis.core.network.response.resp.impl.StringResponse;

import java.util.List;

public class PingProcessor extends AbstractCommandProcessor<SimpleManager>
{
    public PingProcessor()
    {
        super("ping",2,1);
    }



    String result;
    @Override
    public RESP2Response execute()
    {
        if(result==null)
        {
           return new SimpleStringResponse("PONG");
        }else
        {
            return new StringResponse(result);
        }
    }

    @Override
    public void processArgs(List<String > args)
    {
        result=null;
        if(args.isEmpty())
        {
            return;
        }
        result=args.get(0);
    }
}
