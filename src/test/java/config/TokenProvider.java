package config;

public final class TokenProvider {

    private static final String GITHUB_TOKEN_ENV = "GITHUB_TOKEN";

    public static String resolveToken(ApiConfig config) {
        if (!config.token().isBlank()) {
            return config.token();
        }

        String environmentToken = System.getenv(GITHUB_TOKEN_ENV);
        return environmentToken == null ? "" : environmentToken;
    }

    private TokenProvider() {
    }
}
