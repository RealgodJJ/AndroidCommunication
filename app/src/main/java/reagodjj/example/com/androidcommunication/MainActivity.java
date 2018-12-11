package reagodjj.example.com.androidcommunication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvExample = findViewById(R.id.lv_example);

        List<String> appNames = new ArrayList<>();
        appNames.add(getString(R.string.qq));
        appNames.add(getString(R.string.we_chat));
        appNames.add(getString(R.string.mook));

        lvExample.setAdapter(new AppNameAdapter(appNames));
    }

    class AppNameAdapter extends BaseAdapter {
        private List<String> appNames;

        AppNameAdapter(List<String> appNames) {
            this.appNames = appNames;
        }

        @Override
        public int getCount() {
            return appNames == null ? 0 : appNames.size();
        }

        @Override
        public Object getItem(int position) {
            return appNames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_item, null);

            if (convertView != null) {
                ImageView ivItem = convertView.findViewById(R.id.iv_item);
                TextView tvItem = convertView.findViewById(R.id.tv_item);
                tvItem.setText(appNames.get(position));
            }

            return convertView;
        }
    }
}
