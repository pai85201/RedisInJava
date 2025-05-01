package cainsgl.redis.core.command.processor;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.CustomManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.NumberResponse;

import java.util.List;

public class CustomProcessor extends AbstractCommandProcessor<CustomManager>
{
    public CustomProcessor()
    {
        super("author", 5, 1);
    }

    @Override
    public RESP2Response execute() throws RedisException
    {
        return new NumberResponse(1);
    }

    List<String> args;

    @Override
    public void processArgs(List<String> args) throws RedisException
    {

    }

    @Override
    public List<RedisParameter> getParameter()
    {
        return List.of(
                new RedisParameter(
                        String.class,
                        List.of("感谢你的使用",
                                "1", "3")
                ),
                new RedisParameter(
                        Integer.class,
                        List.of("1", "2", "3")
                )
        );
    }
}
