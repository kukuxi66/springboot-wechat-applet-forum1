// pages/table_community/table_community.js
const app = getApp()
Page({
    /**
     * 页面的初始数据
     */
    data: {
        value: '',
        community_class: [],
        community_byClass: [],
        all_community: [],
        img_Url: app.globalData.img_url,
        all_pages: 1,
        love_pages: 1,
        friend_pages: 1,
        kinship_pages: 1,
        now_classname: '',
    },
    synchronous_data(){                          //更新点赞，收藏，评论的数据
        // if(app.globalData.com_itemid == ''){
        //     return
        // }
        // let data = {
        //     "commentId":app.globalData.com_itemid 
        // }
        // console.log(app.globalData.com_itemid)
        // if (this.data.now_classname == '全部'){
        //     console.log(this.data.all_community)
        //     app.haw_req('get','selectLikesAndCommentsAndSawByCommentId',data)
        //         .then((res)=>{
        //             console.log(res.data)
        //             console.log(this.data.all_community)
        //             let new_data = this.data.all_community
        //             for( let i in this.data.all_community){
        //                 if(this.data.all_community[i].commentId==app.globalData.com_itemid){
        //                     new_data[i].likes = res.data.data.likes
        //                     new_data[i].comments = res.data.data.comments
        //                     new_data[i].saw = res.data.data.saw
        //                     this.setData({
        //                         all_community:new_data
        //                     })
        //                 } 
        //             }
        //         })
        // }else{
        //     console.log(this.data.community_byClass)
        //     app.haw_req('get','selectLikesAndCommentsAndSawByCommentId',data)
        //         .then((res)=>{
        //             console.log(res.data)
        //             console.log(this.data.community_byClass)
        //             let new_data = this.data.community_byClass
        //             for( let i in this.data.community_byClass){
        //                 if(this.data.community_byClass[i].commentId==app.globalData.com_itemid){
        //                     new_data[i].likes = res.data.data.likes
        //                     new_data[i].comments = res.data.data.comments
        //                     new_data[i].saw = res.data.data.saw
        //                     this.setData({
        //                         community_byClass:new_data
        //                     })
        //                 } 
        //             }
        //         })
        // }
      
        

    },
    onRefresh: function () {
        //导航条加载动画
        wx.showNavigationBarLoading()
        // var that = this;
        // that.setData({
        //     currentTab: 0 //当前页的一些初始数据，视业务需求而定
        // })

        //loading 提示框
        wx.showLoading({
            title: 'Loading...',
        })
        console.log("下拉刷新啦");
        setTimeout(function () {
            wx.hideLoading();
            wx.hideNavigationBarLoading();
            //停止下拉刷新
            wx.stopPullDownRefresh();
        }, 1000)
        if(this.data.now_classname == '全部'){
            this.get_all_community(1)
            this.setData({
                all_pages:1
            })
        }else{
            this.onpull_byclassname(this.data.now_classname)
            this.setData({
                love_pages: 1,
                friend_pages: 1,
                kinship_pages: 1,
            })
        }
    },
    onpull_byclassname(type) {
        // console.log(type)
        let data = {
            userId: app.globalData.userid,
            page: 1,
            commentCategoryName: type
        }
        app.haw_req('post', 'selectCommentByCommentCategoryAndPage', data)
            .then((res) => {
                this.setData({
                    community_byClass: res.data.data.commentDtoList
                })
                let new_time = this.change_time(this.data.community_byClass)
                // console.log(new_time)
                this.setData({
                    community_byClass: new_time
                })
            })
    },
    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function () {
        this.onRefresh();
    },

    get_community_byClass(event, page) {
        if (event.detail.title == '全部') {
            this.get_all_community(1)
            return
        }
        this.data.now_classname = event.detail.title
        let data = {
            userId: app.globalData.userid,
            page: page||1,
            commentCategoryName: event.detail.title
        }
        app.haw_req('post', 'selectCommentByCommentCategoryAndPage', data)
            .then((res) => {
                // console.log(res.data)
                this.setData({
                    community_byClass: res.data.data.commentDtoList
                })
                let new_time = this.change_time(this.data.community_byClass)
                // console.log(new_time)
                this.setData({
                    community_byClass: new_time
                })
            })
    },
    get_community_classname() {
        app.haw_req('get', 'selectAllCommentCategoryNameAndCounts')
            .then((res) => {
                // console.log(res)
                this.setData({
                    community_class: res.data.data
                })
                // console.log(this.img_Url)
            })

    },
    get_all_community(page) {
        this.data.now_classname = '全部'
        let data = {
            userId: app.globalData.userid,
            page: page
        }
        app.haw_req('post', 'selectAllCommentByPage', data)
            .then((res) => {
                console.log(res.data)
                // let new_data = res.data.data.commentDtoList
                // app.haw_req('get', 'selectCommentAddressByCommentId', data)
                // .then((res) => {
                //     for(let i in res.data.data){
                //         if(new_data[i].commentId==res.data.data[i].commentId){
                //              new_data[i].commentAddress=res.data.data[i].commentAddress
                //         }
                //     }
                // })
                this.setData({
                    all_community: res.data.data.commentDtoList
                })
                let new_time = this.change_time(this.data.all_community)
                this.setData({
                    all_community: new_time
                })
            })
    },
    change_time(data) {
        let now_time = data
        for (let i in data) {
            now_time[i].commentTime = this.GMTToStr(data[i].commentTime)
        }
        return now_time
    },
    GMTToStr(time) { //时间格式转化
        let date = new Date(time)
        let Str = date.getFullYear() + '-' +
            (date.getMonth() + 1) + '-' +
            date.getDate() + ' ' +
            date.getHours() + ':' +
            date.getMinutes() + ':' +
            date.getSeconds()
        return Str
    },
    go_com_item(event) {
        let com_itemid = event.currentTarget.dataset.comid
        app.globalData.com_itemid = com_itemid
        wx.navigateTo({
            url: '../table_com_item/table_com_item'
        })
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.get_all_community(1)
        this.get_community_classname(1)
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
        this.synchronous_data()
    },

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
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
        if (this.data.now_classname == '全部') {
            this.flip_all()
        } else if (this.data.now_classname == "爱情") {
            this.flip_love()
        } else if (this.data.now_classname == "友情") {
            this.flip_friend()
        } else if (this.data.now_classname == "亲情") {
            this.flip_kinship()
        }
    },
    flip_all() {
        this.data.all_pages++
        let data = {
            userId: app.globalData.userid,
            page: this.data.all_pages
        }
        console.log(this.data.all_pages)
        app.haw_req('post', 'selectAllCommentByPage', data)
            .then((res) => {
                console.log(res.data)
                if (res.data.code == 13 && res.data.data.commentDtoList == '') {
                    this.data.all_pages--
                    return
                }
                // const {all_community} = this.data
                let now_all_community = this.data.all_community
                console.log(now_all_community)
                for (let i of res.data.data.commentDtoList) {
                    now_all_community.push(i)
                }
                this.setData({
                    all_community: now_all_community
                })
                let new_time = this.change_time(this.data.all_community)
                this.setData({
                    all_community: new_time
                })


            })
    },
    flip_byclass(type) {
        let flip_pages = ''
        console.log(type)
        if (type == '爱情') {
            this.data.love_pages++
            flip_pages = this.data.love_pages      
            console.log(this.data.love_pages)
        } else if (type == "友情") {
            this.data.friend_pages++
            flip_pages = this.data.friend_pages
            // console.log(this.data.friend_pages)
        } else if (type == "亲情") {
            this.data.kinship_pages++
            flip_pages = this.data.kinship_pages
            // console.log(this.data.kinship_pages)
        }
        let data = {
            userId: app.globalData.userid,
            page: flip_pages,
            commentCategoryName: this.data.now_classname
        }
        app.haw_req('post', 'selectCommentByCommentCategoryAndPage', data)
            .then((res) => {
                console.log(res.data)
                if (res.data.code == 13 && res.data.data.commentDtoList == '') {
                    if (type == '爱情') {
                        this.data.love_pages--
                    } else if (type == "友情") {
                        this.data.friend_pages--
                    } else if (type == "亲情") {
                        this.data.kinship_pages--
                    }
                    return
                }
                console.log(res.data)
                let res_class_data = this.data.community_byClass
                console.log(res_class_data)
                let new_time  = res.data.data.commentDtoList
                for (let i in res.data.data.commentDtoList) {
                    new_time[i].commentTime = this.GMTToStr( new_time[i].commentTime)
                    res_class_data.push(new_time[i])
                }
                this.setData({
                    community_byClass:res_class_data
                })
            })
    },
    flip_love() {
        this.flip_byclass('爱情')
    },
    flip_friend() {
        this.flip_byclass('友情')
    },
    flip_kinship() {
        this.flip_byclass('亲情')
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})