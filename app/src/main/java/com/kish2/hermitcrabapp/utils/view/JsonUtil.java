package com.kish2.hermitcrabapp.utils.view;

import com.kish2.hermitcrabapp.bean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonUtil {

    public static User toUserBean(JSONObject userObj, JSONObject bindObj) throws JSONException {
        User user = new User();
        user.setUid(userObj.getLong("uid"));
        user.setUsername(userObj.getString("username"));
        user.setGender(userObj.getString("gender"));
        user.setEmail(bindObj.getString("email"));
        user.setDepartment(bindObj.getString("department"));
        user.setMobile(bindObj.getString("mobile"));
        user.setGrade(bindObj.getString("grade"));
        user.setStudentId(bindObj.getString("studentId"));
        return user;
    }

    public static List<User> toUserBeanList(JSONObject userListObj, JSONObject bindListObj) throws JSONException {
        return null;
    }
}
