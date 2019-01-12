package study.booksearch.com.sampleservice;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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

/////////////////////////////////////////////////////


    ListView listView;
    Button searchButton;
    BookSearchAdapter adapter;
    String keyword;
    EditText editText;
    ArrayList<BookItemActivity> bookItemActivities = new ArrayList<BookItemActivity>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchButton = findViewById(R.id.buttonSearch);
        editText = findViewById(R.id.serach_edit_text);
        adapter = new BookSearchAdapter(getApplicationContext(), R.layout.activity_book_item, bookItemActivities);
        listView = findViewById(R.id.listView);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookItemActivities.clear();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                keyword = editText.getText().toString();
                listOpenMethod();
            }
        });
    }


    public void listOpenMethod() {
        queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://dapi.kakao.com/v3/search/book?target=title&size=10&query=" + keyword;
        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
        CustomJSONObject customJSONObject = new CustomJSONObject(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
                Log.e(TAG, response.toString());

                try {
                    JSONArray jsonArrayDoumnets = response.getJSONArray("documents");
                    for (int i = 0; i < jsonArrayDoumnets.length(); i++) {
                        JSONObject jsonDocument = jsonArrayDoumnets.getJSONObject(i);

                        String author = "";
                        JSONArray authorsArray = jsonDocument.getJSONArray("authors");
                        for (int j = 0; j < authorsArray.length(); j++) {
                            String authorsList = authorsArray.getString(j);
                            author = author + authorsList;
                        }

                        final String title = jsonDocument.getString("title");
                        final String ImageUrl = jsonDocument.getString("thumbnail");

                        bookItemActivities.add(new BookItemActivity(title, author, ImageUrl));


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




