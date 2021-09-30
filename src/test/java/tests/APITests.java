package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.WebDriverRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.common.mapper.TypeRef;
import config.Layer;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.sleep;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

@Layer("rest")
@Owner("vklonin")
@Feature("Internet bank")
public class APITests extends TestBaseAPI {

    private static Response sessionCookiesInternetBank;
    private static Set<Cookie> cookies;
    private static Cookie cookie;
    private static String cookieForInternetBank = "";
    private static String cookieForCalculation = "";
    private static String header = "";


    static void getCookiesInternetBank() {

        page.openPage("https://ib.rencredit.ru/#/login");
        sleep(500);
        cookies = WebDriverRunner.getWebDriver().manage().getCookies();
        Iterator<Cookie> cookieIterator = cookies.iterator();
        while (cookieIterator.hasNext()) {
            Cookie next = cookieIterator.next();
            cookieForInternetBank = cookieForInternetBank + next.getName() + "=" + next.getValue() + "; ";
            if (next.getName().equals("XSRF-TOKEN")) {
                header = next.getValue();
            }
        }
        cookieForInternetBank = cookieForInternetBank.substring(0, cookieForInternetBank.length() - 2);
    }

    @BeforeAll
    static void getCookiesCalculation() {

        page.openPage("https://rencredit.ru/contributions/");
        sleep(500);
        cookie = WebDriverRunner.getWebDriver().manage().getCookieNamed("rencredit");
        cookieForCalculation = cookie.getName() + "=" + cookie.getValue();
    }

    @Test
    @Story("Negative internet bank registration test valid but not acceptable data")
    @DisplayName("Negative internet bank registration test valid but not acceptable data")
    void internetBankRegistrationNegativeBadData() {

        String requestBody = "product=99999999999&"
                + "phoneNumber=0123456789";
        String checkString = "Вам не может быть подключен Интернет-банк";
        String response =
                step("send a request {requestBody}", () ->
                        given()
                                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                                .body(requestBody)
                                .cookie(cookieForInternetBank)
                                .header("X-XSRF-TOKEN", header)
                                .when()
                                .post("https://ib.rencredit.ru/rencredit.server.portal.app/rest/public/registration")
                                .then()
                                .extract().response().body().asString());
        assertThat(response).contains(checkString);
    }

    @Test
    @Story("Negative internet bank registration test invalid phone number")
    @DisplayName("Negative internet bank registration test invalid phone number")
    void internetBankRegistrationNegativeInvalidPhone() {

        String requestBody = "product=99999999999&"
                + "phoneNumber=77777777";
        String checkString = "Введите номер мобильного телефона, который Вы указывали при оформлении договора";

        String response =
                step("send a request {requestBody}", () ->
                        RestAssured.given()
                                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                                .body(requestBody)
                                .cookie("XSRF-TOKEN=67c92690-19d9-4751-9256-688ef21f5bd1; JSESSIONID=00009mk3lla8HqcgrmhPaeJyiW3:n2")
                                .header("X-XSRF-TOKEN", "67c92690-19d9-4751-9256-688ef21f5bd1")
                                .when()
                                .post("https://ib.rencredit.ru/rencredit.server.portal.app/rest/public/registration")
                                .then()
                                .extract().response().body().asString());
        step("check if response is matching ", () -> assertThat(response).contains(checkString));
    }

    @Test
    @Story("Negative internet bank registration test invalid product related number")
    @DisplayName("Negative internet bank registration test invalid product related number")
    void internetBankRegistrationNegativeValidationError() {

        String requestBody = "product=99999999999sdf&"
                + "phoneNumber=77777777";
        String checkString = "Введите номер банковской карты, указанный на её лицевой стороне (16 цифр)";

        Response response =
                step("send a request {requestBody}", () ->
                        given()
                                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                                .body(requestBody)
                                .cookie("XSRF-TOKEN=67c92690-19d9-4751-9256-688ef21f5bd1; JSESSIONID=00009mk3lla8HqcgrmhPaeJyiW3:n2")
                                .header("X-XSRF-TOKEN", "67c92690-19d9-4751-9256-688ef21f5bd1")
                                .when()
                                .post("https://ib.rencredit.ru/rencredit.server.portal.app/rest/public/registration")
                                .then()
                                .body("errorResponseMo.errorCode", is("VALIDATION_ERROR"))
                                .extract().response());
        step("check if response is matching ", () -> assertThat(response.asString()).contains(checkString));
    }

    @Test
    @Story("Get credit calculation")
    @DisplayName("Get credit calculation check if it in the frame of parmeters")
    void checkIfResponseIsRightForRequest() {
        String requestBody = "ajax=Y&calc=Y&amount=500000&currency=RUB&paymentPeriod=-1&period=18&supplementPeriod=0&supplementAmount=0&partial=0&hasCapitalization=0&deposit_b_y=true";

        Response response =
                step("send a request {requestBody}", () ->
                        given()
                                //.contentType("application/x-www-form-urlencoded; charset=UTF-8")
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                                .cookie(cookieForCalculation)//.cookie("rencredit=i62ceskraa0assvja8pra35k84")
                                .when()
                                .post("https://rencredit.ru/contributions/")
                                .then()
                                .extract().response());
        assertThat(response.asString()).contains("\"InterestRate\":0.0825");
    }

    @Test
    void checkIfResponseIsRightForRequestJSON() throws IOException {
        String requestBody = "ajax=Y&calc=Y&amount=500000&currency=RUB&paymentPeriod=-1&period=18&supplementPeriod=0&supplementAmount=0&partial=0&hasCapitalization=0&deposit_b_y=true";

        //Map<String,Object> responseBody = null;
        ResponseBodyExtractionOptions responseBody = null;

        responseBody =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        //.contentType(ContentType.JSON)
                        .body(requestBody)
                        .cookie(cookieForCalculation)//.cookie("rencredit=i62ceskraa0assvja8pra35k84")
                        .when()
                        .post("https://rencredit.ru/contributions/")
                        .then()
                        .extract().body();
        //.as(new TypeRef<Map<String, Object>>() {});

        System.out.println(responseBody.asString());

        Map<String, Object> response = new ObjectMapper().readValue(responseBody.asString(), HashMap.class);

        JSONObject myObject = new JSONObject(response);

        System.out.println(myObject.get("Result.InterestRate"));
    }


    //TODO
    //Есть много возможностей тестировать API который считает кредитные параметры в  зависимости от положения ручек


}
