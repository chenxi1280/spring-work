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
        url: '/messages/getmessages',
        //表格的名称
        title: '公告信息',
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
                field: 'iid',
                title: 'ID',
                sort: true,
                width: 100,
                fixed: 'left'
            }, {
                field: 'iname',
                title: '消息名字',
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
                    field: 'icontent',
                    title: '消息内容',
                    align: 'center',
                    // edit: 'text',
                }, {
                field: 'xid',
                title: '小区id',
                align: 'center',
                edit: 'text',

            }, {
                field: 'acid',
                title: '创建管理员id',

                align: 'center',
                // edit: 'text',
            }, {
                field: 'auid',
                title: '修改消息管理员id',

                align: 'center',
                // edit: 'text',
            }, {
                field: 'itype',
                title: '消息类型',
                width: 150,
                align: 'center',
                templet: function (v) {


                    if (v.itype == 1) {
                        return '全体业主'
                    } else if (v.itype == 2) {
                        return '全体管理人员';
                    } else {
                        return '全体人员';
                    }
                }

            }, {
                field: 'icreatedate',
                title: '创建时间',
                sort: true,
                align: 'center',
                templet: function (d) {

                    // date.setTime(d.ucreatedate);

                    // return date.Format("yyyy-MM-dd hh:mm:ss");
                    console.log(d)

                    return showTime(d.icreatedate);
                }
            },
                {
                    field: 'iupdatedate',
                    title: '修改时间',
                    sort: true,
                    align: 'center',
                    templet: function (d) {

                        // date.setTime(d.ucreatedate);

                        // return date.Format("yyyy-MM-dd hh:mm:ss");

                        return showTime(d.iupdatedate);
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
    //
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

            ids[i] = data[i].iid;
        }

        switch (obj.event) {
            case 'add':
                layer.open({
                    type: 2,
                    title: '新建用户',
                    area: ['50%', '80%'],
                    content: '/messages/addmessageshtml'
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
                            url: "/messages/delmessages",
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
    //
    // //监听单元格编辑
    // table.on('edit(usertest)', function (obj) {
    //     var value = obj.value //得到修改后的值
    //         , data = obj.data //得到所在行所有键值
    //         , field = obj.field; //得到字段
    //     //update video set video_path="1"  where id="001"
    //     layer.confirm('确定修改', function () {
    //         $.ajax({
    //             type: "post",
    //             url: "/user/updateUser ",
    //             dataType: "json",
    //             data: {
    //                 "uid": data.uid,
    //                 "field": field,
    //                 "value": value
    //             },
    //             success: function (re) {
    //
    //                 layer.msg(re.message)
    //             }
    //         })
    //
    //     })
    //
    //
    // });
    //
    // //监听行工具事件
    table.on('tool(usertest)', function (obj) { //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        var data = obj.data //获得当前行数据
            , layEvent = obj.event; //获得 lay-event 对应的值


        //查看
        if (layEvent === 'detail') {
            layer.open({
                type: 2,
                title: '查看编辑公告',
                area: ['50%', '80%'],
                content: '/messages/geteditmessage' + '?iid=' + data.iid,

            })


        } else if (layEvent === 'del') {
            layer.confirm('真的删除行么', function (index) {
                obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令

                $.ajax({
                    type: "post",
                    url: "/messages/delmessages",
                    dataType: 'json',
                    data: {
                        //key     value
                        "ids": data.iid
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
    //
    // //模糊查询操作
    $("#find").on("click", function () {
        //需要获取到输入框的值,然后请求后台,获取数据以后重新加载我们的数据表格
        table.reload('userdemo', {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            url: "/messages/selectByLikemessages",
            where: {//设置异步请求的参数
                "username": $("#username").val(),
                "useruid": $("#useruid").val(),
                "itype": $("#itype").val()
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
    // //提交表单内容
    // $("#submit").on("click", function () {
    //
    //     $.ajax({
    //         type: 'post',
    //         url: '/user/adduser',
    //         dataType: 'json',
    //         data: $("#form").serialize(),
    //         // succeess:function(res){
    //         //
    //         //
    //         //     parent.layer.alert(res.message,{
    //         //         icon:
    //         //     },function(){
    //         //                         //重新刷新我们的table表格数据
    //         //         window.parent.location.reload()
    //         //     })
    //         //
    //         // },
    //
    //         success: function (res) {
    //             //在前端的控制台进行输出
    //             layer.alert(res.message, {
    //                 icon: 2
    //             }, function () {
    //                 //重新刷新我们的table表格数据
    //                 window.parent.location.reload()
    //             })
    //         }
    //
    //     })
    // })
    //
    //
    // // //提交表单内容
    // // $("#submit").on("click",function(){
    // //     //为了将表单里面的file文件进行序列化 传到后台
    // //     var form=  document.getElementById("form")
    // //     //将我们的文本信息序列化的同时序列化我们的file文件  'file': 文件
    // //     var fd  = new FormData(form)
    // //
    // //     $.ajax({
    // //         type:'POST',
    // //         url:'/user/adduser',
    // //         dataType:'json',
    // //         data: fd,
    // //         //阻止jquery对文件数据进行操作,让我们服务器能够正常解析文件
    // //         contentType:false,
    // //         //取消jquery的ajax对我们的数据进行任何的序列化
    // //         processData:false,
    // //         success:function(res){
    // //             //在前端的控制台进行输出
    // //
    // //             parent.layer.alert(res.message,{
    // //                 icon:1
    // //             },function(){
    // //                 //重新刷新我们的table表格数据
    // //                 window.parent.location.reload()
    // //             })
    // //         }
    // //
    // //     })
    // // })
    //


    //提交编译以后的表单
    $("#subedit").on("click", function () {

        $.ajax({
            type: 'post',
            url: '/messages/toeditmessages',
            dataType: 'json',
            data: $("#form").serialize(),
            success: function (res) {
                //在前端的控制台进行输出

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
                        window.parent.location.reload()
                    })
                }

            }

        })
    })


})
