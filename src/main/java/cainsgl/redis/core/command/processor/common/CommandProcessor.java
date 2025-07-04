package cainsgl.redis.core.command.processor.common;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.SimpleManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.network.NetworkConfig;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.ArrayResponse;
import cainsgl.redis.core.network.response.resp.impl.StringResponse;

import java.util.List;
import java.util.Map;

public class CommandProcessor extends AbstractCommandProcessor<SimpleManager>
{
    public CommandProcessor()
    {
        super("command",2,1);
    }


    @Override
    public RESP2Response execute()
    {
        Map<String, AbstractCommandProcessor.Command> commandMap = NetworkConfig.getManagers();
        RESP2Response[] responses = new RESP2Response[commandMap.size()];
        int i=0;
        for(Map.Entry<String, AbstractCommandProcessor.Command> entry : commandMap.entrySet())
        {
            AbstractCommandProcessor<?> processor = entry.getValue().processor;
            String commandName = entry.getKey();
            List<RedisParameter> parameter = processor.getParameter();

            StringBuilder allArgs=new StringBuilder(commandName);
            for (RedisParameter redisParameter : parameter)
            {
                allArgs.append(" [").append(" ").append(redisParameter.type.getSimpleName());
                List<String> only = redisParameter.only;
                if (only.isEmpty())
                {
                    allArgs.append(" ]");
                    continue;
                }
                allArgs.append(only.size()).append(":").append("{ ");
                for (String s : only)
                {
                    allArgs.append(" ").append(s);
                }
                allArgs.append(" }],");
            }
            RESP2Response oneCommand = new StringResponse(allArgs.toString());
            responses[i] = oneCommand;
            i++;

        }
        return new ArrayResponse(responses);


     //   Map<String, AbstractCommandProcessor.Command> commandMap = NetworkConfig.getManagers();
    //    Response[] res=new Response[commandMap.size()];
    //    int i = 0;
//        //去遍历所有的命令
//        for (Map.Entry<String, AbstractCommandProcessor.Command> entry : commandMap.entrySet()) {
//            AbstractCommandProcessor<?> processor = entry.getValue().processor;
//            String commandName = entry.getKey();
//            List<RedisParameter> parameter = processor.getParameter();
//            //每个命令算上自己，有parameter.size()+1个
//            StringBuilder allArgs=new StringBuilder(commandName);
//            for (RedisParameter redisParameter : parameter)
//            {
//                allArgs.append(" [").append(" ").append(redisParameter.type.getSimpleName());
//                List<String> only = redisParameter.only;
//                if (only.isEmpty())
//                {
//                    allArgs.append(" ]");
//                    continue;
//                }
//                allArgs.append(only.size()).append(":").append("{ ");
//                for (String s : only)
//                {
//                    allArgs.append(" ").append(s);
//                }
//                allArgs.append(" }");
//            }
//
//            Response oneCommand = new Response(allArgs, ResponseEnum.STRING);
//            res[i] = oneCommand;
//            i++;
////            for(int j=0;j<parameter.size();j++)
////            {
////                RedisParameter redisParameter = parameter.get(j);
////                Class<?> type = redisParameter.getType();
////                List<String> only = redisParameter.getOnly();
////                if(only.isEmpty())
////                {
////                    args[j+1]=new Response(type.getSimpleName(),ResponseEnum.STRING);
////                }else
////                {
////                    //仍然是数组，除了展示头的，还要展示枚举的
////                    Response[] argsEnums = new Response[only.size()];
////                    for(int k=0;k<only.size();k++)
////                    {
////                        argsEnums[k]=new Response(only.get(k),ResponseEnum.STRING);
////                    }
////                    args[j+1]=new Response( argsEnums,ResponseEnum.ARRAY);
////                }
////
////            }
////            res[i++]=oneCommand;
//        }
//        return new Response(res,ResponseEnum.ARRAY);




    }

    @Override
    public void processArgs(List<String> args)
    {

    }
}
