package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubGistFileInfo {

    private String filename;
    private String content;

    @JsonProperty("raw_url")
    private String rawUrl;
}
