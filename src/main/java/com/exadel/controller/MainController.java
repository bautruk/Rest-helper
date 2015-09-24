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
@SessionAttributes(value = {"authToken", "setCookieHeaderValue", "exchgToken", "inboxEntryId", "contactsEntryId"})
public class MainController {
    private static final String HTTPS = "https";

    @Autowired
    private MainService mainService;

    private final String baseUrl = "http://192.168.12.60:9512/";
//    private final String baseUrl = "http://192.168.100.22:9512/";
//    private final String baseUrl = "http://localhost:9512/";
//    private final String baseUrl = "http://192.168.100.22:9512/";
//    private final String baseUrl = "http://192.168.0.102:9512/";
//    private final String baseUrl = "https://dev.e-dapt.net:4440/";
//    private final String baseUrl = "http://128.66.200.101:9512/";
//    private final String baseUrl = "https://controller.botf03.net:4440/newmsa/";
//    private final String baseUrl = "http://msa.botf03.net:180/";
//    private final String baseUrl = "http://128.66.101.101:9512/";

    private final String loginUrl = baseUrl + "proxy/authentication/login";
    private final String getPasswordUrl = baseUrl + "proxy/profile/getPassword";
    private final String explorerFolderDataUrl = baseUrl + "shared-drive/getItems";
    private final String explorerGetFile = baseUrl + "shared-drive/do/get";
    private final String forwardUrl = baseUrl + "forwardurl";
    private final String exchangeLoginUrl = baseUrl + "exchjson/service/do/login";
    private final String exchangeFoldersUrl = baseUrl + "exchjson/service/do/folders";
    private final String exchangeFolderdataUrl = baseUrl + "exchjson/service/do/folderdata";
    private final String exchangeContactsdataUrl = baseUrl + "exchjson/service/do/contactsdata";
    private final String exchangeReadEmailUrl = baseUrl + "/exchjson/service/do/reademail";
    private final String exchangeCalendarDataUrl = baseUrl + "/exchjson/service/do/calendardata";

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
        //DEV domain=e-dapt
//        requestData.addRequestParam("data", "tD5g0O2M8mlL2JDLR105sy9z+ASIsGDLZEW+kibSIlwwqAA+Vfdl55XvyGnufqufmHolmvh87cVR0SfAIqHqcSSrCAkgsj\\/3gJ9m2P+bnQG\\/V6GgeI8gSCV6dQtVZSN3jUM5fwUiiFF7SvIN\\/zqy11iTRM8BAmqF28QgxotXmlTHhbO\\/XpRLjFs7Pi+G\\/Zenwc8K0eoKFT29YFZ\\/kNW9dkW8L8nkMKdA5BSMDn0uwzA8Jy5SkXFSqnvchauowRUgSj89rrptpIckedmnC2803jMUnoz09Bbw2HOS2v6bMW4dR3RMdDtVkr3emByicY2Uo3O2OwmCUXlQwqLDCMJT9jGpz1RdD4sMojAwKdX7gHI=");
//        requestData.addRequestParam("key", "pClfjGT35tNWniW0JYU7VzvgIjzKx\\/wSbFgRhfsfhz0sjuybCT1eo7bCtJI6UKm8naeS9T7t3l2U5nxferoWyCbazoQ7VpvhgBXAoEu4bV3IaQd75tmJu04NkkuaHIF14lgVLeASRaWxoOHLSMyOunMalgtt9ZLjGoFASUdsg\\/c7x0DUq2DDNBn0ZKbeX\\/xRjehH4HxKCXrYquLyz3nPyIwiHNH+8W9zcNwUS8VyGxF35VqdM9phR8ibbqkubawXK4kLu9\\/qtJouDa14DJ\\/fhV1+BQVjDcnU6Uw5Ef0G+VucPjzFLLysDzrn3kp1H+Ffy0qIQmv3sXMZCivvNtEPOQ==");
        //DEV domain=botf03
        requestData.addRequestParam("data", "vZQSXEretgwD3tBrpqDvqHGf5mrnKTDNgRMUBsBmIAS6RfAvIfkzdT\\/W6c8OR1uNXWox9CXJ8ks6p8FCAT8EWQcP0O+ObeB86EffzRniup14wrzAt\\/oD9eIAbAQ2Bbnh8bfvWS2CkTrPCcippN7Rgk+doQNQVARbZ+Qu54KpU1xCEPAvPiJsmYxHGkVAePPeKlllm3ratRF8BdL\\/kY9BLslWzMI0cpPwqetl4WVLvgoO4gto1wP2PUpNPeUymlbKtbuNMjbfBtzUFdsBdwePEuDATCkfPPv09AyqYE5tcIP4IsPbfTcMy81TJRM6uQ\\/m+Mg35Oo6JY5E36w8KaNcd08eab\\/q05LdTpE9nsiVjeGSkLZWf4xpn4VBhUK9cM6f3Lr7HXKXm3ILKEfj3jR3+VdbEHcywTVMSzTlJqChuzIJe3Znydx\\/rCNYc5Io1CCn");
        requestData.addRequestParam("key", "HxVpLcxLLUi4tUSUT88BRcOrwYYajHI6fJ5t75hMgQCldVUyvozqrGn+Sg10Lan2KBAzi09Tgdek8WIiKEijWJ0Vs5rRqF6tsdNZzHKCibWO2Ri\\/jVq5WdeuLLAniASQMbH16PcsyUj9HJN\\/y2ZgQeBv3vxkd8VQWBr06t24wpTIzVpEZbWqcEIaXr0Y1fEaaS3ux111sDTC5GVF6Oic2Etr\\/ZywtFdKNXR\\/cSvVo5w+ZyfM9WF3BbcfVuIZoJkE3TlLdiN21vyBNF4mXMoxtOMPIf\\/eiqWF8JWDQhrygUnQeO3qZax877speQyR19qeJSvNQlpyAtiANzGbwAjj2w==");
        //DEMO domain=botf03
//        requestData.addRequestParam("data", "Gd0FLQ4SpOjAvKFkSnvYcELsLUMwl\\/zyDPlCLRalBe3MUOK86FCHKc6ff78qKm9FlP6RAxpY7QoDoAALw5SloSaps9qO2hpjqxwfSTMYr7KLFLHINyPFVMnetPe7Vc\\/hrR4Ac3yH+93GEgjkapcpFmyZzz4hTcEsgh491TBES29YtzmZah1KVqiy1u+9kOutEkp14I02w4yf1I3za8fAOSVEYIKQoxDEBns\\/QGYLTjjDy+hfLW6AkUqW7GtC+Dp7FvAzFl6LPNoZOO6ipFBmdUBg2fWYCWWy38TBX2Bk+WD2etx\\/4AgS21yyFc9U92NrC8zlooxXWnRUv5m6Qme2HS849LR3Qi\\/QTqPwGMTEym3OvEe4U2DNV21IiwDOF5rohdrtYkcF0BEPt\\/8hOnYprh91RAVCGzPDjRO6Wod+dqOrSZFrYLsHm29t2Rt6JCVt");
//        requestData.addRequestParam("key", "g4t1yvfadazJrZcYnjVQmb2\\/jMyK9vxt5EQBij5INF3G\\/IBBk3qCBANosrFMPp1DXh0jggQTDlM8jb0Wu8rmjRgUJgFIN1dGIia3ZjAHsWCD+huTnczURPE635FNOzAhYNlhjesgWogJrgiOhgelEMWksfwmeGRSjjvh76SkBkrj7XLXaIERGA7eVoBqcmW5ZuRneQDf4nT9Qac05AvtxM6X+xwO11WG\\/fben\\/nOhY9qvxp0ySYUsuo0MLc0sN4ol4KQx0kxKzDRutQIFu3BALJjQw7OS7coEJNEe\\/UhLPBcLFpWT70QgRCNqFiZYq0saqWiU0FPNio+UH8hMxnDaQ==");

