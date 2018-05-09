package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class StudentAnnouncements extends AppCompatActivity {

    private ImageButton backButton;
    private ListView listView;

    //TODO: Use a function to get these from database
    String[] postTitle = {"1", "2", "3", "4", "5", "6", "7"};
    String[] timeStamp = {"One", "Two", "Three", "Four", "Five", "Six", "Sever"};
    String[] postContent = {"Uno", "Dos", "Tres", "Quatro", "Cinco", "Sais", "Syete"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_announcements);

        initLayout();
    }

    private void initLayout() {
        backButton = (ImageButton) findViewById(R.id.backButton);

        listView = (ListView) findViewById(R.id.listViewAnnouncements);
        CustomListViewAnnouncements customListViewAnnouncements = new CustomListViewAnnouncements(this, postTitle, timeStamp, postContent);
        listView.setDivider(null);
        listView.setAdapter(customListViewAnnouncements);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentAnnouncements.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
