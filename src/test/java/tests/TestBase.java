package tests;

import config.ApiConfig;
import org.aeonbits.owner.ConfigFactory;

public class TestBase {

    protected static final ApiConfig CONFIG = ConfigFactory.create(ApiConfig.class, System.getProperties());
}
