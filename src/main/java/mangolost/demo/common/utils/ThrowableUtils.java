package mangolost.demo.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by mangolost on 2017-12-24
 */
public class ThrowableUtils {

	public static String printStackTraceToString(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw, true));
		return sw.getBuffer().toString();
	}

}
