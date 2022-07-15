package heyong.intellectPinPang.soundnet.vlive.agora.rtm.model;


import heyong.intellectPinPang.soundnet.vlive.protocol.model.response.EnterRoomResponse;

public class PKStateMessage extends AbsRtmMessage {
    public PKStateMessageBody data;

    public static class PKStateMessageBody {
        public int event;
        public int state;
        public long startTime;
        public long countDown;
        public int remoteRank;
        public int localRank;
        public int result;
        public EnterRoomResponse.RemoteRoom remoteRoom;
        public EnterRoomResponse.RelayConfig relayConfig;
    }
}