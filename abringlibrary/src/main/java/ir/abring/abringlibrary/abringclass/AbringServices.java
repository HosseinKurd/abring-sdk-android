package ir.abring.abringlibrary.abringclass;

import com.orhanobut.hawk.Hawk;

import ir.abring.abringlibrary.AbringConstant;
import ir.abring.abringlibrary.models.abringregister.AbringResult;

public class AbringServices {

    public static Object getUser() {
        Object user = null;
        if (Hawk.contains(AbringConstant.ABRING_USER_INFO))
            user = Hawk.get(AbringConstant.ABRING_USER_INFO, null);
        return user;
    }

    public static String getToken() {
        AbringResult user = (AbringResult) getUser();
        return user.getToken();
    }

    public static void setUser(AbringResult result) {
        Hawk.put(AbringConstant.ABRING_USER_INFO, result);
        if (result != null)
            Hawk.put(AbringConstant.ABRING_TOKEN, result.getToken());
        else
            Hawk.put(AbringConstant.ABRING_TOKEN, null);
    }
}
