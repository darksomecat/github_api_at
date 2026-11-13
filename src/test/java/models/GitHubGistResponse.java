package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubGistResponse {

    private String id;
    private String url;
    private String description;

    @JsonProperty("public")
    private boolean publicGist;

    private Map<String, GitHubGistFileInfo> files;
}
