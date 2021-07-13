package kr.co.softcampus.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    Button btn1;
    ArrayList<RssItem> arrayList;
    Handler handler;

    ListView list1;
    RssAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<RssItem>();
        list1 = findViewById(R.id.list1);
        adapter = new RssAdapter();
        list1.setAdapter(adapter);

        handler = new Handler();
        textview = findViewById(R.id.textView);
        btn1 = findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkThread thread = new NetworkThread();
                thread.start();
            }
        });
    }

    class RssAdapter extends BaseAdapter{
        ArrayList<RssItem> arrayList = new ArrayList<RssItem>();

        public void setArrayList(ArrayList<RssItem> arrayList){
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RowListView view  = null;
            if(convertView==null){
                view = new RowListView(MainActivity.this);
            }else {
                view = (RowListView)convertView;
            }
            view.setRssItem(arrayList.get(position));
            return view;
        }
    }

    class NetworkThread extends Thread{
        @Override
        public void run() {
            super.run();
            try{
                String site = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey=td2mUEW5%2BBJsHwSYNwbnY6ktvxlhqGNHV3MQdvXJznAz1OwOC2j%2F9WC8TsjtzdEwzXaNaX17wG1i0r4DL9GN0g%3D%3D&pageNo=1&numOfRows=10&startCreateDt=20210711&endCreateDt=20210713";
                URL url = new URL(site);
                URLConnection con = url.openConnection();

                InputStream is = con.getInputStream();
                parseRss(is);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void parseRss(InputStream is){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            final ArrayList<RssItem> arrayList = parseDoc(doc);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textview.append("파싱된 갯수 : "+arrayList.size());
                    adapter.setArrayList(arrayList);
                    adapter.notifyDataSetChanged();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public ArrayList<RssItem> parseDoc(Document doc){
        try{
            Element element = doc.getDocumentElement(); //최상위태그
            NodeList item_list = element.getElementsByTagName("item");
            for(int i=0; i<item_list.getLength(); i++){
                RssItem item = parseItemNode(item_list,i);
                arrayList.add(item);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

    public RssItem parseItemNode(NodeList item_list, int i){
        Element item_tag = (Element)item_list.item(i);
        NodeList accDefRate_list = item_tag.getElementsByTagName("accDefRate");
        NodeList accExamCnt_list = item_tag.getElementsByTagName("accExamCnt");
        NodeList accExamCompCnt_list = item_tag.getElementsByTagName("accExamCompCnt");

        Element accDefRate_tag = (Element)accDefRate_list.item(0);
        Element accExamCnt_tag = (Element)accExamCnt_list.item(0);
        Element accExamCompCnt_tag = (Element)accExamCompCnt_list.item(0);

        String accDefRate = "";
        if(accDefRate_tag!=null){
            Node firstChild = accDefRate_tag.getFirstChild();
            if(firstChild!=null){
                accDefRate = firstChild.getNodeValue();
            }
        }

        String accExamCnt = "";
        if(accExamCnt_tag!=null){
            Node firstChild = accExamCnt_tag.getFirstChild();
            if(firstChild!=null){
                accExamCnt = firstChild.getNodeValue();
            }
        }

        String accExamCompCnt = "";
        if(accExamCompCnt_tag!=null){
            Node firstChild = accExamCompCnt_tag.getFirstChild();
            if(firstChild!=null){
                accExamCompCnt = firstChild.getNodeValue();
            }
        }

        RssItem item = new RssItem(accDefRate, accExamCnt, accExamCompCnt);
        return item;

    }
}