package tests;

import models.GitHubGistFile;
import models.GitHubGistRequest;
import net.datafaker.Faker;

import java.util.Map;

public final class TestData {

    private static final Faker FAKER = new Faker();

    public static final String EXPECTED_USER_TYPE = "User";
    public static final String AUTHENTICATION_REQUIRED_MESSAGE = "Requires authentication";
    public static final String NOT_FOUND_MESSAGE = "Not Found";
    public static final String NOT_FOUND_STATUS = "404";

    public static final String NONEXISTENT_REPO_NAME =
            "no-such-repo-aqa-" + FAKER.number().digits(10);

    public static final String GIST_FILE_NAME =
            "aqa-" + FAKER.number().digits(10) + ".txt";
    public static final String GIST_FILE_CONTENT = FAKER.lorem().sentence();

    public static final String CREATE_DESCRIPTION =
            "aqa " + FAKER.lorem().sentence();
    public static final String UPDATE_DESCRIPTION =
            "aqa updated " + FAKER.lorem().sentence();
    public static final String PARAMETER_DESCRIPTION =
            "aqa param " + FAKER.lorem().sentence();
    public static final String UNAUTHORIZED_DESCRIPTION = "aqa unauthorized";

    public static final String DESCRIPTION_PATH = "description";
    public static final String PUBLIC_PATH = "public";

    public static final String ACCEPT_HEADER = "Accept";
    public static final String GITHUB_ACCEPT_HEADER = "application/vnd.github+json";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";

    public static final int OK_STATUS = 200;
    public static final int CREATED_STATUS = 201;
    public static final int NO_CONTENT_STATUS = 204;
    public static final int UNAUTHORIZED_STATUS = 401;
    public static final int NOT_FOUND_STATUS_CODE = 404;

    public static final String USER_SCHEMA_PATH = "schemas/user_schema.json";
    public static final String REPO_SCHEMA_PATH = "schemas/repo_schema.json";
    public static final String GIST_SCHEMA_PATH = "schemas/gist_schema.json";
    public static final String ERROR_SCHEMA_PATH = "schemas/error_schema.json";

    public static GitHubGistRequest buildGistRequest(String description, boolean isPublic) {
        return GitHubGistRequest.builder()
                .description(description)
                .publicGist(isPublic)
                .files(Map.of(GIST_FILE_NAME,
                        GitHubGistFile.builder().content(GIST_FILE_CONTENT).build()))
                .build();
    }

    private TestData() {
    }
}
