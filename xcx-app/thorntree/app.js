// app.js
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })


  },
  globalData: {
    userInfo: null,
    value: '',
    userid: '',
    userAddress: '',
    img_url: 'http://localhost:8085',
    username: '',
    comid: '',
    token: '',
    email:'',
    com_itemid:'',
    com_saw:0,
    com_like:0,
    com_comments:0
  },
  haw_req(type, url, data, header) {
    return new Promise((resolve, reject) => {
      wx.request({
        method: type,
        url: "http://localhost:8085/" + url,
        data: data,
        header: header,
        success: (res) => {
          resolve(res)
        },
        fail: (res) => {
          reject(res)
        }
      })
    })
  },
  GMTToStr(time) { //时间格式转化
    let date = new Date(time)
    let Str = date.getFullYear() + '年' +
      (date.getMonth() + 1) + '月' +
      date.getDate() + '日'
    return Str
  },
  GMTToStr2(time) { //时间格式转化
    let date = new Date(time)
    let Str = date.getFullYear() + '-' +
        (date.getMonth() + 1) + '-' +
        date.getDate() + ' ' +
        date.getHours() + ':' +
        date.getMinutes() + ':' +
        date.getSeconds()
    return Str
},
})