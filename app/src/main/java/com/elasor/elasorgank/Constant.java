package com.elasor.elasorgank;

/**
 * @author Elasor
 */
public class Constant {

    public static class Url{

//        1.随机数据
//        地址：http://gank.io/api/random/data/分类/个数
//        分类：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
//
//        2.每日数据
//        地址：http://gank.io/api/day/年/月/日
//
//        3.分类数据
//        地址：http://gank.io/api/data/数据类型/请求个数/第几页
//        分类：福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
//
//        4.提交干货
//        地址：https://gank.io/api/add2gank
//        方式：POST
//
//        5.获取发过干货日期接口
//        地址：http://gank.io/api/day/history
//
//        6.获取特定日期网站数据
//        地址：http://gank.io/api/history/content/day/2016/05/11
//
//        7.获取某几日干货网站数据
//        地址：http://gank.io/api/history/content/数量/页数
//
//        8.搜索
//        地址：http://gank.io/api/search/query/listview/category/分类/count/10/page/1
//        分类：all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App

        public static final String BASE_URL = "http://gank.io/api/";
    }

    public static class ShareKey{

    }

    public static class Other{
        public static final String CHANNEL_DAILY = "每日推荐";
        public static final String CHANNEL_WELFARE = "福利";
        public static final String CHANNEL_VIDEO = "休息视频";
        public static final String CHANNEL_EXPAND = "拓展资源";
        public static final String CHANNEL_FRONT = "前端";
        public static final String CHANNEL_ANDROID = "Android";
        public static final String CHANNEL_IOS = "iOS";
        public static final String CHANNEL_RECOMMEND = "瞎推荐";
        public static final String CHANNEL_APP = "App";
    }
}
