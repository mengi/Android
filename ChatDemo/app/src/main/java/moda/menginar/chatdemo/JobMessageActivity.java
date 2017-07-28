package moda.menginar.chatdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import moda.menginar.chatdemo.adapter.ChatMessageAdapter;
import moda.menginar.chatdemo.model.ChatMessage;
import moda.menginar.chatdemo.model.User;

/**
 * Created by Menginar on 1.4.2017.
 */

public class JobMessageActivity extends  AppCompatActivity {

        public static ArrayList<String> arrList;
        public ArrayList<ChatMessage> messageArrayList;
        private ListView listView;
        private FloatingActionButton mButtonSend;
        private EditText editTextMessage;
        private ChatMessageAdapter chatMessageAdapter;
        public User user;
        public static final String CHAT_NAME = "chatName";

        public int position = -1;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            Bundle bundle = getIntent().getExtras();
            String chatName = null;
            if (bundle != null) {
                chatName = bundle.containsKey(CHAT_NAME) ? bundle.getString(CHAT_NAME) : null;
            }
            messageArrayList = new ArrayList<>();

            arrList = new ArrayList<String>();
            arrList.add("İyiyim Pislik");
            arrList.add("Ne Diyon Lanet Olasıca");
            arrList.add("Ooo hadi ama bu kadar mı marefetin");
            arrList.add("Koca kafalı aptal");
            arrList.add("Hakaret mi ediyorsun sen");
            arrList.add("Estağfurullah");
            arrList.add("Sanane kız");
            arrList.add("AAAAA çok Komik");
            arrList.add("Kendini beğenmiş");
            arrList.add("Aşağılık kompleksi var bu kızın");
            arrList.add("Merhaba ben bot !!!");

            listView = (ListView) findViewById(R.id.listView);
            mButtonSend = (FloatingActionButton) findViewById(R.id.btn_send);
            editTextMessage = (EditText) findViewById(R.id.editMessage);

            messageArrayList = MainActivity.chatMessagesMap.get(chatName);

            chatMessageAdapter = new ChatMessageAdapter(this, messageArrayList);

            listView.setAdapter(chatMessageAdapter);
            chatMessageAdapter.notifyDataSetChanged();

            mButtonSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = editTextMessage.getText().toString();
                    if (validate()) {
                        sendMessage(message);
                        editTextMessage.setText("");
                    }

                    listView.setSelection(chatMessageAdapter.getCount() - 1);
                }
            });
        }

    public boolean validate() {
        boolean valid = true;

        String message = editTextMessage.getText().toString();

        if (message.isEmpty()) {
            Snackbar.make(getCurrentFocus(), "Fill in the required fields", Snackbar.LENGTH_SHORT).show();
            valid = false;
        } else {
            editTextMessage.setError(null);
        }

        return valid;
    }

    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, 0);
        messageArrayList.add(chatMessage);
        chatMessageAdapter.notifyDataSetChanged();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new JobMessageActivity.MessageWriter());
        executorService.shutdown();
    }

    private void otherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, 1);
        messageArrayList.add(chatMessage);
        chatMessageAdapter.notifyDataSetChanged();
    }

    public class MessageWriter implements Runnable {
        private volatile boolean isRunning = true;

        @Override
        public void run() {
            while (isRunning) {
                writeMessage();
                try {
                    killMe();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void killMe() {
            isRunning = false;
        }

        private void writeMessage() {
            Random generator = new Random();
            int index = 10 - generator.nextInt(10);
            otherMessage(arrList.get(index));
        }
    }


}
