package tests;

import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.utils.MainNavigationMenuItem;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class WebTests extends TestBase {

    @Test
    void changeCityAndCheckResult() {
        String cityToChange = "Новороссийск";

        page.openPage();
        String cityResult = page.changeCity(cityToChange);
        softly.assertThat(cityResult).as("Test city change").isEqualTo(cityToChange);
    }

    @CsvSource(value = {
            "-25,55 000",
            "25,510 000",
            "50,990 000"
    })

    @ParameterizedTest(name = "Moving of sum handle and checking {1} value )")
    void checkCreditCalculation(int movementPrecentage, String validaionString) {

        page.openPage();
        page.getNavigationMenu().navigateToMenuItem(MainNavigationMenuItem.CREDITS);
        page.getNavigationSubMenu().navigateToMenuItem("Кредит наличными");

        softly.assertThat($(".calculator-block__title").text())
                .as("check credit cash page name")
                .isEqualTo("Кредитный калькулятор");

        page.moveSummHandle(movementPrecentage);

        softly.assertThat($(".js-credit-summ-input").getValue())
                .as("check summ after moving a handle")
                .isEqualTo(validaionString);
    }

    @Test
    @Story("Request a credit card")
    void fillRequestForCard() {

        String mainPhoneNumber = "919" + faker.number().digits(7);
        String phoneToCheck = "+7 (919) " + mainPhoneNumber.substring(3, 6) + "-" + mainPhoneNumber.substring(6);

        page.openPage();
        page.getNavigationMenu().navigateToMenuItem(MainNavigationMenuItem.CARDS);
        page.getNavigationSubMenu().navigateToMenuItem("Кредитная карта \"Практичная\"");
        page.pushRequestCardButton();
        page.fillRequestFieldEndWithEnter("ClientLastName", faker.name().lastName());
        page.fillRequestFieldEndWithEnter("ClientName", faker.name().firstName());
        page.fillRequestFieldEndWithEnter("ClientSecondName", faker.name().firstName());
        page.fillRequestFieldEndWithEnter("ClientMobilePhone", mainPhoneNumber);
        page.fillRequestFieldEndWithEnter("AdditionalEmail", faker.internet().emailAddress());
        page.selectRegion("Санкт-Петербург");
        page.selectGender();
        sleep(500); // иногда не успевает нажать кнопку

        page.pushButton("Далее", 0);

        page.fillRequestFieldEndWithEnter("Passport", faker.number().digits(10));
        page.fillRequestField("PassportDepartment", faker.number().digits(6));
        page.fillRequestFieldEndWithEnter("PassportDate", "11102018");
        page.fillRequestFieldEndWithEnter("PassportBirthday", "11011971");
        page.fillRequestFieldEndWithEnter("PassportBirthPlace", "ТОЛЬКА");
        page.fillRequestFieldEndWithTab("FactRegion", "Респ Татарстан");
        page.fillRequestFieldEndWithTab("FactCityFull", "г Казань");
        page.fillRequestFieldEndWithTab("FactStreetFull", "ул Угловая");
        page.fillRequestFieldEndWithTab("FactHouse", "2");
        page.fillRequestFieldEndWithEnter("FactFlat", "2");
        page.fillRequestFieldEndWithTab("PassportIssuedBy", "РУВД");

        page.pushButton("Далее", 1);

        page.selectOption("IncomingType", 1);
        page.fillRequestField("WorkIncome", "100000");
        page.fillRequestField("EmpName", "ООО НОКИЯ");
        page.selectOption("WorkIndustry", 1);
        page.selectOption("WorkPost", 1);
        page.selectOption("WorkExperienceYears", 5);
        page.selectOption("WorkExperienceMonth", 2);
        page.fillRequestFieldEndWithTab("WorkRegion", "Респ Татарстан");
        page.fillRequestFieldEndWithTab("WorkCityFull", "г Казань");
        page.fillRequestFieldEndWithTab("WorkStreetFull", "ул Угловая");
        page.fillRequestFieldEndWithTab("WorkHouse", "2");
        page.fillRequestFieldEndWithEnter("WorkPhone", "919" + faker.number().digits(7));

        page.pushButton("Далее", 2);

        page.selectOption("OtherDocument", 1);
        page.fillRequestField("AdditionalCreditLimit", "100000");
        page.fillRequestFieldEndWithEnter("HomePhone", "8462" + faker.number().digits(6));
        page.selectOption("FamilyStatus", 1);
        page.selectOption("DependentsNum", 1);
        page.selectOption("EducationLevel", 1);
        page.fillRequestField("AdditionalCode", faker.lebowski().character());

        page.pushButton("Отправить заявку", 0);

        softly.assertThat($(".phone-confirm-block__phone").getText()).as("Request for card phone number ").isEqualTo(phoneToCheck);
    }

    @Story("create a broker account")
    @Test
    void createBrokerAccount() {

        String mainPhoneNumber = "919" + faker.number().digits(7);
        String phoneToCheck = "+7 (919) " + mainPhoneNumber.substring(3, 6) + "-" + mainPhoneNumber.substring(6);

        page.openPage();
        page.getNavigationMenu().navigateToMenuItem(MainNavigationMenuItem.INVESTMENTS);
        page.getNavigationSubMenu().navigateToMenuItem("Брокерский счет");
        page.pushButton("Хочу стать клиентом банка", 0);

        page.fillRequestFieldEndWithEnter("ClientLastName", faker.name().lastName());
        page.fillRequestFieldEndWithEnter("ClientName", faker.name().firstName());
        page.fillRequestFieldEndWithEnter("ClientSecondName", faker.name().firstName());
        page.fillRequestFieldEndWithEnter("ClientMobilePhone", mainPhoneNumber);
        page.fillRequestFieldEndWithEnter("AdditionalEmail", fakerEn.internet().emailAddress());
        page.selectOption("CreditRegion", 5);
        page.selectOption("CreditCity", 1);
        page.selectGender();

        page.pushButton("Далее", 0);

        page.fillRequestFieldEndWithEnter("Passport", faker.number().digits(10));
        page.fillRequestField("PassportDepartment", faker.number().digits(6));
        page.fillRequestFieldEndWithEnter("PassportDate", "11102018");
        page.fillRequestFieldEndWithEnter("PassportBirthday", "11011971");
        page.fillRequestFieldEndWithEnter("PassportBirthPlace", "ТОЛЬКА");
        page.fillRequestFieldEndWithTab("FactRegion", "Респ Татарстан");
        page.fillRequestFieldEndWithTab("FactCityFull", "г Казань");
        page.fillRequestFieldEndWithTab("FactStreetFull", "ул Угловая");
        page.fillRequestFieldEndWithTab("FactHouse", "2");
        page.fillRequestFieldEndWithEnter("FactFlat", "2");
        page.fillRequestFieldEndWithTab("PassportIssuedBy", "РУВД");

        page.pushButton("Далее", 1);

        page.fillRequestField("AdditionalCode", faker.lebowski().character());
        page.pushButton("Отправить заявку", 0);

        softly.assertThat($(".phone-confirm-block__phone").getText()).as("Request for card phone number ").isEqualTo(phoneToCheck);
    }

    @Story("create a deposit account")
    @Test
    void createDepositAccount() {

        String mainPhoneNumber = "919" + faker.number().digits(7);
        String phoneToCheck = "+7 (919) " + mainPhoneNumber.substring(3, 6) + "-" + mainPhoneNumber.substring(6);

        page.openPage();
        page.getNavigationMenu().navigateToMenuItem(MainNavigationMenuItem.DEPOSITS);
        page.getNavigationSubMenu().navigateToMenuItem("Все вклады");
        page.pushButton("Открыть вклад без посещения офиса", 0);

        page.fillRequestFieldEndWithEnter("ClientLastName", faker.name().lastName());
        page.fillRequestFieldEndWithEnter("ClientName", faker.name().firstName());
        page.fillRequestFieldEndWithEnter("ClientSecondName", faker.name().firstName());
        page.fillRequestFieldEndWithEnter("ClientMobilePhone", mainPhoneNumber);
        page.fillRequestFieldEndWithEnter("AdditionalEmail", fakerEn.internet().emailAddress());
        page.selectOption("CreditRegion", 5);
        page.selectOption("CreditCity", 1);
        page.selectGender();

        page.pushButton("Далее", 0);

        page.fillRequestFieldEndWithEnter("Passport", faker.number().digits(10));
        page.fillRequestField("PassportDepartment", faker.number().digits(6));
        page.fillRequestFieldEndWithEnter("PassportDate", "11102018");
        page.fillRequestFieldEndWithEnter("PassportBirthday", "11011971");
        page.fillRequestFieldEndWithEnter("PassportBirthPlace", "ТОЛЬКА");
        page.fillRequestFieldEndWithTab("FactRegion", "Респ Татарстан");
        page.fillRequestFieldEndWithTab("FactCityFull", "г Казань");
        page.fillRequestFieldEndWithTab("FactStreetFull", "ул Угловая");
        page.fillRequestFieldEndWithTab("FactHouse", "2");
        page.fillRequestFieldEndWithEnter("FactFlat", "2");
        page.fillRequestFieldEndWithTab("PassportIssuedBy", "РУВД");

        page.pushButton("Далее", 1);

        page.fillRequestField("AdditionalCode", faker.lebowski().character());
        page.pushButton("Отправить заявку", 0);

        softly.assertThat($(".phone-confirm-block__phone").getText()).as("Request for card phone number ").isEqualTo(phoneToCheck);

        //sleep(5000);

    }


}
