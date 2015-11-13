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
        <div style="width:200px;float:left;display:inline-block;">
            <p>REQUESTS: </p>
            <input type="radio" name="contentType" checked="checked" value="form">Form <br>
            <input type="radio" name="contentType" value="json">Json <br>
            <input type="radio" name="contentType" value="text">Text <br>
            <hr/>
            <input type="button" value="Login" id="loginBtn"/>
            <br/>
            <input type="button" value="Get Password" id="getPasswordBtn"/>
            <br/>
            <hr/>
            <input type="button" value="Explorer Folder data" id="explorerFolderDataBtn"/>
            <br/>
            <input type="button" value="Explorer Get File" id="explorerGetFileBtn"/>
            <br/>
            <hr/>
            <input type="button" value="Forward" id="forwardUrlBtn"/>
            <br/>
            <hr/>
            <input type="button" value="Exchange login" id="exchangeLoginBtn"/>
            <br/>
            <input type="button" value="Exchange folders" id="exchangeFoldersBtn"/>
            <br/>
            <input type="button" value="Exchange folder data" id="exchangeFolderDataBtn"/>
            <br/>
            <input type="button" value="Exchange contacts data" id="exchangeContactsDataBtn"/>
            <br/>
            <input type="button" value="Exchange read email" id="exchangeReadEmailBtn"/>
            <br/>
            <input type="button" value="Exchange calendar data" id="exchangeCalendarDataBtn"/>
            <br/>
            <input type="button" value="Exchange read appointment" id="exchangeReadAppointmentBtn"/>
            <br/>
            <input type="button" value="Exchange read master appointment" id="exchangeReadMasterAppointmentBtn"/>
            <br/>
            <hr/>
          <span style="display: none;" id="loading">
            <img src="${pageContext.request.contextPath}/resources/application/images/indicator.gif"/>
          </span>
        </div>
        <%-- SECOND COLUMN (areas) --%>
        <div style="margin-left:200px; padding-left: 20px">
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
