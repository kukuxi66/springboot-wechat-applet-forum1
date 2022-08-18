// pages/revise_header/revise_header.js
import Toast from '../../miniprogram_npm/@vant/weapp/toast/toast';
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        fileList:[],
        file:[],
    },
    revise_header(){
        if(this.data.fileList.length<=0){
           return
        }
        let data = {
            'id':app.globalData.userid
        }
        wx.uploadFile({
            filePath: this.data.file[0].url,
            name: 'file',
            url: 'http://localhost:8085/addUserPortrait',
            formData:data,
            success:(res)=>{
                console.log(res.data)
              let str = JSON.parse(res.data)
              console.log(str)
              if(str.code == 6){
                    app.globalData.userAddress = str.data
                    wx.switchTab({
                        url: '../table_mine/table_mine'
                    })
              }

            }
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