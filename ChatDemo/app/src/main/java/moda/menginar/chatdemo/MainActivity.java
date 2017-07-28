package moda.menginar.chatdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import moda.menginar.chatdemo.model.ChatMessage;
import moda.menginar.chatdemo.model.User;

public class MainActivity extends AppCompatActivity {
    public ArrayList<ChatMessage> chatMessageArrayList;
    public User user;
    RecyclerView recycler_view;

    JobMessageAdapter jobMessageAdapter;
    public static HashMap<String, ArrayList<ChatMessage>> chatMessagesMap = new HashMap<>();

    static{
        chatMessagesMap.put("Metin Savaş",  new ArrayList<ChatMessage>());
        chatMessagesMap.put("Ömer Faruk Çatal",  new ArrayList<ChatMessage>());
        chatMessagesMap.put("Zeynep Kavruk",  new ArrayList<ChatMessage>());
        chatMessagesMap.put("Merve Yılmaz",  new ArrayList<ChatMessage>());
        chatMessagesMap.put("Esra Karaca",  new ArrayList<ChatMessage>());
        chatMessagesMap.put("Betül Utçugiller",  new ArrayList<ChatMessage>());
        chatMessagesMap.put("Seher Yanmaz",  new ArrayList<ChatMessage>());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_message);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> nameList = new ArrayList<>();
        for(String key:chatMessagesMap.keySet()){
            nameList.add(key);
        }


        jobMessageAdapter = new JobMessageAdapter(nameList, MainActivity.this);
        recycler_view.setAdapter(jobMessageAdapter);
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView listAvatar;
        private TextView listTitle;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_jobmessage, parent, false));
            listAvatar = (ImageView) itemView.findViewById(R.id.list_avatar);
            listTitle = (TextView) itemView.findViewById(R.id.list_title);

        }
    }

    private static class JobMessageAdapter extends RecyclerView.Adapter<ViewHolder> {

        //User user;
        ArrayList<String> nameList;
        Context context;

        public JobMessageAdapter(ArrayList<String> nameList, Context context) {
            this.nameList = nameList;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            Picasso.with(context)
                    .load(R.drawable.user_pratikshya)
                    .into(holder.listAvatar);
            holder.listTitle.setText(nameList.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, JobMessageActivity.class);
                    intent.putExtra(JobMessageActivity.CHAT_NAME, nameList.get(position));
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return nameList.size();
        }
    }
}
