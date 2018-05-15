package tbc.techbytecare.kk.dream11client;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import br.com.bloder.magic.view.MagicButton;
import tbc.techbytecare.kk.dream11client.Common.Common;
import tbc.techbytecare.kk.dream11client.Model.User;

public class LogInActivity extends AppCompatActivity {

    MagicButton magicBtnFb, magicBtnGoogle;

    Button btnLogIn;

    EditText edtPassword,edtEmail;

    TextView txtRegister,txtForgotPwd;

    FirebaseAuth mAuth;
    DatabaseReference users;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LOG IN & PLAY");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAuth = FirebaseAuth.getInstance();

        users = FirebaseDatabase.getInstance().getReference().child("Users");

        btnLogIn = findViewById(R.id.btnLogIn);

        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);

        txtRegister = findViewById(R.id.txtRegister);
        txtForgotPwd = findViewById(R.id.txtForgotPwd);

        magicBtnFb = findViewById(R.id.magicBtnFb);
        magicBtnGoogle = findViewById(R.id.magicBtnGoogle);

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this,RegisterActivity.class));
            }
        });

        magicBtnFb.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogInActivity.this, "Facebook Log In..", Toast.LENGTH_SHORT).show();
            }
        });

        magicBtnGoogle.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogInActivity.this, "Google Log In..", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password))    {

                    dialog = new ProgressDialog(LogInActivity.this);
                    dialog.setTitle("User Log In");
                    dialog.setMessage("Please wait while we check your credentials...");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                    loginUser(email, password);
                }
            }
        });
    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())    {

                    dialog.dismiss();

                    String currentUserID = mAuth.getCurrentUser().getUid();
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();

                    users.child(currentUserID).child("deviceToken").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(LogInActivity.this, "Welcome...", Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(LogInActivity.this, HomeActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(mainIntent);
                            finish();
                        }
                    });
                }
                else    {
                    dialog.hide();

                    String taskResult = task.getException().getMessage().toString();

                    Toast.makeText(LogInActivity.this, "ERROR : "+taskResult, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
