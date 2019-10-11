package com.fr.plugin.performance.analysis.mysql.core;

/**
 * @author yuwh
 * @version 1.0.0
 * time:2019/10/11
 * Description: 接口支持 static 方法但并不能继承但是可以重写
 */
public interface IgnoreCase<E extends Enum> {
    /**
     * 让枚举的 valueOf 可以忽略大小写，统一转成大写
     * @param var1
     * @param var2
     * @return
     */
    static <E extends Enum> E findInUpperCase(Class<E> var1, String var2){
        return (E) E.valueOf(var1, var2.toUpperCase());
    }

    /**
     * 让枚举的 valueOf 可以忽略大小写，统一转成小写
     * @param var1
     * @param var2
     * @return
     */
    static <E extends Enum> E findInLowerCase(Class<E> var1, String var2){
        return (E) E.valueOf(var1, var2.toLowerCase());
    }
}
