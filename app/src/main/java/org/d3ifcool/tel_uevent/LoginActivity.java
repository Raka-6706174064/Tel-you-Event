package org.d3ifcool.tel_uevent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;

    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutPassword;

    SqliteHelper sqliteHelper;

    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sqliteHelper = new SqliteHelper(this);
        initCreateAccountTextView();
        initViews();
        buttonLogin = findViewById(R.id.button);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check input valid or not
                if (validate()){
                    String username = editTextUsername.getText().toString();
                    String password = editTextPassword.getText().toString();

                    User currentUser = sqliteHelper.Authenticate(new User(null,username,password,null,null,null,null,null,null,null));

                    if (currentUser != null){
                        //User Berhasil Login dan di pindahkan ke main Activity
                        Snackbar.make(buttonLogin," Berhasil Login",Snackbar.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity_admin.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Snackbar.make(buttonLogin, "Failed to log in , please try again", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initViews() {
        editTextUsername = (EditText) findViewById(R.id.editText);
        editTextPassword = (EditText) findViewById(R.id.editText2);
//        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
//        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        buttonLogin = (Button) findViewById(R.id.button);
    }

    private static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;

    }

    private void initCreateAccountTextView() {
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);

        textViewCreateAccount.setText(fromHtml("<font color='#ffffff'>I don't have account yet. </font><font color='#0c0099'>create one</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, DaftarActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean validate(){
        boolean valid = false;

        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if (username.isEmpty()){
            valid = false;
//            textInputLayoutUsername.setError("Username Error");
        }else{
            valid = true;
//            textInputLayoutUsername.setError(null);
        }

        if (password.isEmpty()){
            valid = false;
//            textInputLayoutPassword.setError("Password Error");
        }else{
            if (password.length()>5){
                valid = true;
//                textInputLayoutPassword.setError(null);
            }else{
                valid = false;
//                textInputLayoutPassword.setError("Password Terlalu Pendek");
            }
        }
        return valid;
    }
}
