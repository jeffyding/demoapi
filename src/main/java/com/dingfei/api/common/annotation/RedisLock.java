package com.dingfei.api.common.annotation;

import java.lang.annotation.*;

/**
 * RedisLock
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisLock {
    /**
     * redis的key
     *
     * @return String
     */
    String value();

    /**
     * 持锁时间,单位毫秒,默认10秒
     */
    int keepMills() default 10000;

    /**
     * 当获取失败时候动作
     */
    LockFailAction action() default LockFailAction.CONTINUE;

    /**
     * LockFailAction
     */
    enum LockFailAction {
        /**
         * 放弃
         */
        GIVEUP,
        /**
         * 继续
         */
        CONTINUE
    }

    /**
     * 睡眠时间,设置GIVEUP忽略此项
     *
     * @return long
     */
    int sleepMills() default 1000;
}
