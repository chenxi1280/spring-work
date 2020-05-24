var date = new Date();
Date.prototype.Format = function (fmt) { //author: meizz

    var o = {

        "M+": this.getMonth() + 1, //月份

        "d+": this.getDate(), //日

        "h+": this.getHours(), //小时

        "m+": this.getMinutes(), //分

        "s+": this.getSeconds(), //秒

        "q+": Math.floor((this.getMonth() + 3) / 3), //季度

        "S": this.getMilliseconds() //毫秒

    };

    if (/(y+)/.test(fmt))

        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));

    for (var k in o)

        if (new RegExp("(" + k + ")").test(fmt))

            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));

    return fmt;

}


layui.use(['table', 'upload', 'layer', 'laydate'], function () {
    //实例化layui模块 table-->数据表格模块  $ 表示我们的jquery模块
    var table = layui.table,
        $ = layui.jquery,
        upload = layui.upload,
        layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate


    //执行一个 table 实例
    table.render({
        //绑定body里面的table标签-->id
        elem: '#userdemo',
        //table表格的高度
        height: 600,
        //请求后台的地址(获取video的视频信息)
        url: '/user/getusers',
        //表格的名称
        title: '用户表',
        //开启分页
        page: true,
        //表头工具栏
        //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        toolbar: '#bar',
        //异步请求时 解析数据的方式
        parseData: function (res) { //res 即为原始返回的数据
            return {
                "code": res.status, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.total, //解析数据长度
                "data": res.item //解析数据列表
            };
        },

        //生成table表头(手动编写)-->解析后台的数据
        cols: [
            [ //表头
                {
                    type: 'checkbox',
                    fixed: 'left'
                }, {
                field: 'uid',
                title: 'ID',
                sort: true,
                width: 100,
                fixed: 'left'
            }, {
                field: 'uusername',
                title: '业主名字',
                align: 'center',
                edit: 'text',

            }, {
                field: 'uimg',
                title: '照片',
                align: 'center',
                templet: function (d) {
                    var path = d.uimg;
                    console.log(path)
                    //需要一个图片标签进行图片展示
                    return "<img src='../" + path + "' style='width: 150px; height: 150px;'/>"
                }
            }, {
                field: 'ubroom',
                title: '地址',
                align: 'center',
                edit: 'text',
            }, {
                field: 'uphoneid',
                title: '联系方式',

                align: 'center',
                edit: 'text',
            }, {
                field: 'cstate',
                title: '是否为本小区的用户',
                width: 150,
                align: 'center',
                templet: function (v) {


                    if (v.cstate == 1) {
                        return '是'
                    } else {
                        return '否';
                    }
                }

            }, {
                field: 'aid',
                title: '创建员工',
                width: 150,
                align: 'center'

            }, {
                field: 'ucreatedate',
                title: '创建时间',
                sort: true,
                align: 'center',
                templet: function (d) {

                    // date.setTime(d.ucreatedate);

                    // return date.Format("yyyy-MM-dd hh:mm:ss");

                    return showTime(d.ucreatedate);
                }
            },
                // {
                //     field: 'likeCounts',
                //     title: '点赞次数',
                //
                //     align:'center'
                // }, {
                //     field: 'status',
                //     title: '状态',
                //
                //     align:'center'
                // },
                {
                    fixed: 'right',
                    width: 165,
                    align: 'center',
                    toolbar: '#barDemo'
                }
            ]
        ]
    });

    //监听头工具栏事件   监听绑定表
    table.on('toolbar(usertest)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id),
            //将选中的数据放在我们的data数组里面
            data = checkStatus.data; //获取选中的数据

        //需要从data数组中将所有的id获取出来
        //声明一个数组,来存放选中的数据的id
        var ids = new Array();
        //循环遍历我们的data数组
        for (var i in data) {

            ids[i] = data[i].uid;
        }

        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2,
                    title: '新建用户',
                    area: ['50%', '80%'],
                    content: '/user/adduserhtml'
                })

                break;
            case 'delete':
                if (data.length === 0) {
                    layer.msg('请选择一行');
                } else {
                    //执行删除操作
                    layer.confirm("数据可贵! 是否确认删除?", function (index) {
                        //使用我们的ajax进行请求发送  异步的形式
                        //异步请求    //同步请求
                        //使用jauery的形式写ajax
                        $.ajax({
                            type: "post",
                            url: "/user/delUser",
                            dataType: 'json',
                            data: {
                                //key     value
                                "ids": ids
                            },
                            traditional: true, //向后台传送数组的时候,必须申明为true
                            success: function (re) {
                                //如果成功了   后台应该返回一个成功的消息 让我们前端进行展示给用户
                                //如果失败了   同上
                                if (re.status == 200) {
                                    layer.alert(re.message, {
                                        icon: 1
                                    }, function (index) {
                                        layer.close(index)

                                    })
                                } else {
                                    layer.alert(re.message, {
                                        icon: 2
                                    }, function (index) {
                                        layer.close(index)
                                    })
                                }

                            }
                        })

                    })
                }
                break;


        }
        ;
    });

    //监听单元格编辑
    table.on('edit(usertest)', function (obj) {
        var value = obj.value //得到修改后的值
            , data = obj.data //得到所在行所有键值
            , field = obj.field; //得到字段
        //update video set video_path="1"  where id="001"
        layer.confirm('确定修改', function () {
            $.ajax({
                type: "post",
                url: "/user/updateUser ",
                dataType: "json",
                data: {
                    "uid": data.uid,
                    "field": field,
                    "value": value
                },
                success: function (re) {

                    layer.msg(re.message)
                }
            })

        })


    });

    //监听行工具事件
    table.on('tool(usertest)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值


        //查看
        if (layEvent === 'detail') {
            layer.open({
                type: 2,
                title: '查看业主',
                area: ['50%', '80%'],
                content: '/user/getedituser' + '?uid=' + data.uid,

            })


        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令

                $.ajax({
                    type: "post",
                    url: "/user/delUser",
                    dataType: 'json',
                    data: {
                        //key     value
                        "ids": data.uid
                    },
                    traditional: true, //向后台传送数组的时候,必须申明为true
                    success: function (re) {
                        //如果成功了   后台应该返回一个成功的消息 让我们前端进行展示给用户
                        //如果失败了   同上
                        if (re.status == 200) {
                            layer.alert(re.message, {
                                icon: 1
                            }, function (index) {
                                layer.close(index)

                            })
                        } else {
                            layer.alert(re.message, {
                                icon: 2
                            }, function (index) {
                                layer.close(index)
                            })
                        }

                    }
                })

            });
        } else if (layEvent === 'edit') {
            layer.msg('编辑操作');
        }
    });

    //模糊查询操作
    $("#find").on("click", function () {
        //需要获取到输入框的值,然后请求后台,获取数据以后重新加载我们的数据表格
        table.reload('userdemo', {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            url: "/user/selectByLikeUsername",
            where: {//设置异步请求的参数
                "username": $("#username").val(),
                "useruid": $("#useruid").val(),
                "userphone": $("#userphone").val()
            }
        })
    })


    //视频上传操作
    //选完文件后不自动上传
    upload.render({
        //绑定选择文件的按钮(id)
        elem: '#chooseimg'
        //向后台发起请求的地址
        , url: '/user/uploadimg'
        //自动提交设置为false-->取消自动提交
        , auto: true
        , accept: 'img'
        //给上传的文件取名字  key(file)   -    value(视频文件)
        , field: 'uimg'
        //绑定我们上传按钮(id)
        , bindAction: '#uploadimg'
        //上传成功以后的回调函数
        , done: function (res) {
            //上传了文件以后 ,需要文件的一些基本信息
            if (res.status == 200) {
                //需要将上传成功以后的文件名称放在我们的input输入框中
                console.log($("#uimg"))

                $("#uimg").val(res.message)
            } else {
                layer.msg("网络延迟,上传失败!")
            }
        }
    });

    //提交表单内容
    $("#submit").on("click", function () {

        $.ajax({
            type: 'post',
            url: '/user/adduser',
            dataType: 'json',
            data: $("#form").serialize(),
            // succeess:function(res){
            //
            //
            //     parent.layer.alert(res.message,{
            //         icon:
            //     },function(){
            //                         //重新刷新我们的table表格数据
            //         window.parent.location.reload()
            //     })
            //
            // },

            success: function (res) {
                //在前端的控制台进行输出
                layer.alert(res.message, {
                    icon: 2
                }, function () {
                    //重新刷新我们的table表格数据
                    window.parent.location.reload()
                })
            }

        })
    })


    // //提交表单内容
    // $("#submit").on("click",function(){
    //     //为了将表单里面的file文件进行序列化 传到后台
    //     var form=  document.getElementById("form")
    //     //将我们的文本信息序列化的同时序列化我们的file文件  'file': 文件
    //     var fd  = new FormData(form)
    //
    //     $.ajax({
    //         type:'POST',
    //         url:'/user/adduser',
    //         dataType:'json',
    //         data: fd,
    //         //阻止jquery对文件数据进行操作,让我们服务器能够正常解析文件
    //         contentType:false,
    //         //取消jquery的ajax对我们的数据进行任何的序列化
    //         processData:false,
    //         success:function(res){
    //             //在前端的控制台进行输出
    //
    //             parent.layer.alert(res.message,{
    //                 icon:1
    //             },function(){
    //                 //重新刷新我们的table表格数据
    //                 window.parent.location.reload()
    //             })
    //         }
    //
    //     })
    // })


    //自定义验证规则
    var aa = form.verify({
        username: function (value, item) { //value：表单的值、item：表单的DOM对象
            if (!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)) {
                return '用户名不能有特殊字符';
            }
            if (/(^\_)|(\__)|(\_+$)/.test(value)) {
                return '用户名首尾不能出现下划线\'_\'';
            }
            if (/^\d+\d+\d$/.test(value)) {
                return '用户名不能全为数字';
            }


        }
        //我们既支持上述函数式的方式，也支持下述数组的形式
        //数组的两个值分别代表：[正则匹配、匹配不符时的提示文字]
        , pass: [
            /^[\S]{6,12}$/
            , '密码必须6到12位，且不能出现空格'
        ],
        // confirmPass:function(value){
        //     if($('input[name=password]').val() !== value)
        //         return '两次密码输入不一致！';
        // }
    });
    //提交编译以后的表单
    $("#subedit").on("click", function () {

        $.ajax({
            type: 'post',
            url: 'http://localhost:8080/work/user/toedituser',
            dataType: 'json',
            data: $("#form").serialize(),

            success: function (res) {
                //在前端的控制台进行输出
                layer.alert(res.message, {
                    icon: 1
                }, function () {
                    //重新刷新我们的table表格数据
                    window.parent.location.reload()
                })
            }

        })
    })


})
