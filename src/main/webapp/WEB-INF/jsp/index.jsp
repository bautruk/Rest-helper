<html>
<head>
</head>
<body>
<div>
    <div>
        <div style="width:200px;float:left;display:inline-block;">
            <input type="button" value="Login" id="loginButton"/>
            <br/>
            <input type="button" value="Forward" id="forwardUrlButton"/>
            <br/>
            <input type="button" value="Exchange" id="exchangeButton"/>
        </div>
        <div style="margin-left:200px;">
            <input type="button" value="Send" id="sendButton"/>
            <br/>
            <label for="parametersArea">Parameters</label>
            <textarea wrap="off" id="parametersArea" rows="10" style="width: 100%"></textarea>
            <br/>
            <label for="headersArea">Headers</label>
            <textarea wrap="off" id="headersArea" rows="10" style="width: 100%"></textarea>
            <br/>
            <br/>
            <hr/>
            <br/>
            <label for="responseMetaArea">Response meta (headers etc.)</label>
            <textarea wrap="off" id="responseMetaArea" rows="10" style="width: 100%"></textarea>
            <br/>
            <label for="responseBodyArea">Response body</label>
            <textarea wrap="off" id="responseBodyArea" rows="10" style="width: 100%"></textarea>
        </div>
    </div>
</div>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/jquery/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/application/js/main.js"></script>
</body>
</html>
