package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.logic.dao.UserDao;
import com.example.myapplication.logic.model.UserLogin;
import com.example.myapplication.logic.utils.SharedPreferencesUtils;
import com.example.myapplication.logic.utils.Tools;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText et_name;
    private EditText et_password;
    private Button mLoginBtn;
    private CheckBox checkBox_password;
    private CheckBox checkBox_login;
    private ImageView iv_see_password;
    private Button registerBtn;
    private UserDao userDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        userDao = Tools.GetUserDao(this);
        setupEvents();
        initData();

    }

    private void initData() {
        if (firstLogin()) {
            checkBox_login.setChecked(false);
            checkBox_password.setChecked(false);
        }

        if (remenberPassword()) {
            checkBox_password.setChecked(true);
            setTextNameAndPassword();
        } else {
            setTextName();
        }
        if (autoLogin()) {
            checkBox_login.setChecked(true);
        }
    }

    private void login() {
        if (getAccount().isEmpty()) {
            showToast("你输入的账号为空！");
            return;
        }
        if (getPassword().isEmpty()) {
            showToast("你输入的密码为空！");
            return;
        }

        setLoginBtnClickable(false);
        UserLogin userLogin = userDao.selectOne(getAccount());
        if (userLogin == null) {
            showToast("输入的登录账号不正确");
            return;
        }
        if (getAccount().equals(userLogin.getName()) && getPassword().equals(userLogin.getPassword())) {
            showToast("登录成功！！！");
            loadCheckBoxState();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            showToast("输入的密码不正确！！！");
        }
        setLoginBtnClickable(true);
    }

    public void setTextNameAndPassword() {
        et_name.setText("" + getLocalName());
        et_password.setText("" + getLocalPassword());
    }

    public void setTextName() {
        et_name.setText("" + getLocalName());
    }

    private void setupEvents() {
        mLoginBtn.setOnClickListener(this);
        checkBox_password.setOnCheckedChangeListener(this);
        checkBox_login.setOnCheckedChangeListener(this);
        iv_see_password.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    private boolean autoLogin() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean autoLogin = helper.getBoolean("autoLogin", false);
        return autoLogin;
    }

    private void initViews() {
        mLoginBtn = findViewById(R.id.btn_login);
        et_name = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        checkBox_password = findViewById(R.id.checkBox_password);
        checkBox_login = findViewById(R.id.checkBox_login);
        iv_see_password = findViewById(R.id.iv_see_password);
        registerBtn = findViewById(R.id.register);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loadUserName();
                login();
                break;
            case R.id.iv_see_password:
                setPasswordVisibility();
                break;
            case R.id.register:
                register();
                break;
            default:
        }
    }

    private void register() {
        if (getAccount().isEmpty()) {
            showToast("用户名不能为空！！！");
            return;
        }
        if (getPassword().isEmpty()) {
            showToast("密码不能为空！！！");
            return;
        }
        UserLogin userLogin = userDao.selectOne(getAccount());
        if (userLogin != null) {
            showToast("用户已经存在");
            return;
        }
        UserLogin userLogin1 = new UserLogin(getAccount(), getPassword());
        userDao.insertAll(userLogin1);
        et_name.setText("");
        et_password.setText("");
        showToast("注册成功！！！");
    }

    public void loadUserName() {
        if (!getAccount().equals("") || !getAccount().equals("请输入登录账号")) {
            SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
            helper.putValues(new SharedPreferencesUtils.ContentValue("name", getAccount()));
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton == checkBox_password) {
            if (!b) {
                checkBox_login.setChecked(false);
            }
        } else if (compoundButton == checkBox_login) {
            if (b) {
                checkBox_password.setChecked(true);
            }
        }

    }

    public String getLocalName() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String name = helper.getString("name");
        return name;
    }

    public String getLocalPassword() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        String password = helper.getString("password");
        return password;
    }

    private boolean remenberPassword() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean remenberPassword = helper.getBoolean("remenberPassword", false);
        return remenberPassword;
    }

    private boolean firstLogin() {
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");
        boolean first = helper.getBoolean("first", true);
        if (first) {
            helper.putValues(new SharedPreferencesUtils.ContentValue("first", false),
                    new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("name", ""),
                    new SharedPreferencesUtils.ContentValue("password", ""));
            return true;
        }
        return false;
    }

    private void setPasswordVisibility() {
        if (iv_see_password.isSelected()) {
            iv_see_password.setSelected(false);
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            iv_see_password.setSelected(true);
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }

    }

    public String getAccount() {
        return et_name.getText().toString().trim();//去掉空格
    }

    private void loadCheckBoxState() {
        loadCheckBoxState(checkBox_password, checkBox_login);
    }

    public void loadCheckBoxState(CheckBox checkBox_password, CheckBox checkBox_login) {

        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        SharedPreferencesUtils helper = new SharedPreferencesUtils(this, "setting");

        //如果设置自动登录
        if (checkBox_login.isChecked()) {
            //创建记住密码和自动登录是都选择,保存密码数据
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", true),
                    new SharedPreferencesUtils.ContentValue("autoLogin", true),
                    new SharedPreferencesUtils.ContentValue("password", getPassword()));

        } else if (!checkBox_password.isChecked()) { //如果没有保存密码，那么自动登录也是不选的
            //创建记住密码和自动登录是默认不选,密码为空
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", false),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("password", ""));
        } else if (checkBox_password.isChecked()) {   //如果保存密码，没有自动登录
            //创建记住密码为选中和自动登录是默认不选,保存密码数据
            helper.putValues(
                    new SharedPreferencesUtils.ContentValue("remenberPassword", true),
                    new SharedPreferencesUtils.ContentValue("autoLogin", false),
                    new SharedPreferencesUtils.ContentValue("password", getPassword()));
        }
    }

    public String getPassword() {
        return et_password.getText().toString().trim();//去掉空格
    }

    public void setLoginBtnClickable(boolean clickable) {
        mLoginBtn.setClickable(clickable);
    }


    public void showToast(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}