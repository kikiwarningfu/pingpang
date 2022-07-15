package heyong.handong.framework.api;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Params {

    private HashMap<String, String> params = new HashMap();

    private Params() {
//        params.put("token", AccountHelper.getToken());
    }

    public static Params newParams() {
        return new Params();
    }

    public Params put(String key, String value) {
        params.put(key, value);
        return this;
    }

    public Params putAll(Map<String,String> map){
        params.putAll(map);
        return this;
    }

    public HashMap<String, String> params() {
        //  删除value 为null 的元素
        for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> next = iterator.next();
            if (next.getValue() == null|| TextUtils.isEmpty(next.getValue())) {
                iterator.remove();
            }
        }
        return params;
    }

}
