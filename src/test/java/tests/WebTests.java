package tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class WebTests extends TestBase{

    @Test
    void openMainPage(){
        step("Open main page", () -> open("https://dataart.team"));
        step("Check if it is a right page" , () -> $("*").shouldHave(Condition.exactText("")));
    }

}
