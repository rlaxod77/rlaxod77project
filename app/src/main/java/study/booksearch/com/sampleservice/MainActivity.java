package study.booksearch.com.sampleservice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    private ListView listView;
    private BookSearchAdapter adapter;
    private String keyword;
    private EditText editText;
    private ArrayList<BookItem> bookItemArrayList = new ArrayList<BookItem>();
    private BookIteamJasonParser bookIteamJasonParser;
    private Button searchButton;
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    //list paging
    private int page = 1;
    private ProgressBar progressBar;
    private boolean mLocListView = false;
    private boolean lastItemVisibleFlag = false;
    boolean firstButtonClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        loadEditText();




        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookItemArrayList.clear();
                Utility.onKeyPadDown(getApplicationContext(), editText);
                keyword = editText.getText().toString();
                saveEditText();
                getListData();


            }
        });


    }
    public void loadEditText(){
        setting = getSharedPreferences("setting",0);
        editor = setting.edit();
        if (setting.getBoolean("Auto_EditText_Write", false)) {
            editText.setText(setting.getString("KEYWORD", ""));
        }
    }

    public void saveEditText(){
        if (editText != null) {
            String saveKeyword = keyword;
            editor.putString("KEYWORD", saveKeyword);
            editor.putBoolean("Auto_EditText_Write", true);
            editor.commit();
        } else {
            editor.clear();
            editor.commit();
        }
    }
    //
    public void setView() {
        searchButton = findViewById(R.id.buttonSearch);
        editText = findViewById(R.id.serach_edit_text);
        adapter = new BookSearchAdapter(getApplicationContext(), R.layout.activity_book_item, bookItemArrayList);
        listView = findViewById(R.id.listView);

        progressBar = findViewById(R.id.progressbar);

    }


    //
    public void setListView() {
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLocListView ==false){
                    progressBar.setVisibility(View.GONE);
                    getListData();

                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItemVisibleFlag = (totalItemCount >0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);

                intent.putExtra("title", bookItemArrayList.get(i).title);
                intent.putExtra("author", bookItemArrayList.get(i).authors);
                intent.putExtra("ImageUrl", bookItemArrayList.get(i).ImageUrl);

                startActivity(intent);
            }
        });

    }


    //Data를 가져오는 로직
    public void getListData() {
        if(firstButtonClick=false) {


            queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://dapi.kakao.com/v3/search/book?target=title&size=10&query=" + keyword + "&page=" + page;
            //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
            bookIteamJasonParser = new BookIteamJasonParser();

            CustomJSONObjectRequest customJSONObjectRequest = new CustomJSONObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //listveiw에 adapter연결 ,list 상세 Activity 띄어주는 메소드


                    setListView();

                    //JSDON 정보 파싱하여 ArrayList에 추가
                    bookIteamJasonParser.getBookItemJasonObject(response, bookItemArrayList);

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );
            queue.add(customJSONObjectRequest);
            //
            firstButtonClick=true;

        }else{
            mLocListView = true;
            page ++;
            queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://dapi.kakao.com/v3/search/book?target=title&size=10&query=" + keyword + "&page=" + page;
            //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
            bookIteamJasonParser = new BookIteamJasonParser();

            CustomJSONObjectRequest customJSONObjectRequest = new CustomJSONObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //listveiw에 adapter연결 ,list 상세 Activity 띄어주는 메소드
                    setListView();

                    //JSDON 정보 파싱하여 ArrayList에 추가
                    bookIteamJasonParser.getBookItemJasonObject(response, bookItemArrayList);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            mLocListView = false;
                        }
                    },1000);

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            );
            queue.add(customJSONObjectRequest);
        }
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




