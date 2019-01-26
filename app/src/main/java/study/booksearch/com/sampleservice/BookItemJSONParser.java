package study.booksearch.com.sampleservice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookItemJSONParser {


    public static void getBookItemJasonObject(JSONObject response, ArrayList<BookItem> bookItemArrayList ){
        try {
            JSONArray jsonArrayDoumnets = response.getJSONArray(Constant.DOCUMENTS);
            for (int i = 0; i < jsonArrayDoumnets.length(); i++) {
                JSONObject jsonDocument = jsonArrayDoumnets.getJSONObject(i);

                String author = "";
                JSONArray authorsArray = jsonDocument.getJSONArray(Constant.BOOK_AUTHORS);
                for (int j = 0; j < authorsArray.length(); j++) {
                    String authorsList = authorsArray.getString(j);
                    author = author + authorsList;
                }

                final String title = jsonDocument.getString(Constant.BOOK_TITLE);
                final String ImageUrl = jsonDocument.getString(Constant.THUMBNAIL);

                bookItemArrayList.add(new BookItem(title, author, ImageUrl));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
