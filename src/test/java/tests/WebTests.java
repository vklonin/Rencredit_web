package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
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

        $("body").shouldHave(Condition.text(secondLevelMenu));
    }

}
