package tests;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class WebTests extends TestBase {

    @Test
    void openMainPageAndCheckLogo() {
        $("img.logo__image").shouldBe(Condition.visible);
    }

    @Test
    void searchStringAndCheckFindings() {

        String searcString = "продукт";
        $("input.search__input").val(searcString).pressEnter();
        $$("p").get(0).shouldHave(Condition.text(searcString));
    }

    @Test
    void chooseCommercialProductCheckIsItRightOne() {

        String productType = "Решения для коммерческих организаций";
        String productItself = "Система электронного документооборота Docsvision";

        $$("div.nav__item").find(Condition.text("Каталог продуктов")).click();//Каталог продуктов
        $$("a.product__item").find(Condition.text(productType)).click();//
        $$("a.product__item").find(Condition.text(productItself)).click();
        $("div.product__content").shouldHave(Condition.text(productItself));
    }

    @Test
    void chooseSalesAreaCheckSalesRepresentative() {

        String salesArea = "Субъекты РФ";
        String stateName = "Амурская область";

        $$("div.nav__item").find(Condition.text("Как начать сотрудничество")).click();
        $$("div.filter__item").find(Condition.text(salesArea)).scrollTo().hover();//
        $$("li.filter__link").find(Condition.text(stateName)).click();
        $("div.team__description").shouldHave(Condition.text(stateName));
    }
}
