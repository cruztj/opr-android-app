package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.Toolbar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
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
                        //MAKE POP UP SHIT FOR LOGIN
                        Toast.makeText(MainActivity.this, "Log in pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    case  R.id.menuAbout:
                        Toast.makeText(MainActivity.this, "About pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }


    public void getData() throws IOException {
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

    }
}
