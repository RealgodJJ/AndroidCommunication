package reagodjj.example.com.androidcommunication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import reagodjj.example.com.androidcommunication.Adapter.LessonAdapter;
import reagodjj.example.com.androidcommunication.model.LessonInfo;
import reagodjj.example.com.androidcommunication.model.LessonResult;

public class SecondActivity extends AppCompatActivity {
    public static final String STATUS = "status";
    public static final String MSG = "msg";
    public static final String DATA = "data";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String URL_STRING = "http://www.imooc.com/api/teacher?type=2&page=1";
    public static final String ID = "id";
    private ListView lvLessonList;
    private List<LessonInfo> lessonInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lvLessonList = findViewById(R.id.lv_lesson_list);

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams")
        View footView = layoutInflater.inflate(R.layout.header_view, null);
        lvLessonList.addFooterView(footView);

        new RequestLessonAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class RequestLessonAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Loading
        }

        @Override
        protected String doInBackground(Void... voids) {
            return request(URL_STRING);
        }

        private String request(String urlString) {
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(30 * 1000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                int requestCode = urlConnection.getResponseCode();
//                String requestMessage = urlConnection.getResponseMessage();

                if (requestCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    return stringBuilder.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Loading 消失，数据处理 刷新界面

            LessonResult lessonResult = new LessonResult();

            try {
                JSONObject jsonObject = new JSONObject(result);
                int status = jsonObject.getInt(STATUS);
                String message = jsonObject.getString(MSG);
                lessonResult.setStatus(status);
                lessonResult.setMessage(message);

                lessonInfos = new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONArray(DATA);

                for (int i = 0; i < jsonArray.length(); i++) {
                    LessonInfo lessonInfo = new LessonInfo();
                    JSONObject tempJSONObject = (JSONObject) jsonArray.get(i);
                    int id = tempJSONObject.getInt(ID);
                    String name = tempJSONObject.getString(NAME);
                    String description = tempJSONObject.getString(DESCRIPTION);
                    lessonInfo.setId(id);
                    lessonInfo.setName(name);
                    lessonInfo.setDescription(description);
                    lessonInfos.add(lessonInfo);
                }
                lessonResult.setLessonInfoList(lessonInfos);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            lvLessonList.setAdapter(new LessonAdapter(SecondActivity.this, lessonInfos));
        }
    }
}
