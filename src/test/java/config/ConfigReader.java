package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:config/credentials.properties"})
public interface ConfigReader extends Config {
    String login();
    String password();
}

