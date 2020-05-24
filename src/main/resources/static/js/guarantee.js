layui.use(['table', 'upload', 'layer', 'laydate'], function () {
    //实例化layui模块 table-->数据表格模块  $ 表示我们的jquery模块
    var table = layui.table,
        $ = layui.jquery,
        upload = layui.upload,
        layer = layui.layer,
        form = layui.form,
        laydate = layui.laydate


    // $("")


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
                width: 100,
                fixed: 'left'
            }, {
                field: 'rname',
                title: '报修名字',
                align: 'center',
                edit: 'text',

            },
                //     {
                //     field: 'uimg',
                //     title: '照片',
                //     align: 'center',
                //     templet: function (d) {
                //         var path = d.uimg;
                //         console.log(path)
                //         //需要一个图片标签进行图片展示
                //         return "<img src='../" + path + "' style='width: 150px; height: 150px;'/>"
                //     }
                // },
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
                        console.log(d.rstate)
                        console.log(d)
                        if (d.rstate == 1) {
                            return '未处理';
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
            layer.open({
                type: 2,
                title: '查看',
                area: ['50%', '80%'],
                content: '/guarantee/seeguarantee' + '?rid=' + data.rid,

            })


        } else if (layEvent === 'del') {
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


        } else if (layEvent === 'edit') {
            layer.msg('编辑操作');
        }
    });
    // //
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
                "userName": $("#username").val(),
                "maintenanceusername": $("#maintenanceusername").val()
            }
        })
    });

    form.render('select');

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

    //
    //
    //
    // //提交编译以后的表单
    // $("#subedit").on("click", function () {
    //
    //     $.ajax({
    //         type: 'post',
    //         url: '/messages/toeditmessages',
    //         dataType: 'json',
    //         data: $("#form").serialize(),
    //         success: function (res) {
    //             //在前端的控制台进行输出
    //
    //             if (res.status == 200) {
    //                 layer.alert(res.message, {
    //                     icon: 1
    //                 }, function () {
    //                     //重新刷新我们的table表格数据
    //                     window.parent.location.reload()
    //                 })
    //             } else {
    //                 layer.alert(res.message, {
    //                     icon: 2
    //                 }, function () {
    //                     //重新刷新我们的table表格数据
    //                     window.parent.location.reload()
    //                 })
    //             }
    //
    //         }
    //
    //     })
    // })


})
