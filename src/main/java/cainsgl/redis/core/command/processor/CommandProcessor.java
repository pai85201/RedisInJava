package cainsgl.redis.core.command.processor;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.CommandManager;
import cainsgl.redis.core.command.manager.PingManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.network.NetworkConfig;
import cainsgl.redis.core.network.response.Response;
import cainsgl.redis.core.network.response.ResponseEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandProcessor extends AbstractCommandProcessor<CommandManager>
{
    public CommandProcessor()
    {
        super("command");
    }

    @Override
    public int maxArgsCount()
    {
        return 1;
    }

    @Override
    public Response execute()
    {
        //TODO
        Map<String, AbstractCommandProcessor<?>> managers = NetworkConfig.getManagers();
        Response[] res=new Response[managers.size()];
        int i = 0;
        //去遍历所有的命令
        for (Map.Entry<String, AbstractCommandProcessor<?>> entry : managers.entrySet()) {
            AbstractCommandProcessor<?> processor = entry.getValue();
            String commandName = entry.getKey();
            List<RedisParameter> parameter = processor.getParameter();
            //每个命令算上自己，有parameter.size()+1个
            StringBuilder allArgs=new StringBuilder(commandName);
            for (RedisParameter redisParameter : parameter)
            {
                allArgs.append(" [").append(" ").append(redisParameter.getType().getSimpleName());
                List<String> only = redisParameter.getOnly();
                if (only.isEmpty())
                {
                    allArgs.append(" ]");
                    continue;
                }
                allArgs.append(only.size()).append(":").append("{ ");
                for (int k = 0; k < only.size(); k++)
                {
                    allArgs.append(" ").append(only.get(k));
                }
                allArgs.append(" }");
            }

            Response oneCommand = new Response(allArgs, ResponseEnum.STRING);
            res[i] = oneCommand;
            i++;
//            for(int j=0;j<parameter.size();j++)
//            {
//                RedisParameter redisParameter = parameter.get(j);
//                Class<?> type = redisParameter.getType();
//                List<String> only = redisParameter.getOnly();
//                if(only.isEmpty())
//                {
//                    args[j+1]=new Response(type.getSimpleName(),ResponseEnum.STRING);
//                }else
//                {
//                    //仍然是数组，除了展示头的，还要展示枚举的
//                    Response[] argsEnums = new Response[only.size()];
//                    for(int k=0;k<only.size();k++)
//                    {
//                        argsEnums[k]=new Response(only.get(k),ResponseEnum.STRING);
//                    }
//                    args[j+1]=new Response( argsEnums,ResponseEnum.ARRAY);
//                }
//
//            }
//            res[i++]=oneCommand;
        }
        return new Response(res,ResponseEnum.ARRAY);
    }

    @Override
    public void processArgs(List<String> args)
    {

    }
}
