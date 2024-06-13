package com.koch.automation.framework.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {
    private static final Logger logger = LogManager.getLogger(LogUtils.class);

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logError(String message, Throwable t) {
        logger.error(message, t);
    }
}