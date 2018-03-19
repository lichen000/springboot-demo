package mangolost.demo.config;

import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Created by mangolost on 2017-08-08
 */
@ControllerAdvice(basePackages = "mangolost.demo.controller")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

	public JsonpAdvice() {
		super("callback", "jsonp");
	}

	@Override
	protected MediaType getContentType(MediaType contentType, ServerHttpRequest request, ServerHttpResponse response) {
		return MediaType.APPLICATION_JSON_UTF8;
	}
}
