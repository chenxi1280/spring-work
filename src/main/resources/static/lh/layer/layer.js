
$(function () {
    $("[lh-layer]").css({
        'position': 'absolute',
        'bottom': 0,
        'left': 0, 'top': 0,
        'right': 0,
        'background': 'rgba(0, 0, 0, 0.2)',
        'z-index ': 999,
        'display': 'none',

    });
    $("[lh-layer] > div").css({
        'background': 'white',
        'display': 'none',
        'border-radius': '5px 5px 0 0',
        'left': 0,
        'right': 0,
        'bottom': 0,
        'position': 'absolute'
    });
    $("[lh-layer]").click(function (e) {
        if(e.target==this){
            lhLayer($(this))
        }
    });
})
/**
 * 弹出层方法
 * @param Jobj jquery对象
 */

function lhLayer(Jobj) {
    Jobj.css({
        'display': '',
    });
    Jobj.children("div").slideToggle(function () {
        let p = $(this).parent()[0];
        p.style.display = this.style.display
    })
}
