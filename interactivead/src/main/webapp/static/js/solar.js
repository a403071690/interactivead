//扩展Date
Date.prototype.Format = function (fmt) { //author: meizz
    if (!fmt) {
        fmt = "yyyy-MM-dd HH:mm:ss";
    }
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "H+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
//扩展字符串startsWith 方法
window.onload=function() {
    String.prototype.startsWith = function(str) {
        var reg = new RegExp("^" + str);
        return reg.test(this);
    }
    String.prototype.endsWith = function(str) {
        var reg = new RegExp(str + "$");
        return reg.test(this);
    }
};
//solar 框架  代理+常用工具包+常用代码段
var solar = {};
solar.windowWith = window.innerWidth - 165;
solar.windowHeight = window.innerHeight - 10;
solar.formatObjectTime = function (obj) {
    //遍历json对象的每个key/value对,p为key
    for (var key in obj) {
        if (key.endsWith("Time") || key == 'gmtCreate' || key == 'gmtModified' || key.endsWith("Date") || key.endsWith("birthday")) {
            if (!isNaN(obj[key]) && obj[key] > 123456789) {//如果为数字的
                obj[key] = new Date(obj[key]).Format("yyyy-MM-dd HH:mm:ss");
            }
        }
    }
    return obj;
}
//BASE
solar.copyObject = function (source) {
    if (!source || source == null) {
        return null;
    }
    return JSON.parse(JSON.stringify(source));
}
solar.getUrlParam = function (name) {

    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");

    var r = window.location.search.substr(1).match(reg);

    if (r != null) return decodeURI(r[2]);
    return null;

}
solar.deleteUrlParam = function (name) {
    var loca = window.location;
    var baseUrl = loca.origin + loca.pathname + "?";
    var query = loca.search.substr(1);
    if (query.indexOf(name) > -1) {
        var obj = {}
        var arr = query.split("&");
        for (var i = 0; i < arr.length; i++) {
            arr[i] = arr[i].split("=");
            obj[arr[i][0]] = arr[i][1];
        }
        ;
        delete obj[name];
        var url = baseUrl + JSON.stringify(obj).replace(/[\"\{\}]/g, "").replace(/\:/g, "=").replace(/\,/g, "&");
        return url
    }
    ;
}
solar.httpHeaders = {};
solar.httpGet = function (url, fun, async) {
    var ajax = new XMLHttpRequest();
    if (!async) {
        async = true;
    }
    ajax.open('get', url, async);
    for (var key in solar.httpHeaders) {
        ajax.setRequestHeader(key, solar.httpHeaders[key]);
    }
    ajax.send();
    ajax.onreadystatechange = function () {
        if (ajax.readyState == 4 && ajax.status == 200) {
            fun(JSON.parse(ajax.responseText));
        }
    }
}

solar.post = function (url, body) {
    $.ajaxSettings.async = false;
    var result;
    $.post(url, body, function (data) {
        if (data.code == 200) {
            layer.alert(data.msg, {icon: 6});
        } else {
            layer.alert(data.msg, {icon: 5});
        }
        result = data;
    });
    $.ajaxSettings.async = true;
    return result;
},

solar.httpPost = function (url, data, fun) {
    var ajax = new XMLHttpRequest();
    ajax.open('post', url);
    ajax.setRequestHeader("Content-Type", "application/json");
    for (var key in solar.httpHeaders) {
        ajax.setRequestHeader(key, solar.httpHeaders[key]);
    }
    ajax.send(JSON.stringify(data));
    ajax.onreadystatechange = function () {
        if (ajax.readyState == 4 && ajax.status == 200 && fun) {
            fun(ajax.responseText);
        }
    }
}
//是否为微信浏览器
solar.isWechatBrower = function () {
    var userAgent = navigator.userAgent.toLowerCase();
    if (userAgent.match(/MicroMessenger/i) == 'micromessenger') {
        return true;
    }
    return false;
}
//获取地区
solar.getAreaTree = function () {
    if (typeof(localStorage.areaTree) == "undefined" || localStorage.areaTree == "") {
        $.ajax({
            type: "get",
            url: baseHost + "/areaTree",
            async: false,
            success: function (data) {
                localStorage.areaTree = JSON.stringify(data.body);
            }
        });
    }
    this.areaTree = JSON.parse(localStorage.areaTree);
    return JSON.parse(localStorage.areaTree);
}
//获取4菜单的前3级（主要给element 组件用）
solar.getAreaTreeContainChildList= function () {
    var areaTree=solar.getAreaTree();
    for (i in areaTree) {
        for (j in areaTree[i].childList) {
            for (k in areaTree[i].childList[j].childList) {
                areaTree[i].childList[j].childList[k].childList=[];
            }
        }
    }
    return areaTree;
}
//按3位加逗号格式化金钱数据
solar.formatMoney = function (s, n) {
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
}
//在微信或支付宝中获取token
solar.getToken = function () {
    if (typeof(localStorage.token) != "undefined" && localStorage.token != ""
        && localStorage.tokenVersion != "" && localStorage.tokenVersion > 1520821502000) {
        return localStorage.token;
    }
    var token = solar.getUrlParam("token");
    if (token != null && token != "") {
        localStorage.token = token;
        localStorage.tokenVersion = new Date().getTime();
        location = solar.deleteUrlParam("token");
        return localStorage.token;
    }
    if (solar.isWechatBrower()) {
        location = "/wechatLogin?url=" + window.location.href;
    } else {
        location = "/alipayLogin?url=" + window.location.href;
    }
    return localStorage.token;
}
//判断是否在时间范围内
solar.inTimeRange = function (beginTime, endTime, nowTime) {
    var strb = beginTime.split(":");
    if (strb.length != 2) {
        return false;
    }

    var stre = endTime.split(":");
    if (stre.length != 2) {
        return false;
    }

    var b = new Date();
    var e = new Date();
    var n = new Date();
    if (nowTime) {
        n = nowTime;
    }

    b.setHours(strb[0]);
    b.setMinutes(strb[1]);
    e.setHours(stre[0]);
    e.setMinutes(stre[1]);

    if (n.getTime() - b.getTime() >= 0 && n.getTime() - e.getTime() <= 0) {
        return true;
    } else {
        return false;
    }
}
//请用 NumberObject.toFixed(num)可把 Number 四舍五入为指定小数位数的数字。默认为整数
solar.returnFloat = function (value) {
    return value.toFixed(2);
}
//加载转圈动画
solar.loading = function () {
    if (typeof(layer) != "undefined") {
        return layer.load(1);
    }
}
//加载完成 取消加载动画
solar.loadComplete = function () {
    if (typeof(layer) != "undefined") {
        return layer.closeAll('loading');
    }
}
//弹出信息框
solar.alert = function (msg, icon, fun) {
    if (typeof(layer) != "undefined") {
        return layer.alert(msg, icon, fun);
    } else {
        return alert(msg);
    }
}
//弹出信息
solar.msg = function (msg) {
    if (typeof(layer) != "undefined") {
        layer.msg(msg);
    } else {
        alert(msg);
    }
}
//弹出提示
solar.tips = function (msg, selector) {
    if (typeof(layer) != "undefined") {
        layer.tips(msg, selector);
    } else {
        alert(msg);
    }
}
//弹出输入框
solar.prompt = function (title, fun) {
    if (typeof(layer) != "undefined") {
        layer.prompt({title: title, formType: 3}, function (inputVal, index) {
            layer.close(index);
            if(fun){
                fun(inputVal, index)
            }
            return inputVal;
        });
    } else {
        alert(title);
    }
}
//关闭浏览器窗口
solar.closeWindow = function () {
    if (typeof(WeixinJSBridge) != "undefined") {
        WeixinJSBridge.invoke('closeWindow', {}, function (res) {
        });
    } else if (typeof(AlipayJSBridge) != "undefined") {
        AlipayJSBridge.call('closeWebview');
    } else {
        window.location.href = "about:blank";
        window.close();
    }
}
//判断照片文件大小 obj为input js元素 size单位为KB
solar.checkPhoto = function (fileId, size) {
    var obj = document.getElementById(fileId);
    var photoExt = obj.value.substr(obj.value.lastIndexOf(".") + 1).toLowerCase();//获得文件后缀名
    if (photoExt != 'jpg' && photoExt != 'png' && photoExt != 'gif' && photoExt != 'jpeg') {
        console.log(photoExt);
        alert("图片格式不对");
        return false;
    }
    var fileSize = 0;
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
    if (isIE && !obj.files) {
        var filePath = obj.value;
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        var file = fileSystem.GetFile(filePath);
        fileSize = file.Size;
    } else {
        fileSize = obj.files[0].size;
    }
    fileSize = Math.round(fileSize / 1024 * 100) / 100; //单位为KB
    if (fileSize > size) {
        alert("照片最大尺寸为" + (size / 1000) + "MB，请重新上传!" + fileSize + "kb");
        obj.value = null;
        return false;
    }
    return true;
}
//预览图片
solar.preview = function (fileId, imgId) {
    var file = document.getElementById(fileId);
    if (file.files && file.files[0]) {
        var reader = new FileReader();
        reader.onload = function (evt) {
            $("#" + imgId).attr("src", evt.target.result);
        }
        reader.readAsDataURL(file.files[0]);
    }
}
//上传文件
solar.uploadFile = function (url, fileInputId, callback) {
    var fd = new FormData();
    fd.append("fileToUpload", document.getElementById(fileInputId).files[0]);
    var xhr = new XMLHttpRequest();
    //xhr.upload.addEventListener("progress", uploadProgress, false);//上传进度
    xhr.addEventListener("load", callback, false);
    xhr.open("POST", url);
    xhr.send(fd);
}
//上传进度
solar.uploadProgress = function (evt) {
    if (evt.lengthComputable) {
        var percentComplete = Math.round(evt.loaded * 100 / evt.total);
        document.getElementById('progressNumber').innerHTML =
            percentComplete.toString() + "%<div style='height:5px;background-color:red;width:" + percentComplete.toString() + "%'></div>";
    }
    else {
        document.getElementById('progressNumber').innerHTML = 'unable to compute';
    }
}
solar.uploadImgFile = function (fileId, imgPreviewId, vId) {
    var _this = this;
    if (!checkPhoto(fileId, 10000)) {
        alert("请选择正确图片!");
        return;
    }
    ;

    function uploadImgFileCallBack(evt) {
        /* 服务器端返回响应时候触发event事件*/
        var data = JSON.parse(evt.target.responseText);
        if (data.code == 200) {
            _this.bean[vId] = data.msg;
            preview(fileId, imgPreviewId);
        } else {
            alert(data.msg);
        }
    }

    solar.uploadFile("/imgUpload", fileId, uploadImgFileCallBack);
}
solar.checkboxModelWatch = {
    handler: function (val, oldVal) {
        if (this.checkboxModel.length === this.beans.length) {
            this.checked = true;
        } else {
            this.checked = false;
        }
        if (this.beans.length == 0) {
            this.checked = false;
        }
    },
    deep: true
}
solar.codeToTreeArray =function (code) {
    if(!code){
        return [];
    }
    var treeArray=[];
    for (i = 3; i <= code.length; i=i+3) {
        treeArray.push(code.substring(0,i))
    }
    return treeArray;
}
solar.formatObjectCodeToArray = function (obj) {
    //遍历json对象的每个key/value对,p为key
    for (var key in obj) {
        if (key.endsWith("Code")) {
                obj[key+"Array"] = solar.codeToTreeArray(obj[key]);
        }
    }
    return obj;
}
//验证
solar.checkIsNumber = function (selector) {
    solar.tips('只能输入数字', selector);
    return;
}
solar.validatorRules = {
    name: [
        {required: true, message: '请输入名称', trigger: 'blur'},
        {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
    ],
    legalRepresentative: [
        {required: true, message: '请输入名字', trigger: 'blur'},
        {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
    ],
    areaCode: [
        {required: true, message: '请选择地区', trigger: 'blur'},
    ],
    wechatNum: [
        {required: true, message: '请输入微信号', trigger: 'blur'},
    ],
    alipayNum: [
        {required: true, message: '请输入支付宝账号(登录邮箱或登录手机号)', trigger: 'blur'},
    ],
    mobileNo: [
        {
            required: true, message: '请输入手机号',
            trigger: ['blur']
        },
        {min: 6, max: 20, message: '长度在 6 到 20', trigger: 'blur'}
    ],
    address: [
        {
            required: true, message: '请输入地址',
            trigger: ['blur']
        },
        {min: 2, max: 50, message: '长度在 1 到 50', trigger: 'blur'}
    ],
    mobileNoCode: [
        {
            required: true, message: '请输入验证码',
            trigger: ['blur']
        },
        {min: 4, max: 4, message: ' ', trigger: 'blur'}
    ],
    chargeRate: [
        {
            required: true, min: 1, max: 5, message: '请输入正确核销手续率',
            trigger: ['blur']
        }
    ],
    password: [
        {required: true, message: '请输入密码!', trigger: ['change', 'blur']},
        {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
    ],
    password2: [
        {required: true, message: '请输入密码!', trigger: ['change', 'blur']},
        {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
    ],
    date1: [
        {type: 'date', required: true, message: '请选择日期', trigger: 'change'}
    ],
    date2: [
        {type: 'date', required: true, message: '请选择时间', trigger: 'change'}
    ],
    email: [
        {type: 'email', required: true, message: '请至少选择一个活动性质', trigger: 'change'}
    ],
}
//VUE BASE
//默认Data
solar.baseData = {
    beans: [],
    fullTextSearchValue: "",
    totalPageNum: 0,
    temp: {},
    param: {},
    totalRecordNum: 0,
    pageNum: 1,
    listPageNum: 1,
    pageSize: 10,
    orderProperty: "id",
    orderDirection: "desc",
    checkboxModel: [],
    checked: false,
}
//默认方法
solar.baseMethods = {
    selectPage: function (pageSize) {
        if(pageSize){
            this.pageSize=pageSize;
        }
        this.beans = [];
        solar.loading();
        var _this = this;
        if (this.pageNum < 1) {
            this.pageNum = 1;
        }
        if (this.pageNum > this.totalPageNum && this.totalPageNum >= 1) {
            this.pageNum = this.totalPageNum;
        }
        var url = baseHost + "/" + this.beanName + "/select?pageNum=" + this.pageNum;
        url = url + "&pageSize=" + this.pageSize;
        if (this.fullTextSearchValue != "") {
            url = url + "&fullTextSearchValue=%25" + this.fullTextSearchValue + "%25";
        }
        if (this.orderProperty != "") {
            url = url + "&orderProperty=" + this.orderProperty + "&orderDirection=" + this.orderDirection;
        }
        $.ajax({
            type: 'GET',
            url: url,
            data: _this.param,
            success: function (data) {
                solar.loadComplete();
                _this.beans = data.body.list;
                _this.checkboxModel = [];
                _this.totalPageNum = data.body.totalPage;
                _this.listPageNum = data.body.pageable.pageNumber;
                _this.totalRecordNum = data.body.totalRecord;
                _this.pageSize = data.body.pageable.pageSize;

            }
        });
    },
    checkedAll: function () {
        var _this = this;
        if (!this.checked) {//实现全选
            _this.checkboxModel = [];
            _this.beans.forEach(function (bean) {
                _this.checkboxModel.push(bean.id);
            });
        } else {//实现反选
            _this.checkboxModel = [];
        }
    },
    deleteBean: function (id) {
        var _this = this;
        layer.confirm('确认删除?', function () {
                $.post(baseHost + "/" + _this.beanName + "/delete", {id: id}, function (data) {
                    layer.alert(data.msg + "(已删除:" + data.body + "条)", {icon: 6});
                    _this.select();
                });
            }
        );
    },
    deleteBeanGoBack: function (id) {
        var _this = this;
        layer.confirm('确认删除?', function () {
                $.post(baseHost + "/" + _this.beanName + "/delete", {id: id}, function (data) {
                    layer.alert(data.msg + "(已删除:" + data.body + "条)", {icon: 6}, function (index) {
                        layer.close(index);
                        history.back();
                    });
                });
            }
        );
    },
    deleteBeans: function (list) {
        if (list.length == 0) {
            layer.alert("请选择需要删除的数据!", {icon: 5});
            return;
        }
        var _this = this;
        layer.confirm('确认删除?', function () {
            var deleteRows = 0;
            for (var i = 0; i < list.length; i++) {
                $.ajax({
                    type: "post",
                    url: baseHost + "/" + _this.beanName + "/delete",
                    data: {id: list[i]},
                    async: false,
                    success: function (data) {
                        deleteRows = deleteRows + data.body;
                    }
                });
            }
            _this.select();
            layer.alert("已删除:" + deleteRows + "条", {icon: 6});
        });

    },
    changeEnable: function (bean) {
        var _this = this;
        var id = bean.id;
        var enable = bean.enable;
        var msg = "确认启用该记录？";
        if (enable == "Y") {
            enable = "N";
            msg = "确认停用该记录？";
        } else {
            enable = "Y";
        }
        var map = {};
        map.id = id;
        map.enable = enable;
        layer.confirm(msg, function () {
                $.post(baseHost + "/" + _this.beanName + "/saveOrUpdate", map, function (data) {
                    if (data.code == 200) {
                        layer.alert(data.msg + "(已修改:" + data.body + "条)", {icon: 6});
                        _this.select();
                    } else {
                        layer.alert(data.msg, {icon: 5});
                    }

                });
            }
        );
    },
    changeState: function (id,state) {
        var _this = this;
        if (state == "1") {

            msg = "确认停用该记录？";
        } else {

            var msg = "确认启用该记录？";
        }
        layer.confirm(msg, function () {
            $.post(baseHost + "/" + _this.beanName + "/changeState", {id: id}, function (data) {
                layer.alert(data.msg + "(已修改:" + data.body + "条)", {icon: 6}, function (index) {
                    layer.close(index);
                    _this.select();
                });
            });

            }
        );
    },
    changeStatus: function (bean, status) {
        var _this = this;
        var map = {};
        map.id = bean.id;
        map.status = status;
        layer.confirm("修改确认", function () {
                $.ajax({
                    type: "post",
                    url: baseHost + "/" + _this.beanName + "/saveOrUpdate",
                    datType: "JSON",
                    contentType: "application/json",
                    data: JSON.stringify(map),
                    async: false,
                    success: function (data) {
                        if (data.code == 200) {
                            layer.alert(data.msg + "(已保存:" + data.body + "条)", {icon: 6});
                            _this.select();
                        } else {
                            layer.alert(data.msg, {icon: 5});
                        }
                    }
                });
            }
        );
    },
    goBeanPage: function (val) {
        if (val) {
            this.$router.push({path: '/' + this.beanName + '/' + this.beanName, query: {id: val}});
        } else {
            this.$router.push({path: '/' + this.beanName + '/' + this.beanName});
        }
    },
    goUpdatePage: function () {
        if (this.checkboxModel.length > 1) {
            layer.alert("只能选择1条!", {icon: 5});
            return;
        }
        if (this.checkboxModel.length == 0) {
            layer.alert("请勾选要修改的数据!", {icon: 5});
            return;
        }
        if (this.checkboxModel.length == 1) {
            this.goBeanPage(this.checkboxModel[0]);
            // this.$router.push({path: '/' + this.beanName + '/' + this.beanName, query: {id: this.checkboxModel[0]}});
        }
    },
    saveOrUpdate: function () {
        var _this = this;
        $.ajax({
            type: "post",
            url: baseHost + "/" + _this.beanName + "/saveOrUpdate",
            datType: "JSON",
            contentType: "application/json",
            data: JSON.stringify(this.bean),
            async: false,
            success: function (data) {
                if (data.code == 200) {
                    solar.alert(data.msg + "(已保存:" + data.body + "条)", {icon: 6});
                    history.back();
                } else {
                    solar.alert(data.msg, {icon: 5});
                }
            }
        });
    },
    select: function () {
        if (!this.$route.query.id) {
            return;
        }
        var _this = this;
        $.get(baseHost + "/" + this.beanName + "/select?id=" + this.$route.query.id, function (data) {
            if (!data.body) {
                layer.alert("未找到该记录!", {icon: 5});
                return;
            }
            _this.bean = solar.formatObjectTime(data.body);
            solar.formatObjectCodeToArray(_this.bean);
        });
    },
    changeOrder: function (val) {
        if (!val || val.order == null) {
            return;
        }
        if (val.order == "ascending") {
            this.orderDirection = "asc";
        } else {
            this.orderDirection = "desc";
        }
        this.orderProperty = val.prop;
        this.select();
    },
    dateFormat: function (row, column) {
        var date = row[column.property];
        if (date == undefined) {
            return "";
        }
        return moment(date).format("YYYY-MM-DD");
    },
    datetimeFormat: function (row, column) {
        var date = row[column.property];
        if (date == undefined) {
            return "";
        }
        return moment(date).format("YYYY-MM-DD HH:mm:ss");
    },
    getCreativeName: function (row, column) {
        var sendMsgUrl = baseHost + "/advertiserCreative/select";
        var data = solar.post(sendMsgUrl, {
            id: row[column.property],
            imgCode: $("#imgCode").val()
        });

        row[column.property]/100;
        return money+"元";
    },

    formatMoney : function (row, column) {
        var money = row[column.property]/100;
        return money+"元";
    },
    switchBidType: function (row, column) {
        var type = row[column.property];
        <!--：1CPM 2CPC 3CPA 4CPS-->
         switch(type){
            case 1:
                return 'CPM';
                break;

            case 2:
                return 'CPC';
                break;

            case 3:
                return 'CPA';
                break;


            case 4:
                return 'CPS';
                break;
            default:
                return '未知';
        }
        return "未知";
    },
    check: function () {
        var _this = this;
        var data = {
            id: this.bean.id,
            enable: "y",
            status: 1,
        };
        $.ajax({
            type: "post",
            url: baseHost + "/" + _this.beanName + "/saveOrUpdate",
            dateType: "JSON",
            contentType: "application/json",
            data: JSON.stringify(data),
            async: false,
            success: function (data) {
                if (data.code == 200) {
                    layer.alert(data.msg + "(已保存:" + data.body + "条)", {icon: 6});
                    history.back();
                } else {
                    layer.alert(data.msg, {icon: 5});
                }
            }
        });
    },
}
solar.sendMsgCode = function (mobileNo) {
    if (!mobileNo || mobileNo == "") {
        solar.alert("请输入正确的手机号!");
        return;
    }
    $.ajax({
        type: "get",
        url: "/sendMsgCode?mobileNo=" + mobileNo,
        async: false,
        success: function (data) {
            solar.alert(data.msg);
        }
    });
}
solar.getAreaTree4 = function (areaId) {
    var _this = this;
    this.bean.areaId = "";
    $.ajax({
        type: "get",
        url: "/areaTree4?areaId=" + areaId,
        async: false,
        success: function (data) {
            _this.areaTree4 = data.body;
        }
    });
}
solar.getAreaTree4ToAreaTree3 = function (_this, areaCode) {
    $.ajax({
        type: "get",
        url: "/areaTreeByCode?areaCode=" + areaCode,
        async: false,
        success: function (data) {
            var childList = data.body;
            for (i in _this.areaTree) {
                for (j in _this.areaTree[i].childList) {
                    for (k in _this.areaTree[i].childList[j].childList) {
                        if (_this.areaTree[i].childList[j].childList[k].code == areaCode) {
                            _this.areaTree[i].childList[j].childList[k].childList = childList;
                            return;
                        }

                    }
                }
            }
        }
    });
}
