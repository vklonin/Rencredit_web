package tests;

import com.github.javafaker.Faker;
import org.assertj.core.api.SoftAssertions;
import pages.MainPage;

import java.util.Locale;

public class TestBaseAPI {

    Faker faker = new Faker(new Locale("ru"));
    Faker fakerEn = new Faker();
    static MainPage page = new MainPage();
    static SoftAssertions softly = new SoftAssertions();

}
