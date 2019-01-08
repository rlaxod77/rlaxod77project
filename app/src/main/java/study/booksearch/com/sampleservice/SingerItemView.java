package study.booksearch.com.sampleservice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SingerItemView extends BaseAdapter {
    Context context;
    //아이템 데이터 리스트

    private ArrayList<SingerItem> listViewItemlist = new ArrayList<SingerItem>();

    public SingerItemView(){

    }


    //Adapter에 사용되는 데이터의 계수를 리턴 : 필수
    @Override
    public int getCount() {
        return listViewItemlist.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //DATA SET (listViewItemlist에 위치한 데이터 참조 획득
            SingerItem listViewItem = listViewItemlist.get(position);

            convertView = inflater.inflate(R.layout.singer_item,parent,false);
            TextView titleTextView = convertView.findViewById(R.id.titleView);
            TextView authors = convertView.findViewById(R.id.authors);
            ImageView imageView = convertView.findViewById(R.id.ImageView);

            titleTextView.setText(listViewItem.getTitle());
            authors.setText(listViewItem.getAuthors());
            //이미지뷰
            Glide.with(context).load(listViewItem.getImageUrl()).into(imageView);



        }


        return convertView;
    }

    @Override
    public long getItemId(int positon) {
        return positon;
    }

    @Override
    public Object getItem(int positon) {
        return listViewItemlist.get(positon);
    }

    public void addItem(String title , String authors, String ImageUrl){
        SingerItem item = new SingerItem();
        item.setTitle(title);
        item.setAuthors(authors);
        item.setImageUrl(ImageUrl);
        listViewItemlist.add(item);
    }
}
