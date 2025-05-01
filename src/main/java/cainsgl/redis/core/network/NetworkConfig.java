package cainsgl.redis.core.network;

import cainsgl.redis.core.command.AbstractCommandProcessor;

import java.util.HashMap;
import java.util.Map;

public class NetworkConfig
{
    private static final Map<String, AbstractCommandProcessor<?>> MANAGERS = new HashMap<>();
    public static Map<String,AbstractCommandProcessor<?>> getManagers()
    {
        return MANAGERS;
    }
    public static AbstractCommandProcessor<?> get(String  commandName)
    {
         return MANAGERS.get(commandName);
    }

    public static void register(AbstractCommandProcessor<?> abstractCommandProcessor)
    {
        MANAGERS.put(abstractCommandProcessor.getCommand(), abstractCommandProcessor);
    }
}
