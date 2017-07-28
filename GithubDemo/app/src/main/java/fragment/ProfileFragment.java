package fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.materialdesigncodelab.R;
import com.example.android.materialdesigncodelab.SearchActivity;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {

    TextView textUserName, textName, textEmail, textFollowers, textFollowing;
    ImageView imgProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_profile, container, false);

        textUserName = (TextView) view.findViewById(R.id.text_username);
        textName = (TextView) view.findViewById(R.id.text_name);
        textEmail = (TextView) view.findViewById(R.id.text_email);
        textFollowers = (TextView) view.findViewById(R.id.text_followers);
        textFollowing = (TextView) view.findViewById(R.id.text_following);

        imgProfile = (ImageView) view.findViewById(R.id.image_profile);

        getUserInfo();

        return view;
    }

    public void getUserInfo() {
        try {
            textUserName.setText(SearchActivity.user.getLogin());
            textName.setText(SearchActivity.user.getName());
            textEmail.setText(SearchActivity.user.getEmail());
            textFollowing.setText("Following : " + Integer.toString(SearchActivity.user.getFollowing()));
            textFollowers.setText("Followers : " + Integer.toString(SearchActivity.user.getFollowers()));

            Picasso.with(getActivity())
                    .load(SearchActivity.user.getAvatarUrl())
                    .into(imgProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
