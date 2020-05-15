<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>

<%
    String path = request.getContextPath();
    String basepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String name = request.getParameter("openID");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Java API for WebSocket (JSR-356)</title>
</head>
<style>
    .msg {outline: 1px solid #ccc; padding: 5px; margin: 5px; }
    .string { color: green; }
    .number { color: darkorange; }
    .boolean { color: blue; }
    .null { color: magenta; }
    .key { color: red; }
</style>
<body>
<script type="text/javascript" src="//cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
<script type="text/javascript" src="//cdn.bootcss.com/sockjs-client/1.1.1/sockjs.js"></script>

    <br><br>
    请输入本人openID：<input type="text" id = "fromUser" value="<%=name%>"/><br><br><br>
    请输入对方openID：<input type="text" id = "toUser" /><br><br><br>
    请输入物品goodsId：<input type="text" id = "goodsId" /><br><br><br>
    请输入交易状态type：
        <select id="type" >
            <option value="false">false</option>
            <option value="true">true</option>
        </select><br><br><br>
    请输入消息：<textarea rows="3" cols="100" id="content" name="content" ></textarea><br><br>

    时间：<h3>自动获取当前时间！！输入的信息转为json并发送，请在控制台查看格式!</h3>
    <button onclick="doSend()">发送</button><br><br>

    <hr />
    <p>这是接收到的消息：</p><br>
    <div id="msg">

    </div>
    <hr />

</body>

<script type="text/javascript">
    var websocket = null;
    var hostname = document.location.hostname;
    var myopenID = $("#fromUser").val()

    if ('WebSocket' in window) {
        //Websocket的连接
        //websocket = new WebSocket("ws://localhost:8080/yunxiaoxian/websocket?openID=" + myopenID);//WebSocket对应的地址
        //websocket = new WebSocket("ws://47.103.18.92/yunxiaoxian-1.0-SNAPSHOT/websocket");//WebSocket对应的地址
        websocket = new WebSocket("wss://www.yxxcloud.cn/yunxiaoxian-1.0-SNAPSHOT/websocket?openID=" + myopenID);//WebSocket对应的地址
    }
    else if ('MozWebSocket' in window) {
        //Websocket的连接
        //websocket = new MozWebSocket("ws://localhost:8080/yunxiaoxian/websocket?openID=" + myopenID);//SockJS对应的地址
        //websocket = new MozWebSocket("ws://47.103.18.92/yunxiaoxian-1.0-SNAPSHOT/websocket");//WebSocket对应的地址
        websocket = new WebSocket("wss://www.yxxcloud.cn/yunxiaoxian-1.0-SNAPSHOT/websocket?openID=" + myopenID);//WebSocket对应的地址
    }
    else {
        //SockJS的连接
        //websocket = new SockJS("wss://47.103.18.92/yunxiaoxian-1.0-SNAPSHOT/sockjs");    //SockJS对应的地址
        websocket = new WebSocket("wss://www.yxxcloud.cn/yunxiaoxian-1.0-SNAPSHOT/sockjs?openID=" + myopenID);//SocketJS对应的地址
    }

    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    websocket.onclose = onClose;

    function onOpen(openEvt) {
        //alert(openEvt.Data);
    }

    function onMessage(evt) {

        $("#msg").append(showbyjson(evt.data+"<br><br><br>")); // 接收后台发送的消息

    }
    function onError() {
        alert("连接出现错误");
    }
    function onClose() {
    }

    function doSend() {
        var fromUser = $("#fromUser").val();
        var toUser = $("#toUser").val();
        var content = $("#content").val();
        var theTime = new Date().Format("yyyy-MM-dd HH:mm:ss");
        var goodsId = $("#goodsId").val();
        var type = $("#type").val();

        var object = {};
        object['fromUser'] = fromUser;
        object['toUser'] = toUser;
        object['content'] = content;
        object['theTime'] = theTime;
        object['goodsId'] = goodsId;
        object['isPic'] = false;

        if(type == "true"){
            object['type'] = true;
        } else {
            object['type'] = false;
        }


        var json = JSON.stringify(object);

        console.log("fromUser:" + fromUser);
        console.log("toUser" + toUser);
        console.log("content" + content);
        console.log("theTime" + theTime);
        console.log("goodsId" + goodsId);
        console.log("isPic" + "false");
        console.log("type" + type);
        console.log("拼接的json:" + json);


        if (websocket.readyState == websocket.OPEN) {
            //websocket.send($("#targetName").val()+"@"+$("#inputMsg").val());//调用后台handleTextMessage方法
            websocket.send(json);
            alert("发送成功!");
        } else {

            alert("连接失败!"+websocket.readyState);
        }
        console.log("test");
    }

    window.close = function () {
        websocket.onclose();
    }

    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }


    function showbyjson(json) {
        if (typeof json != 'string') {
            json = JSON.stringify(json, null, 2);
        }
        json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
        return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
            var cls = 'number';
            if (/^"/.test(match)) {
                if (/:$/.test(match)) {
                    cls = 'key';
                } else {
                    cls = 'string';
                }
            } else if (/true|false/.test(match)) {
                cls = 'boolean';
            } else if (/null/.test(match)) {
                cls = 'null';
            }
            return '<span class="' + cls + '">' + match + '</span>';
        });
    }
</script>

</html>
