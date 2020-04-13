package sh.broy.lpuhms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sh.broy.lpuhms.Models.Appointment;
import com.broy.lpuhms.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.MyViewHolder> {

    private Context mContext;
    private List<Appointment> mList;
    private boolean isDoctor;

    public AppointmentAdapter(Context mContext, List<Appointment> mList, boolean isDoctor) {
        this.mContext = mContext;
        this.mList = mList;
        this.isDoctor = isDoctor;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.appointment_view, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(mList.get(position).getpName(), mList.get(position).getdName(), mList.get(position).getIssue(),
                mList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, issue, time;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            issue = itemView.findViewById(R.id.issue);
            time = itemView.findViewById(R.id.time);
        }

        private void setData(String pName, String dName, String issue, String time) {

            if (isDoctor) {
                this.name.setText(pName);
            } else {
                this.name.setText(dName);
            }

            this.issue.setText(issue);
            this.time.setText(time);
        }
    }
}
