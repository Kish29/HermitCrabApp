package com.kish2.hermitcrabapp;

import android.Manifest;
import android.text.TextUtils;

import com.kish2.hermitcrabapp.utils.sql.SearchHistory;
import com.kish2.hermitcrabapp.utils.ValidateFormInput;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.activities.LoginViewImpl;
import com.kish2.hermitcrabapp.view.activities.MainActivity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit MyTest, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testEmail() {
        String[] emails = {"875691208@qq.com", "qwr91779y7@163.com", "12479sdaf9 nlsa n@lsadf-12=.joh.a"};

        for (String email : emails) {
            System.out.println(ValidateFormInput.isValidEmail(email));
        }
    }

    @Test
    public void getContextTest() {
        BaseActivity baseActivity = new BaseActivity();
        System.out.println(baseActivity.baseActivityGetContext());

        MainActivity mainActivity = new MainActivity();
        System.out.println(mainActivity.baseActivityGetContext());

        LoginViewImpl loginView = new LoginViewImpl();
        System.out.println(loginView.baseActivityGetContext());
    }

    @Test
    public void SQLiteTest() {
        SearchHistory sqLite = new SearchHistory(new MainActivity().baseActivityGetContext());
        String string = "第一条搜索数据";
        sqLite.insert(string);
        System.out.println(sqLite.matchedHistory(string));
    }

}