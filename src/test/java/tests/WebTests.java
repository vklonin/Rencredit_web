package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import pages.MainPage;

import static com.codeborne.selenide.Selenide.*;


public class WebTests extends TestBase {

    @Test
    void openMainPageAndCheckLogo() {

        $("img.logo__image").shouldBe(Condition.visible);
    }

    @Test
    void searchStringAndCheckFindings() {
        String searchString = "продукт";

        page.search(searchString);
        $$("p").get(0).shouldHave(Condition.text(searchString));
    }

    @Test
    void chooseCommercialProductCheckIsItRightOne() {

        String productType = "Решения для коммерческих организаций";
        String productItself = "Система электронного документооборота Docsvision";

        page.switchToMainMenuItem("Каталог продуктов");

        $$("a.product__item").find(Condition.text(productType)).click();
        $$("a.product__item").find(Condition.text(productItself)).click();
        $("div.product__content").shouldHave(Condition.text(productItself));
    }

    @Test
    void chooseSalesAreaCheckSalesRepresentative() {

        String salesArea = "Субъекты РФ";
        String stateName = "Амурская область";

        page.openPage();
        page.switchToMainMenuItem("Как начать сотрудничество");

        $$("div.filter__item").find(Condition.text(salesArea)).scrollTo().hover();//
        $$("li.filter__link").find(Condition.text(stateName)).click();
        $("div.team__description").shouldHave(Condition.text(stateName));
    }

    @Test
    void chooseCareerOpportunityCheckFilterWorks(){

        String mainMenuItem = "Карьера";
        String SecondMenuItem = "Вакансии";
        String vacancyType = "Административный персонал";

        page.hoverToMainMenuItem(mainMenuItem);
        $$("div.sub_list__item").find(Condition.text(SecondMenuItem)).click();
        $$("span.career__title").find(Condition.text(vacancyType)).scrollTo().click();
        SelenideElement option = $$("option").get(2);
        option.click();
        $$("button").find(Condition.text("Найти")).scrollTo().pressEnter();
        String area = option.getText();
        $$("button").find(Condition.text("Найти")).scrollTo();
        $$("div.vacancy__item").get(0).shouldBe(Condition.text(area));
    }
}
