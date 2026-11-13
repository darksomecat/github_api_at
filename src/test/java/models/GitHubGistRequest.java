package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubGistRequest {

    private String description;

    @JsonProperty("public")
    private boolean publicGist;

    private Map<String, GitHubGistFile> files;
}
