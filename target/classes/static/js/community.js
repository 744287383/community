$(document).ready(function(){
var left=$(".left").height();
var right=$(".right").height();

if (left>right){
$(".left").css("border-right","#efefef dashed 2px");

} else {
    $(".right").css("border-left","#efefef dashed 2px");
}

});

function localUserLogin() {
    var phoneNum=$("#recipient-name").val();
    var password=$("#recipient-password").val();
    var tips=document.getElementById("message");

    var formData=JSON.stringify({"phoneNum":phoneNum,
        "password":password
    });

    console.log("账号密码"+phoneNum+password);
    console.log("json:"+formData);
    $.ajax({
        type: "POST",
        url: "/localUserLogin",
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify({
            "phoneNum":phoneNum,
            "password":password
        }),
        success: function (res) {
            if (res.code==200){
                window.location.reload();
            }
            if (res.code==6001||res.code==6002||res.code==6003){

             tips.innerText="Tips:"+res.message;
            }else {
                tips.innerText="Tips:"+res.message;
            }
        },
        dataType: "json"
    });

}

function toGithubLogin() {
    localStorage.setItem("login","login");
    window.open('https://github.com/login/oauth/authorize?client_id='+client_id+'&redirect_uri='+redirectUrl+'&scope=user&state=1');
}

