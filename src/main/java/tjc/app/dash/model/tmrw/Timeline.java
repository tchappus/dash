package tjc.app.dash.model.tmrw;

import java.util.List;

public record Timeline(String timestep, String startTime, String endTime, List<Interval> intervals) {
}
