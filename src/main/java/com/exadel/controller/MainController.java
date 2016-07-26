package com.exadel.controller;

import com.exadel.dto.PredefinedRequestData;
import com.exadel.dto.Response;
import com.exadel.service.MainService;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/")
@SessionAttributes(value = {"authToken", "exchgToken", "inboxEntryId", "contactsEntryId", "X-51MAPS-SK"})
public class MainController {
    private static final String HTTPS = "https";

    @Autowired
    private MainService mainService;

    //super comment
    //super comment-2

    private final String loginUrl = "proxy/authentication/login";
    private final String getPasswordUrl = "proxy/profile/getPassword";
    private final String explorerFolderDataUrl = "shared-drive/getItems";
    private final String explorerGetFile = "shared-drive/getFile";
    private final String explorerUpdateFile = "shared-drive/updateFile";
    private final String forwardUrl = "forwardurl";
    private final String rsaCheckUrl = "rsa/check";
    private final String exchangeJsonToken = "exchjson/";
    private final String exchangeLoginUrl = "exchjson/service/do/login";
    private final String exchangeFoldersUrl = "exchjson/service/do/folders";
    private final String exchangeFolderdataUrl = "exchjson/service/do/folderdata";
    private final String exchangeContactsdataUrl = "exchjson/service/do/contactsdata";
    private final String exchangeUpdateContactUrl = "exchjson/service/do/modifycontact";
    private final String exchangeReadContactUrl = "exchjson/service/do/readcontact";
    private final String exchangeMoveItemUrl = "exchjson/service/do/moveitem";
    private final String exchangeReadEmailUrl = "/exchjson/service/do/reademail";
    private final String exchangeAddAttachmentUrl = "/exchjson/service/do/addAttachment";
    private final String exchangePrepareEmailUrl = "/exchjson/service/do/prepareEmail";
    private final String exchangeUpdateDraftEmailUrl = "/exchjson/service/do/updateDraftEmail";
    private final String exchangeSendDraftEmailUrl = "/exchjson/service/do/sendDraftEmail";
    private final String exchangeForwardEmailUrl = "/exchjson/service/do/forwardemail";
    private final String exchangeCalendarDataUrl = "/exchjson/service/do/calendardata";
    private final String exchangeReadAppointmentUrl = "/exchjson/service/do/readappointment";
    private final String exchangeReadMasterAppointmentUrl = "/exchjson/service/do/readmasterappointment";
    private final String exchangeAddAppointmentUrl = "/exchjson/service/do/addappointment";
    private final String exchangeDeleteAppointmentUrl = "/exchjson/service/do/deleteappointment";
    private final String exchangeUpdateAppointmentUrl = "/exchjson/service/do/updateappointment";
    private final String exchangeAddDelegateUrl = "/exchjson/service/do/adddelegate";
    private final String exchangeRemoveDelegateUrl = "/exchjson/service/do/removedelegate";
    private final String exchangeGetAppointmentCategoriesUrl = "/exchjson/service/do/appointmentcategories";
    private final String exchangeCreateAppointmentCategoryUrl = "/exchjson/service/do/createappointmentcategory";
    private final String exchangeUpdateAppointmentCategoryColorUrl = "/exchjson/service/do/updateappointmentcategorycolor";
    private final String exchangeDeleteAppointmentCategoryUrl = "/exchjson/service/do/deleteappointmentcategory";
    private static final Map<String, String> credentials = new HashMap<>();
    private static final String X_51_MAPS_SESSION_ID = "X-51MAPS-SessionId";

    static {
        credentials.put("milshtyu", "511maps");
        credentials.put("pizito", "511maps");
        credentials.put("exadel1", "Frame1hawk");
        credentials.put("exadel2", "Frame1hawk");
        credentials.put("exadel3", "Frame1hawk");
        credentials.put("skoval", "Exadel1");
        credentials.put("paulk@51maps.onmicrosoft.com", "kNm6iKgYxJ1ILRg4nv");
    }


    @RequestMapping("/")
    public String getMainPageName() {
        return "index";
    }

