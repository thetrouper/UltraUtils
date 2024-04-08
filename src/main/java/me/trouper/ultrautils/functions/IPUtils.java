package me.trouper.ultrautils.functions;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.trouper.ultrautils.UltraUtils;
import me.trouper.ultrautils.data.IpInfo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class IPUtils {

    public static Map<String, JsonObject> reportInfo = new HashMap<>();

    public static String getGeoIPJson(String ip) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://ip-api.com/json/" + ip))
                    .header("X-RapidAPI-Key", "152bf8edc6msh42401aab686811fp144d68jsn3f14a7aec380")
                    .header("X-RapidAPI-Host", "ip-geo-location.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "{}";
        }
    }

    public static IpInfo getInfo(String ip) {
        if (UltraUtils.storage.ipInfoLog.containsKey(ip)) {
            IpInfo seen = UltraUtils.storage.ipInfoLog.get(ip);
            if (TimeUtils.isWithin(LocalDateTime.now(),TimeUtils.convertToLocalDateTime(seen.lastSeen()),5)) {
                return null;
            }
            IpInfo updated = new IpInfo(seen.ip(),seen.country(),seen.region(),seen.city(),seen.zip(),seen.lat(),seen.lon(),seen.isp(),seen.org(),seen.as(),seen.timesSeen() + 1, TimeUtils.convertToUnixTimestamp(LocalDateTime.now()));
            UltraUtils.storage.ipInfoLog.replace(ip,updated);
            UltraUtils.storage.save();
            return updated;
        }
        String json = IPUtils.getGeoIPJson(ip);
        JsonObject loaded = JsonParser.parseString(json).getAsJsonObject();
        IpInfo ipInfo = new IpInfo(ip,
                loaded.get("country").getAsString(),
                loaded.get("regionName").getAsString(),
                loaded.get("city").getAsString(),
                loaded.get("zip").getAsString(),
                loaded.get("lat").getAsString(),
                loaded.get("lon").getAsString(),
                loaded.get("isp").getAsString(),
                loaded.get("org").getAsString(),
                loaded.get("as").getAsString(),
                0,
                TimeUtils.convertToUnixTimestamp(LocalDateTime.now())
        );
        UltraUtils.storage.ipInfoLog.put(ip,ipInfo);
        UltraUtils.storage.save();
        return ipInfo;
    }

    public static JsonObject IPReportInfo(String ip) {
        if (reportInfo.containsKey(ip)) return reportInfo.get(ip);
        try {
            String apiUrl = "https://api.abuseipdb.com/api/v2/reports";

            String ipAddress = "167.86.121.64";
            int page = 5;
            int perPage = 25;

            URI uri = new URI(apiUrl + "?ipAddress=" + ipAddress + "&page=" + page + "&perPage=" + perPage);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Key", "b009a77fa7ec89e521e94388356c8970ca8efbf8fa84c6eb588212caaef399cf8fbd781e3884f2d4")
                    .header("Accept", "application/json")
                    .build();

            // Send the request and handle the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());


        // Print the response body
        return JsonParser.parseString(response.body()).getAsJsonObject();
        } catch (Exception ex) {
            return new JsonObject();
        }
    }

    public static int totalReports(String ip) {
        return IPReportInfo(ip).getAsJsonObject("data").get("total").getAsInt();
    }
}
