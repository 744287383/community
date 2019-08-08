

//添加一级评论
function  addComment() {
    var parentId=$("#parentId").val();
    var type=$("#type").val();
    var content=$("#comment-content").val();
post(parentId,content,type);

}
//添加二级评论
function addTwoComment(e) {
    var id=e.getAttribute("data-id");
    console.log(id);
    var parentId=id;
    var content=$("#reply-"+id).val();
    console.log(content);
    var type=2;

    post(parentId,content,type);
}


function post(parentId,content,type) {
    if (content==null||content.trim().length==0){
        alert("回复的内容不能为空！");
        return;
    }
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
                window.location.reload();
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


function showTwoComment(e) {
    var id=e.getAttribute("data-id");
    console.log(id);
    var state=$("#comment-"+id).data("in");
    console.log(state);
    if (state){
        // 缩回二级评论
        e.classList.remove("reply-icon");
        $("#comment-"+id).removeClass("in");
        $("#comment-"+id).removeData("in");
    }else {
        //展开二级评论
        e.classList.add("reply-icon");
        $("#comment-"+id).data("in","in");
        $("#comment-"+id).addClass("in");
    }

}