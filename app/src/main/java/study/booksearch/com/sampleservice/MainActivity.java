package study.booksearch.com.sampleservice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

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
    private BookItemJSONParser bookIteamJasonParser;
    private Button searchButton;

//    SharedPreferences setting;
//    SharedPreferences.Editor editor;

    //list paging
    private int page = 1;
    private ProgressBar progressBar;
    private boolean mLocListView = false;
    private boolean lastItemVisibleFlag = false;
    boolean firstButtonClick = false;
    //PreferenceManager preferenceManager = new PreferenceManager();
    String a = "KK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView();
        editText.setText(PreferenceManager.getString(getApplication(),a ));


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookItemArrayList.clear();
                page = 1;
                Utility.onKeyPadDown(getApplicationContext(), editText);
                keyword = editText.getText().toString();
                getListData();
                PreferenceManager.setString(getApplication(),a,keyword);

            }
        });


    }


//    public void loadEditText() {
//        setting = getSharedPreferences("setting", 0);
//        editor = setting.edit();
//        if (setting.getBoolean("Auto_EditText_Write", false)) {
////            editText.setText(setting.getString("KEYWORD", ""));
////        }
//    }
//
//    public void saveEditText() {
//            String saveKeyword = keyword;
//            editor.putString("KEYWORD", saveKeyword);
//            editor.putBoolean("Auto_EditText_Write", true);
//            editor.commit();
//    }

    //
    public void setView() {
        searchButton = findViewById(R.id.buttonSearch);
        editText = findViewById(R.id.serach_edit_text);
        adapter = new BookSearchAdapter(getApplicationContext(), R.layout.activity_book_item, bookItemArrayList);
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressbar);
        setListView();

    }


    //
    public void setListView() {
        listView.setAdapter(adapter);
        //스크롤 시 페이징 처리 하는 부분
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && mLocListView == false) {
                    progressBar.setVisibility(View.GONE);
                    getListData();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
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
        //버튼으로 검색하는 데이타
        if (firstButtonClick) {
            queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://dapi.kakao.com/v3/search/book?target=title&size=10&query=" + keyword + "&page=" + page;
            //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
            bookIteamJasonParser = new BookItemJSONParser();

            CustomJSONObjectRequest customJSONObjectRequest = new CustomJSONObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //listveiw에 adapter연결 ,list 상세 Activity 띄어주는 메소드
                    adapter.notifyDataSetChanged();
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
            firstButtonClick = true;
            //스크롤하여 페이징하여 가져오는 데이타
        } else {
            mLocListView = true;
            page++;
            queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://dapi.kakao.com/v3/search/book?target=title&size=10&query=" + keyword + "&page=" + page;
            //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
            //bookIteamJasonParser = new BookItemJSONParser();

            CustomJSONObjectRequest customJSONObjectRequest = new CustomJSONObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //listveiw에 adapter연결 ,list 상세 Activity 띄어주는 메소드

                    //JSDON 정보 파싱하여 ArrayList에 추가
                    BookItemJSONParser.getBookItemJasonObject(response, bookItemArrayList);

                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    mLocListView = false;
                    progressBar.setVisibility(View.GONE);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapter.notifyDataSetChanged();
//                            progressBar.setVisibility(View.GONE);
//                            mLocListView = false;
//                        }
//                    }, 1000);
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




