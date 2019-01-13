package study.booksearch.com.sampleservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookIteamJasonParser {


    public void getBookItemJasonObject(JSONObject response, ArrayList<BookItem> bookItemActivities ){
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

                bookItemActivities.add(new BookItem(title, author, ImageUrl));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
