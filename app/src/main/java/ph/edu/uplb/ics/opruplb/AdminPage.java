package ph.edu.uplb.ics.opruplb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminPage extends AppCompatActivity {

    private EditText postTitleEditText;
    private EditText postDescriptionEditText;
    private Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        postTitleEditText = (EditText) findViewById(R.id.postTitleText);
        postDescriptionEditText = (EditText) findViewById(R.id.postDescriptionText);
        postButton = (Button) findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to Firebase database
                if(!postTitleEditText.getText().toString().isEmpty() && !postDescriptionEditText.getText().toString().isEmpty()) {
                    Toast.makeText(AdminPage.this, "Post Created", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
