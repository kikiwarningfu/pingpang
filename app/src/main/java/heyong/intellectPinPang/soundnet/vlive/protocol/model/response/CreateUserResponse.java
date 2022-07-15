package heyong.intellectPinPang.soundnet.vlive.protocol.model.response;

public class CreateUserResponse extends Response {
    public CreateUserInfo data;

    public class CreateUserInfo {
        public String userId;
    }
}
