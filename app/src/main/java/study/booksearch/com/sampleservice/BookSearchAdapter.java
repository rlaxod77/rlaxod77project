package study.booksearch.com.sampleservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookSearchAdapter extends BaseAdapter {
    Context context;
    int layout;
    LayoutInflater inf;

    //아이템 데이터 리스트

    private ArrayList<BookItemActivity> listViewItemlist;
    ViewHolder viewHolder;

    public BookSearchAdapter(Context context, int layout, ArrayList<BookItemActivity> listViewItemlist) {
        this.context = context;
        this.layout = layout;
        this.listViewItemlist = listViewItemlist;
        inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    //Adapter에 사용되는 데이터의 계수를 리턴 : 필수
    @Override
    public int getCount() {
        return listViewItemlist.size();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_book_item, null);

            //DATA SET (listViewItemlist에 위치한 데이터 참조 획득
            //BookItemActivity listViewItem = listViewItemlist.get(position);
            viewHolder = new ViewHolder();


            viewHolder.TextView = (TextView) convertView.findViewById(R.id.titleView);
            viewHolder.authors = (TextView) convertView.findViewById(R.id.authors);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.ImageView);
            convertView.setTag(viewHolder);

            //viewHolder.TextView.setText(listViewItem.getTitle());
            //viewHolder.authors.setText(listViewItem.getAuthors());
            //이미지뷰
            //Glide.with(context).load(listViewItem.getImageUrl()).into(viewHolder.imageView);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.TextView.setText(listViewItemlist.get(position).getTitle());
        viewHolder.authors.setText(listViewItemlist.get(position).getAuthors());
        Glide.with(context).load(listViewItemlist.get(position).getImageUrl()).into(viewHolder.imageView);
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

    class ViewHolder {
        TextView TextView;
        TextView authors;
        ImageView imageView;

    }


}
