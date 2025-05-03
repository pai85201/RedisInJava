package cainsgl.redis.core.command.processor.list;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.ListManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.EnumResponse;
import cainsgl.redis.core.storage.redisObj.RedisObj;
import cainsgl.redis.core.storage.redisObj.factory.RedisObjFactory;
import cainsgl.redis.core.storage.share.ExpirableProducer;
import cainsgl.redis.core.storage.share.MainMemory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RPushProcessor extends AbstractCommandProcessor<ListManager> implements ExpirableProducer {
    private String key;
    private List<String> values;

    public RPushProcessor() {
        super("rpush",Integer.MAX_VALUE, 2);
    }

    @Override
    public RESP2Response execute() throws RedisException {
        // 将数据存入工作空间
        Map<String, RedisObj<LinkedList<String>>> redisObjGroup = getManager().redisObjGroup;
        List<String> valueCollection = new ArrayList<>(values);
        if(null == redisObjGroup.get(key)){
            // 该 key对应的list 第一次被创建，封装 redisObj对象
            RedisObj<LinkedList<String>> redisObj =
                    (RedisObj<LinkedList<String>>) RedisObjFactory.produce(RedisObjFactory.ObjType.LIST, values.toString(), 60000, this, key);
            redisObjGroup.put(key, redisObj);
            // 将数据存入主存(数据库)
        }else {
            // NOTE 区别于 LPUSH 直接添加到 linkedList末尾
            redisObjGroup.get(key).getData().addAll(valueCollection);
        }
        MainMemory.put(key, redisObjGroup.get(key));
        return EnumResponse.ok;
    }

    @Override
    public void processArgs(List<String> args) throws RedisException {
        key = args.getFirst();
        values = args.subList(1, args.size());
    }

    @Override
    public List<RedisParameter> getParameter() {
        return List.of(new RedisParameter(String.class, List.of()), new RedisParameter(String.class, List.of()));
    }

    @Override
    public void del(RedisObj<?> r, Object... param) {
        String key = (String) param[0];
        getManager().redisObjGroup.remove(key);
    }
}
