package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubErrorResponse {

    private String message;

    @JsonProperty("documentation_url")
    private String documentationUrl;

    private String status;
}
