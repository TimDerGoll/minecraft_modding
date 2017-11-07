package de.timgoll.facadingIndustry.util;

import de.timgoll.facadingIndustry.FacadingIndustry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {
    private static Logger logger;

    public static Logger getLogger() {
        if(logger == null) {
            logger = LogManager.getFormatterLogger(FacadingIndustry.MODID);
        }
        return logger;
    }

}
