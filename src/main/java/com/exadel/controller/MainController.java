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

    private final String baseUrl = "http://localhost:80/";
//    private final String baseUrl = "https://controller.botf03.net:4440/newmsa/";
//    private final String baseUrl = "http://msa.botf03.net:180/";

    private final String loginUrl = baseUrl + "proxy/authentication/login";
    private final String getPasswordUrl = baseUrl + "proxy/profile/getPassword";
    private final String explorerFolderDataUrl = baseUrl + "shared-drive/do/dir";
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
//        {"username":"milshtyu",
//                "password":"Frame1hawk",
//                "encryptedPassword":"Frame1hawk",
//                "domain":"botf03.net",
//                "deviceId":"sfdfwefwefds",
//                "deviceIp":"192.168.0.12",
//                "os":"IOS",
//                "osVersion":"7.1",
//                "application":"edge",
//                "applicationVersion":"1"}
//        requestData.addRequestParam("username", "milshtyu");
//        requestData.addRequestParam("password", "Frame1hawk");
//        requestData.addRequestParam("encryptedPassword", "MOZ8KuXRS0GPa9dx020/kg==");
//        OjQDB8l/1JzIPZFqXxLV9uoWbxX3vzX4+FWamQgRt+n9FnLxyHU4wprWuGX2o+OURdbCzJxIKAvhOdp99rNNPxx/R2QaS7kXBQ/VgCg/EFsbM6K7e428wIW6MnfCSsnf0ofgpO38a893aq2mwfSTKts+xpJgxogq1S8fvPKJftH9EPQI57kg6xhiyrvpX5RYl+W/YBca/s4j52D7LwFaWk+NG5mS2xkjAZs0OBQ2JGKloExTv5fJMLdngbNxAFpex9ZJcg3yp7nvp/zC4WZrF1QNGNIPUTHZE0pidJh52QvqLmzpv/lJJDSPvB19dAck49fwKChp7rNhYbMr3OW/BQ==

