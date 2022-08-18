// pages/table_home/table_home.js
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        img_Url:app.globalData.img_url,
        all_text:[],
        text_pages:1,
        text_pagesize:10
    },
    go_home_item(event){
        let comid = event.currentTarget.dataset.comid
        app.globalData.comid = comid
        console.log('ok')
        wx.navigateTo({
            url: '../table_home_item/table_home_item'
        })
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.get_all_text(1)
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
    GMTToStr(time) { //时间格式转化
        let date = new Date(time)
        let Str = date.getFullYear() + '年' +
            (date.getMonth() + 1) + '月' +
            date.getDate() + '日' 
        return Str
    },
    get_all_text(pages){
        let data = {
            'userId':app.globalData.userid,
            'page':pages,
            'pagesize':10
        }
        let header = {
            'content-type': 'application/json'
        }
        app.haw_req('post','selectAllArticle',data,header)
            .then((res)=>{
                console.log(res.data)
                let new_time = res.data.data
                for(let i in res.data.data){
                    new_time[i].articleTime = this.GMTToStr(new_time[i].articleTime)
                }
                this.setData({
                    all_text:new_time
                })
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
        this.get_text_next_pages()
    },
    get_text_next_pages(){
        this.data.text_pages++
        let data = {
            userId:app.globalData.userid,
            page:this.data.text_pages,
            pagesize:10
        }
        let header = {
            'content-type': 'application/json'
        }
        app.haw_req('post','selectAllArticle',data,header)
            .then((res)=>{
                console.log(res.data)
                if(res.data.code == 13&&res.data.data==''){
                    this.data.text_pages--
                    return
                }
                let now_all_text = this.data.all_text

                let new_time = res.data.data
                for(let i in res.data.data){
                    new_time[i].articleTime = this.GMTToStr(new_time[i].articleTime)
                    now_all_text.push(new_time[i])
                }
                this.setData({
                    all_text:now_all_text
                })

            }) 
    },
   
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})