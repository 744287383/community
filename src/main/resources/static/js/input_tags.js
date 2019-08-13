(function ($) {
    $.FreeTags = function (e) {
        addtagsbox(e);
        var oa = '#' + e + ' input:first';
        var ta = $(oa);
        ta.bind('keypress', function (event) {
            if (event.keyCode == 32) {
                return false;
            }
            if (ta.val()) {
                if (event.keyCode == 13 || event.keyCode == 32) {
                    var val = ta.val();
                    var tags = ta.next('input').val();
                    var tag = tags.split(',');
                    if ($.inArray(val, tag) >= 0) {
                        shwoalert(e, val);
                        return false;
                    }
                    ta.before('<a href="javascript:;"><span><span class="glyphicon glyphicon-tags" aria-hidden="true" style="margin-right: 5px"></span>' + val + '</span><em>X</em></a>');
                    if (ta.next('input').val().length==0){
                        ta.next('input').val( val);
                        ta.val('');
                        return false;
                    }else {
                        ta.next('input').val(ta.next('input').val() + ',' + val);
                        ta.val('');
                        return false;
                    }
                }
            }
        });

        ta.bind('keydown', function (event) {
            if (event.keyCode == 8) {
                var del = '#' + e;
                del = $(del);
                if (ta.val() == '') {
                    var name = del.find('a').last().find('span').text();
                    deltags(e, name);
                    del.find('a').last().remove();
                } else {
                    return true;
                }
            }
            ;
        });
    };

    $.tapAddTags=function (e,val) {
        var oa = '#' + e + ' input:first';
        var ta = $(oa);

        var tags = ta.next('input').val();
        var tag = tags.split(',');
        if ($.inArray(val, tag) >= 0) {
            shwoalert(e, val);
            return false;
        }
        ta.before('<a href="javascript:;"><span><span class="glyphicon glyphicon-tags" aria-hidden="true" style="margin-right: 5px;"></span>' + val + '</span><em>X</em></a>');
        if (ta.next('input').val().length==0){
            ta.next('input').val( val);
            ta.val('');
            return false;
        }else {
            ta.next('input').val(ta.next('input').val() + ',' + val);
            ta.val('');
            return false;
        }


    }

    var shwoalert = function (e, name) {
        var c = '#' + e + ' a';
        var fg = $(c);
        $.each(fg, function (val, item) {
            if (name == $(item).find('span').text()) {
                $(this).animate({borderColor: 'red'}, 'slow');
                $(this).animate({borderColor: '#ccc'}, 'slow');
            }
        });
    }



    var bingbtn = function (e) {
        var d = '#' + e;
        $(d).on('click', 'em', function (event) {
            $(this).parents('a').remove();
            var name = $(this).parents('a').find('span').text();
            deltags(e, name);
        })
    }


    var addtagsbox = function (e) {
        if (e == '') throw'ID不存在！';
        var o = '#' + e;
        var t = $(o);
        var placeholder = t.find('input').attr('placeholder');
        var tags = t.find('input').val();
        t.find('input').before('<input class="input_content" placeholder="' + placeholder + '" />');
        if (tags) {
            tag = tags.split(',');
            var oa = '#' + e + ' input:first';
            var ta = $(oa);
            $.each(tag, function (val, item) {
                ta.before('<a href="javascript:;"><span><span class="glyphicon glyphicon-tags" aria-hidden="true" style="margin-right: 5px;"></span>' + item +'</span><em>X</em></a>');
            });
        }
        ;bingbtn(e);
    };

    var deltags = function (e,delname) {
        var o = '#' + e;
        var t = $(o);
        var tags = t.find('input').last('input').val().toString();
        var tag1 = tags.split(',');
        if (tag1.length==1){
            t.find('input').last('input').val("");
        }
        var num = $.inArray(delname,tag1);
         tag1.splice(num, 1);
        t.find('input').last('input').val(tag1.toString());
    }
})(jQuery);

function  tapTags(e) {
var lastChild = e.lastChild;
    $.tapAddTags('tags',lastChild.innerHTML);
}
function showORHide() {

      $("#nav-tags").show();



}


