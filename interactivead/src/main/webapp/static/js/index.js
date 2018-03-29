/**
 * Created by xianchuanwu on 2017/9/23.
 */
Vue.filter('time', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD HH:mm:ss';
    if (value == "" || !value) {
        return "";
    }
    return moment(value).format(formatString);
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
    routes // （缩写）相当于 routes: routes
})
// 4. 创建和挂载根实例。
// 记得要通过 router 配置参数注入路由，
// 从而让整个应用都有路由功能
const app = new Vue({
    el: "#app",
    router: router,
    data: {
        openModuleName: "nrgl",
        activeName: "",
        account: localStorage.account,
        password:"",
        today: new Date().Format("yyyy年-MM月-dd日"),
        showLoginView: false
    },
    methods: {
        onModuleNameClick: function (val) {
            if (val == this.openModuleName) {
                app.openModuleName = '';
            } else {
                app.openModuleName = val;
            }
        },
        chcekLogin: function () {
            var isLogin = true;
            var _this = this;
            $.ajax({
                type: "get",
                url: baseHost + "/isLogin?t=u",
                async: false,
                success: function (data) {
                    if (data.code != 200) {
                        // layer.alert("请先登录!", {icon: 6}, function (index) {
                        //     layer.close(index);
                        //
                        // });
                        _this.showLoginView = true;
                        isLogin = false;
                        location="login.html";
                    }
                }
            });
            return isLogin;
        },
        login: function () {
            var _this = this;
            $.ajax({
                type: "post",
                url: baseHost + "/login",
                data: {account:this.account,password:this.password},
                async: false,
                success: function (data) {
                    if (data.code == 200) {
                        layer.alert("登录成功!", {icon: 6}, function (index) {
                            layer.close(index);
                            _this.showLoginView = false;
                        });
                        setAjaxHeader("token", data.msg);
                        localStorage.token = data.msg;
                        localStorage.account = _this.account;
                        location="/";
                    }else {
                        layer.alert(data.msg, {icon: 5}, function (index) {
                            layer.close(index);
                        });
                    }
                }
            });
        },
        loginOut: function () {
            var _this = this;
            $.ajax({
                type: "post",
                url: baseHost + "/loginOut",
                data: {},
                async: false,
                success: function (data) {
                    if (data.code == 200) {
                        layer.alert(data.msg, {icon: 6});
                        location="/";
                        _this.showLoginView = true;
                    }
                }
            });
            localStorage.token = '';
        }
    },
    mounted: function () {
        if (typeof(localStorage.token) != "undefined") {
            setAjaxHeader("token", localStorage.token);
        }
        var isLogin = this.chcekLogin();
        if (!isLogin) {
            this.showLoginView = true;
            return;
        }
        this.account=localStorage.account;
        var url = location.href;
        var path = url.split("#")[1];
        var path2 = path.split("/")[2];
        path = path.split("/")[1];

        if (!path) {
            return;
        }
        if (path == "dspMediaInfo" || path == "dspMediaAdSiteInfo" || path == "project" || path == "company"
            || path == "investEvent" || path == "product"|| path == "goMemberAccount"|| path == "sysUser") {
            this.openModuleName = "zygl";
        } else if (path == "sspMediaExpendStatement" || path == "user" || path == "userHistory" || path == "store") {
            this.openModuleName = "tjbb";
        } else if (path == "sspPtAppOwnerBankCardInfo" || path == "sspAppOwnerAccountDetail"
            || path == "destoonFinanceCash" || path == "dict" ){
            this.openModuleName = "cwgl";
        } else {
            this.openModuleName = "zhgl";
        }
        if (path == "financial" && path2 == "overview"  ){
            this.openModuleName = "index";
        }
        // alert (path2);
        this.activeName = path;
    }
});
//app.$mount('#app')
