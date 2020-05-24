var province = $('#province')
var city=$('#city')
var district = $('#district')

$.ajax({

    url: 'http://localhost:8080/date/getcity',  // 请求的地址
    async: true,
    type: 'get',
    // contentType : 'pplication/json',
    dataType: "json",
    success: function (_data) {
        console.log(_data)
        _data.list.forEach((v) => {
            console.log(v.name)

            let  a = $("<option>" + v.name+" </option>")
            console.log(a)
            province.append(a)
        })

    },
})

// province.change(function () {
//
//     city.empty()
//     district.empty()
//     district.append($('<option > 请选择地区 </option>'))
//     city.append("<option > 请选择城市</option>")
//
//     $.ajax({
//
//         url:   'http://localhost:8080/javawork/ajax',
//         async: true,
//         type: 'get',
//         data: {method: 'getCity',cid :province.val()},
//         dataType: 'json',
//         success:function (_data) {
//
//             _data.forEach(v => {
//
//                 city.append("<option value='"+ v.id+ "'>" + v.name+" </option>")
//             })
//         }
//
//     })
// })
// city.change(function () {
//     district.empty()
//     district.append($('<option > 请选择地区 </option>'))
//
//     $.ajax({
//
//         url:'http://localhost:8080/javawork/ajax',
//         type: "GET",
//         data:{method:'getDistrict',pid:city.val()},
//         async: true,
//         dataType: 'json',
//         success: function (_data) {
//             _data.forEach( v => {
//                 district.append($('<option value="'+v.id+'" > '+ v.name+' </option>'))
//             })
//         }
//
//     })
//
// })
