package com.kish2.hermitcrabapp.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.kish2.hermitcrabapp.utils.App.USER;
import static com.kish2.hermitcrabapp.utils.App.LOAD_USER_SUCCESS;

public class AppAndJSONUtil {
    public static final int SERVER_OPERATED_FAILURE = 222;
    public static final String key_server_status = "server_code";
    public static final String key_server_msg = "server_msg";
    public static final String key_user_info = "user";
    public static final String key_user_bind_info = "user_bind_info";
    public static final String key_user_uid = "uid";
    public static final String key_username = "username";
    public static final String key_user_gender = "gender";
    public static final String key_user_email = "email";
    public static final String key_user_department = "department";
    public static final String key_user_mobile = "mobile";
    public static final String key_user_grade = "grade";
    public static final String key_user_student_id = "studentId";

    // TODO
    public enum DO_JSON_TYPE {
        SET_USER,
        SET_ITEM,
    }

    public enum DO_JSON_RET {
        RET_STATUS,
        RET_MSG,
        RET_OBJ,
    }

    public static Map<DO_JSON_RET, Object> doJsonObject(String rowData, DO_JSON_TYPE type) throws JSONException {
        /* 检验服务器返回状态
         * 如果是not available则直接返回服务端消息 */
        Map<DO_JSON_RET, Object> res = new HashMap<>();
        JSONObject jsonData = new JSONObject(rowData);
        int status = jsonData.getInt(key_server_status);
        /* 服务端返回msg可以直接压入 */
        res.put(DO_JSON_RET.RET_MSG, jsonData.getString(key_server_msg));
        /* 服务端访问成功，但是操作失败 */
        if (status == SERVER_OPERATED_FAILURE) {
            res.put(DO_JSON_RET.RET_STATUS, false);
            res.put(DO_JSON_RET.RET_OBJ, null);
            return res;
        } else {
            switch (type) {
                case SET_USER:
                    setUserFormJson(jsonData);
                    res.put(DO_JSON_RET.RET_STATUS, true);
                    res.put(DO_JSON_RET.RET_OBJ, null);
                    return res;
                default:
                    return null;
            }
        }
    }

    public static void setUserFormJson(JSONObject jsonData) throws JSONException {
        JSONObject user = jsonData.getJSONObject(key_user_info);
        JSONObject user_bind_info = jsonData.getJSONObject(key_user_bind_info);
        USER.setUid(user.getLong(key_user_uid));
        USER.setUsername(user.getString(key_username));
        USER.setGender(user.getString(key_user_gender));
        USER.setEmail(user_bind_info.getString(key_user_email));
        USER.setDepartment(user_bind_info.getString(key_user_department));
        USER.setMobile(user_bind_info.getString(key_user_mobile));
        USER.setGrade(user_bind_info.getString(key_user_grade));
        String studentId = user_bind_info.getString(key_user_student_id);
        if (studentId.equals("未绑定")) {
            USER.setInfoBind(false);
        } else USER.setInfoBind(true);
        USER.setStudentId(studentId);
        LOAD_USER_SUCCESS = true;
        App.IS_USER_LOG_IN = true;
    }
}
