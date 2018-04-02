/**
 * Created by xianchuanwu on 2017/9/23.
 */
Vue.use(ELEMENT);
//BASE
var baseData = solar.baseData;
var baseMethods = solar.baseMethods;
var copyObject = solar.copyObject;
var checkboxModelWatch = solar.checkboxModelWatch;
var baseHost = '';
// 如果baseHost未指定 则取当前默认url host
if (baseHost == "") {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPath = curWwwPath.substring(0, pos);
    baseHost = localhostPath;
}
var Config = {
    homeIndexTemplatesName: [
        "advertiserAccountLog-advertiserAccountLog",
        "advertiserAccountLog-advertiserAccountLogPage",
        "advertiserAccountVirtualLog-advertiserAccountVirtualLog",
        "advertiserAccountVirtualLog-advertiserAccountVirtualLogPage",
        "advertiserCampaign-advertiserCampaign",
        "advertiserCampaign-advertiserCampaignPage",
        "advertiserCreative-advertiserCreative",
        "advertiserCreative-advertiserCreativePage",
        "advertiserInfo-advertiserInfo",
        "advertiserInfo-advertiserInfoPage",
        "advertiserQualifications-advertiserQualifications",
        "advertiserQualifications-advertiserQualificationsPage",
        "campaignCreativeReport-campaignCreativeReport",
        "campaignCreativeReport-campaignCreativeReportPage",
        "campaignCreativeRealtimeReport-campaignCreativeRealtimeReport",
        "campaignCreativeRealtimeReport-campaignCreativeRealtimeReportPage",
        "mediaBankCard-mediaBankCard",
        "mediaBankCard-mediaBankCardPage",
        "mediaOwnerInfo-mediaOwnerInfo",
        "mediaOwnerInfo-mediaOwnerInfoPage",
        "mediaOwnerReport-mediaOwnerReport",
        "mediaOwnerReport-mediaOwnerReportPage",
        "templateManage-templateManage",
        "templateManage-templateManagePage",
        "templateReport-templateReport",
        "templateReport-templateReportPage",
        "myindex-myindex",
    ],
    templateUrl: "template_admin",
    templateFileSuffix: ".html?v=0309"
}
// 定义路由 - 组件
var templates = {};
var routes = [ {path: '/', redirect: 'advertiserInfo/advertiserInfoPage'}];
//模版加载 必须同步 不然templateName变量混乱 以及后面VUE 启动找不到组件
for (var i = 0; i < Config.homeIndexTemplatesName.length; i++) {
    $.ajax({
        type: "get",
        url: Config.templateUrl + "/" + Config.homeIndexTemplatesName[i].replace("-", "/") + Config.templateFileSuffix,
        async: false,
        success: function (html) {
            $("#templates").append(html);
            var obj = {};
            obj.path = "/" + Config.homeIndexTemplatesName[i].replace("-", "/");
            obj.component = templates[Config.homeIndexTemplatesName[i]];
            routes.push(obj);
        }
    });
}

/**
 * Created by xianchuanwu on 2017/9/23.
 */
Vue.filter('time', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD HH:mm:ss';
    if (value == "" || !value) {
        return "";
    }
    return moment(value).format("YYYY-MM-dd HH:mm:ss");
});
Vue.filter('formatMoney', function (s, n) {
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
});
Vue.filter('toThousands', function (num) {
    var num = (num || 0).toString(), re = /\d{3}$/, result = '';
    while ( re.test(num) ) {
        result = RegExp.lastMatch + result;
        if (num !== RegExp.lastMatch) {
            result = ',' + result;
            num = RegExp.leftContext;
        } else {
            num = '';
            break;
        }
    }
    if (num) { result = num + result; }
    return result;
});
// 3. 创建 router 实例，然后传 `routes` 配置  还可以传别的配置参数
const router = new VueRouter({
    routes: routes // （缩写）相当于 routes: routes
});

function setAjaxHeader(name, val) {
    $.ajaxSetup({
        //发送请求前触发
        beforeSend: function (XMLHttpRequest) {
            //可以设置自定义标头
            XMLHttpRequest.setRequestHeader(name, val);
        }
    })
}
if (typeof(localStorage.token) != "undefined") {
    setAjaxHeader("token", localStorage.token);
}
var windowHeight = window.innerHeight - 20;
$("body").height(windowHeight);
$("#menu-nav").height(windowHeight - 47);