package mangolost.demo.common.utils;

import mangolost.demo.common.helper.ApiStatusCode;
import mangolost.demo.exception.ServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * Created by mangolost on 2017-04-19
 */
public class JsonPUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonPUtils.class);

	/**
	 * 根据有无callback，确定返回json对象类型
	 * 如果有callback，则返回callback(obj)
	 * 如果没有callback，则返回obj
	 *
	 * @param obj
	 * @param callback
	 * @return
	 */
	public static Object doJsonP(Object obj, String callback) {

		if (StringUtils.isBlank(callback)) {
			return obj;
		} else {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = null;
			try {
				json = objectMapper
						.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
						.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
				LOGGER.error("jsonp parse error", e);
				throw new ServiceException(ApiStatusCode.INTERNAL_SERVER_ERROR, "JSON处理出错");
			}

			return callback + "(" + json + ");";
		}
	}
}
