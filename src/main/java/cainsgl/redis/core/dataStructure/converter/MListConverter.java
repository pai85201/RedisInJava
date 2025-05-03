package cainsgl.redis.core.dataStructure.converter;

import cainsgl.redis.core.dataStructure.Converter;
import cainsgl.redis.core.dataStructure.Impl.MList;
import cainsgl.redis.core.dataStructure.factory.DefaultConverterFactory;

import java.util.List;

@cainsgl.redis.core.annotation.Converter(converterType = DefaultConverterFactory.ConverterType.M_LIST)
public class MListConverter implements Converter<List<?>, MList<?>> {
    @Override
    public MList<?> convert(List<?> objects) {
        return null;
    }

}
