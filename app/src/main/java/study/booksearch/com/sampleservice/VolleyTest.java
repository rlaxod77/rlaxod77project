package study.booksearch.com.sampleservice;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

public class VolleyTest extends AppCompatActivity {

    private static final String TAG = "MAIN";
    TextView tv;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_test);
        tv = findViewById(R.id.tvMain);

        queue = Volley.newRequestQueue(this);
        String url = "https://dapi.kakao.com/v3/search/book?target=title&query=test";



        CustomJSONObject jsonObjectRequest = new CustomJSONObject(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonRoot) {

                Log.e(TAG, jsonRoot.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        queue.add(jsonObjectRequest);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

   class CustomJSONObject extends JsonObjectRequest{
        public CustomJSONObject(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        public CustomJSONObject(String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
            super(url, jsonRequest, listener, errorListener);
        }

        @Override
          public Map<String, String> getHeaders() throws AuthFailureError {
            String appkey = "KakaoAK 3f97e64d6d8a56fbebe6ce5f6ad593c6";
            Map<String,String> params = new HashMap();
            params.put("Authorization" , appkey);
            return params;
        }
    }


}