package mangolost.demo.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by mangolost on 2017-08-15
 */
public class MyBeanUtils extends BeanUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyBeanUtils.class);

	/**
	 * 根据传入的对象，以及属性与属性值键值对，修改目标对象的属性值	 *
	 * @param target      需要修改的对象
	 * @param map         属性名与属性值 键值对
	 * @param entityClass 对象的类型
	 */
	public static void changeProps(Object target, Map<String, Object> map, Class entityClass) {

		for (String key : map.keySet()) {
			Field field = null;
			try {
				field = entityClass.getDeclaredField(key);
			} catch (NoSuchFieldException e) {
				LOGGER.error("没有field：" + key, e);
				continue; //这里的处理策略是：如果前端传了一个对象中不存在的属性，则忽略它，而不是报错
			}
			field.setAccessible(true);
			try {
				field.set(target, map.get(key));
			} catch (IllegalAccessException e) {
				LOGGER.error("illegal access for field：" + key, e);
			}
		}
	}
}
