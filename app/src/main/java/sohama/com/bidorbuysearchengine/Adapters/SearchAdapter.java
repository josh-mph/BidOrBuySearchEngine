package sohama.com.bidorbuysearchengine.Adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import sohama.com.bidorbuysearchengine.Models.TradeObject;
import sohama.com.bidorbuysearchengine.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context mContext;
    public List<TradeObject> tradeObjectList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, type, price;
        public ImageView thumb, image;

        MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            type = (TextView) view.findViewById(R.id.type);
            price = (TextView) view.findViewById(R.id.price);
            image = (ImageView) view.findViewById(R.id.image);
            thumb = (ImageView) view.findViewById(R.id.thumb);
        }
    }


    public SearchAdapter(Context mContext, List<TradeObject> tradeObjectList) {
        this.mContext = mContext;
        this.tradeObjectList = tradeObjectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        TradeObject tradeObject = tradeObjectList.get(position);
        holder.title.setText(tradeObject.getTitle());
        String fullDetails = tradeObject.getType()+"\nLocation: "+tradeObject.getLocation();
        holder.type.setText(fullDetails);
        holder.price.setText(String.format(mContext.getString(R.string.currencyFormat), Float.valueOf(tradeObject.getAmount()).doubleValue()));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // loading album cover using Glide library
        try {
            URL imageUrl = new URL (tradeObject.getImages().get(0).getImage());
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            holder.image.setImageBitmap(BitmapFactory.decodeStream(is));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            URL imageUrl = new URL (tradeObject.getImages().get(0).getThumbnail());
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            holder.thumb.setImageBitmap(BitmapFactory.decodeStream(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return tradeObjectList.size();
    }
}
