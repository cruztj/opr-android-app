package ph.edu.uplb.ics.opruplb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminPage extends AppCompatActivity {

    private EditText postTitleEditText;
    private EditText postContentEditText;
    private EditText postLinkEditText;
    private Button postButton;
    private Button uploadButton;
    private ImageButton backButton;
    private ImageView imageView;
    private ImageButton optionsButton;

    private File mCurrentPhoto;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        mAuth = FirebaseAuth.getInstance();

        postTitleEditText = (EditText) findViewById(R.id.postTitleText);
        postContentEditText = (EditText) findViewById(R.id.postDescriptionText);
        postLinkEditText = (EditText) findViewById(R.id.postLinkText);
        postButton = (Button) findViewById(R.id.postButton);
//        uploadButton = (Button) findViewById(R.id.uploadButton);
        backButton = (ImageButton) findViewById(R.id.backButton);
        optionsButton = (ImageButton) findViewById(R.id.moreButton);
//        imageView = (ImageView) findViewById(R.id.imageView);

//        imageView.setBackgroundColor(Color.rgb(140,140,140));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp(v);
            }
        });

//        uploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent, 0);
//            }
//        });


        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postTitle = postTitleEditText.getText().toString().trim();
                String postContent = postContentEditText.getText().toString().trim();
                if(postTitle.isEmpty()){
                    postTitleEditText.setError("Post title is required");
                    postTitleEditText.requestFocus();
                    return;
                }
                if(postContent.isEmpty()){
                    postContentEditText.setError("Post content is required");
                    postContentEditText.requestFocus();
                    return;
                }
                try {
                    sendDataToServer(createJSONObject(postTitleEditText, postContentEditText, postLinkEditText));
                } catch (JSONException e) {
                    Log.d("AdminPage", "JSONException");
                    e.printStackTrace();
                }
            }
        });
    }

    public void showPopUp(View v){
        PopupMenu popUp = new PopupMenu(this, v);
        MenuInflater inflater = popUp.getMenuInflater();
        inflater.inflate(R.menu.admin_options_menu, popUp.getMenu());
        popUp.show();

        popUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menuLogOut:
                        mAuth.signOut();
                        Intent intent = new Intent(AdminPage.this, MainActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.menuAddAccount:
                        AlertDialog.Builder logInDialogBuilder = new AlertDialog.Builder(AdminPage.this);
                        View addUserView = getLayoutInflater().inflate(R.layout.dialog_adduser, null);
                        final EditText emailEditText = (EditText) addUserView.findViewById(R.id.emailText);
                        final EditText passwordEditText = (EditText) addUserView.findViewById(R.id.passwordText);
                        final EditText passwordConfirmEditText = (EditText) addUserView.findViewById(R.id.passwordConfirmText);
                        Button addUserButton = (Button) addUserView.findViewById(R.id.addUserButton);

                        addUserButton.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v){
                                createUser(emailEditText, passwordEditText, passwordConfirmEditText);
                            }
                        });

                        logInDialogBuilder.setView(addUserView);
                        AlertDialog logInAlertDialog = logInDialogBuilder.create();
                        logInAlertDialog.show();

                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void createUser(EditText emailEditText, EditText passwordEditText, EditText passwordConfirmEditText){
        String email = emailEditText.getText().toString().trim();
        String pass = passwordEditText.getText().toString().trim();
        String passConfirm = passwordConfirmEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Email is required");
            emailEditText.requestFocus();
            return;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please enter a valid email address");
            emailEditText.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }
        if(passConfirm.isEmpty()){
            passwordConfirmEditText.setError("Confirm Password is required");
            passwordConfirmEditText.requestFocus();
            return;
        }
        if(pass.length() < 8){
            passwordEditText.setError("Password should be at least 8 characters");
            passwordEditText.requestFocus();
            return;
        } else if(!pass.equals(passConfirm)){
            passwordEditText.setError("Passwords should match");
            passwordConfirmEditText.setError("Passwords should match");
            passwordEditText.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(AdminPage.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("USER SIGN UP", "signUpWithEmail:success");
                    Toast.makeText(AdminPage.this, "User successfully added", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();

                    Intent intent = new Intent(AdminPage.this, AdminPage.class);
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("USER SIGN IN", "signInWithEmail:failure", task.getException());
                    Toast.makeText(AdminPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 0:
//                if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
//                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                    Cursor cursor = this.getContentResolver().query(data.getData(), filePathColumn, null, null, null);
//                    if (cursor == null || cursor.getCount() < 1) {
//                        mCurrentPhoto = null;
//                        break;
//                    }
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    if(columnIndex < 0) { // no column index
//                        mCurrentPhoto = null;
//                        break;
//                    }
//                    mCurrentPhoto = new File(cursor.getString(columnIndex));
//                    cursor.close();
//                } else {
//                    mCurrentPhoto = null;
//                }
//                break;
//            default: Log.d("Image show", "No image");
//        }
//
//        String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
//
//        final int myVersion = Build.VERSION.SDK_INT;
//        if (myVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
//            if (!checkIfAlreadyHasPermission()) {
//                ActivityCompat.requestPermissions(this,galleryPermissions, 1);
//            }
//        }
//            changeBitmap();
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void changeBitmap(){
//        if (mCurrentPhoto != null) {
//            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhoto.getAbsolutePath());
//            imageView.setImageBitmap(bitmap);
//        }
//    }
//
//    private boolean checkIfAlreadyHasPermission() {
//        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        return result == PackageManager.PERMISSION_GRANTED;
//    }

    @SuppressLint("StaticFieldLeak")
    private void sendDataToServer(JSONObject jsonObject){
        final String jsonString = jsonObject.toString();

        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    return getServerResponse(jsonString);
                } catch (UnsupportedEncodingException e) {
                    Log.d("AdminPushDataEncoding", e.toString());
                } catch (IOException e) {
                    Log.d("AdminPushDataIO", e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(AdminPage.this, "Post created!", Toast.LENGTH_SHORT).show();
                postTitleEditText.setText("");
                postContentEditText.setText("");
                postLinkEditText.setText("");
            }
        }.execute();
    }

    private String getServerResponse(String json) throws IOException {
        String url = "http://192.168.1.160:3001/announcements";
//        String url = "http://10.0.3.42:3001/announcements";

        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Content-type", "application/json");

        DefaultHttpClient client = new DefaultHttpClient();

        BasicResponseHandler handler = new BasicResponseHandler();
        String response = client.execute(post, handler);

        Log.d("Admin Server Response", response);
        return response;
    }


    private JSONObject createJSONObject(EditText postTitleEditText, EditText postContentEditText, EditText postLinkEditText) throws JSONException {
        String postTitle = postTitleEditText.getText().toString().trim();
        String postContent = postContentEditText.getText().toString().trim();
        String postLink = postLinkEditText.getText().toString().trim();
//        String admin = mAuth.getCurrentUser().toString();
        FirebaseUser user = mAuth.getCurrentUser();
        String admin = user.getEmail().toString();
        if(postLink.isEmpty())
            postLink = "null";


        Log.i("AdminPage.getData", "CONNECTING TO API...");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("announcement_title", postTitle);
        jsonObject.put("announcement_text", postContent);
        jsonObject.put("announcement_link", postLink);
        jsonObject.put("admin", admin);

        return jsonObject;
    }
}
