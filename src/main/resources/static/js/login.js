layui.use(['form', 'layer', 'jquery'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer
    $ = layui.jquery;

    form.on('submit(login)', function (data) {
        //向后台发送请求 AJAX
        $.ajax({
            type: "post",
            url: "/home/login",
            data: data.field, // 数据设置参数
            dataType: "json",
            success: function (re) {
                if (re.status == 200) {
                    //判定如果成功-->直接跳转到主页面
                    window.location.href = "/home/index "
                } else {
                    //如果失败-->打印失败的信息,继续留在登录界面
                    layer.alert(re.message, {icon: 2})
                }
            }
        })

        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });


    //表单输入效果
    $(".loginBody .input-item").click(function (e) {
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function () {
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function () {
        $(this).parent().removeClass("layui-input-focus");
        if ($(this).val() != '') {
            $(this).parent().addClass("layui-input-active");
        } else {
            $(this).parent().removeClass("layui-input-active");
        }
    })


    //需要编写一个点击验证码进行验证码切换的功能
    $('#codeImg').click(function () {

        var img = document.getElementById("codeImg")
        //设置一个新的src地址 http://localhost:8080/
        //因为请求的地址是一样的,浏览器有可能会直接从缓存中将上一次获取到的数据返回回来
        img.src = "/home/getCode?time=" + new Date().getTime()
    })


})