//        requestData.addRequestParam("OjQDB8l/1JzIPZFqXxLV9uoWbxX3vzX4+FWamQgRt+n9FnLxyHU4wprWuGX2o+OURdbCzJxIKAvhOdp99rNNPxx/R2QaS7kXBQ/VgCg/EFsbM6K7e428wIW6MnfCSsnf0ofgpO38a893aq2mwfSTKts+xpJgxogq1S8fvPKJftH9EPQI57kg6xhiyrvpX5RYl+W/YBca/s4j52D7LwFaWk+NG5mS2xkjAZs0OBQ2JGKloExTv5fJMLdngbNxAFpex9ZJcg3yp7nvp/zC4WZrF1QNGNIPUTHZE0pidJh52QvqLmzpv/lJJDSPvB19dAck49fwKChp7rNhYbMr3OW/BQ==", "pkIYHk+1Iro776XKsSjdD5tRsGKPfo7RMtGBjp7lGcJMUrYFpQKS7L6LyNaOQ4QJlCeZC641B7wU80uFd1EDxwzU1CkFOIoDolzkaIWp0hbDMAH2sbWj4wrP0cKrwnkt7XVAEt5MxeDArs18fzAw8uUcpbHjjDDM4F+mQdNLEHuI6OOSE4p/VYy87OE+S1zzmwS0bgOCk7oZ3JicQXRgAgrkym28K9gdeVJ9jTQ1ji5cLXV7OSz4RPW32hvUfkJqq4k2q9YAhTSqxycPSBJ2ilR6QAKU6CS5doU5KGOFeosR2jXCJ4kijT/pGLRwW+uZ4mLPxNrUwikvbpZ420BYpg==");
        requestData.addRequestParam("data", "b4yaGZsFixCGE5ouyvqcqrHsKZawL1oHa2IY7q1qd/x+pQCap08VGZ/zhdAs9LX10Vp0nApdD9b26gJA2Vv8r9F2JF+0sLgiXafvon4C0gStDSXPhwDIk3HuQWtGj12yiVwVisUkQF15NZmcvS7fhegVzbvBvGa2pAtd2Ci1N/jdGXMiwPX0YeoE4vpEOJ/M2zsRRV8UOc5RLJgvDSwpLQpfpreMlMjtJoSByA+tVHHN1M7Mg446UPsUMui0r6EDSb8mjtG9vtw/0HsZfiqXZ+fmKRuJwC0DlJfIrBCrJBDhY6zBWw0J1Q6y4bcBTdwuLcYwYHerhR0z4WnZ5B07apVLIMQSsbElQDC+/Zbgzs9RHLkWu0n21wzzljSAf8KlZNPyqckHlqq1TQ81/0SC7/VjSA3vAVFObk+12KdtuJCcSvNcY3zl1gVofmpxEivX5uSkmM1USlbInucrAMJ45gV70iNZZGt9/qk17LrWwdpSNRABOmOQ3do/G0nFewJRkGXxHCAGbd54cWNvfhmfV4ph6ViKucHntKLjo+xsu18=");
        requestData.addRequestParam("key", "DS8ubiLNLgscR6nlAUKMvQExOiORh7kcP8FYS9pDJmwvCENL6ARD7R5mEZtTx/nFIQaXY7DJlL3UA8Vy1AwEbRpUFb5P9v7cg5nDxsfFyD70OPTrkh9Jk1za7Rr0WqHmID4kreAGstWrzkNilhkmI4xWc3wjkvGWQYe2wNkt3GRCCqZHcJjPPv/8mDll7Y9zmpTLUpHHgC/4dYWqS93XdiFTLEzPe/tikcCTOyI6RI+AYtnwnPvbQZYp/MipTLqCGOUmyiiXK/hhA3K4I3Y2T9S8bI8OuZW1rEf0QtKe6PaiPFY8l7YJyG3wTbF+5lCdJjJvBVipPNaRi53uIho2bA==");

        return requestData;
    }

    @RequestMapping(value = "/explorerFolderData/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExplorerFolderData(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-PasswordKey", "SAAsnm/iOPx9o3unhwbE1ZFq61HnJjePWx/J7T7qfg/HQcN7wnzln13fpHPX90PFQT3SaBrCQEGUTQfCX4Ut8CRKe+7zzdmf5jGBQkYals1fOkbxgE1OZF0OOkdqPrpw5fMkJSjSpLhkW31J09gk9GsZfMp8Yw2L2Nu8wf3DV3b1OgS17fRMrAeytUKW7OCDEOhwUC0jrMjaH4D/I8WTLqx3sS3Sj0WDUSrDbJvQjIqKzT4eKUCuZEoyIxLlvKJu3l5Rjf3tWWRgPqerHfIJ3af/o/0ADBB6c8PSDkoUkMqS+yxqzlBXyMojiuV/8ig6vctShTriEQEQ5PvBUSeAVg==");
        requestData.addRequestHeader("X-51MAPS-PasswordSalt", "VOqtuXmAR5fP2uPYlWSRlw==");
        requestData.addRequestHeader("X-51MAPS-CredentialsRequired", "true");

        requestData.addRequestParam("create", "true");

        return requestData;
    }

    @RequestMapping(value = "/getPassword/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForGetPassword(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);

        return requestData;
    }

    @RequestMapping(value = "/forwardUrl/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForForward(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-ForwardUrl", "http://www.google.com/");

        return requestData;
    }

    @RequestMapping(value = "/exchangeLogin/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgLogin(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-PasswordKey", "SAAsnm/iOPx9o3unhwbE1ZFq61HnJjePWx/J7T7qfg/HQcN7wnzln13fpHPX90PFQT3SaBrCQEGUTQfCX4Ut8CRKe+7zzdmf5jGBQkYals1fOkbxgE1OZF0OOkdqPrpw5fMkJSjSpLhkW31J09gk9GsZfMp8Yw2L2Nu8wf3DV3b1OgS17fRMrAeytUKW7OCDEOhwUC0jrMjaH4D/I8WTLqx3sS3Sj0WDUSrDbJvQjIqKzT4eKUCuZEoyIxLlvKJu3l5Rjf3tWWRgPqerHfIJ3af/o/0ADBB6c8PSDkoUkMqS+yxqzlBXyMojiuV/8ig6vctShTriEQEQ5PvBUSeAVg==");
        requestData.addRequestHeader("X-51MAPS-PasswordSalt", "VOqtuXmAR5fP2uPYlWSRlw==");
        requestData.addRequestHeader("X-51MAPS-CredentialsRequired", "true");

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
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

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
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

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
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("EntryID", entryId);

        return requestData;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response login(@RequestBody Map<String, String> requestData, Model model) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost authRequest = new HttpPost(loginUrl);
        authRequest.setEntity(mainService.constructRequestBody(requestData));

        HttpResponse response = httpClient.execute(authRequest);

        String responseBody = mainService.getResponseBody(response);
        model.addAttribute("authToken", mainService.getResponseParameter(responseBody, response, "result", "token"));
        model.addAttribute("setCookieHeaderValue", "");

        return mainService.constructSuccessResponse(response, responseBody);
    }

    @RequestMapping(value = "/getPassword", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response getPassword(@RequestBody Map<String, String> requestData, Model model) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost authRequest = new HttpPost(getPasswordUrl);
        authRequest.setEntity(mainService.constructRequestBody(requestData));
        authRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(authRequest);
        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/explorerFolderData", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response explorerFolderData(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost explorerFRequest = new HttpPost(explorerFolderDataUrl);
        explorerFRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));
        explorerFRequest.setEntity(mainService.constructRequestBody(requestData));

        HttpResponse response = httpClient.execute(explorerFRequest);

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
        exchangeRequest.setEntity(mainService.constructRequestBody(requestData));
        exchangeRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(exchangeRequest);
        Response result = mainService.constructSuccessResponse(response);
        Matcher matcher = getMatcherForSecurityToken(result);

        if (matcher.find()) {
            model.addAttribute("setCookieHeaderValue", getHeaderBeginWith(response, "Set-Cookie", "X-51MAPS-SessionId").getValue());
            model.addAttribute("exchgToken", matcher.group(1));
        }

        return result;
    }

    @RequestMapping(value = "/exchangeFolders", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeFolders(@RequestBody Map<String, String> requestData, Model model) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpPost foldersRequest = new HttpPost(exchangeFoldersUrl);
        foldersRequest.setEntity(mainService.constructRequestBody(requestData));
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
        foldersRequest.setEntity(mainService.constructRequestBody(requestData));
        foldersRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(foldersRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchangeContactsData", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeContactsData(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpPost contactsRequest = new HttpPost(exchangeContactsdataUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
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
