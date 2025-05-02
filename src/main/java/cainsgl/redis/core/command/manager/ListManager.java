package cainsgl.redis.core.command.manager;

import cainsgl.redis.core.command.AbstractCommandManager;
import cainsgl.redis.core.command.AbstractCommandProcessor;
import cainsgl.redis.core.command.processor.list.LPushProcessor;
import cainsgl.redis.core.storage.RedisObj;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ListManager extends AbstractCommandManager {

    public Map<String, RedisObj<LinkedList<String>>> redisObjGroup = new HashMap<>();

    public ListManager() {
        super(false, new LPushProcessor());
    }

    /* 命令集合相关枚举 */
    public enum CommandCollection {

        L_PUSH("lpush", 10, 1),
        R_PUSH("rpush", 10 ,1),
        RANGE("range", 2, 2),

        ;

        private final String commandName;
        private final Integer maxArgsCount;
        private final Integer minArgsCount;



        CommandCollection(String commandName, Integer maxArgsCount, Integer minArgsCount) {
            this.commandName = commandName;
            this.maxArgsCount = maxArgsCount;
            this.minArgsCount = minArgsCount;
        }

        public String getCommandName() {
            return commandName;
        }

        public Integer getMaxArgsCount() {
            return maxArgsCount;
        }

        public Integer getMinArgsCount() {
            return minArgsCount;
        }
    }

}
