package cainsgl.redis.core.annotation;


import cainsgl.redis.core.dataStructure.factory.DefaultConverterFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Converter {

    DefaultConverterFactory.ConverterType converterType();

}
