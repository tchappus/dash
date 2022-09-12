package tjc.app.dash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tjc.app.dash.service.GitHubService;
import tjc.app.dash.service.WeatherService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private final GitHubService gitHubService;
    private final WeatherService weatherService;

    public IndexController(GitHubService gitHubService, WeatherService weatherService) {
        this.gitHubService = gitHubService;
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String index(final Model model) {

        var gitResponse = gitHubService.fetchCommitData();
        var maxCommits = 0;
        List<List<Integer>> weekDays = List.of(new ArrayList<>(), new ArrayList<>(),  new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        for (var week : gitResponse.data().user().contributionsCollection().contributionCalendar().weeks()) {
            for (int i = 0; i < week.contributionDays().size(); i++) {
                var commits = week.contributionDays().get(i).contributionCount();

                if (commits > maxCommits) {
                    maxCommits = commits;
                }

                weekDays.get(i).add(commits);
            }
        }

        var commitRatio = 100 / maxCommits;

        model.addAttribute("commitWeekDays", weekDays);
        model.addAttribute("commitRatio", commitRatio);

        var weatherResponse = weatherService.fetchWeatherData();
        model.addAttribute("temp", weatherResponse.data().timelines().get(0).intervals().get(0).values().temperature());

        return "index";
    }
}
