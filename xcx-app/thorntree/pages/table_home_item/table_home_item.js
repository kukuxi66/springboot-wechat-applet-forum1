// pages/table_home_item/table_home_item.js
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        img_Url: app.globalData.img_url,
        com_item: [],
        show:false
    },
    GMTToStr(time) { //时间格式转化
        let date = new Date(time)
        let Str = date.getFullYear() + '年' +
            (date.getMonth() + 1) + '月' +
            date.getDate() + '日' 
        return Str
    },
    onClickLeft() {
        wx.switchTab({
            url: '../table_home/table_home'
        })
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        
    },
    get_home_item(comid) {
        let data = {
            "articleId": comid,
            "userId": app.globalData.userid
        }
        let header = {
            'content-type': 'application/x-www-form-urlencoded'
        }
        app.haw_req('post', 'selectArticleByArticleId', data, header)
            .then((res) => {
                let new_time = res.data.data.articleIdDto
                for (let i in res.data.data.articleIdDto) {
                    new_time[i].articleTime = this.GMTToStr(new_time[i].articleTime)
                }
                this.setData({
                    com_item: new_time
                })
            })
        app.haw_req('post', 'selectArticleLike', data, header)
            .then((res) => {                
                if(res.data.code == 999){
                    let like_comid = res.data.data[0].commentId
                    if(like_comid==app.globalData.comid){
                        this.setData({
                            show:true
                        })
                        return
                    }else{
                        this.setData({
                            show:false
                        })
                    }
                }
            })
                    
    },
    post_like(){
        let data = {
            "userId":app.globalData.userid,
            "token":wx.getStorageSync('token'),
            "articleId":app.globalData.comid  
        }	
        app.haw_req('post','articleLikes',data)
            .then((res)=>{
                console.log(res.data)
                let flag = "0"
                if(res.data.code == 10){
                    flag="0"
                   this.setData({
                       show:false
                   })
                }else if(res.data.code == 11||res.data.code==9){
                    flag="1"
                    this.setData({
                        show:true
                    })
                }
                console.log(this.data)
                let now_data = this.data.com_item
                now_data.likes = res.data.data
                console.log(now_data[0].likes)
                if(flag=="0"){
                    now_data[0].likes=now_data[0].likes-1
                }else{
                    now_data[0].likes=now_data[0].likes+1
                }
                this.setData({
                    com_item:now_data
                })
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
        this.get_home_item(app.globalData.comid)
        console.log(app.globalData.comid)
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