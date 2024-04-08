package me.trouper.ultrautils.data;

import java.time.LocalDateTime;

public record IpInfo(String ip, String country, String region, String city, String zip, String lat, String lon, String isp, String org, String as, int timesSeen, long lastSeen) {

}
