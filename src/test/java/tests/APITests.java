package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

public class APITests {

    @Test
    void internetBankRegistrationNegativeBadData() {

        String requestBody = "product=99999999999&"
                + "phoneNumber=0123456789";
        String checkString = "Вам не может быть подключен Интернет-банк";
        String response =
                step("send a request {requestBody}", () ->
                        given()
                                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                                .body(requestBody)
                                .cookie("XSRF-TOKEN=67c92690-19d9-4751-9256-688ef21f5bd1; JSESSIONID=00009mk3lla8HqcgrmhPaeJyiW3:n2")
                                .header("X-XSRF-TOKEN", "67c92690-19d9-4751-9256-688ef21f5bd1")
                                .when()
                                .post("https://ib.rencredit.ru/rencredit.server.portal.app/rest/public/registration")
                                .then()
                                .extract().response().body().asString());
        assertThat(response).contains(checkString);
    }

    @Test
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
        assertThat(response.asString()).contains(checkString);
    }

}
