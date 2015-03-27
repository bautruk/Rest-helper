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

//    private final String baseUrl = "http://192.168.12.60:9512/";
    private final String baseUrl = "https://controller.e-dapt.net:4440/";
//    private final String baseUrl = "http://10.0.0.3:9512/";
//    private final String baseUrl = "https://controller.botf03.net:4440/newmsa/";
//    private final String baseUrl = "http://msa.botf03.net:180/";

    private final String loginUrl = baseUrl + "proxy/authentication/login";
    private final String getPasswordUrl = baseUrl + "proxy/profile/getPassword";
    private final String explorerFolderDataUrl = baseUrl + "shared-drive/do/dir";
    private final String explorerGetFile = baseUrl + "shared-drive/do/get";
    private final String forwardUrl = baseUrl + "forwardurl";
    private final String exchangeLoginUrl = baseUrl + "exchjson/service/do/login";
    private final String exchangeFoldersUrl = baseUrl + "exchjson/service/do/folders";
    private final String exchangeFolderdataUrl = baseUrl + "exchjson/service/do/folderdata";
    private final String exchangeContactsdataUrl = baseUrl + "exchjson/service/do/contactsdata";
    private final String exchangeReadEmailUrl = baseUrl + "/exchjson/service/do/reademail";

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
        requestData.addRequestParam("data", "bMhNT3DXv21XuGIXOAsF08hIaHEyUlp7B57CYh44qjya6mg0s2PquioWpc\\/rQtAb2vpqoP8lRyjfeG6AyUvlXUHj8Edw8hFa9CFPvszWTgC+3g41Jc1OYiysEMFxiSIkdopnkn4Vrv7uCLuolU3K6\\/rf7am2DhE8zxqY+iAM74XUcXNQUrMf3NRrzTJxex4Wrl8lzyKaoNXJ1OsLO4\\/5dbZhfd9787yMuqdsVnN4PeMUfWcZq\\/oiAC8DLnOZAKR1EQePMPzFCyPOC4F9fhJdZheMqhO4waTrubfuYF3hC2VyYdV+BezhdvKrSjppui2Jj\\/bA1xegDDNzryTar2WmcRvcARhADK0Tqw18rNHFlcE=");
        requestData.addRequestParam("key", "pTgVx5n2PYt8pTY+tMNzSFogZdbLfrZuC8D\\/DzrkzHE\\/\\/FQWXcYC5bDCo6WzMFeBth11OB\\/YG4llXfsfTJ9LqOlSSZRKLl6kPgVEZKGJpmYHzxBTb9HKhQvO0jpkMCZ+tIA511xDq49tnjHtbtUN9HkV72OwOtvkds0ezy+6fc3UW\\/mtA1unuz4SAYwCAo0cZOxisclVlHx85Lqt0il4NW4LEvi9CDciVLdSv+NH17PVqlanU1fLsD5jxGVrGciRkvldhLj82va13khOeKeqrbT98682QS8tJLKNYc7qROu5lc\\/\\/hpVIxmJhCRaDhb1GPmY9wi6RaiGrxCiyWK3n1A==");

        return requestData;
    }

    @RequestMapping(value = "/explorerFolderData/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExplorerFolderData(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-PasswordKey", "PYaNLLFyOf/3SRRU95zgmeVfmY8q/6stU7kkvrNc+fr6NOG5MQu4R4dW6Z3DRpMqnz1XTBrjH06MK04pNGBW9rKBGp3BPwjunMwvObuzxCB+2r/ouOnUoBXh1G6fwuNdCr6sE1fxFpdJGbPiZuII9KcahE8FR9kWhB/AsShPTXUdiyM9El1R3LPgOZLfgSX0JK3Eu8WOK1o5xArgXNH3xgE/XQYglN1N5NeSws0BlOQ9sVtmvHinw7KyDNRPuQ0hwMioetHOwJESLvz5MmsxPg4IFeyK8NGkbEC8NgTaRl8PY4bgtNKHKMALf3YDnGvUJRijOMYXLjV5XtQlklLnjA==");
        requestData.addRequestHeader("X-51MAPS-CredentialsRequired", "true");

        requestData.addRequestParam("create", "true");

        return requestData;
    }

    @RequestMapping(value = "/explorerGetFile/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExplorerGetFile(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-PasswordKey", "PYaNLLFyOf/3SRRU95zgmeVfmY8q/6stU7kkvrNc+fr6NOG5MQu4R4dW6Z3DRpMqnz1XTBrjH06MK04pNGBW9rKBGp3BPwjunMwvObuzxCB+2r/ouOnUoBXh1G6fwuNdCr6sE1fxFpdJGbPiZuII9KcahE8FR9kWhB/AsShPTXUdiyM9El1R3LPgOZLfgSX0JK3Eu8WOK1o5xArgXNH3xgE/XQYglN1N5NeSws0BlOQ9sVtmvHinw7KyDNRPuQ0hwMioetHOwJESLvz5MmsxPg4IFeyK8NGkbEC8NgTaRl8PY4bgtNKHKMALf3YDnGvUJRijOMYXLjV5XtQlklLnjA==");
        requestData.addRequestHeader("X-51MAPS-CredentialsRequired", "true");

        requestData.addRequestParam("create", "true");
        requestData.addRequestParam("url", "smb://mainserver/S_Drive_Shares/testtester1/Autosync/Autosync");

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
        requestData.addRequestHeader("X-51MAPS-PasswordKey", "PYaNLLFyOf/3SRRU95zgmeVfmY8q/6stU7kkvrNc+fr6NOG5MQu4R4dW6Z3DRpMqnz1XTBrjH06MK04pNGBW9rKBGp3BPwjunMwvObuzxCB+2r/ouOnUoBXh1G6fwuNdCr6sE1fxFpdJGbPiZuII9KcahE8FR9kWhB/AsShPTXUdiyM9El1R3LPgOZLfgSX0JK3Eu8WOK1o5xArgXNH3xgE/XQYglN1N5NeSws0BlOQ9sVtmvHinw7KyDNRPuQ0hwMioetHOwJESLvz5MmsxPg4IFeyK8NGkbEC8NgTaRl8PY4bgtNKHKMALf3YDnGvUJRijOMYXLjV5XtQlklLnjA==");
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

    @RequestMapping(value = "/exchangeReadEmail/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeReadEmail(@ModelAttribute("authToken") String authToken,
                                                                  @ModelAttribute("setCookieHeaderValue") String setCookieHeaderValue,
                                                                  @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("Cookie", setCookieHeaderValue);
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("WithBody", "true");
        requestData.addRequestParam("ItemIDs", "~/-FlatUrlSpace-/6260f6461db98c499b21d63a02cd56a7-2cc1/6260f6461db98c499b21d63a02cd56a7-8f776;");

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

    @RequestMapping(value = "/explorerGetFile", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response explorerGetFile(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost explorerFRequest = new HttpPost(explorerGetFile);
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

    @RequestMapping(value = "/exchangeReadEmail", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeReadEmail(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpPost contactsRequest = new HttpPost(exchangeReadEmailUrl);
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
