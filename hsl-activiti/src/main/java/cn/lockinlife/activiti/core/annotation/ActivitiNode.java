package cn.lockinlife.activiti.core.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ActivitiNode {

    /**
     * the name indicate the node in process.
     * @return
     */
    String value() default "";

    /**
     * the value indicate the chinese name fof activiti's process,
     * if not set the value , the name equals value.
     * @return
     */
    String tag() default "";

    /**
     *  the value can choose manual or auto.
     *  the value indicate the node is manual node or auto node,
     *  manual node means the node needs person to trigger,
     *  auto node means the system will do function of the node.
     * @return
     */
    String type()  default "";

    /**
     * the value indicate the execute orders,
     * the smaller the number, the quicker it is executed.
     * the number range between 1 ~ 100.
     * @return
     */
    int order() default 999;

    /**
     *
     * @return
     */
    String oprDep() default "auto";
}
