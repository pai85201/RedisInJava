package cainsgl.redis.core.command.processor.list;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.ListManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;
import cainsgl.redis.core.storage.RedisObj;
import cainsgl.redis.core.storage.share.MainMemory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
* TODO
*  0. 每一个值以空格隔开封装成 string；处理器负责处理数据
*  1. 过期时间暂时写死
* */
public class LPushProcessor extends AbstractCommandProcessor<ListManager> {

    private String key;
    private String values;

    public LPushProcessor() {
        super(ListManager.CommandCollection.L_PUSH.getCommandName(),
               ListManager.CommandCollection.L_PUSH.getMaxArgsCount(), ListManager.CommandCollection.L_PUSH.getMinArgsCount());
    }

    @Override
    public RESP2Response execute() throws RedisException {
        // 将数据存入工作空间
        Map<String, RedisObj<LinkedList<String>>> redisObjGroup = getManager().redisObjGroup;
        String[] valueInfo = values.trim().replaceAll("\\s+", " ").split(" ");
        List<String> valueCollection = Arrays.stream(valueInfo).toList();
        if(null == redisObjGroup.get(key)){
            // 该 key对应的list 第一次被创建，封装 redisObj对象
            RedisObj<LinkedList<String>> redisObj = new RedisObj<>(key, 30, new LinkedList<>(valueCollection));
            redisObjGroup.put(key, redisObj);
            // 将数据存入主存(数据库)
        }else {
            // 该列表之前存在
            redisObjGroup.get(key).value.addAll(valueCollection);
        }
        MainMemory.put(key, redisObjGroup.get(key));
        return EnumResponse.ok;
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
