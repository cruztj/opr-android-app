package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

public class PopUpActivity extends AppCompatActivity {

    private ImageButton closeButton;
    private WebView webview;
    private String urlString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        closeButton= (ImageButton) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String urlString = intent.getStringExtra("urlString");
        String titleString = intent.getStringExtra("titleString");

        webview = (WebView) findViewById(R.id.webView);

        setPopUpLayout(0.90, 0.85);

        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(urlString);

        webview.findAllAsync(titleString);
        webview.findNext(true);

    }

    public void setPopUpLayout(double widthMultiplier, double heightMultiplier){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*widthMultiplier),(int)(height*heightMultiplier));
    }
}
