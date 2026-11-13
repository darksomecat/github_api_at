package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:api.properties"
})
public interface ApiConfig extends Config {

    @Key("baseUri")
    @DefaultValue("https://api.github.com")
    String baseUri();

    @Key("githubUser")
    @DefaultValue("TestAPIAQA")
    String user();

    @Key("githubRepo")
    @DefaultValue("octocat/Hello-World")
    String repo();

    @Key("githubToken")
    @DefaultValue("")
    String token();
}
