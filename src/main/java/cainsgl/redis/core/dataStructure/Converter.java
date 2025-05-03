package cainsgl.redis.core.dataStructure;

public interface Converter<T, R> {

    R convert(T t);

}
