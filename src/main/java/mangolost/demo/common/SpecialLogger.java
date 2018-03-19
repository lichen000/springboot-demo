package mangolost.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mangolost on 2018-02-27
 */
public class SpecialLogger {
	private static final String LOGGER_NAME = "special";

	private final static Logger LOGGER = LoggerFactory.getLogger(LOGGER_NAME);

	public static void info(String message) {
		LOGGER.info("{}", message);
	}

	public static void main(String[] args) {
		SpecialLogger.info("test");
	}
}
