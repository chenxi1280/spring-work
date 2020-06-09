function setImgInput(divImg) {
    let urls = '';
    divImg.parent().find("img").each(function () {
        let src = this.src;
        src = src.substring(src.indexOf('/upload/'));
        urls += src + ",";
    });
    urls = urls.substring(0, urls.length - 1);
    let uploadDiv = divImg.parent();
    let name = uploadDiv.attr("id");
    uploadDiv.children("input[name=" + name + "]").remove();
    divImg.before($("<input name='" + name + "' type='hidden' value='" + urls + "'/>"));
}

function appendImg(url, divImg) {
    let deleteBtn = $("<span style='color: orange;display: none;position: absolute;z-index: 1;left: 50%;top:50%;cursor: pointer;transform: translate(-50%,-50%)'>删除</span>")
    let img = $("<div style=\"width: 80px;height: 80px;padding: 5px 0;position: relative;margin-right: 5px;\">\n" +
        "                                                <img src='" + url + "' style=\"width: 100%;height: 100%;border-radius: 5px;border: solid  1px lightgrey;\"/>\n" +
        "                                            </div>");
    img.prepend(deleteBtn);
    img.hover(function () {
        deleteBtn.css({display: 'block'})
    }, function () {
        deleteBtn.css({display: 'none'})
    });
    deleteBtn.click(function () {
        img.remove();
        setImgInput(divImg);
    });
    divImg.before(img);
    setImgInput(divImg);
    divImg.siblings("input[type=file]")[0].value = '';
}

// 可以配置url（自定义上传文件路径）和dir（保存文件路径）
function uploadFile(options) {
    let url = "/upload/uploadFiles";
    $("div[lh-upload]").each(function () {

        let divId = this.id;
        let uploadDiv = $(this);
        uploadDiv.css({display: 'flex', 'flex-wrap': 'wrap', 'align-items': 'center'})
        let id = divId + '-File';
        let divImgId = divId + '-img';
        let fileInput = $("<input type='file' style='display: none' id='" + id + "'  multiple/>");
        let divImg = $("<div id='" + divImgId + "' style=\"width: 80px;height: 80px;border-radius: 5px;border: dashed 1px gray;cursor: pointer;display: flex;align-items: center;justify-content: center\">\n" +
            "                                                <span style=\"font-size: 35px;color: gray;user-select: none;\">+</span>\n" +
            "                                            </div>");
        uploadDiv.append(fileInput);
        uploadDiv.append(divImg);
        divImg.click(function () {
            fileInput.click();
        });
        let initValue = $(this).attr("value");// 这一句话是或div标签的属性的值

        if (initValue != undefined) {// 有值
            let arr = initValue.split(',');
            arr.forEach(url => {
                appendImg(url, divImg);
            })
        }
        fileInput.change(function () {// 直接开始上传文件
            let files = this.files;

            let formData = new FormData();
            for (let x = 0; x < files.length; x++) {
                formData.append("files" + x, files[x]);
            }
            if (options != undefined) {
                if (options.dir != undefined) {
                    formData.append("dir", options.dir);
                }
                if (options.url != undefined) {
                    url = options.url;
                }
            }
            $.ajax({
                url: url,// 请求数据服务器地址，只能是当前项目，JSONP才能访问外网（需要服务器配合才能用）
                contentType: false,// 默认就是表单数据(不写)，如果此时要传递的是json字符串，
                // 那么此时就用application/json,如果此时要传递图片"multipart/form-data，还有数组，集合，还有对象，那么contentType必须指定为false
                data: formData,// 要上传的参数
                dataType: 'json',// 服务器响应数据：json,text,html,xml
                error: function () {//请求错误的时候，会触发此函数

                },
                processData: false,//布尔值,一般都不用设置，规定通过请求发送的数据是否转换为查询字符串。默认是 true。如果此时上传的时候，有图片，这里必须设置false,
                success: function (res) {// 请求成功，回调函数,data，指的就是服务器返回的数据
                    if (res.res) {
                        let urls = res.data;
                        if (urls != '' && urls != null) {
                            if (urls.indexOf(",") > -1) {// 有逗号，表示多个图片
                                let arr = urls.split(',');
                                arr.forEach((url) => {
                                    appendImg(url, divImg);
                                })
                            } else {
                                appendImg(urls, divImg);
                            }
                        }
                    } else {
                        alert(res.msg)
                    }
                },
                type: 'post',// ajax请求的方式，post或get
            })


        })
    })
}

