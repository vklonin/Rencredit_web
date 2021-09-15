package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MainPage {

    private static String basePageURL = "https://rencredit.ru";
    private static MainNavigationMenu navigationMenu = new MainNavigationMenu();
    private static NavigationSubMenu navigationSubMenu = new NavigationSubMenu();

    public String getBasePageURL() {
        return basePageURL;
    }

    public NavigationSubMenu getNavigationSubMenu() {
        return new NavigationSubMenu();
    }

    public MainNavigationMenu getNavigationMenu() {
        return new MainNavigationMenu();
    }

    @Step("Open initial page")
    public void openPage() {
        open(basePageURL + "/local/templates/.default/assets/images/logo.svg");
        open(basePageURL);
    }

    @Step("Change city to '{cityToChange}'")
    public String changeCity(String cityToChange) {
        $("span.site-geolocation__name-link-title").click();
        $$("li.change-location-window__list-item").find(Condition.text(cityToChange)).click();
        return $("span.site-geolocation__name-link-title").text();
    }

    @Step("Move handle on a {movementPrecentage} %")
    public void moveSummHandle(int movementPrecentage) {

        SelenideElement sliderHandle = $(".ui-slider-handle");
        SelenideElement sliderBar = $(".js-credit-summ-range");

        int sliderLength = sliderBar.getSize().getWidth();
        int handleWidth = sliderHandle.getSize().getWidth();
        Actions builder = new Actions(getWebDriver());
        builder.clickAndHold(sliderHandle)
                .moveByOffset(sliderLength / 100 * movementPrecentage + handleWidth / 2 + 16, 0)
                .release(sliderHandle).build().perform();
    }

    @Step("Push request card button")
    public void pushRequestCardButton() {
        $("a.card-main__btn").click();
    }

    @Step("Fill {locator} field with {value} ")
    public void fillRequestField(String locator, String value) {
        $("[name=" + locator + "]").setValue(value);
    }

    @Step("Fill {locator} field with {value} ")
    public void fillRequestFieldEndWithEnter(String locator, String value) {
        $("[name=" + locator + "]").setValue(value).pressEnter();
    }

    @Step("Fill {locator} field with {value} ")
    public void fillRequestFieldEndWithTab(String locator, String value) {
        $("[name=" + locator + "]").setValue(value).pressTab();
    }

    @Step("Select region {region} ")
    public void selectRegion(String region) {
        $(".CreditRegion").click();
        $$("li").find(Condition.text(region)).click();
        $("div[data-placeholder=Город]").doubleClick();

    }

    @Step("Push button {name}")
    public void pushButton(String name, int step) {
        $$(byText(name)).get(step).click();
    }

    public void selectGender() {
        if ($(byText("Мужской")).isDisplayed()) {
            $(byText("Мужской")).click();
        }
    }

    @Step("Select option {selector} field with {i} ")
    public void selectOption(String selector, int i) {
        $("select[name=" + selector + "]").selectOption(i);
    }
}
