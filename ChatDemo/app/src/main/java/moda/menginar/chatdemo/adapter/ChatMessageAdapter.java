package moda.menginar.chatdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import moda.menginar.chatdemo.R;
import moda.menginar.chatdemo.model.ChatMessage;


public class ChatMessageAdapter extends ArrayAdapter<ChatMessage> {
    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1;

    Context context;

    public ChatMessageAdapter(Context context, ArrayList<ChatMessage> chatMessages) {
        super(context, R.layout.item_send_message, chatMessages);
        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage item = getItem(position);
        int number = 0;

        if (item.getType() == 0) number = MY_MESSAGE;
        else if (item.getType() == 1) number = OTHER_MESSAGE;

        return number;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == MY_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_send_message_two, parent, false);

            TextView textView = (TextView) convertView.findViewById(R.id.textViewSendMessage);
            ImageView imageViewSend = (ImageView) convertView.findViewById(R.id.imageViewSend);
            TextView textDate = (TextView) convertView.findViewById(R.id.text_date);


            Picasso.with(context)
                    .load(R.drawable.user_pacific)
                    .into(imageViewSend);
            textView.setText(getItem(position).getText());
            textDate.setText(getItem(position).getDate());

        } else if (viewType == OTHER_MESSAGE) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_other_message_two, parent, false);

            TextView textView = (TextView) convertView.findViewById(R.id.otherMessageText);
            ImageView imageViewOther = (ImageView) convertView.findViewById(R.id.imageViewOther);
            TextView textDate = (TextView) convertView.findViewById(R.id.text_date);

            Picasso.with(context)
                    .load(R.drawable.user_pratikshya)
                    .into(imageViewOther);
            textView.setText(getItem(position).getText());
            textDate.setText(getItem(position).getDate());
        }

        return convertView;
    }
}
