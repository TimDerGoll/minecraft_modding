package de.timgoll.facading.util;

import de.timgoll.facading.Facading;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
    private static Logger logger;

    public static Logger getLogger() {
        if(logger == null) {
            logger = LogManager.getFormatterLogger(Facading.MODID);
        }
        return logger;
    }

}
