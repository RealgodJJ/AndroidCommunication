package reagodjj.example.com.androidcommunication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import reagodjj.example.com.androidcommunication.R;
import reagodjj.example.com.androidcommunication.model.LessonInfo;

public class LessonAdapter extends BaseAdapter {
    private List<LessonInfo> lessonInfos;
    private Context context;

    public LessonAdapter(Context context, List<LessonInfo> lessonInfos) {
        this.lessonInfos = lessonInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lessonInfos == null ? 0 : lessonInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return lessonInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
//        return lessonInfos.get(position).getId();
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.second_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvId.setText(String.valueOf(lessonInfos.get(position).getId()));
        viewHolder.tvName.setText(lessonInfos.get(position).getName());
        viewHolder.tvDescription.setText(lessonInfos.get(position).getDescription());

        return convertView;
    }

    class ViewHolder {
        private TextView tvId;
        private TextView tvName;
        private TextView tvDescription;

        ViewHolder(View view) {
            tvId = view.findViewById(R.id.tv_id);
            tvName = view.findViewById(R.id.tv_name);
            tvDescription = view.findViewById(R.id.tv_description);
        }
    }
}
