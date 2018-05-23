package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity{

    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private boolean loggedInStudentFlag = false;

    private Button latestNewsButton;
    private Button jobOpeningsButton;
    private Button studentAnnouncementsButton;
    private ImageButton optionsButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser() != null) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            String email[] = currentUser.getEmail().split("@");
            if (!email[1].equals("up.edu.ph")) {
                loggedInStudentFlag = false;
                signOut();
                Toast.makeText(MainActivity.this, "Please use UP Mail", Toast.LENGTH_LONG).show();
            }
        }

        initLayout();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            FirebaseUser currentFirebaseUser = mAuth.getCurrentUser();
            String email[] = currentFirebaseUser.getEmail().split("@");
            if (!email[1].equals("up.edu.ph")) {
                loggedInStudentFlag = false;
                signOut();
                Toast.makeText(MainActivity.this, "Please use UP Mail", Toast.LENGTH_LONG).show();
            } else if(email[1].equals("up.edu.ph")){
                loggedInStudentFlag = true;
//              updateUI(currentUser);
            }
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("onActivityResult", "Google sign in failed", e);
//                updateUI(null);
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //check domain name first
                            Log.d("Acct email",acct.getEmail());
                            String emailSplit[] = acct.getEmail().split("@");
                            if(!emailSplit[1].equals("up.edu.ph")){
                                signOut();
                                Toast.makeText(MainActivity.this, "Please use UP Mail", Toast.LENGTH_LONG).show();
                            } else if(emailSplit[1].equals("up.edu.ph")){
                                // Sign in success, update UI with the signed-in user's information
                                loggedInStudentFlag = true;
                                Log.d("AuthCredential", "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(MainActivity.this, StudentAnnouncements.class);
                                startActivity(intent);

//                                updateUI(user);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("AuthCredential", "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        loggedInStudentFlag = false;

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        updateUI(null);
                    }
                });
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
                signIn();
                if(loggedInStudentFlag == true){
                    Intent intent = new Intent(MainActivity.this, StudentAnnouncements.class);
                    startActivity(intent);
                }
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

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    // User is signed in
                                    Intent i = new Intent(MainActivity.this, AdminPage.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                } else {
                                    authenticateLogIn(emailEditText, passwordEditText);
                                }
                            }
                        });

                        logInDialogBuilder.setView(logInView);
                        AlertDialog logInAlertDialog = logInDialogBuilder.create();
                        logInAlertDialog.show();

                        return true;

                    case R.id.menuContactUs:
                        AlertDialog.Builder contactDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        View contactView = getLayoutInflater().inflate(R.layout.dialog_contactus, null);

                        contactDialogBuilder.setView(contactView);

                        AlertDialog contactAlertDialog = contactDialogBuilder.create();
                        contactAlertDialog.show();

                        return true;

                    case  R.id.menuAbout:
                        AlertDialog.Builder aboutDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        View aboutView = getLayoutInflater().inflate(R.layout.dialog_about, null);

                        aboutDialogBuilder.setView(aboutView);
                        AlertDialog aboutAlertDialog = aboutDialogBuilder.create();
                        aboutAlertDialog.show();

                        return true;

                    case R.id.menuLogOut:
                        signOut();
                        Toast.makeText(MainActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();

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
        if(pass.length() < 8) {
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
}
