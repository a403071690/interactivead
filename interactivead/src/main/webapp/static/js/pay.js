Vue.filter('time', function (value, formatString) {
    formatString = formatString || 'yyyy-MM-dd';
    if (value == "" || !value) {
        return "";
    }
    return new Date(value).Format(formatString);
});
const app = new Vue({
    el: "#app",
    data: {
        totalMoney: "",
        today: new Date().Format("yyyy年-MM月-dd日"),
        shop: {},
        payStatus: 0,
        myRedEnvelopes: [],
        myUsableRedEnvelopes: [],
        myNotUsableRedEnvelopes: [],
        myCoupons: [],
        myUsableCoupons: [],
        myNotUsableCoupons: [],
        redEnvelope: {
            money: 0,
        },
        coupon: {
            money: 0,
        },
        pageView: "payIndexPage",
    },
    methods: {
        pay: function (shopId) {
            if (this.totalMoney == "" || this.totalMoney == 0) {
                alert("请输入金额!");
                return false;
            }
            if (this.payStatus != 0) {
                alert("正在准备支付!");
                return false;
            }
            this.payStatus = 1;
            var bill = {};
            if (!this.redEnvelope.id) {
                this.redEnvelope.id = "";
            }
            if (!this.coupon.id) {
                this.coupon.id = "";
            }
            $.ajax({
                type: "get",
                url: "/unifiedOrder?shopId=" + shopId + "&totalMoney=" + (this.totalMoney * 100) +
                "&accountRedEnvelopeId=" + this.redEnvelope.id +
                "&accountCouponId=" + this.coupon.id,
                async: false,
                success: function (data) {
                    bill = data.body;
                }
            });
            if (typeof(WeixinJSBridge) != "undefined") {
                var param = {};
                $.ajax({
                    type: "get",
                    url: "/weChatPay?id=" + bill.id,
                    async: false,
                    success: function (data) {
                        param = data.body;
                        if(data.code!=200){
                            alert(JSON.stringify(data));
                        }
                    }
                });

                var data = {
                    "appId": param.appId,     //公众号名称，由商户传入
                    "timeStamp": param.timeStamp,         //时间戳，自1970年以来的秒数
                    "nonceStr": param.nonceStr, //随机串
                    "package": param.package,
                    "signType": param.signType,         //微信签名方式：
                    "paySign": param.paySign //微信签名
                };
                WeixinJSBridge.invoke(
                    'getBrandWCPayRequest', data,
                    function (res) {
                        if (res.err_msg == "get_brand_wcpay_request:ok") {
                            location = "/paySuccess?id=" + param.billId;
                        }
                    }
                );
                this.payStatus = 0;
                return;
            }
            window.location = "/aliPay?id=" + bill.id;
        },
        isUnusable: function (bean) {
            var nowDate = new Date();
            if (bean.useInHoliday == "n") {
                var day = nowDate.getDay();
                if (day == 0 || day == 6) {
                    return "该红包不能在节假日使用!";
                }
            }
            if (nowDate.getTime() < bean.useBeginTime) {
                return "还未到使用时间!";
            }
            if (nowDate.getTime() > bean.useOverTime) {
                return "红包已过期!";
            }
            if (bean.useBeginTimeBucket && bean.useBeginTimeBucket != ""
                && bean.useOverTimeBucket && bean.useOverTimeBucket != "") {
                if (!solar.inTimeRange(bean.useBeginTimeBucket, bean.useOverTimeBucket)) {
                    return "不在使用时间段内!";
                }
            }
            if (bean.shopId && bean.shopId != shopId) {
                return "商铺不符合!";
            }
            if (bean.payType == 1 && solar.isWechatBrower()) {
                return "需要支付宝支付!";
            }
            if (bean.payType == 2 && !solar.isWechatBrower()) {
                return "需要微信支付!";
            }
            if ((this.totalMoney * 100) < bean.totalMoney) {
                return "账单总金额不够" + bean.totalMoney / 100;
            }
            return false;
        },
        chooseRedEnvelope: function (bean) {
            var isUnusable = this.isUnusable(bean);
            if (isUnusable) {
                alert(isUnusable);
                return false;
            }
            this.redEnvelope = bean;
            this.pageView = "payIndexPage";
        },
        chooseCoupon: function (bean) {
            var isUnusable = this.isUnusable(bean);
            if (isUnusable) {
                alert(isUnusable);
                return false;
            }
            this.coupon = bean;
            this.pageView = "payIndexPage";
        },
        autoChooseRedEnvelope: function (bean) {
            for (i in this.myRedEnvelopes) {
                var isUnusable = this.isUnusable(this.myRedEnvelopes[i]);
                if (!isUnusable) {
                    this.redEnvelope = this.myRedEnvelopes[i];
                    return;
                }
            }
        },
        autoChooseCoupon: function (bean) {
            for (i in this.myCoupons) {
                var isUnusable = this.isUnusable(this.myCoupons[i]);
                if (!isUnusable) {
                    this.coupon = this.myCoupons[i];
                    return;
                }
            }
        },
        checkMyRedEnvelopesAndCoupons: function () {
            this.myNotUsableRedEnvelopes = [];
            this.myUsableRedEnvelopes = [];
            this.myNotUsableCoupons = [];
            this.myUsableCoupons = [];
            for (i in this.myRedEnvelopes) {
                var isUnusable = this.isUnusable(this.myRedEnvelopes[i]);
                if (isUnusable) {
                    this.myRedEnvelopes[i].unusable = isUnusable;
                    this.myNotUsableRedEnvelopes.push(this.myRedEnvelopes[i]);
                } else {
                    this.myUsableRedEnvelopes.push(this.myRedEnvelopes[i]);
                }
            }
            for (i in this.myCoupons) {
                var isUnusable = this.isUnusable(this.myCoupons[i]);
                if (isUnusable) {
                    this.myCoupons[i].unusable = isUnusable;
                    this.myNotUsableCoupons.push(this.myCoupons[i]);
                } else {
                    this.myUsableCoupons.push(this.myCoupons[i]);
                }
            }
        }
    },
    mounted: function () {
        var _this = this;
        solar.httpGet("/myRedEnvelopes?status=0", function (data) {
            _this.myRedEnvelopes = data.body;
            _this.autoChooseRedEnvelope();
            _this.checkMyRedEnvelopesAndCoupons();
        });
        solar.httpGet("/myCoupons?status=0", function (data) {
            _this.myCoupons = data.body;
            _this.autoChooseCoupon();
            _this.checkMyRedEnvelopesAndCoupons();
        });
    },
    watch: {
        'totalMoney': {
            handler: function (val, oldVal) {
                this.redEnvelope = {money: 0};
                this.coupon = {money: 0};
                this.autoChooseRedEnvelope();
                this.autoChooseCoupon();
                this.checkMyRedEnvelopesAndCoupons();
            },
            deep: true
        },
    }
});