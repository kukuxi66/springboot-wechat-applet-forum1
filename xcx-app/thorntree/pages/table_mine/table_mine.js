// pages/table_mine/table_mine.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    img_Url: '',
    user_avatar: '',
    activeNames: ['1'],
    mine_username: '张三',
    mine_email: '2041433070@qq.com'
    // https://www.xiaochenya.xyz:8081//thorntree/userAddress/userDefaultAddress/默认头像.jpg
  },
  onChange(event) {
    this.setData({
      activeNames: event.detail,
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  },
  go_revise_username() {
    console.log('go_revise_username')
    wx.navigateTo({
      url: '../revise_username/revise_username'
    })
  },
  go_revise_email() {
    console.log('go_revise_email')
    wx.navigateTo({
      url: '../revise_email/revise_email'
    })
  },
  go_revise_pwd() {
    console.log('go_revise_pwd')
    wx.navigateTo({
      url: '../revise_pwd/revise_pwd'
    })
  },
  go_revise_header() {
    console.log('go_revise_header')
    wx.navigateTo({
      url: '../revise_header/revise_header'
    })
  },
  go_out() {
    wx.redirectTo({
      url: '../login/login'
    })
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
    console.log(app.globalData.userAddress)
    console.log(app.globalData.token)
    console.log(app.globalData.userid)
    console.log(app.globalData.username)
    console.log(app.globalData.email)
    console.log(wx.getStorageSync('token')) 
    this.setData({
      user_avatar: app.globalData.userAddress
    })
    this.setData({
      img_Url: app.globalData.img_url
    })
    this.setData({
      mine_username: app.globalData.username
    })
    this.setData({
      mine_email: app.globalData.email
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