package com.exadel.controller;

import com.exadel.dto.PredefinedRequestData;
import com.exadel.dto.Response;
import com.exadel.service.MainService;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
@SessionAttributes(value = {"authToken", "setCookieHeaderValue", "exchgToken", "inboxEntryId", "contactsEntryId"})
public class MainController {

    @Autowired
    private MainService mainService;

//    private final String baseUrl = "http://localhost:80/";
    private final String baseUrl = "http://msa.botf03.net:180/";

    private final String loginUrl = baseUrl + "service/session/logins";
    private final String forwardUrl = baseUrl + "forwardurl";
    private final String exchangeLoginUrl = baseUrl + "exchjson/service/do/login";
    private final String exchangeFoldersUrl = baseUrl + "exchjson/service/do/folders";
    private final String exchangeFolderdataUrl = baseUrl + "exchjson/service/do/folderdata";
    private final String exchangeContactsdataUrl = baseUrl + "exchjson/service/do/contactsdata";

    @RequestMapping("/")
    public String getMainPageName() {
        return "index";
    }

    @RequestMapping(value = "/login/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForLogin() {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestParam("username", "milshtyu");
        requestData.addRequestParam("password", "Frame1hawk");
        requestData.addRequestParam("domain", "botf03.net");
        requestData.addRequestParam("deviceId", "sfdfwefwefds");
        requestData.addRequestParam("deviceIp", "192.168.0.12");
        requestData.addRequestParam("os", "IOS");
        requestData.addRequestParam("osVersion", "7.1");
        requestData.addRequestParam("application", "BOTF Mobile");
        requestData.addRequestParam("applicationVersion", "1");

        return requestData;
    }

    @RequestMapping(value = "/forwardUrl/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForForward(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-UBSAS-Proxy-Authorization", authToken);
        requestData.addRequestHeader("X-UBSAS-FORWARDURL", "http://www.google.com/");

        return requestData;
    }

    @RequestMapping(value = "/exchangeLogin/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgLogin(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("Authorization", "Bearer " + authToken);

        requestData.addRequestParam("username", "milshtyu");
        requestData.addRequestParam("password", "Frame1hawk");
        requestData.addRequestParam("domain", "botf03.net");
        requestData.addRequestParam("RequestId", "authStepOne");
        requestData.addRequestParam("jsonSupport", "true");

        return requestData;
    }

    @RequestMapping(value = "/exchangeFolders/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgFolders(@ModelAttribute("authToken") String authToken,
                                                             @ModelAttribute("setCookieHeaderValue") String setCookieHeaderValue,
                                                             @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("Cookie", setCookieHeaderValue);
        requestData.addRequestHeader("Authorization", "Bearer " + authToken);
        requestData.addRequestHeader("X-UBSAS-Exchg-Token", exchgToken);

        requestData.addRequestParam("AllTree", "false");

        return requestData;
    }

    @RequestMapping(value = "/exchangeFolderData/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgFolderData(@ModelAttribute("authToken") String authToken,
                                                                @ModelAttribute("setCookieHeaderValue") String setCookieHeaderValue,
                                                                @ModelAttribute("exchgToken") String exchgToken,
                                                                @ModelAttribute("inboxEntryId") String entryId) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("Cookie", setCookieHeaderValue);
        requestData.addRequestHeader("Authorization", "Bearer " + authToken);
        requestData.addRequestHeader("X-UBSAS-Exchg-Token", exchgToken);

        requestData.addRequestParam("EntryID", entryId);
        requestData.addRequestParam("StartIndex", "0");
        requestData.addRequestParam("PageSize", "500");
        requestData.addRequestParam("ShortHeader", "true");
        requestData.addRequestParam("WithBody", "false");
        requestData.addRequestParam("ModifiedSince", "0");

        return requestData;
    }

    @RequestMapping(value = "/exchangeContactsData/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgContactsData(@ModelAttribute("authToken") String authToken,
                                                                  @ModelAttribute("setCookieHeaderValue") String setCookieHeaderValue,
                                                                  @ModelAttribute("exchgToken") String exchgToken,
                                                                  @ModelAttribute("contactsEntryId") String entryId) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("Cookie", setCookieHeaderValue);
        requestData.addRequestHeader("Authorization", "Bearer " + authToken);
        requestData.addRequestHeader("X-UBSAS-Exchg-Token", exchgToken);

        requestData.addRequestParam("EntryID", entryId);

        return requestData;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response login(@RequestBody Map<String, String> requestData, Model model) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost authRequest = new HttpPost(loginUrl);
        authRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));

        HttpResponse response = httpClient.execute(authRequest);

        Header tokenHeader = response.getFirstHeader("X-UBSAS-AuthToken");
        if (tokenHeader != null) {
            model.addAttribute("authToken", tokenHeader.getValue());
        }
        model.addAttribute("setCookieHeaderValue", "");

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/forwardUrl", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response forwardUrl(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet forwardRequest = new HttpGet(forwardUrl);
        forwardRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(forwardRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchangeLogin", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response exchangeLogin(@RequestBody Map<String, String> requestData, Model model) throws IOException {

        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpPost exchangeRequest = new HttpPost(exchangeLoginUrl);
        exchangeRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));
        exchangeRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(exchangeRequest);
        Response result = mainService.constructSuccessResponse(response);
        Matcher matcher = getMatcherForSecurityToken(result);

        if (matcher.find()) {
            model.addAttribute("setCookieHeaderValue", getHeaderBeginWith(response, "Set-Cookie", "X-UBSAS-SESSIONID").getValue());
            model.addAttribute("exchgToken", matcher.group(1));
        }

        return result;
    }

    @RequestMapping(value = "/exchangeFolders", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeFolders(@RequestBody Map<String, String> requestData, Model model) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpPost foldersRequest = new HttpPost(exchangeFoldersUrl);
        foldersRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));
        foldersRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(foldersRequest);
        Response result = mainService.constructSuccessResponse(response);

        Matcher inboxMatcher = getMatcherForEntryID(result, "Inbox");
        if (inboxMatcher.find()) {
            model.addAttribute("inboxEntryId", inboxMatcher.group(1));
        }

        Matcher contactsMatcher = getMatcherForEntryID(result, "Contacts");
        if (contactsMatcher.find()) {
            model.addAttribute("contactsEntryId", contactsMatcher.group(1));
        }

        return result;
    }

    @RequestMapping(value = "/exchangeFolderData", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeFolderData(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpPost foldersRequest = new HttpPost(exchangeFolderdataUrl);
        foldersRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));
        foldersRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(foldersRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchangeContactsData", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeContactsData(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpPost contactsRequest = new HttpPost(exchangeContactsdataUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData.get("parameters").trim()));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response);
    }

    private Matcher getMatcherForSecurityToken(Response response) {
        Pattern pattern = Pattern.compile("\"SecurityToken\":\"(.*)\"");
        return pattern.matcher(response.getBody());
    }

    private Matcher getMatcherForEntryID(Response response, String name) {
        Pattern pattern = Pattern.compile("\"Name\":\"" + name + "\",\\s*\"EntryID\":\"([^\"]+)");
        return pattern.matcher(response.getBody());
    }

    private Header getHeaderBeginWith(HttpResponse response, String name, String begin) {
        for (Header header : response.getHeaders(name)) {
            if (header.getValue().startsWith(begin)) {
                return header;
            }
        }

        return null;
    }
}
