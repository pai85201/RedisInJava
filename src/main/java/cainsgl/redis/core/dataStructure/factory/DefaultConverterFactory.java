package cainsgl.redis.core.dataStructure.factory;


import cainsgl.redis.core.dataStructure.Converter;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class DefaultConverterFactory {

    private static final Map<String, Converter<?, ?>> converterGroup = new ConcurrentSkipListMap<>();



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
