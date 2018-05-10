package ph.edu.uplb.ics.opruplb;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomListViewAnnouncements extends ArrayAdapter<String> {

    //private ArrayList<String> postTitle = new ArrayList<String>();
    //private ArrayList<String> timeStamp = new ArrayList<String>();
    //private ArrayList<String> postContent = new ArrayList<String>();
    String[] postTitle;
    String[] timeStamp;
    String[] postContent;
    private Activity context;

    public CustomListViewAnnouncements(Activity context, ArrayList<String> postTitle, ArrayList<String> timeStamp, ArrayList<String> postContent) {
        super(context, R.layout.listview_announcement_layout, postTitle.toArray(new String[postTitle.size()]));

        Log.d("CustomListView","CREATED NEW CUSTOMLISTVIEW");

        String[] postTitleArray = postTitle.toArray(new String[postTitle.size()]);
        String[] timeStampArray = timeStamp.toArray(new String[timeStamp.size()]);
        String[] postContentArray = postContent.toArray(new String[postContent.size()]);

        Log.d("postTitleArray", Integer.toString(postTitleArray.length));
        Log.d("timeStampArray", Integer.toString(timeStampArray.length));
        Log.d("postContentArray", Integer.toString(postContentArray.length));

        Log.d("postTitle", Integer.toString(postTitle.size()));
        Log.d("timeStamp", Integer.toString(timeStamp.size()));
        Log.d("postContent", Integer.toString(postContent.size()));


        for(int i=0; i<postTitle.size(); i++){
            Log.d("postTitleArray", postTitleArray[i]);
            Log.d("timeStampArray", timeStampArray[i]);
            Log.d("postContentArray", postContentArray[i]);
        }

        this.context = context;
        this.postTitle = postTitleArray;
        this.timeStamp = timeStampArray;
        this.postContent = postContentArray;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolderAnnouncements viewHolder = null;
        if(view==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.listview_announcement_layout, null, false);
            viewHolder = new ViewHolderAnnouncements(view);
            view.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolderAnnouncements) view.getTag();
        }
        viewHolder.postTitleTextView.setText(postTitle[position]);
        viewHolder.postTimeStampTextView.setText(timeStamp[position]);
        viewHolder.postContentTextView.setText(postContent[position]);

        return view;
    }


    class ViewHolderAnnouncements{
        TextView postTitleTextView;
        TextView postTimeStampTextView;
        TextView postContentTextView;

        ViewHolderAnnouncements(View view){
            postTitleTextView = view.findViewById(R.id.postTitleTextViewAnnouncements);
            postTimeStampTextView = view.findViewById(R.id.postTimeStampTextViewAnnouncements);
            postContentTextView = view.findViewById(R.id.postContentTextViewAnnouncements);
        }
    }
}
