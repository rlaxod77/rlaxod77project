package study.booksearch.com.sampleservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //Volley 셋팅
    private static final String TAG = "MAIN";
    private RequestQueue queue;

    Utility utility;
    ListView listView;
    BookSearchAdapter adapter;
    String keyword;
    EditText editText;
    ArrayList<BookItem> bookItemActivities = new ArrayList<BookItem>();
    BookIteamJasonParser bookIteamJasonParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button searchButton = findViewById(R.id.buttonSearch);
        editText = findViewById(R.id.serach_edit_text);
        adapter = new BookSearchAdapter(getApplicationContext(), R.layout.activity_book_item, bookItemActivities);
        listView = findViewById(R.id.listView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookItemActivities.clear();
                Utility.onKeyPadDown(getApplicationContext() ,editText);
                keyword = editText.getText().toString();
                getListData();
            }
        });
    }

    //
    public void


    //
    public void setListSetting(){
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);

                intent.putExtra("title", bookItemActivities.get(i).title);
                intent.putExtra("author", bookItemActivities.get(i).authors);
                intent.putExtra("ImageUrl", bookItemActivities.get(i).ImageUrl);

                startActivity(intent);
            }
        });
    }


    //Data를 가져오는 로직
    public void getListData() {
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://dapi.kakao.com/v3/search/book?target=title&size=10&query=" + keyword;
        //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
        bookIteamJasonParser = new BookIteamJasonParser();

        CustomJSONObjectRequest customJSONObjectRequest = new CustomJSONObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //listveiw에 adapter연결 ,list 상세 Activity 띄어주는 메소드
                setListSetting();

                //JSDON 정보 파싱하여 ArrayList에 추가
                bookIteamJasonParser.getBookItemJasonObject(response,bookItemActivities);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        queue.add(customJSONObjectRequest);
        //
    }


    //Volley 셋팅3
    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

}




