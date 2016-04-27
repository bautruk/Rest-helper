<html>
<head>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/jquery/js/jquery-1.11.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/application/js/main.js"></script>
</head>
<body>
<div>
    <div>
        <%-- FIRST COLUMN (buttons) --%>
        <div style="width:220px;float:left;display:inline-block;">
            <p>REQUESTS: </p>
            <select id="contentType">
                <option value="get">GET</option>
                <option value="form" selected="selected">Form </option>
                <option value="json">Json </option>
                <option value="text">Text </option>
                <option value="multipart-form">Multipart-form </option>
            </select>
            <select id="baseUrl">
                <option value="http://192.168.12.60:9512/" selected="selected">192.168.12.60:9512</option>
                <option value="http://128.66.200.101:9512/">Dev-128.66.200.101:9512</option>
                <option value="http://192.168.100.22:9512/">192.168.100.22:9512</option>
                <option value="http://192.168.100.21:9512/">192.168.100.21:9512</option>
                <option value="https://dev.e-dapt.net:4440/">dev.e-dapt.net:4440</option>
                <option value="https://controller.botf03.net:4440/newmsa/">controller.botf03.net:4440/newmsa</option>
                <option value="http://128.66.101.101:9512/">128.66.101.101:9512</option>
                <option value="http://192.168.12.58:9512/">192.168.12.58:9512</option>
                <option value="http://192.168.12.7:9512/">192.168.12.7:9512</option>
            </select>
            <hr/>
            <input type="button" value="Login" id="loginBtn"/>
            <select id="username">
                <option value="milshtyu" selected="selected">milshtyu</option>
                <option value="pizito">pizito</option>
                <option value="exadel1">exadel1</option>
                <option value="exadel2">exadel2</option>
                <option value="exadel3">exadel3</option>
            </select>
            <br/>
            <input type="button" value="Get Password" id="getPasswordBtn"/>
            <br/>
            <hr/>
            <hr/>
            <input type="button" value="Explorer Folder data" id="explorerFolderDataBtn"/>
            <br/>
            <input type="button" value="Explorer Get File" id="explorerGetFileBtn"/>
            <br/>
            <hr/>
            <hr/>
            <input type="button" value="Forward" id="forwardUrlBtn"/>
            <br/>
            <hr/>
            <hr/>
            <input type="button" value="Exchange login" id="exchangeLoginBtn"/>
            <br/>
            <input type="button" value="Exchange folders" id="exchangeFoldersBtn"/>
            <br/>
            <input type="button" value="Exchange folder data" id="exchangeFolderDataBtn"/>
            <br/>
            <input type="button" value="Exchange contacts data" id="exchangeContactsDataBtn"/>
            <br/>
            <input type="button" value="Exchange move item" id="exchangeMoveItemBtn"/>
            <br/>
            <hr/>
            <input type="button" value="Exchange read contact" id="exchangeReadContactBtn"/>
            <br/>
            <hr/>
            <input type="button" value="Exchange read email" id="exchangeReadEmailBtn"/>
            <br/>
            <input type="button" value="Exchange prepare email" id="exchangePrepareEmailBtn"/>
            <br/>
            <input type="button" value="Exchange update draft email" id="exchangeUpdateDraftEmailBtn"/>
            <br/>
            <input type="button" value="Exchange send draft email" id="exchangeSendDraftEmailBtn"/>
            <br/>
            <input type="button" value="Exchange add attachment to draft" id="exchangeAddAttachmentToDraft"/>
            <br/>
            <input type="button" value="Exchange fetch attachment" id="exchangeFetchAttachment"/>
            <br/>
            <hr/>
            <input type="button" value="Exchange calendar data" id="exchangeCalendarDataBtn"/>
            <br/>
            <input type="button" value="Exchange read appointment" id="exchangeReadAppointmentBtn"/>
            <br/>
            <input type="button" value="Exchange read master appointment" id="exchangeReadMasterAppointmentBtn"/>
            <br/>
            <input type="button" value="Exchange add appointment" id="exchangeAddAppointmentBtn"/>
            <br/>
            <input type="button" value="Exchange delete appointment" id="exchangeDeleteAppointmentBtn"/>
            <br/>
            <input type="button" value="Exchange update appointment" id="exchangeUpdateAppointmentBtn"/>
            <br/>
            <hr/>
            <input type="button" value="Exchange get appointment categories" id="exchangeGetAppointmentCategoriesBtn"/>
            <br/>
            <input type="button" value="Exchange create appointment category" id="exchangeCreateAppointmentCategoryBtn"/>
            <br/>
            <input type="button" value="Exchange update appointment category color" id="exchangeUpdateAppointmentCategoryColorBtn"/>
            <br/>
            <input type="button" value="Exchange delete appointment category" id="exchangeDeleteAppointmentCategoryBtn"/>
            <br/>
            <hr/>
          <span style="display: none;" id="loading">
            <img src="${pageContext.request.contextPath}/resources/application/images/indicator.gif"/>
          </span>
        </div>
        <%-- SECOND COLUMN (areas) --%>
        <div style="margin-left:220px; padding-left: 20px">
            <input type="button" value="Send" id="sendButton"/>
            <br/>
            <label for="parametersArea">Parameters</label>
            <textarea wrap="off" id="parametersArea" rows="10" style="width: 100%"></textarea>
            <br/>
            <label for="headersArea">Headers</label>
            <textarea wrap="off" id="headersArea" rows="10" style="width: 100%; margin-bottom: 30px"></textarea>
            <br/>
            <hr/>
            <br/>
            <label for="responseMetaArea" style="margin-top: 30px">Response meta (headers etc.)</label>
            <textarea wrap="off" id="responseMetaArea" rows="10" style="width: 100%"></textarea>
            <br/>
            <label for="responseBodyArea">Response body</label>
            <textarea wrap="off" id="responseBodyArea" rows="10" style="width: 100%"></textarea>
        </div>
    </div>
</div>
</body>
</html>
