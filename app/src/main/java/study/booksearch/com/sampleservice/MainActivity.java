package study.booksearch.com.sampleservice;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
//Volley 셋팅
    private static final String TAG = "MAIN";
    private RequestQueue queue;
    TextView JsonTextView;
/////////////////////////////////////////////////////

    EditText editText;
    ListView listView;
    SingerAdepter adepter;
    Button searchButton;
    String keyword;
    String kedywordData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        searchButton = findViewById(R.id.buttonSearch);
        editText = findViewById(R.id.editText);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = editText.getText().toString();
                //Volley 셋팅 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
                JsonTextView = findViewById(R.id.JsonTextView);
                queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://dapi.kakao.com/v3/search/book?target=title&query=" + keyword;


                CustomJSONObject customJSONObject = new CustomJSONObject(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, response.toString());
                        try {
                            JSONArray jsonArrayDoumnets = response.getJSONArray("documents");
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonDocument = jsonArrayDoumnets.getJSONObject(i);
                                JsonTextView.append(jsonDocument.getString("title"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
                );
                queue.add(customJSONObject);
                // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

            }
        });

        adepter = new SingerAdepter();

        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));

        listView.setAdapter(adepter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //각 아이템 선택시 노출되는 얼럿..나중에 액티비티로 바꿔야됨.
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SingerItem item = (SingerItem) adepter.getItem(position);
                Toast.makeText(getApplicationContext(),"선택 : " + item.getName(),Toast.LENGTH_LONG).show();
            }
        });

    }

    //볼리관련 소스2 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    @Override
    protected void onStop() {
        super.onStop();
        if(queue !=null){
            queue.cancelAll(TAG);
        }
    }
    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    //볼리관련 소스3 JsonObjectRequest 커스텀ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
    class CustomJSONObject extends JsonObjectRequest {

        public CustomJSONObject(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        public CustomJSONObject(String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
            super(url, jsonRequest, listener, errorListener);
        }
        //볼리소스 4 헤더값 전달 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            String appkey = "KakaoAK 3f97e64d6d8a56fbebe6ce5f6ad593c6";
            Map<String, String> params = new HashMap();
            params.put("Authorization", appkey);
            return params;
        }

    }


        class SingerAdepter extends BaseAdapter {
            //각 아이템의 데이터를 담고 있는 SIngerItem 객체를 저장할 ArrayList
            ArrayList<SingerItem> items = new ArrayList<SingerItem>();

            // 전체 아이템 개수 리턴
            public int getCount() {
                return items.size();
            }

            public void addItem(SingerItem item) {
                items.add(item);
            }

            @Override
            public Object getItem(int position) {
                return items.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup viewGroup) {
                SingerItemView view = new SingerItemView(getApplicationContext());
                SingerItem item = items.get(position);
                view.setName(item.getName());
                view.setMobile(item.getMobile());
                view.setAge(item.getAge());
                view.setImage(item.getResId());
                return view;
            }
        }
    }