package com.kish2.hermitcrabapp;

import com.kish2.hermitcrabapp.sqlite.SearchHistory;
import com.kish2.hermitcrabapp.utils.InputCheckUtil;
import com.kish2.hermitcrabapp.view.BaseActivity;
import com.kish2.hermitcrabapp.view.activities.LoginActivity;
import com.kish2.hermitcrabapp.view.activities.MainActivity;

import org.junit.Test;

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
            System.out.println(InputCheckUtil.isValidEmail(email));
        }
    }

    @Test
    public void getContextTest() {
        BaseActivity baseActivity = new BaseActivity();
        System.out.println(baseActivity.baseActivityGetContext());

        MainActivity mainActivity = new MainActivity();
        System.out.println(mainActivity.baseActivityGetContext());

        LoginActivity loginView = new LoginActivity();
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