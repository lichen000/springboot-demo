package mangolost.demo.common.helper;

import java.io.Serializable;

/**
 * Created by mangolost on 2017-04-24
 */
public class CommonResult implements Serializable {

	public static final long serialVersionUID = 1L;

    private int code = 200;
	private String message = "OK";
	private Object data = null;

	public CommonResult() {
	    super();
    }

    public CommonResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

	public CommonResult setCodeAndMessage(int code, String message) {
		this.code = code;
		this.message = message;
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public CommonResult setData(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return "CommonResult{" +
				"code=" + code +
				", message='" + message + '\'' +
				", data=" + data +
				'}';
	}
}
