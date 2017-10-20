package am.greattours.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{
    ListView MainListView;

    String[] ListItems={
            "Փնտրել"
            ,"Փաթեթների Ցանկ"
            ,"Տաք Տուրեր"
            ,"Վաղ Ամրագրում"
            ,"Հայտ"
            ,"Ծանուցում"
            ,"Տեսարժան Վայր"
            ,"Հյուրանոց"
            ,"Նախընտրություններ"};

    Integer[] imageItems = {R.drawable.searchinpackage
            ,R.drawable.packagelist
            ,R.drawable.hottours
            ,R.drawable.earlybook
            ,R.drawable.book
            ,R.drawable.notification
            ,R.drawable.place
            ,R.drawable.hotel
            ,R.drawable.favorite};

    private void LoadListViewItems()
    {
        MainListCustom adapter = new MainListCustom(MainActivity.this, ListItems, imageItems);
        MainListView=(ListView)findViewById(R.id.MainListView);
        MainListView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainListView=(ListView)findViewById(R.id.MainListView);
        MainListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {

        if (position == 0)
        {
            Intent i = new Intent(MainActivity.this, AllPackagesActivity.class);
            startActivityForResult(i,1);
        }




            }
        });

        LoadListViewItems();

    }

}