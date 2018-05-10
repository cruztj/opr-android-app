package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{

    private Button latestNewsButton;
    private Button jobOpeningsButton;
    private Button studentAnnouncementsButton;
    private ImageButton optionsButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();

        mAuth = FirebaseAuth.getInstance();
    }

    private void initLayout(){
        latestNewsButton = (Button) findViewById(R.id.latestNewsButton);
        jobOpeningsButton = (Button) findViewById(R.id.jobOpeningsButton);
        studentAnnouncementsButton = (Button) findViewById(R.id.studentAnnouncementsButton);
        optionsButton = (ImageButton) findViewById(R.id.moreButton);

        latestNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LatestNews.class);
                startActivity(intent);
            }
        });

        jobOpeningsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JobOpenings.class);
                startActivity(intent);
            }
        });

        studentAnnouncementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentAnnouncements.class);
                startActivity(intent);
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v);
            }
        });
    }

    public void showPopUp(View v){
        PopupMenu popUp = new PopupMenu(this, v);
        MenuInflater inflater = popUp.getMenuInflater();
        inflater.inflate(R.menu.home_options_menu, popUp.getMenu());
        popUp.show();

        popUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuLogIn:
                        AlertDialog.Builder logInDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        View logInView = getLayoutInflater().inflate(R.layout.dialog_login, null);
                        final EditText emailEditText = (EditText) logInView.findViewById(R.id.emailText);
                        final EditText passwordEditText = (EditText) logInView.findViewById(R.id.passwordText);
                        Button logInButton = (Button) logInView.findViewById(R.id.logInButton);

                        logInButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                authenticateLogIn(emailEditText, passwordEditText);
                            }
                        });

                        logInDialogBuilder.setView(logInView);
                        AlertDialog logInAlertDialog = logInDialogBuilder.create();
                        logInAlertDialog.show();

                        return true;
                    case  R.id.menuAbout:
                        Toast.makeText(MainActivity.this, "About pressed", Toast.LENGTH_LONG).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }



    private void authenticateLogIn(EditText emailEditText, EditText passwordEditText){
        String email = emailEditText.getText().toString().trim();
        String pass = passwordEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please enter a valid email address");
            emailEditText.requestFocus();
            return;
        }
        if(pass.length() < 8){
            passwordEditText.setError("Password should be at least 8 characters");
            passwordEditText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("USER SIGN IN", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    Intent intent = new Intent(MainActivity.this, AdminPage.class);
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("USER SIGN IN", "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /*public void getData() throws IOException {
        Log.i("MainActivity.getData", "CONNECTING TO API...");
        HttpClient client = new DefaultHttpClient();
        String url = "http://54.186.68.67:3000/suppliers/";
        HttpGet get = new HttpGet(url);
        HttpResponse responseGet = client.execute(get);
        HttpEntity resEntityGet = responseGet.getEntity();

        if(resEntityGet != null){
            String response = EntityUtils.toString(resEntityGet);
            //parse JSON here
            String[] tokens = response.replaceAll("[\\[\\{]","").split("\\}");
            for(int i=0; i<tokens.length; i++)
                Log.i("GET RESPONSE", tokens[i]);
        }
    }*/


}
