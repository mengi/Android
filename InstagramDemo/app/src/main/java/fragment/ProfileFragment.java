package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.materialdesigninstagram.R;
import com.example.android.materialdesigninstagram.SearchActivity;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    TextView textFullName, textUserName;
    ImageView imgProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(
                R.layout.activity_profile, container, false);

        textFullName = (TextView) view.findViewById(R.id.text_fullname);
        textUserName = (TextView) view.findViewById(R.id.text_username);
        imgProfile = (ImageView) view.findViewById(R.id.image_profile);


        getUserInfo();

        return view;
    }

    public void getUserInfo() {
        try {
            textFullName.setText(SearchActivity.instagramData.getItems().get(0).getUser().getFullName());
            textUserName.setText(SearchActivity.instagramData.getItems().get(0).getUser().getUsername());

            Picasso.with(getActivity()).
                    load(SearchActivity.instagramData.getItems().get(0).getUser().getProfilePicture())
                    .into(imgProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
