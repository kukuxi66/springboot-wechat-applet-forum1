// pages/revise_pwd/revise_pwd.js
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
    },
    revise_pwd(){
        if(this.data.uperpwd1_err == ''){
            return
        }else{
            console.log('ok')
            let data = {
                'id':app.globalData.userid,
                'newpassword':this.data.userpwd1,
                'token':wx.getStorageSync('token')
            }
            app.haw_req('post','changePassword',data)
                .then((res)=>{
                    console.log(res.data)
                    if(res.data.code == 20){
                        wx.redirectTo({
                            url: '../login/login'
                        })
                    }
                })
        }    
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