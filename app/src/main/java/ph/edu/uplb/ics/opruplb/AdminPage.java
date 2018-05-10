package ph.edu.uplb.ics.opruplb;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        postTitleEditText = (EditText) findViewById(R.id.postTitleText);
        postContentEditText = (EditText) findViewById(R.id.postDescriptionText);
        postButton = (Button) findViewById(R.id.postButton);


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
        String url = "http://54.186.68.67:3001/announcements";

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

        String urlString = "http://54.186.68.67:3001/announcements";

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
