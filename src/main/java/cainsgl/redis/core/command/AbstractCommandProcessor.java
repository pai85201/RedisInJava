package cainsgl.redis.core.command;


import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.network.NetworkConfig;
import cainsgl.redis.core.network.response.Response;
import io.netty.channel.EventLoopGroup;
import lombok.Data;

import java.util.List;

@Data
public abstract  class AbstractCommandProcessor<T extends AbstractCommandManager>
{
    private final String command;
     EventLoopGroup workerGroup;
    T manager;

    public AbstractCommandProcessor(String commandName)
    {
        this.command = commandName;
        NetworkConfig.register(this);
    }


    public abstract int maxArgsCount();

    public  abstract Response execute();

    public abstract void processArgs(List<String > args);

    public  List<RedisParameter> getParameter()
    {
        return List.of();
    }

}
