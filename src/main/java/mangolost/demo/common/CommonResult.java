package mangolost.demo.common;

import java.io.Serializable;

/**
 * Created by mangolost on 2017-04-24
 */
public class CommonResult implements Serializable {

	public static final long serialVersionUID = 1L;

    private int code = 200;
	private String message = "OK";
	private Object data;
	private long ts = System.currentTimeMillis();

	public CommonResult() {
	    super();
    }

    public CommonResult(int code, String message, Object data, long ts) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.ts = ts;
    }

	public void setCodeAndMessage(int code, String message) {
		this.code = code;
		this.message = message;
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

	public void setData(Object data) {
		this.data = data;
	}

	public long getTs() {
		return ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}

	@Override
	public String toString() {
		return "CommonResult{" +
				"code=" + code +
				", message='" + message + '\'' +
				", data=" + data +
				", ts=" + ts +
				'}';
	}
}