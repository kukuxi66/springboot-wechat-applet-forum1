import Toast from "../../miniprogram_npm/@vant/weapp/toast/toast"

// pages/login/login.js
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        username: '',
        userpwd: '',
        'token':wx.getStorageSync('token')
    },

    go_registered() {
        wx.redirectTo({
            url: '../registered/registered'
        })
    },
    go_forget(){
        wx.redirectTo({
            url: '../forget_pwd/forget_pwd'
        })
    },
    go_login(){
        
        if (this.data.username ==''||this.data.userpwd==''){
            return false
        }
        let data = {
            "username":this.data.username,
            "password":this.data.userpwd
        }
        app.haw_req('post','login',data)
            .then((res)=>{
                console.log(res.data)
                if(res.data.code==-2){
                    Toast.fail('账号或密码错误');
                }else if(res.data.code == 2){
                    app.globalData.username = res.data.data.username //用户名
                    app.globalData.userid = res.data.data.id //id
                    app.globalData.token = res.data.data.token //token
                    app.globalData.userAddress = res.data.data.avatar //头像
                    app.globalData.email = res.data.data.email //电子邮件
                    wx.setStorageSync({
                        key: 'token',
                        data: res.data.data.token,
                    })
                    Toast.success('成功')

                    wx.switchTab({
                        url: '../table_home/table_home'
                    })
                }
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
        console.log(this.data.token)
        let data = {
            "token":this.data.token
        }
        app.haw_req('post','logincheck',data)
        .then((res)=>{
            console.log(res.data)
            if(res.data.code==-990){
                Toast.fail('未登录');
            }else if(res.data.code == 990){
                app.globalData.username = res.data.data.username //用户名
                app.globalData.userid = res.data.data.id //id
                app.globalData.token = res.data.data.token //token
                app.globalData.userAddress = res.data.data.avatar //头像
                app.globalData.email = res.data.data.email //电子邮件
                wx.setStorage({
                    key: 'token',
                    data: res.data.data.token,
                })
                Toast.success('成功')

                wx.switchTab({
                    url: '../table_home/table_home'
                })
            }
        })

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