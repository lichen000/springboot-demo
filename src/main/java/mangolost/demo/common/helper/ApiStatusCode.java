package mangolost.demo.common.helper;

/**
 * Created by mangolost on 2017-04-19
 */
public class ApiStatusCode {

	//以下是部分官方状态码

	/**
	 * 成功
	 */
	public static final int OK = 200;

	/**
	 * 不支持请求的方法
	 */
	public static final int METHOD_NOT_ALLOWED = 405;

	/**
	 * 不支持请求的方法
	 */
	public static final int NOT_ACCEPTABLE = 406;

	/**
	 * 不是服务器中所支持的媒体类型
	 */
	public static final int UNSUPPORTED_MEDIA_TYPE = 415;

	/**
	 * 系统错误
	 */
	public static final int INTERNAL_SERVER_ERROR = 500;

	/**
	 * 参数格式错误：包括缺少参数、空值、空格、类型错误、格式不符、数值范围有误等
	 */
	public static final int PARAM_ERROR = 430;

	/**
	 * 权限错误：没有权限
	 */
	public static final int AUTH_ERROR = 432;
}