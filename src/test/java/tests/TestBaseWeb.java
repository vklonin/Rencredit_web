package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import io.qameta.allure.selenide.AllureSelenide;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.MainPage;
import utils.Attach;

import java.util.Locale;

import static config.ConfigTests.credentialsConfig;
import static java.lang.String.format;

public class TestBaseWeb {

    Faker faker = new Faker(new Locale("ru"));
    Faker fakerEn = new Faker();
    static MainPage page = new MainPage();
    static SoftAssertions softly = new SoftAssertions();

    @BeforeAll
    static void setup() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        Configuration.startMaximized = true;
        if (System.getProperty("remote").equals("1")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", true);

            Configuration.browserCapabilities = capabilities;
            Configuration.remote = format("https://%s:%s@%s", credentialsConfig.login(), credentialsConfig.password(), System.getProperty("remoteWD"));
        }

    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

    @AfterAll
    static void conclusionSoftAssertions() {
        softly.assertAll();
    }

}
