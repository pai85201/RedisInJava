package cainsgl.redis.core.command.processor.list;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.ListManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;

import java.util.List;
import java.util.stream.Collectors;

public class RangeProcessor extends AbstractCommandProcessor<ListManager>
{

    private String key;
    private String values;

    public RangeProcessor(){
        super("range",2,2);
    }

    @Override
    public RESP2Response execute() throws RedisException
    {
        return null;
    }

    @Override
    public void processArgs(List<String> args) throws RedisException {
        key = args.get(0);
        values = args.subList(1, args.size())
                     .stream()
                     .map(Object::toString)
                     .collect(Collectors.joining(" "));
    }

    @Override
    public List<RedisParameter> getParameter() {
        return List.of(new RedisParameter(String.class, List.of()), new RedisParameter(String.class, List.of()));
    }
}