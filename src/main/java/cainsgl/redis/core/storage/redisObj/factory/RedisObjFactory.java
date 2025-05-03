package cainsgl.redis.core.storage.redisObj.factory;


import cainsgl.redis.core.command.CommandManager;
import cainsgl.redis.core.command.CommandProcessor;
import cainsgl.redis.core.exception.RedisException;
import cainsgl.redis.core.storage.RedisProxyObj;
import cainsgl.redis.core.storage.redisObj.RedisObj;
import cainsgl.redis.core.storage.redisObj.impl.RedisListObj;
import cainsgl.redis.core.storage.redisObj.impl.RedisStringObj;
import cainsgl.redis.core.storage.share.ExpirableProducer;
import io.netty.channel.EventLoopGroup;

import java.util.LinkedList;

public class RedisObjFactory {

//    public static RedisObj<?> produce(RedisObj<?> obj, long expire, ExpirableProducer willDell, Object... params) {
//        CommandProcessor proc = (CommandProcessor) willDell;
//        CommandManager manager = proc.getManager();
//        EventLoopGroup workGroup = manager.getWorkGroup();
//        return new RedisProxyObj<>(obj, expire, () -> {
//            workGroup.execute(() -> {
//                willDell.del(obj, params);
//            });
//        });
//    }
//
//    public static RedisObj<?> produce(String obj, long expire) {
//        return new RedisProxyObj<>(produceReallyObj(obj), expire);
//    }

    public static RedisObj<?> produce(ObjType objType, Object obj, long expire, ExpirableProducer willDell, Object... params) {
        //去拿到processor里的del方法并代理
        CommandProcessor proc = (CommandProcessor) willDell;
        CommandManager manager = proc.getManager();
        EventLoopGroup workGroup = manager.getWorkGroup();
        RedisObj<?> redisObj = produceReallyObj(obj, objType);
        return new RedisProxyObj<>(redisObj, expire, () -> {
            workGroup.execute(() -> {
                willDell.del(redisObj, params);
            });
        });
    }

    private static RedisObj<?> produceReallyObj(Object obj, ObjType objType) {
        int type = objType.getType();
        return switch (type) {
            case 1 -> new RedisStringObj(obj.toString());
            case 2 -> new RedisListObj((LinkedList<String>) obj);
            default -> throw new RedisException("now such type");
        };
    }

    // 枚举类型 - 用于定义生产的 redisObj类型
    public enum ObjType{
        STRING(1, "string数据结构类型代数"),
        LIST(2, "list数据结构类型代数"),
        HASH(3, "hash数据结构类型代数"),
        SET(4, "set数据结构类型代数"),
        Z_SET(5, "zset数据结构类型代数"),

        ;

        private final Integer type;
        private final String desc;

        public Integer getType() {
            return type;
        }
        public String getDesc() {
            return desc;
        }

        ObjType(Integer type, String desc) {
            this.type = type;
            this.desc = desc;
        }
    }
}
