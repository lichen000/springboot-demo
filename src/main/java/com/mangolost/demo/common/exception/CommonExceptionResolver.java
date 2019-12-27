package com.mangolost.demo.common.exception;

import com.mangolost.demo.common.helper.CommonResult;
import com.mangolost.demo.common.helper.ApiStatusCode;
import com.mangolost.demo.common.helper.CommonMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

/**
 * Created by mangolost on 2017-04-13
 */
@ControllerAdvice
//public class CommonExceptionResolver implements HandlerExceptionResolver {
public class CommonExceptionResolver {

	private final static Logger LOGGER = LoggerFactory.getLogger(CommonExceptionResolver.class);
	@Value("${system.runmode}")
	private String runMode;

	/**
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	public CommonResult resolveException(HttpServletRequest request, HttpServletResponse response, Exception ex) {

		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		int code = ApiStatusCode.OK;
		String message = CommonMessage.OK_MESSAGE;

		try {

			String exMsg = "";
			if ("dev".equals(runMode) || "uat".equals(runMode) || "stg".equals(runMode)) {
				exMsg = ": " + ex.getMessage();
			}

			//根据异常类型返回对应的code与message
			if (ex instanceof MissingServletRequestParameterException
					|| ex instanceof MethodArgumentTypeMismatchException
					|| ex instanceof NumberFormatException
					|| ex instanceof BindException
					|| ex instanceof ConstraintViolationException) {
				code = ApiStatusCode.PARAM_ERROR;
				message = CommonMessage.PARAM_ERROR + exMsg;
			} else if (ex instanceof HttpRequestMethodNotSupportedException) {
				code = ApiStatusCode.METHOD_NOT_ALLOWED; // 405 Method Not Allowed
				message = CommonMessage.METHOD_NOT_ALLOWED + exMsg;
			} else if (ex instanceof HttpMediaTypeNotSupportedException) {
				// 415 Content type not supported
				code = ApiStatusCode.UNSUPPORTED_MEDIA_TYPE;
				message = CommonMessage.CONTENT_TYPE_NOT_SUPPORTED + exMsg;
			} else if (ex instanceof HttpMediaTypeNotAcceptableException) {
				// 406 Could not find acceptable representation
				code = ApiStatusCode.NOT_ACCEPTABLE;
				message = CommonMessage.COULD_NOT_FIND_ACCEPTABLE_REPRESENTATION + exMsg;
			} else if (ex instanceof ServiceException) {
				// 自定义异常
				ServiceException se = (ServiceException) ex;
				if (se.getCode() != 0) {
					code = se.getCode();
				}
				if (code == ApiStatusCode.OK) {
					code = ApiStatusCode.INTERNAL_SERVER_ERROR;
				}
				if (se.getMessage() != null) {
					message = se.getMessage();
				}
			} else {

				code = ApiStatusCode.INTERNAL_SERVER_ERROR;
				if ("dev".equals(runMode) || "uat1".equals(runMode)) {
					exMsg += "\r\n" + ex.toString();
				}
				message = CommonMessage.INTERNAL_SERVER_ERROR + exMsg;
				LOGGER.error("服务器内部异常: ", ex);
			}
		} catch (Exception e) {
			LOGGER.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", e);
		}

		return buildCommonResult(code, message);

	}

	/**
	 * @param code
	 * @param message
	 * @return
	 */
	// 返回结果处理, JSON字符串化的CommonResult对象
	private CommonResult buildCommonResult(int code, String message) {
		CommonResult commonResult = new CommonResult();
		return commonResult.setCodeAndMessage(code, message);
	}
}
