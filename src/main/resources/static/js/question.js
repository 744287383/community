
function  addComment() {
console.log(client_id);
    console.log(redirectUrl);
    var parentId=$("#parentId").val();
    var type=$("#type").val();
    var content=$("#comment-content").val();
    console.log(parentId);
    console.log(type);
    console.log(content);
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify({
            "parentId":parentId,
            "type":type,
            "content":content
        }),
        success: function (res) {
            if (res.code==200){
                alert(res.message);
            }else
            {

                if (res.code==3001){

                    var isLogin = confirm(res.message);
                    if (isLogin){
                        localStorage.setItem("login","login");
                        window.open('https://github.com/login/oauth/authorize?client_id='+client_id+'&redirect_uri='+redirectUrl+'&scope=user&state=1');

                    }

                }else {
                    alert(res.message);

                }
            }
        },
        dataType: "json"
    });
}