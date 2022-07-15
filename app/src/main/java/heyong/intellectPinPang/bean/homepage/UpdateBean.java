package heyong.intellectPinPang.bean.homepage;
//http://39.103.221.204:19590/xlMatchInfo/updateVersion
//Header Authorization eyJhbGciOiJSUzI1NiJ9.eyJ1c2VySWQiOiI1MTE0ODYxNjAwODMxMjgzMzYiLCJuYW1lIjoi546L5a6BIiwic2FwIjoiMTMyOTI4MDMyNjkiLCJleHAiOjE2MjQxNTgyNTB9.EGGIsTivXF4Dkz2GkeXQcrq5WfCh2UPfUqxZpnXOrws2T3LoqXGyQGh0AsOdl_dONac7b__cqAwifUDUN23lTAPbJS34-WhMFZU4oDCoFVRedEqbnDIXv0Y5WauIR6YJQwZc3c1z_QEa_y0Od3MLn7_-HYrQdMqeD7P2GAlyVoQ
//Body
//{"code":"11","description":"优化已知BUG","downloadUrl":"http://images.xlttsports.com/Androidapk/Release/app-release.apk","usable":"0"}
/*
更新后台Url
http://39.103.221.204:19580/updateDownLoadUrl?url=http://images.xlttsports.com/app-release-0526-v2.apk
 */
//http://backstage.xlttsports.com/appDownLoad/index.html
//{"code":"11","description":"优化已知BUG 直播上线啦","downloadUrl":"http://images.xlttsports.com/Androidapk/Release/app-release.apk","usable":"0"}

//http://images.xlttsports.com/app-release-08-11.apk
//基准包------ app-0721-16-13-12
//http://images.xlttsports.com/Androidapk/Release/app-release.apk

//每次七牛云目录
//基准包------ app-0721-16-13-12
//
public class UpdateBean {

//   http://images.xlttsports.com/Androidapk/Release/app-release.apk
//    http://images.xlttsports.com/Androidapk/Releaseapp-release.apk
    /**
     * code : 1.0
     * createTime : 2021-01-21
     * description : hello
     * downloadUrl : www.baidu.com
     * id : 1
     * lastVersion : 1
     * modifyTime : 2021-01-21
     * name : app
     * releaseDate : 2021-01-21
     * released : 1
     * type : 1
     * usable : 1
     */

    private int code;
    //    private String createTime;
    private String description;
    private String downloadUrl;
    //    private String id;
//    private String lastVersion;
//    private String modifyTime;
//    private String name;
//    private String releaseDate;
//    private String released;
//    private String type;
    private String usable;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description == null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadUrl() {
        return downloadUrl == null ? "" : downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUsable() {
        return usable == null ? "0" : usable;
    }

    public void setUsable(String usable) {
        this.usable = usable;
    }
}
