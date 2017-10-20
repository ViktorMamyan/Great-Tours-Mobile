package am.greattours.mobile;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CustomListAllPackages extends ArrayAdapter<String>
{
    private final Activity context;

    private final String[] titleID;
    private final String[] imageID;
    private final String[] startendID;
    private final String[] durationpriceID;

    public CustomListAllPackages(Activity context, String[] titleid, String[] imageid, String[] startendid, String[] durationpriceid)
    {
        super(context, R.layout.tourlist_item, titleid);

        this.context = context;

        this.titleID = titleid;
        this.imageID = imageid;
        this.startendID = startendid;
        this.durationpriceID = durationpriceid;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.tourlist_item, null, true);

        TextView title = (TextView) rowView.findViewById(R.id.title);
        title.setText(titleID[position]);

        URL picUrl = null;
        Bitmap lPicture = null;

        try {
            picUrl = new URL(imageID[position]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            lPicture = BitmapFactory.decodeStream(picUrl.openConnection() .getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageView tourlistitem = (ImageView) rowView.findViewById(R.id.tourlistitem);
        tourlistitem.setImageBitmap(lPicture);

        TextView startenddate = (TextView) rowView.findViewById(R.id.startenddate);
        startenddate.setText(startendID[position]);

        TextView durationprice = (TextView) rowView.findViewById(R.id.durationprice);
        durationprice.setText(durationpriceID[position]);

        return rowView;
    }

}