$(function () {
    $("#register-form").validate();
    $("#username").rules('add', {
        required: true,
        minlength: 2,
        maxlength: 20,
        messages: {required: '请输入昵称！', minlength: '昵称不能小于{0}位！', maxlength: '昵称不能小于{0}位！'}
    });

    $("#phone").rules('add', {
        required: true,
        minlength: 11,
        maxlength: 11,
        digits: true,
        // phone: true,
        messages: {required: '请输入您的手机号！', rangelength: '请输入正确的手机号！', digits: '请输入正确的手机号！',minlength:'请输入11位得手机号码',maxlength:'请输入11位得手机号码'}
    });
    $("#email").rules('add', {required: true,        minlength: 4, messages: {required: '请输入您的个性签名！',minlength:'个性签名不能少于4个字符'}});
    $("#birthday").rules('add', {
        required: true,
        date: true,
        messages: {required: '请选择生日，如1993-03-29！', date: '生日格式不正确'}
    });
    $("#password").rules('add', {
        required: true,
        minlength: 6,
        maxlength: 20,

        messages: {
            required: '请输入6-20位密码，数字和字母！',
            minlength: '密码不能小于{0}位！',
            maxlength: '密码不能超过{0}位！'
        }
    });
    $("#notpass").rules('add', {
        required: true,
        equalTo: '#password',
        messages: {required: '请再次输入密码', equalTo: '密码输入不一致',}
    });
    $("#accept").rules('add', {
        required: true, messages: {required: '请接受版权声明和隐私保护！'}, highlight: function (element, errorClass) {
            setTimeout(function () {
                if ($("#accept-error").get(0)) {
                    $("#accept-error").css("display", "block")
                    $(".accept").insertBefore("#accept-error");
                }
            }, 0);
        },
    });
    $("#email").autocomplete({
        delay: 0, source: function (request, response) {
            var hosts = ['qq.com', '163.com', '126.com', 'sina.com.cn', '263.com'], term = request.term, name = term,
                host = '', ix = term.indexOf('@'), result = [];
            if (ix > -1) {
                name = term.slice(0, ix);
                host = term.slice(ix + 1);
            }
            if (name) {
                var findedHosts = [];
                if (host) {
                    findedHosts = $.grep(hosts, function (value, index) {
                        return value.indexOf(host) > -1
                    });
                } else {
                    findedHosts = hosts;
                }
                var findedResult = $.map(findedHosts, function (value, index) {
                    return name + '@' + value;
                })
                if (findedResult == '') {
                    result.push(term)
                }
                result = result.concat(findedResult);
            }
            response(result);
        }
    });
    $.validator.addMethod('email', function (value, element) {
        var email = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        return this.optional(element) || (email.test(value));
    });
    $.validator.addMethod('phone', function (value, element) {
        var phone = /^1[3|4|5|7|8][0-9]\d{8}$/;
        return this.optional(element) || (phone.test(value));
    });
    $.validator.addMethod('pass', function (value, element) {
        var pass = /^[\w]+$/
        return this.optional(element) || (pass.test(value));
    });
    $.validator.addMethod('date', function (value, element) {
        var date = /^(19|20)\d{2}-(1[0-2]|0?[1-9])-(0?[1-9]|[1-2][0-9]|3[0-1])$/;
        return this.optional(element) || (date.test(value));
    });
})