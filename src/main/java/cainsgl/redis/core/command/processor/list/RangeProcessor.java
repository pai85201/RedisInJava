package cainsgl.redis.core.command.processor.list;

import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.manager.ListManager;
import cainsgl.redis.core.command.parameter.RedisParameter;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.network.response.resp.RESP2Response;
import cainsgl.redis.core.network.response.resp.impl.StringResponse;
import cainsgl.redis.core.storage.redisObj.RedisObj;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class RangeProcessor extends AbstractCommandProcessor<ListManager> {

    private String key;
    private List<String> rangeInfo;

    public RangeProcessor(){
        super("range",3,2);
    }

    @Override
    public RESP2Response execute() throws RedisException {
        RedisObj<LinkedList<String>> redisObj = getManager().redisObjGroup.get(key);
        if(null == redisObj)
            throw new RedisException("Value not found.");
        // 1. 只有起始位置，返回起始位置到末尾；2. 包含起始位置与结束位置
        if(rangeInfo.size() == 1){
            return executeJustBegin(redisObj, Integer.parseInt(rangeInfo.getFirst()));
        }
        else if(rangeInfo.size() == 2){
            return executeBegin2End(redisObj, Integer.parseInt(rangeInfo.getFirst()), Integer.parseInt(rangeInfo.getLast()));
        }else {
            throw new RuntimeException("args ERR");
        }
    }

    private RESP2Response executeJustBegin(RedisObj<LinkedList<String>> redisObj, Integer begin) throws RedisException{
        // 参数校验
        if(0 > begin){
            System.out.println("参数错误");
            throw new RedisException("args ERR");
        }
        ListIterator<String> listIterator = redisObj.getData().listIterator(begin);
        StringBuilder rangeRes = new StringBuilder();
        while (listIterator.hasNext()){
            rangeRes.append(listIterator.next());
        }
        return new StringResponse(rangeRes.toString());
    }

    private RESP2Response executeBegin2End(RedisObj<LinkedList<String>> redisObj, Integer begin, Integer end) throws RedisException{
        // 参数校验
        if(redisObj.getData().size() <= end || 0 > begin || begin >= end)
            throw new RedisException("args ERR");
        // 计算需要遍历的元素个数
        int count = end - begin + 1;
        StringBuilder rangeRes = new StringBuilder();
        ListIterator<String> listIterator = redisObj.getData().listIterator(begin);
        for (int i = 0; i < count && listIterator.hasNext(); i++) {
            rangeRes.append(listIterator.next());
        }
        return new StringResponse(rangeRes.toString());
    }

    @Override
    public void processArgs(List<String> args) throws RedisException {
        key = args.getFirst();
        rangeInfo = args.subList(1, args.size());
    }

    @Override
    public List<RedisParameter> getParameter() {
        return List.of(new RedisParameter(String.class, List.of()), new RedisParameter(String.class, List.of()));
    }
}