// pages/table_com_item/table_com_iem.js
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        com_item: [],
        img_Url:'',
        com_prompt:'到底了哦',
        com_item2:[],
        com_remakr:'',
        show:true,
    },
    onClickLeft() {
        wx.switchTab({
            url: '../table_community/table_community'
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    post_like(){
        let data = {
            "userId":app.globalData.userid,
            "token":wx.getStorageSync('token'),
            "commentId":app.globalData.com_itemid  
        }	
        app.haw_req('post','likes',data)
            .then((res)=>{
                if(res.data.code == 10){
                   this.setData({
                       show:false
                   })
                }else if(res.data.code == 11||res.data.code==9){
                    this.setData({
                        show:true
                    })
                }
                let now_data = this.data.com_item
                console.log(now_data)
                now_data.likes = res.data.data[0].likes
                this.setData({
                    com_item:now_data
                })
            })
    },
    post_com_remakr(){
        console.log(this.data.com_remakr)
        if(this.data.com_remakr==''){
            return
        }
        let data = {
            "userId":app.globalData.userid,
            "commentId":app.globalData.com_itemid,
            "token":app.globalData.token,
            "commentSonText":this.data.com_remakr
        }
        wx.request({
            method: 'post',
            url: "http://localhost:8085/commentSon",
            data: data,
            success:(e)=>{
                console.log(e)
                if(e.data.code ==8){
                    this.get_com_remarks()
                    this.get_com_item()
                    this.setData({
                        com_remakr:''
                    })
                }
            }
        })   
    },
    onLoad: function (options) {
        this.setData({
            img_Url:app.globalData.img_url
        })
        this.get_com_item()
        this.get_com_remarks()
    },
    get_com_item() {
        let data = {
            "userId": app.globalData.userid,
            "commentId": app.globalData.com_itemid
        }
        app.haw_req('post', 'selectCommentByCommentId', data)
            .then((res) => {
                console.log(res.data)
                if (res.data.code == 19) {
                    let new_time = app.GMTToStr2(res.data.data.commentDtoList[0].commentTime)
                    let now_data = res.data.data.commentDtoList[0]
                    now_data.commentTime = new_time
                    now_data.userPortraitAddress = now_data.avatar
                    this.setData({
                        com_item: now_data
                    })
                }
            })
        app.haw_req('post', 'selectCommentLikes', data)
            .then((res) => {
                console.log(res.data)
                if(res.data.code == 999){
                    this.setData({
                        show:true
                    })
                }
                else{
                    this.setData({
                        show:false
                    })
                }
            })

    },
    get_com_remarks() {
        let data = {
            "commentId": app.globalData.com_itemid
        }
        app.haw_req('post', 'selectCommentSonByCommentId', data)
            .then((res) => {
                console.log(res.data)
                if(res.data.code == 14){
                    let now_data = res.data.data.commentSonDtoList      
                    for (let i in now_data){
                        let new_time = app.GMTToStr2(now_data[i].commentSonDate)
                        now_data[i].commentSonDate = new_time
                        now_data[i].userPortraitAddress = now_data[i].avatar
                    }   
                    this.setData({
                        com_item2:now_data
                    })
                    console.log(this.data.com_item2)
                }
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