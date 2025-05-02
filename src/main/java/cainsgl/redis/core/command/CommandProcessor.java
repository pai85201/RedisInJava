package cainsgl.redis.core.command;

import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;

import java.util.List;

public interface CommandProcessor
{
    RESP2Response execute() throws RedisException;

    void processArgs(List<String> args) throws RedisException;

    List<RedisParameter> getParameter();

    void setManager(CommandManager manager);

    public static final class Command
    {
        public final int maxCount;
        public final int minCount;
        public final AbstractCommandProcessor<?> processor;
        public final String cmd;

        public Command(String cmd, int maxCount, int minCount, AbstractCommandProcessor<?> processor)
        {
            this.maxCount = maxCount;
            this.minCount = minCount;
            this.processor = processor;
            this.cmd = cmd;
        }
    }
}
