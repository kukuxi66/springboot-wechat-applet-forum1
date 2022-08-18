// pages/revise_username/revise_username.js
import Toast from '../../miniprogram_npm/@vant/weapp/toast/toast';
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        username:''
    },
    onClickLeft() {
        wx.switchTab({
            url: '../table_mine/table_mine'
        })
    },
    revise_username(){
        if(this.data.username == ''){
            return 
        }
        let data = {
            'id':app.globalData.userid,
            "newUsername":this.data.username,
            'token':wx.getStorageSync('token')
        }
        app.haw_req('post','changeUserName',data)
            .then((res)=>{
                if(res.data.code == -13){
                    this.setData({
                        username:''
                    })
                    Toast.fail('用户名已存在')
                }else if(res.data.code==25){
                    app.globalData.username = this.data.username
                    wx.switchTab({
                        url: '../table_mine/table_mine'
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