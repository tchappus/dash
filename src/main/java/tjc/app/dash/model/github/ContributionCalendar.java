package tjc.app.dash.model.github;

import java.util.List;

public record ContributionCalendar(int totalContributions, List<CommitWeek> weeks) {
}
