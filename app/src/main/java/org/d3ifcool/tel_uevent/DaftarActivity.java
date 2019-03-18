package org.d3ifcool.tel_uevent;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DaftarActivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextEmail;
    EditText editTextPassword;

    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;

    Button buttonRegister;

    SqliteHelper sqliteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        sqliteHelper = new SqliteHelper(this);
        initTextViewLogin();
        initViews();
        buttonRegister = findViewById(R.id.button2);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = editTextUsername.getText().toString();
                    String Email = editTextEmail.getText().toString();
                    String Password = editTextPassword.getText().toString();
                    String hakAkses = "User";

                    if (!sqliteHelper.isUsernameExists(UserName)){
                        sqliteHelper.addUser(new User(null,UserName,Password,null,null,null,null,null,null,Email));
                        Snackbar.make(buttonRegister, "Berhasil membuat Akun", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);
                    }else {
                        Snackbar.make(buttonRegister, "Username sudah ada!!", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void initViews() {
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
//        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
//        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
//        textInputLayoutUsername = (TextInputLayout) findViewById(R.id.textInputLayoutUserName);
        buttonRegister = (Button) findViewById(R.id.button);
    }

    private void initTextViewLogin() {
        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public boolean validate() {
        boolean valid = false;

        //Get values from EditText fields
        String UserName = editTextUsername.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false;
            textInputLayoutUsername.setError("Please enter valid username!");
        } else {
            if (UserName.length() > 5) {
                valid = true;
//                textInputLayoutUsername.setError(null);
            } else {
                valid = false;
//                textInputLayoutUsername.setError("Username is to short!");
            }
        }

        //Handling validation for Email field
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false;
//            textInputLayoutEmail.setError("Please enter valid email!");
        } else {
            valid = true;
//            textInputLayoutEmail.setError(null);
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false;
//            textInputLayoutPassword.setError("Please enter valid password!");
        } else {
            if (Password.length() > 5) {
                valid = true;
//                textInputLayoutPassword.setError(null);
            } else {
                valid = false;
//                textInputLayoutPassword.setError("Password is to short!");
            }
        }


        return valid;
    }
}
