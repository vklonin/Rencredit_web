package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


public class WebTests extends TestBase {

    @Test
    void searchStringAndCheckFindings() {
        String searchString = "продукт";

        step("execute search and check results", () -> {
            SelenideElement serchResult = page.searchDesktop(searchString);
            page.getFirstFoundElementBody(serchResult).shouldHave(Condition.text(searchString));
        });
    }

    @Test
    void navigateToSecondLevelMenuItemAndCheckIt() {
        String firstLevelMenu = "УСЛУГИ";
        String secondLevelMenu = "Внедрение MS Dynamics CRM";

        page.manuNavigateTo(firstLevelMenu).hover();
        page.manuNavigateInMenu(secondLevelMenu, page.manuNavigateTo(firstLevelMenu)).click();


        step("check if it is a right '{secondLevelMenu}' page", () ->
                $("body").shouldHave(Condition.text(secondLevelMenu))
        );

    }

    @Test
    void negativeGetConsultationMessage() {
        String name = (new Faker()).name().fullName();
        page.fillMessageForm(name, "", "", "");
        page.submitMessage();
        $("body").shouldHave(Condition.text("Пожалуйста, заполните обязательное поле"));
    }

    @Test
    void negativeSendMessage() {
        String firstLevelMenu = "О нас";
        String secondLevelMenu = "Контакты";

        String name = (new Faker()).name().fullName();

        page.manuNavigateTo(firstLevelMenu).hover();
        page.manuNavigateInMenu(secondLevelMenu, page.manuNavigateTo(firstLevelMenu)).click();

        page.fillMessageForm(name, "", "", "");
        page.submitMessage();

        $("body").shouldHave(Condition.text("Пожалуйста, заполните обязательное поле"));
    }

}
