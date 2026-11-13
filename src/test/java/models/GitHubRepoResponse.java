package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepoResponse {

    private Long id;
    private String name;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("default_branch")
    private String defaultBranch;
}
