package study.booksearch.com.sampleservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    EditText editText;
    ListView listView;
    SingerAdepter adepter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        adepter = new SingerAdepter();

        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));
        adepter.addItem(new SingerItem("소녀시대", "010-5595-9780", 20, R.drawable.singer));

        listView.setAdapter(adepter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //각 아이템 선택시 노출되는 얼럿..나중에 액티비티로 바꿔야됨.
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SingerItem item = (SingerItem) adepter.getItem(position);
                Toast.makeText(getApplicationContext(),"선택 : " + item.getName(),Toast.LENGTH_LONG).show();
            }
        });

    }

    class SingerAdepter extends BaseAdapter {
        //각 아이템의 데이터를 담고 있는 SIngerItem 객체를 저장할 ArrayList
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        // 전체 아이템 개수 리턴
        public int getCount(){
            return items.size();
        }
        public void addItem(SingerItem item){
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            SingerItemView view = new SingerItemView(getApplicationContext());
            SingerItem item = items.get(position);
            view.setName(item.getName());
            view.setMobile(item.getMobile());
            view.setAge(item.getAge());
            view.setImage(item.getResId());
            return view;
        }
    }

}
