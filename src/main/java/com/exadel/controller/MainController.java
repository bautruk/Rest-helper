package com.exadel.controller;

import com.exadel.dto.PredefinedRequestData;
import com.exadel.dto.Response;
import com.exadel.service.MainService;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/")
@SessionAttributes(value = {"authToken"})
public class MainController {

    @Autowired
    private MainService mainService;

    @RequestMapping("/")
    public String getMainPageName() {
        return "index";
    }

    @RequestMapping(value = "/login/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData getPredefParamsForLogin() {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestParam("username", "skoval");
        requestData.addRequestParam("password", "Exadel1");
        requestData.addRequestParam("domain", "botf03.net");

        return requestData;
    }

    @RequestMapping(value = "/forwardUrl/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData getPredefParamsForForward(@ModelAttribute("authToken") String authToken) {
        // TODO: check if authToken is empty
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-UBSAS-Proxy-Authorization", authToken);
        requestData.addRequestHeader("X-UBSAS-FORWARDURL", "http://www.google.com/");

        return requestData;
    }

    @RequestMapping(value = "/exchange/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData getPredefParamsForExchange(@ModelAttribute("authToken") String authToken) {
        // TODO: check if authToken is empty
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();

        requestData.addRequestParam("username", "skoval");
        requestData.addRequestParam("password", "Exadel1");
        requestData.addRequestParam("domain", "botf03.net");
        requestData.addRequestParam("RequestId", "authStepOne");
        requestData.addRequestParam("jsonSupport", "true");

        requestData.addRequestHeader("Authorization", "Bearer " + authToken);

        return requestData;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response authenticate(@RequestBody Map<String, String> requestData, Model model) throws IOException {
        HttpClient client = HttpClients.createDefault();

        HttpPost authRequest = new HttpPost("http://localhost:25945/service/session/logins");
        authRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));

        HttpResponse response = client.execute(authRequest);
        Header[] responseHeaders = response.getHeaders("X-UBSAS-AuthToken");
        model.addAttribute("authToken", responseHeaders[0].getValue());

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/forwardUrl", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response forwardUrl(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient client = HttpClients.createDefault();
        HttpGet forwardRequest = new HttpGet("http://localhost:25945/forwardurl");
        forwardRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = client.execute(forwardRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchange", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response exchange(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient client = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpPost exchangeRequest = new HttpPost("http://localhost:25945/exchjson/service/do/login");
        exchangeRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));
        exchangeRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = client.execute(exchangeRequest);

        return mainService.constructSuccessResponse(response);
    }
}
