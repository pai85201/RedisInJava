package cainsgl.redis.core.dataStructure;

/*
* 转化器接口
* */
public interface Converter<T, R> {

    R convert(T t);

}
