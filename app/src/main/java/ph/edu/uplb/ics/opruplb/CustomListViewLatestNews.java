package ph.edu.uplb.ics.opruplb;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomListViewLatestNews extends ArrayAdapter<String>{

    private String[] postTitle;
    private String[] timeStamp;
    private String[] postContent;
    private Activity context;

    public CustomListViewLatestNews(Activity context, String[] postTitle, String[] timeStamp, String[] postContent) {
        super(context, R.layout.listview_news_layout, postTitle);

        this.context = context;
        this.postTitle = postTitle;
        this.timeStamp = timeStamp;
        this.postContent = postContent;
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
        viewHolder.postTimeStampTextView.setText(timeStamp[position]);
        viewHolder.postContentTextView.setText(postContent[position]);

        return view;
    }


    class ViewHolderLatestNews{
        TextView postTitleTextView;
        TextView postTimeStampTextView;
        TextView postContentTextView;

        ViewHolderLatestNews(View view){
            postTitleTextView = view.findViewById(R.id.newsPostTitleTextView);
            postTimeStampTextView = view.findViewById(R.id.newsPostTimeStampTextView);
            postContentTextView = view.findViewById(R.id.newsPostContentTextView);
        }
    }
}
