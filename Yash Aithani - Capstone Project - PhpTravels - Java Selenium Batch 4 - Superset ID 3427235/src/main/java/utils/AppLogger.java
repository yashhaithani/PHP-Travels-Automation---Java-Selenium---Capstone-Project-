package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
	private static final Logger logger = LoggerFactory.getLogger(AppLogger.class);

	public static void info(String message) {
		logger.info(message);
	}

	public static void warn(String message) {
		logger.warn(message);
	}

	public static void error(String message) {
		logger.error(message);
	}

	public static void debug(String message) {
		logger.debug(message);
	}

	// SLF4J does not have fatal, so map it to error with [FATAL] prefix
	public static void fatal(String message) {
		logger.error("[FATAL] {}", message);
	}
}
