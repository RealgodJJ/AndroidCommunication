package reagodjj.example.com.androidcommunication;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

//        List<String> appInfos = new ArrayList<>();
//        appInfos.add(getString(R.string.qq));
//        appInfos.add(getString(R.string.we_chat));
//        appInfos.add(getString(R.string.mook));

//        lvExample.setAdapter(new AppNameAdapter(appInfos));

//        List<ResolveInfo> appInfos = new ArrayList<>();

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams") View headerView = layoutInflater.inflate(R.layout.header_view, null);
        lvExample.addHeaderView(headerView);

        //1.设置单选项的方法
        final List<ResolveInfo> appInfos = getAppInfos();
        lvExample.setAdapter(new AppNameAdapter(appInfos));

        lvExample.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String packageName = appInfos.get(position).activityInfo.packageName;
                String className = appInfos.get(position).activityInfo.name;

                ComponentName componentName = new ComponentName(packageName, className);

                startActivity(new Intent().setComponent(componentName));
            }
        });
    }

    /**
     * 获取所有应用的信息
     * @return  应用的信息列表
     */
    private List<ResolveInfo> getAppInfos() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        return getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    class AppNameAdapter extends BaseAdapter {
        private List<ResolveInfo> appInfos;

        AppNameAdapter(List<ResolveInfo> appInfos) {
            this.appInfos = appInfos;
        }

        @Override
        public int getCount() {
            return appInfos == null ? 0 : appInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return appInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
            convertView = layoutInflater.inflate(R.layout.list_item, null);

            if (convertView != null) {
                ImageView ivItem = convertView.findViewById(R.id.iv_item);
                TextView tvItem = convertView.findViewById(R.id.tv_item);
                tvItem.setText(appInfos.get(position).activityInfo.loadLabel(getPackageManager()));
                ivItem.setImageDrawable(appInfos.get(position).activityInfo.loadIcon(getPackageManager()));

                //2.设置选项布局
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String packageName = appInfos.get(position).activityInfo.packageName;
                        String className = appInfos.get(position).activityInfo.name;

                        ComponentName componentName = new ComponentName(packageName, className);

                        startActivity(new Intent().setComponent(componentName));
                    }
                });
            }
            return convertView;
        }
    }
}
