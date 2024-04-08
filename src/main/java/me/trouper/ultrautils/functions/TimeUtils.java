package me.trouper.ultrautils.functions;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtils {

    public static boolean isWithin(LocalDateTime time1, LocalDateTime time2, int maxDifferenceSeconds) {
        long differenceSeconds = Math.abs(java.time.Duration.between(time1, time2).getSeconds());

        return differenceSeconds <= maxDifferenceSeconds;
    }

    public static long convertToUnixTimestamp(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }

    public static LocalDateTime convertToLocalDateTime(long unixTimestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp), ZoneOffset.UTC);
    }
}
