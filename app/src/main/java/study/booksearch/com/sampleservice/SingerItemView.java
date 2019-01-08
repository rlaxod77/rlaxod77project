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

import java.util.ArrayList;

public class SingerItemView extends BaseAdapter {

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
            TextView publisherTextView = convertView.findViewById(R.id.publisher);

            titleTextView.setText(listViewItem.getTitle());
            publisherTextView.setText(listViewItem.getPublisher());
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

    public void addItem(String title , String publisher){
        SingerItem item = new SingerItem();
        item.setTitle(title);
        item.setPublisher(publisher);
        listViewItemlist.add(item);
    }
}
