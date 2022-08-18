// pages/forget_pwd/forget_pwd.js
import Toast from '../../miniprogram_npm/@vant/weapp/toast/toast';
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userpwd1: '',
        userpwd1_err: false,
        userpwd2: '',
        userpwd2_err: false,
        userpwd2_msg_err: '',
        e_mail: '',
        e_mail_err: false,
        verify: '',
        verify_err: false,
        disabled: true,
        codename: '获取验证码',
        num: 10,
        userid: 0
    },

    /**
     * 生命周期函数--监听页面加载
     */
    get_ver_nub() {
        if (this.data.e_mail_err == true || this.data.e_mail == '') {
            return false
        } else {
            this.go()
        }
    },
    go() {
        let data = {
            "email": this.data.e_mail
        }
        let header = {
            'content-type': 'application/x-www-form-urlencoded'
        }
        app.haw_req('post', 'verifyEmailAndSendEmail', data, header)
            .then((res) => {
                if (res.data.code == -12) {
                    Toast.fail('账号不存在');
                    this.setData({
                        e_mail: ''
                    })
                } else {
                    Toast.fail('验证码已发送');
                }
            })
    },
    verify_email() {
        let str = this.data.e_mail
        let reg = /^([a-zA-Z0-9])+(([a-zA-Z0-9])|([._-][a-zA-Z0-9])*)+@([a-zA-Z0-9-])+((\.[a-zA-Z0-9-]{2,3}){1,2})$/
        if (reg.test(str) == false) {
            this.setData({
                e_mail_err: true,
                disabled: true
            })
        } else {
            this.setData({
                disabled: false,
                e_mail_err: false
            })
        }
    },
    verify_ver() {
        if (this.data.verify == "") {
            this.setData({
                verify_err: true
            })
        } else {
            this.setData({
                verify_err: false
            })
        }
    },
    get_verify_nub() {
        if (this.data.e_mail_err == true) {
            return false
        }
        if (this.data.disabled == true) {
            return false
        }
        this.get_ver_nub()
        this.setData({
            codename: this.data.num + "s"
        })
        let nums = this.data.num
        // console.log(this.data.num)
        let timer = setInterval(() => {
            nums--
            if (nums <= 0) {
                clearInterval(timer)
                this.setData({
                    codename: '重新发送',
                    disabled: false
                })
            } else {
                this.setData({
                    codename: nums + "s",
                    disabled: true
                })
            }
        }, 1000)
    },
    onClickLeft() {
        console.log('ok')
        wx.redirectTo({
            url: '../login/login'
        })
    },
    verify_pwd() {
        if (this.data.userpwd1 == "") {
            this.setData({
                userpwd1_err: true
            })
        } else {
            this.setData({
                userpwd1_err: false
            })
        }
    },
    verify_pwd2() {
        let reg = /^(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9 _]{8,15}$/
        if (reg.test(this.data.userpwd2) == false) {
            console.log('密码格式不正确')
            this.setData({
                userpwd2_err: true,
                userpwd2_msg_err: '密码格式不正确'
            })
        } else if (this.data.userpwd2 != this.data.userpwd1) {
            console.log('2次密码不一致')
            this.setData({
                userpwd2_err: true,
                userpwd2_msg_err: '2次密码不一致'
            })
        } else {
            this.setData({
                userpwd2_err: false,
                userpwd2_msg_err: ''
            })
            console.log('密码正确')
        }

    },
    forget_second() {
        let data = {
            "email": this.data.e_mail,
            "msg":this.data.verify
        }
        app.haw_req('post', 'verifyVerificationCodeAndVerifcationTime', data)
            .then((res) => {
                console.log(res.data)
                if (res.data.code == -7) {
                    Toast.fail('验证码失效 请重新发送');
                } else if (res.data.code == -4) {
                    Toast.fail('验证码错误 请重新输入');
                } else if (res.data.code == 21) {
                    console.log('验证码正确');
                    this.data.userid = res.data.data
                    this.forget_third()
                }
            })
    },
    forget_third() {
        let data = {
            "userId": this.data.userid,
            "newPassword": this.data.userpwd1
        }
        app.haw_req('post', 'changePassword', data)
            .then((res) => {
                console.log(res.data)
                if(res.data.code ==20){
                    Toast.success('修改成功');
                    this.setData({
                        userpwd1:'',
                        userpwd2:'',
                        e_mail:'',
                        verify:''
                    })
                }
            })

    },
    verify_forget() {
        if (this.data.e_mail_err == true || this.data.e_mail == '') {
            return false
        } else if (this.data.userpwd1_err == true || this.data.userpwd1 == '') {
            return false
        } else if (this.data.userpwd2_err == true || this.data.userpwd2 == '') {
            return false
        } else if (this.data.verify_err == true || this.data.verify == '') {
            return false
        } else {
            this.forget_second()
        }
    },
    onLoad: function (options) {

    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function () {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})