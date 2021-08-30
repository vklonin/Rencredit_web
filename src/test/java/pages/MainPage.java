package pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MainPage {

    public static String basePageURL = "https://bftcom.com";

    public void openPage() {
        Cookie subWindow = new Cookie("visibleSubBanner", "Y");
        open(basePageURL + "/local/templates/bft/images/1x/logo-mobile.svg");
        getWebDriver().manage().addCookie(subWindow);
        open(basePageURL);
    }

    public void search(String searchString){
        $("input.search__input").val(searchString).pressEnter();
    }

    public void switchToMainMenuItem(String menuItem){
        $$("div.nav__item").find(Condition.text(menuItem)).click();
    }


}
