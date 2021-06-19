package pp.facerecognizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Show_Attendance_Adapter extends RecyclerView.Adapter<Show_Attendance_Adapter.ViewHolder> {
    private List<Model_Attendance> itemList;
    private Context context;
    private ItemClickListener clickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_txt,Status_txt,Name_txt;


        public ViewHolder(View itemView) {
            super(itemView);
            Name_txt = itemView.findViewById (R.id.itemlist_name);
            date_txt = itemView.findViewById(R.id.itemlist_date);
            Status_txt =  itemView.findViewById(R.id.itemlist_status);

        }
    }

    public Show_Attendance_Adapter(Context context, List<Model_Attendance> itemList) {

        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public Show_Attendance_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_show_attendance, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(Show_Attendance_Adapter.ViewHolder holder, final int position) {
        holder.date_txt.setText(itemList.get(position).getDate ());
        holder.Name_txt.setText (itemList.get (position).getStudent ());
        if(itemList.get (position).getStatus () == "A"){
            holder.Status_txt.setTextColor(ContextCompat.getColor(context, R.color.Red));
            holder.Status_txt.setText(itemList.get(position).getStatus ());

        }
        else{

            holder.Status_txt.setText(itemList.get(position).getStatus ());

        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public interface ItemClickListener {

        public void itemClick(View view, int position);
    }
}

