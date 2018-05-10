package ph.edu.uplb.ics.opruplb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
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
                try {
                    writeToDatabase(postTitleEditText, postContentEditText);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void writeToDatabase(EditText postTitleEditText, EditText postContentEditText) throws IOException{
        String postTitle = postTitleEditText.getText().toString().trim();
        String postContent = postContentEditText.getText().toString().trim();
        String url = "http://54.186.68.67:3001/announcements";

        //TODO: Create a class for this later on???
        Log.i("AdminPage.getData", "CONNECTING TO API...");






        /*HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse responseGet = client.execute(get);
        HttpEntity resEntityGet = responseGet.getEntity();

        if(resEntityGet != null){
            //String response = EntityUtils.toString(resEntityGet);
            //parse JSON here
            //String[] tokens = response.replaceAll("[\\[\\{]","").split("\\}");
            //for(int i=0; i<tokens.length; i++)
            //    Log.i("GET RESPONSE", tokens[i]);

            InputStream inputStream = resEntityGet.getContent();
            result = convertStreamToString(inputStream);

        }*/



        //Toast.makeText(AdminPage.this, "Post Created", Toast.LENGTH_SHORT).show();
    }
}
