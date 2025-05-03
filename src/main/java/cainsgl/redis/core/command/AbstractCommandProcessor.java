package cainsgl.redis.core.command;


import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.NetworkConfig;

import java.util.List;


public abstract class AbstractCommandProcessor<T extends CommandManager> implements CommandProcessor {

    T manager;

    public AbstractCommandProcessor(String commandName, int maxArgsCount, int minArgsCount) {

        NetworkConfig.register(new Command(commandName, maxArgsCount, minArgsCount, this));
    }
    @Override
    public final T getManager()
    {
        return manager;
    }
    @Override
    public final void setManager(CommandManager t)
    {
        if (manager == null)
        {
            manager = (T) t;
        } else
        {
            throw new RedisException("CommandProcessor already initialized");
        }
    }

    @Override
    public List<RedisParameter> getParameter()
    {
        return List.of();
    }

}
