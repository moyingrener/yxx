<%--
  Created by IntelliJ IDEA.
  User: 无处安身
  Date: 2019/8/9
  Time: 12:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript" src="http://cdn.bootcss.com/sockjs-client/1.1.1/sockjs.js"></script>

</head>
<body>
    <input type="button" value="start" name="uploadchats" id="uploadchats"/>
    <input type="button" value="start2" onclick="test()"/>



</body>

<script>

    function test(){
        var json = {};
        json.name = [];
        //json.openID = "test-A";
        var h = {fromUser:"test-A", toUser:"test-B", content:"haha",theTime: "2019-08-01 23:25:22", isPic: false, isSelf: true}


        var c2 = {openID: "test-A"};

        json.name.push(h);

        //var chats = JSON.stringify(json);
        //var test = chats;
        console.log(json);

        var s = JSON.stringify(json);
        console.log(json);

    };

    $(function () {
        $("#uploadchats").click(function () {


            var h = {
                "fromUser":"test-A",
                "toUser":"test-B",
                "content":"haha",
                "theTime": "2019-08-01 23:25:22",
                "goodsId": 1,
                "isPic" : false,
                "isSelf": true,
                "type":false
            };
            var json = new Array();
            json.push(h);
            var chatRecords = JSON.stringify(json);

            $.ajax({
                type:"post",
                url:"https://www.yxxcloud.cn/api/uploadChatRecord",

                data: chatRecords,
                contentType : 'application/json',
                dataType:'json',
                success:function(re){
                    console.log(re);
                },
                error:function(re){
                    console.log('请求失败，请检查网络连接!', re);
                    alert("请求失败！");
                }
            });
        })
    })
</script>
</html>
