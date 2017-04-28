package arab_offers.lue.com.FourAdapters;

import android.app.Activity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import arab_offers.lue.com.Interfaces.PassText;
import arab_offers.lue.com.Models.CountryModel;
import arab_offers.lue.com.Models.RegionModel;
import arab_offers.lue.com.R;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder>{
    static  int k=0;
    ArrayList<RegionModel> loadEntities=new ArrayList<RegionModel>();
    Activity context1;
    View getView1;
    View view1;
    PassText passText=null;
    int click=0;
    public AreaAdapter(Activity context2, ArrayList<RegionModel> loadEntities, PassText passText){
        this.loadEntities = loadEntities;
        context1 = context2;
        this.passText=passText;
        //getView1=view;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView contact_name,contact_number;
        public AppCompatTextView checktext;
        public ImageView images_alert;
        public ViewHolder(View v){
            super(v);
          /*contact_name = (TextView) v.findViewById(R.id.contact_name);
            contact_number = (TextView) v.findViewById(R.id.contact_number);*/
            checktext=(AppCompatTextView) v.findViewById(R.id.checktext);
            images_alert=(ImageView) v.findViewById(R.id.images_alert);
        }
    }
    @Override
    public AreaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        view1 = LayoutInflater.from(context1).inflate(R.layout.alert_list_item, parent, false);
        ViewHolder viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }
    @Override
    public void onBindViewHolder(final ViewHolder Vholder, final int position){
       // Vholder.checktext.setText(loadEntities.get(position).getName());
        Vholder.checktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                k= position;
                Vholder.images_alert.setVisibility(View.VISIBLE);
                notifyDataSetChanged();
                passText.pass(k);
                click=1;
            }
        });
        if(click!=0){
            if(position==k){
                Vholder.images_alert.setVisibility(View.VISIBLE);
            }else{
                Vholder.images_alert.setVisibility(View.GONE);
            }
        }
       // Vholder.images_alert.setVisibility(View.GONE);

        try {
            Vholder.checktext.setText(loadEntities.get(position).getName());
        }catch (Exception e){e.printStackTrace();}
    }
    @Override
    public int getItemCount(){
        //return loadEntities.size();
        return loadEntities.size();
    }
}
