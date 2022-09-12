package tjc.app.dash.model.tmrw;

public record WeatherRequest(float[] location, String[] fields, String units, String startTime, String endTime, String timezone) {
}
