package ph.edu.uplb.ics.opruplb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PopUpActivityJobs extends AppCompatActivity {

    private TextView unitTextView;
    private TextView positionTextView;
    private TextView itemNumTextView;
    private TextView minEducTextView;
    private TextView minExpTextView;
    private TextView minTrainTextView;
    private TextView minEliTextView;
    private TextView dueDateTextView;
    private TextView contactPersonTextView;
    private ImageButton closeButton;
    private LinearLayout imInterestedSpace;

    private String unitString;
    private String positionString;
    private String itemNumString;
    private String minEducString;
    private String minExpString;
    private String minTrainString;
    private String minEliString;
    private String dueDateString;
    private String contactPersonString;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_jobs);

        unitTextView = (TextView) findViewById(R.id.unitTextView);
        positionTextView = (TextView) findViewById(R.id.positionTextView);
        itemNumTextView = (TextView) findViewById(R.id.itemNoTextView);
        minEducTextView = (TextView) findViewById(R.id.minEducationTextView);
        minExpTextView = (TextView) findViewById(R.id.minExperienceTextView);
        minTrainTextView = (TextView) findViewById(R.id.minTrainingTextView);
        minEliTextView = (TextView) findViewById(R.id.minEligibilityTextView);
        dueDateTextView = (TextView) findViewById(R.id.dueDateTextView);
        contactPersonTextView = (TextView) findViewById(R.id.contactPersonTextView);

        imInterestedSpace = (LinearLayout) findViewById(R.id.imInterestedSpace);

        closeButton= (ImageButton) findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        unitString = intent.getStringExtra("unit");
        positionString = intent.getStringExtra("position");
        itemNumString = intent.getStringExtra("itemNumber");
        minEducString = intent.getStringExtra("minEducation");
        minExpString = intent.getStringExtra("minExperience");
        minTrainString = intent.getStringExtra("minTraining");
        minEliString = intent.getStringExtra("minEligibility");
        dueDateString = intent.getStringExtra("dueDate");
        contactPersonString = intent.getStringExtra("contactPerson");

        setPopUpLayout(0.9, 0.93);

        unitTextView.setText(unitString);
        positionTextView.setText(positionString);
        itemNumTextView.setText(itemNumString);
        minEducTextView.setText(minEducString);
        minExpTextView.setText(minExpString);
        minTrainTextView.setText(minTrainString);
        minEliTextView.setText(minEliString);
        dueDateTextView.setText(dueDateString);
        contactPersonTextView.setText(contactPersonString);

        imInterestedSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder contactDialogBuilder = new AlertDialog.Builder(PopUpActivityJobs.this);
                View contactView = getLayoutInflater().inflate(R.layout.dialog_requirements, null);

                contactDialogBuilder.setView(contactView);


                Button proceedButton = (Button) contactView.findViewById(R.id.proceedButton);

                proceedButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendEmail();
                    }
                });

                AlertDialog contactAlertDialog = contactDialogBuilder.create();
                contactAlertDialog.show();
            }
        });
    }

    protected void sendEmail() {
        String emailText = "";
        Log.i("Send email", "");

        emailText = "Good Day! \n\n I am interested for the position: " +positionString+ " for the " +unitString+
            " with Item Number " +itemNumString+ ".\n\nI would like to know if it is still available." +
            "\n\nThank You,\n\nName: \nContact Number: ";


        String[] TO = {"hrdo.uplb@up.edu.ph"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Job Application. Reply to Job Offer using eUPLB");
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailText);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(PopUpActivityJobs.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    public void setPopUpLayout(double widthMultiplier, double heightMultiplier){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

//        getWindow().setLayout((int)(width*widthMultiplier),(int)(height*heightMultiplier));
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
