package com.exadel.controller;

import com.exadel.dto.PredefinedRequestData;
import com.exadel.dto.Response;
import com.exadel.service.MainService;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
@SessionAttributes(value = {"authToken", "setCookieHeaderValue", "exchgToken",
        "inboxEntryId", "contactsEntryId", "X-51MAPS-SK"})
public class MainController {
    private static final String HTTPS = "https";

    @Autowired
    private MainService mainService;

//    private final String baseUrl = "http://192.168.12.60:9512/";
//    private final String baseUrl = "http://192.168.100.22:9512/";
//    private final String baseUrl = "http://localhost:9512/";
//    private final String baseUrl = "http://192.168.100.22:9512/";
//    private final String baseUrl = "http://192.168.0.102:9512/";
//    private final String baseUrl = "https://dev.e-dapt.net:4440/";
//    private final String baseUrl = "http://128.66.200.101:9512/";
//    private final String baseUrl = "https://controller.botf03.net:4440/newmsa/";
//    private final String baseUrl = "http://msa.botf03.net:180/";
//    private final String baseUrl = "http://128.66.101.101:9512/";

//    private final String loginUrl = baseUrl + "proxy/authentication/login";
//    private final String getPasswordUrl = baseUrl + "proxy/profile/getPassword";
//    private final String explorerFolderDataUrl = baseUrl + "shared-drive/getItems";
//    private final String explorerGetFile = baseUrl + "shared-drive/do/get";
//    private final String forwardUrl = baseUrl + "forwardurl";
//    private final String exchangeLoginUrl = baseUrl + "exchjson/service/do/login";
//    private final String exchangeFoldersUrl = baseUrl + "exchjson/service/do/folders";
//    private final String exchangeFolderdataUrl = baseUrl + "exchjson/service/do/folderdata";
//    private final String exchangeContactsdataUrl = baseUrl + "exchjson/service/do/contactsdata";
//    private final String exchangeReadEmailUrl = baseUrl + "/exchjson/service/do/reademail";
//    private final String exchangeCalendarDataUrl = baseUrl + "/exchjson/service/do/calendardata";
//    private final String exchangeReadAppointmentUrl = baseUrl + "/exchjson/service/do/readappointment";
//    private final String exchangeReadMasterAppointmentUrl = baseUrl + "/exchjson/service/do/readmasterappointment";
    private final String loginUrl = "proxy/authentication/login";
    private final String getPasswordUrl = "proxy/profile/getPassword";
    private final String explorerFolderDataUrl = "shared-drive/getItems";
    private final String explorerGetFile = "shared-drive/do/get";
    private final String forwardUrl = "forwardurl";
    private final String exchangeLoginUrl = "exchjson/service/do/login";
    private final String exchangeFoldersUrl = "exchjson/service/do/folders";
    private final String exchangeFolderdataUrl = "exchjson/service/do/folderdata";
    private final String exchangeContactsdataUrl = "exchjson/service/do/contactsdata";
    private final String exchangeReadEmailUrl = "/exchjson/service/do/reademail";
    private final String exchangeCalendarDataUrl = "/exchjson/service/do/calendardata";
    private final String exchangeReadAppointmentUrl = "/exchjson/service/do/readappointment";
    private final String exchangeReadMasterAppointmentUrl = "/exchjson/service/do/readmasterappointment";

    @RequestMapping("/")
    public String getMainPageName() {
        return "index";
    }

    @RequestMapping(value = "/login/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForLogin() {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        //requestData.addRequestParam("data", "vZQSXEretgwD3tBrpqDvqHGf5mrnKTDNgRMUBsBmIAS6RfAvIfkzdT\\/W6c8OR1uNXWox9CXJ8ks6p8FCAT8EWQcP0O+ObeB86EffzRniup14wrzAt\\/oD9eIAbAQ2Bbnh8bfvWS2CkTrPCcippN7Rgk+doQNQVARbZ+Qu54KpU1xCEPAvPiJsmYxHGkVAePPeKlllm3ratRF8BdL\\/kY9BLslWzMI0cpPwqetl4WVLvgoO4gto1wP2PUpNPeUymlbKtbuNMjbfBtzUFdsBdwePEuDATCkfPPv09AyqYE5tcIP4IsPbfTcMy81TJRM6uQ\\/m+Mg35Oo6JY5E36w8KaNcd08eab\\/q05LdTpE9nsiVjeGSkLZWf4xpn4VBhUK9cM6f3Lr7HXKXm3ILKEfj3jR3+VdbEHcywTVMSzTlJqChuzIJe3Znydx\\/rCNYc5Io1CCn");
        //requestData.addRequestParam("key", "HxVpLcxLLUi4tUSUT88BRcOrwYYajHI6fJ5t75hMgQCldVUyvozqrGn+Sg10Lan2KBAzi09Tgdek8WIiKEijWJ0Vs5rRqF6tsdNZzHKCibWO2Ri\\/jVq5WdeuLLAniASQMbH16PcsyUj9HJN\\/y2ZgQeBv3vxkd8VQWBr06t24wpTIzVpEZbWqcEIaXr0Y1fEaaS3ux111sDTC5GVF6Oic2Etr\\/ZywtFdKNXR\\/cSvVo5w+ZyfM9WF3BbcfVuIZoJkE3TlLdiN21vyBNF4mXMoxtOMPIf\\/eiqWF8JWDQhrygUnQeO3qZax877speQyR19qeJSvNQlpyAtiANzGbwAjj2w==");
        requestData.addRequestParam("username", "milshtyu");
        requestData.addRequestParam("password", "Frame1hawk");
        requestData.addRequestParam("domain", "botf03.net");
        requestData.addRequestParam("deviceId", "D0971C72-7413-41AB-9C5F-6A7771127F27");
        requestData.addRequestParam("deviceIp", "10.0.0.86");
        requestData.addRequestParam("os", "IOS");
        requestData.addRequestParam("osVersion", "8.3");
        //requestData.addRequestParam("provisionVersions", "0e664c701741d9448e2bf9831c411733");
        requestData.addRequestParam("application", "edge");
        requestData.addRequestParam("applicationVersion", "1.0");
       return requestData;
    }

