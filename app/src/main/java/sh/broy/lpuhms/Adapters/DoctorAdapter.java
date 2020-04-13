package sh.broy.lpuhms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.broy.lpuhms.R;
import java.util.List;
import sh.broy.lpuhms.Models.Appointment;
import sh.broy.lpuhms.Models.Doctor;
import sh.broy.lpuhms.Utils.ListOnClick;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.MyViewHolder> {

    private Context mContext;
    private List<Doctor> mList;
    private ListOnClick listOnClick;

    public DoctorAdapter(Context mContext, List<Doctor> mList, ListOnClick listOnClick) {
        this.mContext = mContext;
        this.mList = mList;
        this.listOnClick = listOnClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.appointment_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(mList.get(position).getName(), mList.get(position).getEmail(),
                mList.get(position).getSpecialization(), this.listOnClick);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, issue, time;
        private CardView docRoot;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            issue = itemView.findViewById(R.id.issue);
            time = itemView.findViewById(R.id.time);
            docRoot = itemView.findViewById(R.id.docRoot);
        }

        private void setData(String dName, String email, String spcl, ListOnClick listOnClick) {

            this.name.setText(dName);
            this.issue.setText(email);
            this.time.setText(spcl);

            docRoot.setOnClickListener(view -> listOnClick.onClick(new String[]{dName}));
        }
    }
}
