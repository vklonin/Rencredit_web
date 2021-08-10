package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.selector.ByAttribute;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class WebTests extends TestBase{


    @Test
    void openBasicPage(){
        open("https://www.dataart.com");
        $(".language-button__lang").$(byText("EN (US)")).shouldBe(Condition.visible);
    }
    @Test
    void openUAPage(){
        open("https://www.dataart.com.ua");
        $$(".main-menu__item").get(0).hover();
        $(byAttribute("title", "Про DataArt")).click();
        $(".feature__heading").shouldHave(Condition.text("ПРО DATAART"));
     }
    @Test
    void contactUsNegative(){ //making negative test in order to not load real software with fake data
        open("https://www.dataart.com");
        $(byAttribute("title", "CONTACT US")).click();
        $(byAttribute("name", "first_name")).setValue(faker.name().firstName());
        $(byAttribute("name", "last_name")).setValue(faker.name().lastName());
        $(byAttribute("name", "email")).setValue(faker.internet().emailAddress());
        $(byAttribute("type", "submit")).click();
        $(".form-success__heading").shouldNotBe(Condition.visible);

    }



}
