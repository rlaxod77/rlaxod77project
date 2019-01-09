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
import android.widget.ListAdapter;
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
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
//Volley 셋팅
    private static final String TAG = "MAIN";
    private RequestQueue queue;
    TextView JsonTextView;
/////////////////////////////////////////////////////

    EditText editText;
    ListView listView;
    Button searchButton;
    SingerItemView adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchButton = findViewById(R.id.buttonSearch);
        editText = findViewById(R.id.editText);
        adapter = new SingerItemView();





        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = editText.getText().toString();
                //Volley 셋팅 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
                JsonTextView = findViewById(R.id.JsonTextView);
                queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://dapi.kakao.com/v3/search/book?target=title&query=" + keyword;
                listView = findViewById(R.id.listView);
                listView.setAdapter(adapter);




                CustomJSONObject customJSONObject = new CustomJSONObject(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.e(TAG, response.toString());

                        try {
                            JSONArray jsonArrayDoumnets = response.getJSONArray("documents");
                            for (int i = 0; i < jsonArrayDoumnets.length(); i++) {
                                JSONObject jsonDocument = jsonArrayDoumnets.getJSONObject(i);

                                String author ="";
                                JSONArray authorsArray =  jsonDocument.getJSONArray("authors");
                                for(int j = 0; j < authorsArray.length(); j++){
                                    String authorsList = authorsArray.getString(j);
                                    author =   author + authorsList;
                                }

                                String title = jsonDocument.getString("title");
                                String ImageUrl = jsonDocument.getString("thumbnail");
                                adapter.addItem(title,author,ImageUrl);
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
}