        return requestData;
    }

    @RequestMapping(value = "/explorerFolderData/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExplorerFolderData(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-CredentialsRequired", "true");
        //DEV domain=botf03
        requestData.addRequestHeader("X-51MAPS-SK", "IaDNEzojmoMwOLW+mYuQNyRz8z55qK5GaZrW/+6ULig=");

        requestData.addRequestParam("path", "");

        return requestData;
    }

    @RequestMapping(value = "/explorerGetFile/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExplorerGetFile(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-PasswordKey", "G6/XUPJKltPuKBpDNpPZvjafvfn/vLM+cZTafMKzbt0LMQ44dTibQVsQVjfRpsAEtnQNbP6wS8dVCfbOY/pKRTrvKQnkH1djnb/fZlvRQGjLbXWghhwMKqjTArWYXznt0B+KrojG8c5+UvXNOraG71/mwhXE7nPoER+apJDuIY36f7pgPspeBH2HzUKBPKjUzwVFVlCR7BmXpCAb7rsge/hBiNTc07xLv8W4zFdjYCBCGcDp9hUqkCKM5DTC3zr4LV+KQVm3c4Rgmg1wGGIbnHpnB3/IGl3JC0C24oA6mmoaGd8Fjrw5DX41lIfg91KPjBMRr5RgnAugotm32griMQ==");
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
        requestData.addRequestHeader("X-51MAPS-SK", "IaDNEzojmoMwOLW+mYuQNyRz8z55qK5GaZrW/+6ULig=");
        requestData.addRequestHeader("X-51MAPS-ForwardUrl", "http://ec2-54-171-174-5.eu-west-1.compute.amazonaws.com/ux/signin.html");

        return requestData;
    }

    @RequestMapping(value = "/exchangeLogin/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgLogin(@ModelAttribute("authToken") String authToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        //DEV domain=botf03
        requestData.addRequestHeader("X-51MAPS-SK", "IaDNEzojmoMwOLW+mYuQNyRz8z55qK5GaZrW/+6ULig=");
        //DEMO domain=e-dapt
//        requestData.addRequestHeader("X-51MAPS-PasswordKey", "ISQrPdyzDqMnNBYpcg9WLxILB4Fli2tTUhHILA3w4X0RbGK16Jpp0H/3rczujpBIW+0Y0gqQMQo/GZxf1gWsoxQO5D1D0GQqteuvl2YfVl1irvdPtq1QToadTDmh1eoRfbUQeeM1FjaULJ/Oy9CbXn8s8uZhwSOmy0eHxXFE+bIuGfaXxsbxValtXKDBwyzB1EEiMHu2bYAjdZYPL0R+/Mbqev/MK3x19xTM9tw6YbFChXXs2DJRM+ECbSnSTR9RHJdHyBvXZLE5vhiFLQLSMFlwmRcuaviaxMjcmAsMR9CDbvF7MOrsN7jwlB6xoz8bq5QbT7r6LBKfGLnW9tTn2Q==");
        requestData.addRequestHeader("X-51MAPS-CredentialsRequired", "true");

        requestData.addRequestParam("username", "milshtyu");
        requestData.addRequestParam("password", "Frame1hawk");
        requestData.addRequestParam("encryptedPassword", "wO+ET6dgk5GeizTtZA+7KQ==");
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
        requestData.addRequestParam("EndDate", "1436904000000");
        requestData.addRequestParam("ReturnInstances", "true");
        requestData.addRequestParam("StartDate", "1436824800000");
        requestData.addRequestParam("WithBody", "false");
        requestData.addRequestParam("EntryID", "~/-FlatUrlSpace-/6260f6461db98c499b21d63a02cd56a7-1f75");

        return requestData;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response login(@RequestBody Map<String, String> requestData, Model model) throws IOException {
//        System.setProperty("jsse.enableSNIExtension", "false");
        HttpClient httpClient = getHttpClientAnyCertificate(loginUrl);
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

    @RequestMapping(value = "/exchangeCalendarData", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeCalendarData(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpPost contactsRequest = new HttpPost(exchangeCalendarDataUrl);
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
