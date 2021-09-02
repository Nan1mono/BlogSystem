$(document).ready(function () {
    $("[name*='user']").css({"width":"280px","height":"30px"});
});

function deleteTopic(topicId){
    if(confirm("确定要删除编号为:"+topicId+"的文章吗?")){
        location.href="delete?topicId="+topicId;
    }
}