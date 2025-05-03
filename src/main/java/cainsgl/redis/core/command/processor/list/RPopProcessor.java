package cainsgl.redis.core.command.processor.list;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.ListManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.StringResponse;
import cainsgl.redis.core.storage.redisObj.RedisObj;
import cainsgl.redis.core.storage.share.MainMemory;

import java.util.LinkedList;
import java.util.List;

public class RPopProcessor extends AbstractCommandProcessor<ListManager> {

    private String key;

    public RPopProcessor(){
        super("rpop", 1, 1);
    }

    @Override
    public RESP2Response execute() throws RedisException {
        RedisObj<LinkedList<String>> redisObj = getManager().redisObjGroup.get(key);
        if(null == redisObj)
            throw new RedisException("Value not found.");
        String lastValue = redisObj.getData().getLast();
        // 移除末尾的元素
        redisObj.getData().removeLast();
        MainMemory.put(key, redisObj);
        return new StringResponse(lastValue);
    }

    @Override
    public void processArgs(List<String> args) throws RedisException {
        key = args.getFirst();
    }

    @Override
    public List<RedisParameter> getParameter() {
        return List.of(new RedisParameter(String.class, List.of()), new RedisParameter(String.class, List.of()));
    }

}
