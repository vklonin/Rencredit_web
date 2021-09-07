package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.addListener;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MainPage {

    public static String basePageURL = "http://it-basis.com/";

    public void openPage() {
        open(basePageURL + "/wp-content/uploads/2018/02/cropped-190-54-blue-2.png");
        open(basePageURL);
    }

    @Step("search by '{searchString}'")
    public SelenideElement searchDesktop(String searchString) {
        $("button.fa-search.desktop").click();
        $("input[name=s]").val(searchString).pressEnter();

        return $("ol.search-posts");
    }

    @Step("return first found element")
    public SelenideElement getFirstFoundElementBody(SelenideElement serchResult) {
        serchResult.$$("li").get(0).click();
        return $("body");
    }

    @Step("navigating to the '{menuItem}'")
    public SelenideElement manuNavigateTo(String menuItem) {
        return $$("li").find(Condition.text(menuItem));
    }

    @Step("navigating to the '{menuItem}'")
    public SelenideElement manuNavigateInMenu(String menuItem, SelenideElement menu) {
        return menu.$$("li").find(Condition.text(menuItem));
    }

    public void fillMessageForm(String name, String mail, String subject, String message) {
        $("[name=your-name]").val(name);
        $("[name=your-email]").val(mail);
        $("[name=your-subject]").val(subject);
        $("[name=your-message]").val(message);
    }

    public void submitMessage() {
        $("input[type=submit]").click();
    }
}
