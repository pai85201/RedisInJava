package cainsgl.redis.core.command;


import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.NetworkConfig;
import cainsgl.redis.core.network.response.Response;
import cainsgl.redis.core.network.response.resp.RESP2Response;

import java.util.List;


public abstract class AbstractCommandProcessor<T extends AbstractCommandManager>
{

    public static class Command
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
    public void setManager(AbstractCommandManager t)
    {
        if(manager==null)
        {
            manager= (T) t;
        }else
        {
            throw new RedisException("CommandProcessor already initialized");
        }
    }
    T manager;
    public T getManager()
    {
        return manager;
    }
    public AbstractCommandProcessor(String commandName, int maxArgsCount, int minArgsCount)
    {

           NetworkConfig.register(new Command(commandName, maxArgsCount, minArgsCount, this));
    }



    public abstract RESP2Response execute() throws RedisException;

    public abstract void processArgs(List<String> args) throws RedisException;

    public List<RedisParameter> getParameter()
    {
        return List.of();
    }

}
