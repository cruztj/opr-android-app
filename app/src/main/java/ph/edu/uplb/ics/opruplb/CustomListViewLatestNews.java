package ph.edu.uplb.ics.opruplb;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListViewLatestNews extends ArrayAdapter<String>{

    private String[] postTitle;
    private String[] timeStamp;
    private String[] postLink;
    private String[] postContent;
    private Activity context;

    public CustomListViewLatestNews(Activity context, ArrayList<String> postTitle, ArrayList<String> timeStamp, ArrayList<String> postContent, ArrayList<String> postLink) {
        super(context, R.layout.listview_news_layout, postTitle.toArray(new String[postTitle.size()]));

        String[] postTitleArray = postTitle.toArray(new String[postTitle.size()]);
        String[] timeStampArray = timeStamp.toArray(new String[timeStamp.size()]);
        String[] postLinkArray = postLink.toArray(new String[postLink.size()]);
        String[] postContentArray = postContent.toArray(new String[postContent.size()]);

        this.context = context;
        this.postTitle = postTitleArray;
        this.timeStamp = timeStampArray;
        this.postContent = postContentArray;
        this.postLink = postLinkArray;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolderLatestNews viewHolder = null;
        if(view==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.listview_news_layout, null, false);
            viewHolder = new ViewHolderLatestNews(view);
            view.setTag(viewHolder);
        } else{
            viewHolder = (ViewHolderLatestNews) view.getTag();
        }
        viewHolder.postTitleTextView.setText(postTitle[position]);
        //viewHolder.postTimeStampTextView.setText(timeStamp[position]);
        viewHolder.postLinkTextView.setText(postLink[position]);
        //viewHolder.postContentTextView.setText(postContent[position]);

        return view;
    }


    class ViewHolderLatestNews{
        TextView postTitleTextView;
        TextView postTimeStampTextView;
        TextView postContentTextView;
        TextView postLinkTextView;

        ViewHolderLatestNews(View view){
            postTitleTextView = view.findViewById(R.id.newsPostTitleTextView);
            postTimeStampTextView = view.findViewById(R.id.newsPostTimeStampTextView);
            postLinkTextView = view.findViewById(R.id.newsPostLinkTextView);
            postContentTextView = view.findViewById(R.id.newsPostContentTextView);
        }
    }
}
