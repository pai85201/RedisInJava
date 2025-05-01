package cainsgl.redis.core.network;

import cainsgl.redis.core.command.AbstractCommandProcessor;

import java.util.HashMap;
import java.util.Map;

public class NetworkConfig
{
    private static final Map<String, AbstractCommandProcessor.Command> MANAGERS = new HashMap<>();

    public static Map<String, AbstractCommandProcessor.Command> getManagers()
    {
        return MANAGERS;
    }

    public static AbstractCommandProcessor.Command get(String commandName)
    {
        return MANAGERS.get(commandName);
    }

    public static void register(AbstractCommandProcessor.Command command)
    {
        MANAGERS.put(command.cmd, command);
    }
}
