package cainsgl.redis.core.dataStructure.factory;


import cainsgl.redis.core.dataStructure.Converter;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class DefaultConverterFactory {

    private static final Map<String, Converter<?, ?>> converterGroup = new ConcurrentSkipListMap<>();

    /* TODO
    *   1. 通过包扫描得到 converter 下的所有 converter 并封装成List集合
    *   2. 在List集合中通过注解解析的方式将不同的 converter 注册进 converterGroup 中
    *   3. converterGroup 中 key为枚举类中的 type；由工厂提供，业务层索要的转化器类型只能是工厂中提供的这几种
    *   4. 这种模式下工厂更类似于一个商店
    *  */

    // 转化器类型枚举类应该类聚在工厂类之中
    public enum ConverterType {
        M_LIST("MList", "指示该转化器类型是将数据转化为 MList"),
        M_SET("MSet", "指示该转化器类型是将数据转化为 MSet")

        ;

        private final String type;
        private final String desc;

        ConverterType(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }
    }
}
