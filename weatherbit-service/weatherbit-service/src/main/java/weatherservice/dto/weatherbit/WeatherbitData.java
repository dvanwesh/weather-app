package weatherservice.dto.weatherbit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WeatherbitData {

    private double appTemp;
    private int aqi;
    private String cityName;
    private int clouds;
    private String countryCode;
    private String datetime;
    private double dewpt;
    private int dhi;
    private int dni;
    private double elevAngle;
    private int ghi;
    private double gust;
    private double windGustSpd;
    private int hAngle;
    private double lat;
    private double lon;
    private String obTime;
    private String pod;
    private double precip;
    private int pres;
    private int rh;
    private double slp;
    private int snow;
    private int solarRad;
    private List<String> sources;
    private String stateCode;
    private String station;
    private String sunrise;
    private String sunset;
    private double temp;
    private double minTemp;
    private double maxTemp;
    private String timezone;
    private long ts;
    private int uv;
    private int vis;
    private WeatherInfo weather;
    private String windCdir;
    private String windCdirFull;
    private int windDir;
    private double windSpd;

    @Getter
    public static class WeatherInfo {
        private int code;
        private String icon;
        private String description;
    }
}
