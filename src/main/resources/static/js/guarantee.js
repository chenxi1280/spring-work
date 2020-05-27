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
        url: '/guarantee/getguarantees',
        //表格的名称
        title: '报修管理',
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
                field: 'rid',
                title: 'ID',
                sort: true,
                fixed: 'left'
            }, {
                field: 'rname',
                title: '报修名字',
                align: 'center',
                edit: 'text',

            },
                {
                    field: 'rcontent',
                    title: '报修内容',
                    align: 'center',
                    // edit: 'text',
                },
                {
                field: 'raddress',
                title: '地址',
                align: 'center',

            },
                {
                    field: 'uphone',
                    title: '用户手机号码',
                    align: 'center',

                },
                {
                    field: 'maintenanceusername',
                    title: '维修人员名字',
                    align: 'center',

                },
            //     {
            //     field: 'aid',
            //     title: '管理员id',
            //
            //     align: 'center',
            //     // edit: 'text',
            // },
                {
                field: 'uid',
                title: '报修用户',

                align: 'center',
                // edit: 'text',
            }, {
                field: 'revaluation',
                title: '维修费用',
                width: 150,
                align: 'center',

            }, {
                field: 'rpublicdate',
                title: '创建时间',
                sort: true,
                align: 'center',
                templet: function (d) {

                    // date.setTime(d.ucreatedate);

                    // return date.Format("yyyy-MM-dd hh:mm:ss");


                    return showTime(d.rpublicdate);
                }
            },
                {
                    field: 'raccpetdate',
                    title: '修改时间',
                    sort: true,
                    align: 'center',
                    templet: function (d) {

                        // date.setTime(d.ucreatedate);

                        // return date.Format("yyyy-MM-dd hh:mm:ss");

                        return showTime(d.raccpetdate);
                    }
                },
                {
                    field: 'rcompletiondate',
                    title: '完成时间',
                    sort: true,
                    align: 'center',
                    templet: function (d) {

                        // date.setTime(d.ucreatedate);

                        // return date.Format("yyyy-MM-dd hh:mm:ss");

                        return showTime(d.rcompletiondate);
                    }
                },
                // {
                //     field: 'likeCounts',
                //     title: '点赞次数',
                //
                //     align:'center'
                // },
                {
                    field: 'rstate',
                    title: '状态',
                    align: 'center',
                    templet:function (d) {
                        if (d.rstate == 1) {
                            return '未处理';
                        }else if (d.rstate == 2) {
                            return '等待维修';
                        }else if (d.rstate == 3) {
                            return '维修完成';
                        }else if (d.rstate == 4) {
                            return '已完成';
                        }

                            // case '2':
                            //     return '等待维修';
                            // case '3':
                            //     return '维修完成';
                            // case '4':
                            //     return '已完成';
                        // }

                    }
                },
                {
                    field: 'rimg',
                    title: '图片',
                    templet: function (d) {
                        // 这个d指的是本行的数据 js对象
                        return '<div><img style="width: 100px;height: 100px" src=' + d.rimg + '/></div>'
                    }
                },
                {
                    fixed: 'right',
                    width: 165,
                    align: 'center',
                    toolbar: '#barDemo'
                }
            ]
        ]
    });

    // // //监听行工具事件
    table.on('tool(usertest)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值

        //查看
        if (layEvent === 'detail') {
            form.render();
            layer.open({
                type:1,
                title: '修改',
                area: ['50%', '80%'],
                content: $("#editguatan").html(),

            })
            form.render();
            form.render('select');

            $("#rname").val(data.rname)
            $("#rcontent").val(data.rcontent)
            // select 框回显数据
            $("#rtype").each(function() {
                // this代表的是<option></option>，对option再进行遍历
                $(this).children("option").each(function () {
                    // 判断需要对那个选项进行回显
                    if (this.value == 2) {
                        // 进行回显
                        $(this).attr("selected", "selected");
                        //重新渲染select框 重要
                        form.render('select');
                    }
                });
            })
            let skey = $("#rtype").val(data.rstate)
            if (data.rstate < 3){
                $.ajax({
                    async: false,                    //默认为true，默认为异步请求
                    type: "POST",                   //类型post
                    url: "/maintenanceuser/getmaintenanceuser",                 //url
                    contentType: "application/json",//请求内容编码类型
                    data: {},       //发送到服务器的数据
                    dataType: "json",               //返回数据格式
                    success: function(data){         //成功的方法
                        let list = data.item;
                        list.forEach( v =>{
                            if (v.maintenanceuseridstate == 1 ){
                                let maintenanceusername = $("<option value='"+v.maintenanceuserid+"'>"+v.maintenanceusername+"" +
                                    "</option>")
                                $("#maintenance").append(maintenanceusername)
                            }

                        })

                    }
                });
            }else {

                let maintenanceusername = $("<option value='"+data.maintenanceuserid+"' selected>"+data.maintenanceusername+"" +
                    "</option>")
                $("#maintenance").append(maintenanceusername)

            }

            form.render('select');
            $("#sureEdit")[0].rid = data.rid;
            console.log( data)
            $("#editUploadImg").attr("src", data.rimg);


        } else if (layEvent === 'edit') {
            $.ajax({
                type: "post",
                url: "/guarantee/chengeguarantee",
                dataType: 'json',
                data: {
                    //key     value
                    "rid": data.rid
                },
                traditional: true, //向后台传送数组的时候,必须申明为true
                success: function (re) {
                    //如果成功了   后台应该返回一个成功的消息 让我们前端进行展示给用户
                    //如果失败了   同上
                    console.log(re);
                    if (re.status == 200) {
                        layer.alert(re.message, {
                            icon: 1
                        }, function (index) {
                            layer.close(index)
                            window.location.reload()

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
        } else if (layEvent === 'del') {


                // $.ajax({
                //         async: true,                    //默认为true，默认为异步请求
                //         type: "POST",                   //类型post
                //         url: "/guarantee/completeguarantee",                 //url
                //         contentType: "application/json",//请求内容编码类型
                //         data: {"rid": data.rid},       //发送到服务器的数据
                //         dataType: "json",               //返回数据格式
                //         success: function(msg){         //成功的方法
                //             layer.msg('完成');
                //         }
                //     });

            // layer.msg('完成');
        }
    });

    // // //模糊查询操作
    $("#find").on("click", function () {
        //需要获取到输入框的值,然后请求后台,获取数据以后重新加载我们的数据表格
        table.reload('userdemo', {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            url: "/guarantee/selectByLikeguarantee",
            where: {//设置异步请求的参数
                "uphone": $("#uphone").val(),
                "uname": $("#uname").val(),
                "maintenanceusername": $("#maintenanceusername").val(),
                "rstate":$("#itype").val()
            }
        })
    });

    form.render('select');

    form.on("submit(sureEdit)",function (data) {

        let formData = new FormData(data.form)

        formData.append("rid", $("#sureEdit")[0].rid);

        $.ajax({
            async: true,                    //默认为true，默认为异步请求
            type: "POST",                   //类型post
            url: "/guarantee/toeditguarantee",                 //url
            contentType: false,              //请求内容编码类型
            data: formData,       //发送到服务器的数据
            dataType: "json",               //返回数据格式
            processData: false,//布尔值,一般都不用设置，规定通过请求发送的数据是否转换为查询字符串。默认是 true。如果此时上传的时候，有图片，这里必须设置false,
            success: function(msg){         //成功的方法

                console.log(msg)

                if (msg.status == 200) {
                    layer.alert(msg.message, {
                        icon: 1
                    }, function () {
                        //重新刷新我们的table表格数据
                        window.location.reload()
                    })
                } else {
                    layer.alert(msg.message, {
                        icon: 2
                    }, function () {
                        //重新刷新我们的table表格数据
                        window.location.reload()
                    })
                }
            }
        });
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })

    //add
    form.on("submit(subadd)",function (data) {
        let formData = new FormData(data.form);


        $.ajax({

            contentType: false,// 默认就是表单数据(不写)，如果此时要传递的是json字符串，
            url: '/guarantee/ajaxaddguarantee',
            dataType: 'json',
            data: formData,
            type: 'post',// ajax请求的方式，post或get
            processData: false,//布尔值,一般都不用设置，规定通过请求发送的数据是否转换为查询字符串。默认是 true。如果此时上传的时候，有图片，这里必须设置false,
            success: function (res) {
                //在前端的控制台进行输出
                console.log(res)

                if (res.status == 200) {

                    layer.alert(res.message, {
                        icon: 1
                    }, function () {
                        //重新刷新我们的table表格数据
                        window.parent.location.reload()
                    })
                } else {
                    layer.alert(res.message, {
                        icon: 2
                    }, function () {
                        //重新刷新我们的table表格数据
                        window.location.reload()
                    })
                }
            }
        })
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })

    //提交表单内容
    $("#submit").on("click", function () {

        $.ajax({
            type: 'POSt',
            url: '/messages/addmessages',
            dataType: 'json',
            data: $("#form").serialize(),
            success: function (res) {
                //在前端的控制台进行输出


                if (res.status == 200) {
                    layer.alert(res.message, {
                        icon: 1
                    }, function () {
                        //重新刷新我们的table表格数据
                        window.location.reload()
                    })
                } else {
                    layer.alert(res.message, {
                        icon: 2
                    }, function () {
                        //重新刷新我们的table表格数据
                        window.location.reload()
                    })
                }

            }


        })
    })






    //监听图片
    $("#uploadImg").on("click",function () {
        $("#rimg").click();
    })
    $("#rimg").change(function () {
        imgPreview(this.files[0],$("#uploadImg")[0])
    })



    //回显 图片
    function imgPreview(file, imgDom) {
        //判断是否支持FileReader
        if (window.FileReader) {
            var reader = new FileReader();
        } else {
            alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
        }
        var imageType = /^image\//;
        //是否是图片
        if (!imageType.test(file.type)) {
            alert("请选择图片！");
            return;
        }
        //读取完成
        reader.onload = function (e) {
            //获取图片dom
            //图片路径设置为读取的图片
            imgDom.src = e.target.result;

        };
        reader.readAsDataURL(file);
    }

})
