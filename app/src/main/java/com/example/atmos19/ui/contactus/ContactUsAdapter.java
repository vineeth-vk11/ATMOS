package com.example.atmos19.ui.contactus;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.atmos19.R;
import java.util.ArrayList;

public class ContactUsAdapter extends RecyclerView.Adapter<ContactUsAdapter.ViewHolder>{


    private ArrayList<ContactUs> contactUs;
    private Context context;
    public ContactUsAdapter(Context context, ArrayList<ContactUs> list)
    {
        this.context = context;
        contactUs = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView contactUsName, contactUsPost;
        ImageView btnCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contactUsName = itemView.findViewById(R.id.contactUsName);
            contactUsPost = itemView.findViewById(R.id.contactUsPost);
            btnCall = itemView.findViewById(R.id.ivCall);

        }
    }
    @NonNull
    @Override
    public ContactUsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contact_us,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactUsAdapter.ViewHolder holder, int position) {
        holder.contactUsName.setText(contactUs.get(position).getName());
        holder.contactUsPost.setText(contactUs.get(position).getPost());
        final String nu = contactUs.get(position).getNumber();
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ nu));
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return contactUs.size();
    }
}
