package www.tonghao.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessToken {
	
	/**
	 * @Description:角色code
	 * @date 2019年5月24日
	 */
	String[] rolesCode() default"";
}
