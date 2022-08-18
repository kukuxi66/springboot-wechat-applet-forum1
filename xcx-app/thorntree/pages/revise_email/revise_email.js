// pages/revise_email/revise_email.js
import Toast from '../../miniprogram_npm/@vant/weapp/toast/toast';
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        e_mail: '',
        e_mail_err: false,
        verify: '',
        verify_err: false,
        disabled: true,
        codename: '获取验证码',
        num: 10,
        userid: 0
    },
    verify_forget() {
        if (this.data.e_mail_err == true || this.data.e_mail == '') {
            return false
        } else if (this.data.verify_err == true || this.data.verify == '') {
            return false
        } else {
            this.bind_email()
        }
    },
    bind_email(){
        let data = {
            'userId':app.globalData.userid,
            'token':app.globalData.token,
            'email':this.data.e_mail,
            'msg':this.data.verify
        }
        app.haw_req('post','bindingEmail',data)
            .then((res)=>{
                console.log(res.data)
                if(res.data.code==4){
                    app.globalData.email = this.data.e_mail
                    Toast.success('绑定成功'); 
                    this.setData({
                        e_mail:'',
                        verify:''
                    })
                    wx.switchTab({
                        url: '../table_mine/table_mine'
                    })
                }
                if(res.data.code==-4){
                    Toast.fail('验证码错误'); 
                    this.setData({
                        verify:''
                    })
                }
                if(res.data.code==-11){
                    Toast.fail('该邮箱已被绑定'); 
                    this.setData({
                        e_mail:'',
                        verify:''
                    })
                }
            })
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
        app.haw_req('get', 'sendEmail', data)
            .then((res) => {

                if (res.data.code == 5) {
                    Toast.success('发送成功'); 
                } else {
                    Toast.fail('发送失败');
                    this.setData({
                        e_mail: ''
                    })
                }
            })
    },
    // verify_email() {
    //     let str = this.data.e_mail
    //     let reg = /^([a-zA-Z0-9])+(([a-zA-Z0-9])|([._-][a-zA-Z0-9])*)+@([a-zA-Z0-9-])+((\.[a-zA-Z0-9-]{2,3}){1,2})$/
    //     if (reg.test(str) == false) {
    //         this.setData({
    //             e_mail_err: true,
    //             disabled: true
    //         })
    //     } else {
    //         this.setData({
    //             disabled: false,
    //             e_mail_err: false
    //         })
    //     }
    // },
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
   
    onClickLeft() {
        wx.switchTab({
            url: '../table_mine/table_mine'
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
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