    @RequestMapping(value = "/login/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForLogin(@RequestParam("username") String username) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();

        requestData.addRequestParam("username", username);
        requestData.addRequestParam("password", credentials.get(username));
        requestData.addRequestParam("domain", "botf03.net");
        requestData.addRequestParam("deviceId", "rest-client");
        requestData.addRequestParam("deviceIp", "10.0.0.86");
        requestData.addRequestParam("os", "IOS");
        requestData.addRequestParam("osVersion", "8.3");
        requestData.addRequestParam("application", "edge");
        requestData.addRequestParam("applicationVersion", "1.0");
        requestData.addRequestParam("deviceType", "test-device-type");
        requestData.addRequestParam("rsaPasscode", "123");
        requestData.addRequestParam("activationCode", "");
        requestData.addRequestParam("sendActivationCode", "");

        return requestData;
    }

    @RequestMapping(value = "/explorerFolderData/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExplorerFolderData(@ModelAttribute("authToken") String authToken,
                                                                   @ModelAttribute("X-51MAPS-SK") String key) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-SK", key);

        requestData.addRequestParam("path", "");

        return requestData;
    }

    @RequestMapping(value = "/explorerGetFile/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExplorerGetFile(@ModelAttribute("authToken") String authToken,
                                                                @ModelAttribute("X-51MAPS-SK") String key) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-SK", key);

        requestData.addRequestParam("path", "");

        return requestData;
    }

    @RequestMapping(value = "/explorerUpdateFileContent/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExplorerPutFile(@ModelAttribute("authToken") String authToken,
                                                                @ModelAttribute("X-51MAPS-SK") String key) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-SK", key);

        requestData.addRequestParam("path", "");
        requestData.addRequestParam("FilePath", "");
        requestData.addRequestParam("FileName", "");

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

    @RequestMapping(value = "/rsaCheck/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefRSACheck(@ModelAttribute("authToken") String authToken, @ModelAttribute("X-51MAPS-SK") String key) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestParam("passcode", "");
        return requestData;
    }

    @RequestMapping(value = "/exchangeLogin/predefined", method = RequestMethod.GET)
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgLogin(@ModelAttribute("authToken") String authToken,
                                                           @ModelAttribute("X-51MAPS-SK") String key) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-SK", key);