    @RequestMapping(value = "/explorerFolderData/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExplorerFolderData(@ModelAttribute("authToken") String authToken,
                                                                   @ModelAttribute("X-51MAPS-SK") String key) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-CredentialsRequired", "true");
        //DEV domain=botf03
        requestData.addRequestHeader("X-51MAPS-SK", key);

        requestData.addRequestParam("path", "");

        return requestData;
    }

    @RequestMapping(value = "/explorerGetFile/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExplorerGetFile(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
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
    public PredefinedRequestData predefParamsForForward(@ModelAttribute("authToken") String authToken,
                                                        @ModelAttribute("X-51MAPS-SK") String key) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-SK", key);
        requestData.addRequestHeader("X-51MAPS-ForwardUrl", "http://www.tashkent.org/whoami.asp");

        return requestData;
    }

    @RequestMapping(value = "/exchangeLogin/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgLogin(@ModelAttribute("authToken") String authToken,
                                                           @ModelAttribute("X-51MAPS-SK") String key) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        //DEV domain=botf03
        requestData.addRequestHeader("X-51MAPS-SK", key);
        //DEV domain=e-dapt
        requestData.addRequestHeader("X-51MAPS-CredentialsRequired", "true");

//        requestData.addRequestParam("username", "milshtyu");
//        requestData.addRequestParam("password", "Frame1hawk");
//        requestData.addRequestParam("encryptedPassword", "wO+ET6dgk5GeizTtZA+7KQ==");
//        requestData.addRequestParam("domain", "botf03.net");
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

    @RequestMapping(value = "/exchangeCalendarData/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeCalendarData(@ModelAttribute("authToken") String authToken,
                                                                     @ModelAttribute("setCookieHeaderValue") String setCookieHeaderValue,
                                                                     @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("Cookie", setCookieHeaderValue);
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("AsHtml", "false");
        requestData.addRequestParam("EndDate", "1445140799000");
        requestData.addRequestParam("ReturnInstances", "true");
        requestData.addRequestParam("StartDate", "1444536000000");
        requestData.addRequestParam("WithBody", "false");
        //2010
//        requestData.addRequestParam("EntryID", "AAMkAGIxNDM0ZDVhLTVkNjAtNDExNC05OGJlLTBkYTY2ZGM0Y2RlNQAuAAAAAACj/PfB/94AQ6N0pbnWmcjNAQCJOXq+UKNwSJ+TqdVrKLqdAAAAC9yeAAA=");
        //2003
        requestData.addRequestParam("EntryID", "~/-FlatUrlSpace-/6260f6461db98c499b21d63a02cd56a7-1f75");

        return requestData;
    }

    @RequestMapping(value = "/exchangeReadAppointment/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeReadAppointment(@ModelAttribute("authToken") String authToken,
                                                                        @ModelAttribute("setCookieHeaderValue") String setCookieHeaderValue,
                                                                        @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("Cookie", setCookieHeaderValue);
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("AsHtml", "false");
        requestData.addRequestParam("WithBody", "true");
        requestData.addRequestParam("EntryID", "AAMkAGIxNDM0ZDVhLTVkNjAtNDExNC05OGJlLTBkYTY2ZGM0Y2RlNQFRAAgI0taF5pJAAEYAAAAAo/z3wf/eAEOjdKW51pnIzQcAiTl6vlCjcEifk6nVayi6nQAAAAvcngAAiTl6vlCjcEifk6nVayi6nQAAAA7x5gAAEA==");

        return requestData;
    }

    @RequestMapping(value = "/exchangeReadMasterAppointment/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeReadMasterAppointment(@ModelAttribute("authToken") String authToken,
                                                                        @ModelAttribute("setCookieHeaderValue") String setCookieHeaderValue,
                                                                        @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("Cookie", setCookieHeaderValue);
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("AsHtml", "false");
        requestData.addRequestParam("WithBody", "true");
        requestData.addRequestParam("EntryID", "AAMkAGIxNDM0ZDVhLTVkNjAtNDExNC05OGJlLTBkYTY2ZGM0Y2RlNQFRAAgI0taF5pJAAEYAAAAAo/z3wf/eAEOjdKW51pnIzQcAiTl6vlCjcEifk6nVayi6nQAAAAvcngAAiTl6vlCjcEifk6nVayi6nQAAAA7x5gAAEA==");

        return requestData;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response login(@RequestBody Map<String, String> requestData, Model model) throws IOException {
//        System.setProperty("jsse.enableSNIExtension", "false");
        String baseUrl = requestData.get("baseUrl").trim();
        HttpClient httpClient = getHttpClientAnyCertificate(baseUrl + loginUrl);
        HttpPost authRequest = new HttpPost(baseUrl + loginUrl);
        String passwordKey = mainService.encodeRequestParameters(requestData);
        authRequest.setEntity(mainService.constructRequestBody(requestData));
        model.addAttribute("X-51MAPS-SK", passwordKey);

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
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost authRequest = new HttpPost(baseUrl + getPasswordUrl);
        authRequest.setEntity(mainService.constructRequestBody(requestData));
        authRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(authRequest);
        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/explorerFolderData", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response explorerFolderData(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost explorerFRequest = new HttpPost(baseUrl + explorerFolderDataUrl);
        explorerFRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));
        explorerFRequest.setEntity(mainService.constructRequestBody(requestData));

        HttpResponse response = httpClient.execute(explorerFRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/explorerGetFile", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response explorerGetFile(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost explorerFRequest = new HttpPost(baseUrl + explorerGetFile);
        explorerFRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));
        explorerFRequest.setEntity(mainService.constructRequestBody(requestData));

        HttpResponse response = httpClient.execute(explorerFRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/forwardUrl", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response forwardUrl(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpGet forwardRequest = new HttpGet(baseUrl + forwardUrl);
        forwardRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(forwardRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchangeLogin", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response exchangeLogin(@RequestBody Map<String, String> requestData, Model model) throws IOException {

        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost exchangeRequest = new HttpPost(baseUrl + exchangeLoginUrl);
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
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost foldersRequest = new HttpPost(baseUrl + exchangeFoldersUrl);
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
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost foldersRequest = new HttpPost(baseUrl + exchangeFolderdataUrl);
        foldersRequest.setEntity(mainService.constructRequestBody(requestData));
        foldersRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(foldersRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchangeContactsData", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeContactsData(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeContactsdataUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchangeReadEmail", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeReadEmail(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeReadEmailUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchangeCalendarData", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeCalendarData(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeCalendarDataUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchangeReadAppointment", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeReadAppointment(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeReadAppointmentUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response);
    }

    @RequestMapping(value = "/exchangeReadMasterAppointment", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeReadMasterAppointment(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeReadMasterAppointmentUrl);
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

    private HttpClient getHttpClientAnyCertificate(String url) {
        HttpClient httpClient = null;
        if (url.startsWith(HTTPS)) {
            SchemeRegistry schemeRegistry = prepareSchemeRegistryForHttps(url);
            if (schemeRegistry != null) {
                httpClient = new DefaultHttpClient(new BasicClientConnectionManager(schemeRegistry));
            }
        } else {
            httpClient = new DefaultHttpClient(new BasicClientConnectionManager());
        }

        return httpClient;
    }

    private static SchemeRegistry prepareSchemeRegistryForHttps(String url) {
        SchemeRegistry schemeRegistry = null;

        if (url.startsWith(HTTPS)) {
            //  Accept any certificate
            try {
                URI uriObj = new URI(url);
                int port = uriObj.getPort();
                port = (port > 0 ? port : 443);

                schemeRegistry = new SchemeRegistry();
                SSLSocketFactory factory = getSocketFactory();
                Scheme httpsScheme = new Scheme(HTTPS, port, factory);
                schemeRegistry.register(httpsScheme);
            } catch (URISyntaxException e) {
                schemeRegistry = null;
            }
        }

        return schemeRegistry;
    }

    private static SSLSocketFactory getSocketFactory() {
        try {
            return new SSLSocketFactory(
                    new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] x509Certificates,
                                                 String s) throws CertificateException {
                            return true;
                        }
                    },
                    SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER
            );
        }
        catch (Exception e) {
            return null;
        }
    }
}
