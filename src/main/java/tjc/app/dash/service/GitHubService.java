package tjc.app.dash.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tjc.app.dash.model.github.GitRequest;
import tjc.app.dash.model.github.GitResponse;
import tjc.app.dash.model.github.QueryVariables;

@Component
public class GitHubService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String githubToken;

    public GitHubService(@Value("${GITHUB_TOKEN}") String githubToken) {
        this.githubToken = githubToken;
    }

    public GitResponse fetchCommitData() {
        var requestBody = new GitRequest("""
                query($userName:String!) {
                		user(login: $userName){
                		  contributionsCollection {
                			contributionCalendar {
                			  totalContributions
                			  weeks {
                				contributionDays {
                				  contributionCount
                				  date
                				}
                			  }
                			}
                		  }
                		}
                	  }
                """, new QueryVariables("tchappus"));

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(githubToken));
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        var request = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForObject("https://api.github.com/graphql", request, GitResponse.class);
    }
}
