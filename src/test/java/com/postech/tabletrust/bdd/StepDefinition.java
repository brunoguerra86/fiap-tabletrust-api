package com.postech.tabletrust.bdd;

import com.postech.tabletrust.entity.FeedBack;
import com.postech.tabletrust.utils.NewEntititesHelper;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;


public class StepDefinition {
    private Response response;

    private FeedBack feedbackResponse;

    private final String ENDPOINT_API_FEEDBACKS = "http://localhost:8080/feedback";

    @Given("registrar um novo feedback")
    public void registrar_um_novo_feedback() {
        var feedbackRequest = NewEntititesHelper.createAFeedBack();
        response = given().relaxedHTTPSValidation()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(feedbackRequest)
                .when()
                .post(ENDPOINT_API_FEEDBACKS);
    }
}
