package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * Created by joybar on 2017/7/27.
 */
public class GsonUtil {


    public static <T> T parseJsonStrToBean(String jsonData,Type type) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    public static String parseBeanToStr(Object objBean) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String obj = gson.toJson(objBean);
        return obj;
    }


}
