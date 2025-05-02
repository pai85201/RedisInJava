package cainsgl.redis.core.command.processor.system;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.SystemManager;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;

import java.util.List;

public class GcProcessor extends AbstractCommandProcessor<SystemManager>
{
    public GcProcessor()
    {
        super("gc", 1, 1);
    }

    @Override
    public RESP2Response execute() throws RedisException
    {
        System.gc();
        return EnumResponse.ok;
    }

    @Override
    public void processArgs(List<String> args) throws RedisException
    {

    }
}