//        requestData.addRequestParam("username", "milshtyu");
//        requestData.addRequestParam("password", "Frame1hawk");
//        requestData.addRequestParam("encryptedPassword", "wO+ET6dgk5GeizTtZA+7KQ==");
//        requestData.addRequestParam("domain", "botf03.net");
        requestData.addRequestParam("jsonSupport", "true");

        return requestData;
    }

    @RequestMapping(value = "/exchangeFolders/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgFolders(@ModelAttribute("authToken") String authToken,
                                                             @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("AllTree", "false");

        return requestData;
    }

    @RequestMapping(value = "/exchangeFolderData/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgFolderData(@ModelAttribute("authToken") String authToken,
                                                                @ModelAttribute("exchgToken") String exchgToken,
                                                                @ModelAttribute("inboxEntryId") String entryId) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("EntryID", entryId);
        requestData.addRequestParam("StartIndex", "0");
        requestData.addRequestParam("PageSize", "500");
        requestData.addRequestParam("ShortHeader", "false");
        requestData.addRequestParam("WithBody", "false");
        requestData.addRequestParam("ModifiedSince", "0");

        return requestData;
    }

    @RequestMapping(value = "/exchangeContactsData/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgContactsData(@ModelAttribute("authToken") String authToken,
                                                                  @ModelAttribute("exchgToken") String exchgToken,
                                                                  @ModelAttribute("contactsEntryId") String entryId) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("EntryID", entryId);

        return requestData;
    }

    @RequestMapping(value = "/exchangeAddContact/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgAddContact(@ModelAttribute("authToken") String authToken,
                                                                @ModelAttribute("exchgToken") String exchgToken,
                                                                @ModelAttribute("contactsEntryId") String entryId) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("EntryID", entryId);
        requestData.addRequestParam("FirstName", "");
        requestData.addRequestParam("LastName", "");
        requestData.addRequestParam("MiddleName", "");
        requestData.addRequestParam("JobTitle", "");
        requestData.addRequestParam("Company", "");
        requestData.addRequestParam("Department", "");
        requestData.addRequestParam("Email1", "");
        requestData.addRequestParam("Email2", "");
        requestData.addRequestParam("Email3", "");
        requestData.addRequestParam("IMId", "");
        requestData.addRequestParam("WebAddress", "");
        requestData.addRequestParam("Phone", "");
        requestData.addRequestParam("HomePhone", "");
        requestData.addRequestParam("MobilePhone", "");
        requestData.addRequestParam("BusinessAddressStreet", "");
        requestData.addRequestParam("BusinessAddressCity", "");
        requestData.addRequestParam("BusinessAddressState", "");
        requestData.addRequestParam("BusinessAddressPostalCode", "");
        requestData.addRequestParam("BusinessAddressCountry", "");
        requestData.addRequestParam("HomeAddressStreet", "");
        requestData.addRequestParam("HomeAddressCity", "");
        requestData.addRequestParam("HomeAddressState", "");
        requestData.addRequestParam("HomeAddressPostalCode", "");
        requestData.addRequestParam("HomeAddressCounty", "");
        requestData.addRequestParam("Birthday", "");

        return requestData;
    }

    @RequestMapping(value = "/exchangeEditContact/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgEditContact(@ModelAttribute("authToken") String authToken,
                                                                 @ModelAttribute("exchgToken") String exchgToken,
                                                                 @ModelAttribute("contactsEntryId") String entryId) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("EntryID", entryId);
        requestData.addRequestParam("ItemId", "");
        requestData.addRequestParam("FirstName", "");
        requestData.addRequestParam("LastName", "");
        requestData.addRequestParam("MiddleName", "");
        requestData.addRequestParam("JobTitle", "");
        requestData.addRequestParam("Company", "");
        requestData.addRequestParam("Department", "");
        requestData.addRequestParam("Email1", "");
        requestData.addRequestParam("Email2", "");
        requestData.addRequestParam("Email3", "");
        requestData.addRequestParam("IMId", "");
        requestData.addRequestParam("WebAddress", "");
        requestData.addRequestParam("Phone", "");
        requestData.addRequestParam("HomePhone", "");
        requestData.addRequestParam("MobilePhone", "");
        requestData.addRequestParam("BusinessAddressStreet", "");
        requestData.addRequestParam("BusinessAddressCity", "");
        requestData.addRequestParam("BusinessAddressState", "");
        requestData.addRequestParam("BusinessAddressPostalCode", "");
        requestData.addRequestParam("BusinessAddressCountry", "");
        requestData.addRequestParam("HomeAddressStreet", "");
        requestData.addRequestParam("HomeAddressCity", "");
        requestData.addRequestParam("HomeAddressState", "");
        requestData.addRequestParam("HomeAddressPostalCode", "");
        requestData.addRequestParam("HomeAddressCounty", "");
        requestData.addRequestParam("Birthday", "");

        return requestData;
    }

    @RequestMapping(value = "/exchangeReadContact/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgReadContact(@ModelAttribute("authToken") String authToken,
                                                                 @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("EntryID", "");

        return requestData;
    }

    @RequestMapping(value = "/exchangeMoveItem/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchgMoveData(@ModelAttribute("authToken") String authToken,
                                                              @ModelAttribute("exchgToken") String exchgToken,
                                                              @ModelAttribute("itemId") String itemId,
                                                              @ModelAttribute("destinationId") String destId) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("ItemId", "~/-FlatUrlSpace-/6260f6461db98c499b21d63a02cd56a7-bcc48/6260f6461db98c499b21d63a02cd56a7-bd60b");
        requestData.addRequestParam("FolderId", "~/-FlatUrlSpace-/6260f6461db98c499b21d63a02cd56a7-bcc49");

        return requestData;
    }

    @RequestMapping(value = "/exchangeReadEmail/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeReadEmail(@ModelAttribute("authToken") String authToken,
                                                                  @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("WithBody", "true");
        requestData.addRequestParam("ItemIDs", "~/-FlatUrlSpace-/6260f6461db98c499b21d63a02cd56a7-2cc1/6260f6461db98c499b21d63a02cd56a7-8f776;");

        return requestData;
    }

    @RequestMapping(value = "/exchangePrepareEmail/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangePrepareEmail(@ModelAttribute("authToken") String authToken,
                                                                     @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("Subject", "Test Subj");
        requestData.addRequestParam("Body", "Test body");
        requestData.addRequestParam("TO", "testmail@test.test");
        requestData.addRequestParam("Send", "false");
        requestData.addRequestParam("CC", "testmail@test.test");
        requestData.addRequestParam("BCC", "testmail@test.test");
        requestData.addRequestParam("Importance", "high");

        return requestData;
    }

    @RequestMapping(value = "/exchangeUpdateDraftEmail/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeUpdateDraftEmail(@ModelAttribute("authToken") String authToken,
                                                                         @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("Subject", "Test Subj");
        requestData.addRequestParam("Body", "Test body");
        requestData.addRequestParam("TO", "testmail@test.test");
        requestData.addRequestParam("Send", "false");
        requestData.addRequestParam("CC", "testmail@test.test");
        requestData.addRequestParam("BCC", "testmail@test.test");
        requestData.addRequestParam("Importance", "high");
        requestData.addRequestParam("Attachments", "");
        requestData.addRequestParam("ItemId", "");

        return requestData;
    }

    @RequestMapping(value = "/exchangeSendDraftEmail/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeSendDraftEmail(@ModelAttribute("authToken") String authToken,
                                                                       @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("ItemId", "");

        return requestData;
    }

    @RequestMapping(value = "/exchangeForwardEmail/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeForwardEmail(@ModelAttribute("authToken") String authToken,
                                                                     @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("ItemId", "");
        requestData.addRequestParam("Subject", "Test Subj");
        requestData.addRequestParam("Body", "Test body");
        requestData.addRequestParam("TO", "testmail@test.test");
        requestData.addRequestParam("CC", "testmail@test.test");
        requestData.addRequestParam("BCC", "testmail@test.test");
        requestData.addRequestParam("Importance", "high");
        requestData.addRequestParam("Attachments", "");

        return requestData;
    }

    @RequestMapping(value = "/exchangeAddAttachment/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeAddAttachment(@ModelAttribute("authToken") String authToken,
                                                                      @ModelAttribute("exchgToken") String exchgToken,
                                                                      @ModelAttribute("X-51MAPS-SK") String key) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);
        requestData.addRequestHeader("X-51MAPS-SK", key);

        requestData.addRequestParam("FilePath", "");
        requestData.addRequestParam("FileName", "");
        requestData.addRequestParam("EntryID", "");
        requestData.addRequestParam("AttachmentId", "");
        requestData.addRequestParam("ExplorerPath", "");

        return requestData;
    }

    @RequestMapping(value = "/exchangeFetchAttachment/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeDownloadAttachment(@ModelAttribute("authToken") String authToken,
                                                                           @ModelAttribute("exchgToken") String exchgToken,
                                                                           @ModelAttribute("X-51MAPS-SK") String key) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);
        requestData.addRequestHeader("X-51MAPS-SK", key);

        requestData.addRequestParam("URI", "");
        requestData.addRequestParam("AttachmentId", "");

        return requestData;
    }

    @RequestMapping(value = "/exchangeCalendarData/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeCalendarData(@ModelAttribute("authToken") String authToken,
                                                                     @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("AsHtml", "false");
        requestData.addRequestParam("EndDate", "1449377999000");
        requestData.addRequestParam("ReturnInstances", "true");
        requestData.addRequestParam("StartDate", "1448773200000");
        requestData.addRequestParam("WithBody", "false");
        requestData.addRequestParam("EntryID", "AAMkAGIxNDM0ZDVhLTVkNjAtNDExNC05OGJlLTBkYTY2ZGM0Y2RlNQAuAAAAAACj/PfB/94AQ6N0pbnWmcjNAQCJOXq+UKNwSJ+TqdVrKLqdAAAAC9yeAAA=");

        return requestData;
    }

    @RequestMapping(value = "/exchangeReadAppointment/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeReadAppointment(@ModelAttribute("authToken") String authToken,
                                                                        @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
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
                                                                              @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("AsHtml", "false");
        requestData.addRequestParam("WithBody", "true");
        requestData.addRequestParam("EntryID", "AAMkAGIxNDM0ZDVhLTVkNjAtNDExNC05OGJlLTBkYTY2ZGM0Y2RlNQFRAAgI0taF5pJAAEYAAAAAo/z3wf/eAEOjdKW51pnIzQcAiTl6vlCjcEifk6nVayi6nQAAAAvcngAAiTl6vlCjcEifk6nVayi6nQAAAA7x5gAAEA==");

        return requestData;
    }

    @RequestMapping(value = "/exchangeAddAppointment/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeAddAppointment(@ModelAttribute("authToken") String authToken,
                                                                       @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("Email", "");
        requestData.addRequestParam("AllDayEvent", "false");
        requestData.addRequestParam("Body", "");
        requestData.addRequestParam("EndDate", "1448546896000");
        requestData.addRequestParam("Categories", "");
        requestData.addRequestParam("Location", "");
        requestData.addRequestParam("RecurrenceRule", "");
        requestData.addRequestParam("ReminderOffset", "0");
        requestData.addRequestParam("StartDate", "1448545096000");
        requestData.addRequestParam("Subject", "");
        requestData.addRequestParam("TimeZone", "Europe/Minsk");

        return requestData;
    }

    @RequestMapping(value = "/exchangeDeleteAppointment/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeDeleteAppointment(@ModelAttribute("authToken") String authToken,
                                                                          @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("ItemIDs", "");
        requestData.addRequestParam("Notify", "false");

        return requestData;
    }

    @RequestMapping(value = "/exchangeUpdateAppointment/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeChangeAppointment(@ModelAttribute("authToken") String authToken,
                                                                          @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("EntryID", "");
        requestData.addRequestParam("AllDayEvent", "false");
        requestData.addRequestParam("Body", "");
        requestData.addRequestParam("EndDate", "1448546896000");
        requestData.addRequestParam("Location", "");
        requestData.addRequestParam("RecurrenceRule", "");
        requestData.addRequestParam("ReminderOffset", "0");
        requestData.addRequestParam("StartDate", "1448545096000");
        requestData.addRequestParam("Subject", "");
        requestData.addRequestParam("Categories", "");
        requestData.addRequestParam("TimeZone", "Europe/Minsk");


        return requestData;
    }

    @RequestMapping(value = "/exchangeAddDelegate/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeAddDelegate(@ModelAttribute("authToken") String authToken,
                                                                    @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("DelegateEmail", "");

        return requestData;
    }

    @RequestMapping(value = "/exchangeRemoveDelegate/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeRemoveDelegate(@ModelAttribute("authToken") String authToken,
                                                                       @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("DelegateEmail", "");

        return requestData;
    }

    @RequestMapping(value = "/exchangeGetAppointmentCategories/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeGetAppointmentCategories(@ModelAttribute("authToken") String authToken,
                                                                                 @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        return requestData;
    }

    @RequestMapping(value = "/exchangeCreateAppointmentCategory/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeCreateAppointmentCategory(@ModelAttribute("authToken") String authToken,
                                                                                  @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("Name", "");
        requestData.addRequestParam("Color", "RED");

        return requestData;
    }

    @RequestMapping(value = "/exchangeUpdateAppointmentCategoryColor/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeUpdateAppointmentCategoryColor(@ModelAttribute("authToken") String authToken,
                                                                                       @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("Name", "");
        requestData.addRequestParam("Color", "RED");

        return requestData;
    }

    @RequestMapping(value = "/exchangeDeleteAppointmentCategory/predefined")
    @ResponseBody
    public PredefinedRequestData predefParamsForExchangeDeleteAppointmentCategory(@ModelAttribute("authToken") String authToken,
                                                                                  @ModelAttribute("exchgToken") String exchgToken) {
        PredefinedRequestData requestData = mainService.createPredefinedRequestData();
        requestData.addRequestHeader("X-51MAPS-AuthToken", authToken);
        requestData.addRequestHeader("X-51MAPS-Exchange-AuthToken", exchgToken);

        requestData.addRequestParam("Names", "");

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
        authRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));
        model.addAttribute("X-51MAPS-SK", passwordKey);

        HttpResponse response = httpClient.execute(authRequest);

        String responseBody = mainService.getResponseBody(response);
        model.addAttribute("authToken", mainService.getResponseParameter(responseBody, response, "result", "token"));
        Set<String> requestedHeaders = new HashSet<>();
        requestedHeaders.add(X_51_MAPS_SESSION_ID);
        return mainService.constructSuccessResponse(response, responseBody, requestedHeaders);
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
        return mainService.constructSuccessResponse(response, null);
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

        return mainService.constructSuccessResponse(response, null);
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

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/explorerUpdateFileContent", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response explorerPutFile(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost explorerFRequest = new HttpPost(baseUrl + explorerUpdateFile);
        explorerFRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));
        explorerFRequest.setEntity(mainService.constructRequestBody(requestData));

        HttpResponse response = httpClient.execute(explorerFRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/forwardUrl", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response forwardUrl(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpGet forwardRequest = new HttpGet(baseUrl + forwardUrl);
        forwardRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(forwardRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/rsaCheck", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Response rsaCheck(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost rsaCheckRequest = new HttpPost(baseUrl + rsaCheckUrl);
        rsaCheckRequest.setEntity(mainService.constructRequestBody(requestData));
        rsaCheckRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(rsaCheckRequest);
        Set<String> requestedHeaders = new HashSet<>();
        requestedHeaders.add(X_51_MAPS_SESSION_ID);
        return mainService.constructSuccessResponse(response, requestedHeaders);
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
        Response result = mainService.constructSuccessResponse(response, null);
        Matcher matcher = getMatcherForSecurityToken(result);

        if (matcher.find()) {
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
        Response result = mainService.constructSuccessResponse(response, null);

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

        return mainService.constructSuccessResponse(response, null);
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

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeUpdateContact", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeUpdateContact(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeUpdateContactUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeReadContact", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeReadContact(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeReadContactUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeMoveItem", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeMoveData(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeMoveItemUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
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

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangePrepareEmail", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangePrepareEmail(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangePrepareEmailUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeUpdateDraftEmail", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeUpdateDraftEmail(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeUpdateDraftEmailUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeSendDraftEmail", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeSendDraftEmail(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeSendDraftEmailUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeForwardEmail", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeForwardEmail(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeForwardEmailUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeAddAttachment", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeAddAttachment(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeAddAttachmentUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeFetchAttachment", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeDownloadAttachment(@RequestBody Map<String, String> requestData) throws IOException, URISyntaxException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        URI uri = new URIBuilder(baseUrl + exchangeJsonToken + mainService.getUriParameter(requestData)).addParameters(mainService.constructGetMethodParameters(requestData)).build();
        HttpGet contactsRequest = new HttpGet(uri);
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
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

        return mainService.constructSuccessResponse(response, null);
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

        return mainService.constructSuccessResponse(response, null);
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

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeAddAppointment", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeAddAppointment(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeAddAppointmentUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeDeleteAppointment", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeDeleteAppointment(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeDeleteAppointmentUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeUpdateAppointment", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeChangeAppointment(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeUpdateAppointmentUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeAddDelegate", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeAddDelegate(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeAddDelegateUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeRemoveDelegate", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeRemoveDelegate(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeRemoveDelegateUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeGetAppointmentCategories", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeGetAppointmentCategories(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeGetAppointmentCategoriesUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeCreateAppointmentCategory", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeCreateAppointmentCategory(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeCreateAppointmentCategoryUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeUpdateAppointmentCategoryColor", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeUpdateAppointmentCategoryColor(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeUpdateAppointmentCategoryColorUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
    }

    @RequestMapping(value = "/exchangeDeleteAppointmentCategory", method = RequestMethod.POST)
    @ResponseBody
    public Response exchangeDeleteAppointmentCategory(@RequestBody Map<String, String> requestData) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        String baseUrl = requestData.get("baseUrl").trim();
        HttpPost contactsRequest = new HttpPost(baseUrl + exchangeDeleteAppointmentCategoryUrl);
        contactsRequest.setEntity(mainService.constructRequestBody(requestData));
        contactsRequest.setHeaders(mainService.constructHeadersArray(requestData.get("headers").trim()));

        HttpResponse response = httpClient.execute(contactsRequest);

        return mainService.constructSuccessResponse(response, null);
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
        } catch (Exception e) {
            return null;
        }
    }
}
