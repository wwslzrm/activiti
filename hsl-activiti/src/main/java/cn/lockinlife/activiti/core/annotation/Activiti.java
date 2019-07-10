package cn.lockinlife.activiti.core.annotation;


import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Service
public @interface Activiti {

    /***
     * the value indicate the name of activiti's process name
     * @return
     */
    String value() default "";

    /**
     * the value indicate the chinese name fof activiti's process,
     * if not set the value , the name equals value.
     * @return
     */
    String tag() default "";

}
