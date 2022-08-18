// pages/table_add_com/table_add_com.js
import Toast from "../../miniprogram_npm/@vant/weapp/toast/toast"
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    radio: '1',
    com_class:'爱情',
    add_com_box_title: '',
    fileList: [],
    add_com_box_texts:'',
    file:[]
  },
  verify_com(){
    console.log('ok')
    if(this.data.add_com_box_title == ''){
      Toast.fail('标题为空')
      return
    }else if(this.data.add_com_box_texts == ''){
      Toast.fail('内容为空')
      return
    }
    if(this.data.fileList.length>0){
      this.post_img_com()
    }else{
      this.post_com()
    }
  },
  post_img_com(){
    let data = {
      "userId":app.globalData.userid,
      "commentTitle":this.data.add_com_box_title,
      "commentText":this.data.add_com_box_texts,
      "commentCategoryName":this.data.com_class,
      "token":app.globalData.token
    }
    let header ={
      'content-type': 'application/x-www-form-urlencoded'
    }
    console.log("111")
    wx.request({
      url: 'https://www.xiaochenya.xyz:8086/commentWithOutAddressQianZhi',
      method: 'post',
      data: data,
      header: header,
      success:(res)=>{
        console.log(res.data)
        if(res.data.code == 18){
          for(let i in this.data.file){
            console.log(this.data.file[i].url)
            let data2 = {
              "commentId":res.data.data,
            }
            console.log(data2)
            wx.uploadFile({
              filePath: this.data.file[i].url,
              name: 'commentAddress',
              url: 'https://www.xiaochenya.xyz:8086/commentWithOutAddressHouZhi',
              formData:data2,
              success:(res)=>{
                console.log(res.data)
                let str = JSON.parse(res.data)
                console.log(str.code)
                if(str.code == 7){
                  Toast.success('发布成功')
                  this.setData({
                    add_com_box_title:''
                  })
                  this.setData({
                    add_com_box_texts:''
                  })
                  this.setData({
                    file:[]
                  })
                  this.setData({
                    fileList:[]
                  })
                }
              }
            })
      
          }
        }
      }
    })
    // app.haw_req('post','commentWithOutAddressQianZhi',data,header)
    //   .then((res)=>{
        
    //   }).then(
        
    //   )
  },
  post_com(){
    let data = {
      "userId":app.globalData.userid,
      "commentTitle":this.data.add_com_box_title,
      "commentText":this.data.add_com_box_texts,
      "commentCategoryName":this.data.com_class,
      "token":wx.getStorageSync('token')
    }
    let header ={
      'content-type': 'application/x-www-form-urlencoded'
    }
    app.haw_req('post','commentWithOutAddressQianZhi',data,header)
      .then((res)=>{
        console.log(res.data)
        if(res.data.code == 18){
          Toast.success('发表成功');
        }
        this.setData({
          add_com_box_texts:'',
          add_com_box_title:''
        })
      })
    
  },
  dele_img(event){
    let now_fileList = this.data.fileList
    now_fileList.splice(event.detail.index,1)
    this.setData({
      fileList:now_fileList
    })
  },
  afterRead(event){
    console.log(event)
    const {
      file
    } = event.detail;
    // callback(file.type === 'image');
    
    let data = {
      url:file.url,
    }
    
    let now_fileList = this.data.fileList
    now_fileList.push(data)
    this.setData({
        fileList:now_fileList
    })
    let data_file= this.data.file
    let data_f = {
      'url':file.url
    }
    data_file.push(data_f)

    this.setData({
      file:data_file
    })
    console.log(this.data.file)
    
  },
  beforeRead(event) {
    const {
      file,
      callback
    } = event.detail;
    if(file.type != 'image'){
      Toast.fail('类型错误');
    }
    if(file.size/1024>3000){
      Toast.fail('图片太大');
      let time = setTimeout(()=>{
        callback(false)
        return 
      },1000)
      
    }else{
      this.afterRead(event)
    } 
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  },
  onChange(event) {
    this.setData({
      radio: event.detail,
    });
    if(event.detail == 1){
      this.data.com_class = '爱情'
    }else if(event.detail == 2){
      this.data.com_class = '友情'
    }else if(event.detail  == 3){
      this.data.com_class = '亲情'
    }
  },

  onClick(event) {
    const {
      name
    } = event.currentTarget.dataset;
    this.setData({
      radio: name,
    });
    console.log(name)
    if(name == 1){
      this.data.com_class = '爱情'
    }else if(name == 2){
      this.data.com_class = '友情'
    }else if(name  == 3){
      this.data.com_class = '亲情'
    }
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