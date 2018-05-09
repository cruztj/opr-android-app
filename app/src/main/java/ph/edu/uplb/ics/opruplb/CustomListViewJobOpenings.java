package ph.edu.uplb.ics.opruplb;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomListViewJobOpenings extends ArrayAdapter<String> {

    private String[] jobOpeningCollegeUnitArray;
    private String[] jobOpeningPositionArray;
    private String[] jobOpeningItemNoArray;
    private String[] jobOpeningMinEducationArray;
    private String[] jobOpeningMinExperienceArray;
    private String[] jobOpeningMinTrainingArray;
    private String[] jobOpeningMinEligibilityArray;
    private String[] jobOpeningDueDateArray;
    private String[] jobOpeningContactPersonArray;
    private Activity context;

   public CustomListViewJobOpenings(Activity context, String[] jobOpeningCollegeUnit, String[] jobOpeningPosition,
                                    String[] jobOpeningItemNo, String[] jobOpeningMinEducation, String[] jobOpeningMinExperience,
                                    String[] jobOpeningMinTraning, String[] jobOpeningMinEligibility, String[] jobOpeningDueDate,
                                    String[] jobOpeningContactPerson){
       super(context, R.layout.listview_job_layout, jobOpeningCollegeUnit);

       this.context = context;
       this.jobOpeningCollegeUnitArray = jobOpeningCollegeUnit;
       this.jobOpeningPositionArray = jobOpeningPosition;
       this.jobOpeningItemNoArray = jobOpeningItemNo;
       this.jobOpeningMinEducationArray = jobOpeningMinEducation;
       this.jobOpeningMinExperienceArray = jobOpeningMinExperience;
       this.jobOpeningMinTrainingArray = jobOpeningMinTraning;
       this.jobOpeningMinEligibilityArray = jobOpeningMinEligibility;
       this.jobOpeningDueDateArray = jobOpeningDueDate;
       this.jobOpeningContactPersonArray = jobOpeningContactPerson;
   }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolderJobOpenings viewHolder = null;
        if(view==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.listview_job_layout, null, false);
            viewHolder = new ViewHolderJobOpenings(view);
            view.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolderJobOpenings) view.getTag();
        }
        viewHolder.jobCollegeUnit.setText(jobOpeningCollegeUnitArray[position]);
        viewHolder.jobPosition.setText(jobOpeningPositionArray[position]);
        viewHolder.jobItemNo.setText(jobOpeningItemNoArray[position]);
        viewHolder.jobMinEducation.setText(jobOpeningMinEducationArray[position]);
        viewHolder.jobMinExperience.setText(jobOpeningMinExperienceArray[position]);
        viewHolder.jobMinTraining.setText(jobOpeningMinTrainingArray[position]);
        viewHolder.jobMinEligibility.setText(jobOpeningMinEligibilityArray[position]);
        viewHolder.jobDueDate.setText(jobOpeningDueDateArray[position]);
        viewHolder.jobContactPerson.setText(jobOpeningContactPersonArray[position]);

        return view;
    }


    class ViewHolderJobOpenings{
        TextView jobCollegeUnit;
        TextView jobPosition;
        TextView jobItemNo;
        TextView jobMinEducation;
        TextView jobMinExperience;
        TextView jobMinTraining;
        TextView jobMinEligibility;
        TextView jobDueDate;
        TextView jobContactPerson;

        ViewHolderJobOpenings(View view){
            jobCollegeUnit = view.findViewById(R.id.jobOpeningCollegeUnitTextView);
            jobPosition = view.findViewById(R.id.jobOpeningPositionTextView);
            jobItemNo = view.findViewById(R.id.jobOpeningItemNoTextView);
            jobMinEducation = view.findViewById(R.id.jobOpeningMinEducationTextView);
            jobMinExperience = view.findViewById(R.id.jobOpeningMinExperienceTextView);
            jobMinTraining = view.findViewById(R.id.jobOpeningMinTrainingTextView);
            jobMinEligibility = view.findViewById(R.id.jobOpeningMinEligibilityTextView);
            jobDueDate = view.findViewById(R.id.jobOpeningDueDateTextView);
            jobContactPerson = view.findViewById(R.id.jobOpeningContactPersonTextView);
        }
    }


}
