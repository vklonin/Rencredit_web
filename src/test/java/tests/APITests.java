package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

public class APITests {

    @Test
    void internetBankRegistrationNegativeBadData() {

        String response =

                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .body("product=99999999999&"
                                + "phoneNumber=0123456789")
                        .cookie("XSRF-TOKEN=67c92690-19d9-4751-9256-688ef21f5bd1; JSESSIONID=00009mk3lla8HqcgrmhPaeJyiW3:n2")
                        .header("X-XSRF-TOKEN", "67c92690-19d9-4751-9256-688ef21f5bd1")
                        .when()
                        .post("https://ib.rencredit.ru/rencredit.server.portal.app/rest/public/registration")
                        .then()
                        .extract().response().body().asString();


        assertThat(response).contains("Вам не может быть подключен Интернет-банк");

    }

    @Test
    void internetBankRegistrationNegativeInvalidPhone() {

        String response =

                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .body("product=99999999999&"
                                + "phoneNumber=77777777")
                        .cookie("XSRF-TOKEN=67c92690-19d9-4751-9256-688ef21f5bd1; JSESSIONID=00009mk3lla8HqcgrmhPaeJyiW3:n2")
                        .header("X-XSRF-TOKEN", "67c92690-19d9-4751-9256-688ef21f5bd1")
                        .when()
                        .post("https://ib.rencredit.ru/rencredit.server.portal.app/rest/public/registration")
                        .then()
                        .extract().response().body().asString();


        assertThat(response).contains("Введите номер мобильного телефона, который Вы указывали при оформлении договора");

    }

    @Test
    void internetBankRegistrationNegativeValidationError() {

        Response response =

                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .body("product=99999999999zdf&"
                                + "phoneNumber=77777777")
                        .cookie("XSRF-TOKEN=67c92690-19d9-4751-9256-688ef21f5bd1; JSESSIONID=00009mk3lla8HqcgrmhPaeJyiW3:n2")
                        .header("X-XSRF-TOKEN", "67c92690-19d9-4751-9256-688ef21f5bd1")
                        .when()
                        .post("https://ib.rencredit.ru/rencredit.server.portal.app/rest/public/registration")
                        .then()
                        .body("errorResponseMo.errorCode", is("VALIDATION_ERROR"))
                        .extract().response();


        assertThat(response.asString()).contains("Введите номер мобильного телефона, который Вы указывали при оформлении договора");

    }

}
