package am.greattours.mobile;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListMain extends ArrayAdapter<String>
{

    private final Activity context;

    private final String[] lTitle;
    private final Integer[] lImage;

    public CustomListMain(Activity context, String[] ltitle, Integer[] limage)
    {
        super(context, R.layout.row_item, ltitle);

        this.context = context;

        this.lTitle = ltitle;
        this.lImage = limage;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.row_item, null, true);

        TextView txtTitle = (TextView)rowView.findViewById(R.id.txtView);
        txtTitle.setText(lTitle[position]);

        ImageView imgView = (ImageView)rowView.findViewById(R.id.imgView);
        imgView.setImageResource(lImage[position]);

        return rowView;
    }

}