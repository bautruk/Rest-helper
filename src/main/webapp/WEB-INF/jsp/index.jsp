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
                <option value="form">Form </option>
                <option value="json">Json </option>
                <option value="text">Text </option>
            </select>
            <select id="baseUrl">
                <option value="http://192.168.12.60:9512/">192.168.12.60:9512</option>
                <option value="http://192.168.100.22:9512/">192.168.100.22:9512</option>
                <option value="http://192.168.100.22:9512/">192.168.100.22:9512</option>
                <option value="http://localhost:9512/">localhost:9512</option>
                <option value="http://192.168.0.102:9512/">192.168.0.102:9512</option>
                <option value="https://dev.e-dapt.net:4440/">dev.e-dapt.net:4440</option>
                <option value="http://128.66.200.101:9512/" selected="selected">128.66.200.101:9512</option>
                <option value="https://controller.botf03.net:4440/newmsa/">controller.botf03.net:4440/newmsa</option>
                <option value="http://msa.botf03.net:180/">msa.botf03.net:180</option>
                <option value="http://128.66.101.101:9512/">128.66.101.101:9512</option>
                <option value="http://192.168.12.219:9512/">192.168.12.219:9512</option>
            </select>
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
            <input type="button" value="Exchange add appointment" id="exchangeAddAppointmentBtn"/>
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
