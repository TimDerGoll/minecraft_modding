package de.timgoll.facading.misc;

public class Common {

    public static int limit(int value, int min, int max) {
        if (value > max)
            return max;
        if (value < min)
            return min;
        return value;
    }

}
