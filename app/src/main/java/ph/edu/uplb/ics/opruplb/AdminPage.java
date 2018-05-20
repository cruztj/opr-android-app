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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AdminPage extends AppCompatActivity {

    private EditText postTitleEditText;
    private EditText postContentEditText;
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
        postButton = (Button) findViewById(R.id.postButton);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        backButton = (ImageButton) findViewById(R.id.backButton);
        optionsButton = (ImageButton) findViewById(R.id.moreButton);
        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setBackgroundColor(Color.rgb(140,140,140));

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

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });


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
                    sendDataToServer(createJSONObject(postTitleEditText, postContentEditText));
                } catch (JSONException e) {
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
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = this.getContentResolver().query(data.getData(), filePathColumn, null, null, null);
                    if (cursor == null || cursor.getCount() < 1) {
                        mCurrentPhoto = null;
                        break;
                    }
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    if(columnIndex < 0) { // no column index
                        mCurrentPhoto = null;
                        break;
                    }
                    mCurrentPhoto = new File(cursor.getString(columnIndex));
                    cursor.close();
                } else {
                    mCurrentPhoto = null;
                }
                break;
            default: Log.d("Image show", "No image");
        }

        String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

        final int myVersion = Build.VERSION.SDK_INT;
        if (myVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyHasPermission()) {
                ActivityCompat.requestPermissions(this,galleryPermissions, 1);
            }
        }
            changeBitmap();

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void changeBitmap(){
        if (mCurrentPhoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhoto.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
        }
    }

    private boolean checkIfAlreadyHasPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("StaticFieldLeak")
    private void sendDataToServer(JSONObject jsonObject){
        final String jsonString = jsonObject.toString();

        new AsyncTask<Void, Void, String>(){
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    return getServerResponse(jsonString);
                } catch (UnsupportedEncodingException e) {
                    Log.d("AdminPushData", e.toString());
                } catch (IOException e) {
                    Log.d("AdminPushData", e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(AdminPage.this, "Post created!", Toast.LENGTH_SHORT).show();
                postTitleEditText.setText("");
                postContentEditText.setText("");
            }
        }.execute();
    }

    private String getServerResponse(String json) throws IOException {
        String url = "http://192.168.1.160:3001/announcements";

        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Content-type", "application/json");

        DefaultHttpClient client = new DefaultHttpClient();

        BasicResponseHandler handler = new BasicResponseHandler();
        String response = client.execute(post, handler);

        return response;
    }


    private JSONObject createJSONObject(EditText postTitleEditText, EditText postContentEditText) throws JSONException {
        String postTitle = postTitleEditText.getText().toString().trim();
        String postContent = postContentEditText.getText().toString().trim();
        String dateTime = getCurrentDateTime();

        String urlString = "http://192.168.1.160:3001/announcements";

        Log.i("AdminPage.getData", "CONNECTING TO API...");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("announcement_title", postTitle);
        jsonObject.put("announcement_text", postContent);
        jsonObject.put("announcement_date_schedule", dateTime);

        return jsonObject;
    }

    public String getCurrentDateTime(){
        Calendar calendar = Calendar.getInstance();
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        return simpleDateFormat.format(calendar.getTime());
    }
}
