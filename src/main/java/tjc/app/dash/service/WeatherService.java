package tjc.app.dash.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tjc.app.dash.model.tmrw.WeatherRequest;
import tjc.app.dash.model.tmrw.WeatherResponse;

@Component
public class WeatherService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String tomorrowToken;

    public WeatherService(@Value("${TOMORROW_IO_TOKEN}") String tomorrowToken) {
        this.tomorrowToken = tomorrowToken;
    }


    public WeatherResponse fetchWeatherData() {
        var requestBody = new WeatherRequest(new float[]{45.5245773f, -73.596708f}, new String[]{"temperature"}, "metric", "now", "nowPlus6h", "America/Montreal");

        var request = new HttpEntity<>(requestBody);
        return restTemplate.postForObject("https://api.tomorrow.io/v4/timelines?apikey=%s".formatted(tomorrowToken), request, WeatherResponse.class);

    }

}
