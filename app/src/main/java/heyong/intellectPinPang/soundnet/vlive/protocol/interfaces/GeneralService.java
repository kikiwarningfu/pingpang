package heyong.intellectPinPang.soundnet.vlive.protocol.interfaces;


import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.AppVersionResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.GiftListResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.MusicListResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.OssPolicyResponse;
import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.RefreshTokenResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GeneralService {
    @GET("ent/v1/app/version")
    Call<AppVersionResponse> requestAppVersion(@Header("reqId") long reqId, @Header("reqType") int reqType,
                                               @Query("appCode") String appCode, @Query("osType") int osType,
                                               @Query("terminalType") int terminalType, @Query("appVersion") String appVersion);

    @GET("ent/v1/gifts")
    Call<GiftListResponse> requestGiftList(@Header("reqId") long reqId, @Header("reqType") int reqType);

    @GET("ent/v1/musics")
    Call<MusicListResponse> requestMusicList(@Header("reqId") long reqId, @Header("reqType") int reqType);

    @GET("ent/v1/room/{roomId}/token/refresh")
    Call<RefreshTokenResponse> requestRefreshToken(@Header("reqId") long reqId, @Header("reqType") int reqType,
                                                   @Header("token") String token, @Query("roomId") String roomId);

    @GET("ent/v1/file/policy")
    Call<OssPolicyResponse> requestOssPolicy(@Header("reqId") long reqId, @Header("reqType") int reqType,
                                             @Header("token") String token, @Query("type") int type);

    @GET("ent/v1/file/policy")
    Call<OssPolicyResponse> requestOssPolicy(@Body RequestBody body);



}
