$(document).ready(function(){
var left=$(".left").height();
var right=$(".right").height();

if (left>right){
$(".left").css("border-right","#efefef dashed 2px");

} else {
    $(".right").css("border-left","#efefef dashed 2px");
}

});