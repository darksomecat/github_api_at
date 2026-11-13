package helpers;

import api.GistApi;
import models.GitHubGistRequest;
import models.GitHubGistResponse;

import static specs.BaseSpec.noContent204ResponseSpec;
import static specs.GistSpec.createdGistResponseSpec;

public final class GistHelper {

    public static GitHubGistResponse createGist(GitHubGistRequest request) {
        return GistApi.createGist(request)
                .spec(createdGistResponseSpec)
                .extract().as(GitHubGistResponse.class);
    }

    public static void deleteGist(String gistId) {
        GistApi.deleteGist(gistId).spec(noContent204ResponseSpec);
    }

    private GistHelper() {
    }
}
