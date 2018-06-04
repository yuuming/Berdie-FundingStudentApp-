package com.techtator.berdie.scholarshipDetail;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.techtator.berdie.Models.FBModel.FBScholarship;
import com.techtator.berdie.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bominkim on 2018-04-16.
 */

public class ScholarshipDetailFragment extends Fragment implements ScholarshipDetailContract.View {

    private ScholarshipDetailContract.Presenter presenter;
    private TextView textImage, amountSD, due, text;
    private Button applyB;
    private ImageView imageSD;
    private String link;

    SimpleDateFormat dateChange = new SimpleDateFormat("MMM dd, yyyy");

    public static ScholarshipDetailFragment newInstance(FBScholarship scholar) {

        Bundle args = new Bundle();
        ScholarshipDetailFragment fragment = new ScholarshipDetailFragment();
        args.putString("scholarship_id", scholar.getId());
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scholarshipdetail, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Scholarship");

        String id = getArguments().getString("scholarship_id");
        presenter = new ScholarshipDetailPresenter(this, id, this);

        textImage = (TextView) view.findViewById(R.id.textImage_scholarshipDetail);
        amountSD = (TextView) view.findViewById(R.id.amount_scholarshipDetail);
        due = (TextView) view.findViewById(R.id.date_scholarshipDetail);
        text = (TextView) view.findViewById(R.id.text_scholarshipDetail);
        applyB = (Button) view.findViewById(R.id.button_scholarshipDetail);
        imageSD = (ImageView)view.findViewById(R.id.image_scholarshipDetail);

        applyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void setScholarshipText(String text) {
        textImage.setText(text);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }
    @Override
    public void setScholarshipAmount(Double amount) {
        amountSD.setText(String.valueOf(amount));
    }

    @Override
    public void setDetailDate(Date date) {
        String formatDate = dateChange.format(date);
         due.setText(formatDate);
    }

    @Override
    public void setTextBody(String textBody) {
        text.setText(textBody);
    }

    @Override
    public void setImageView(String image) {
        Picasso.get()
                .load(image)
                .error(R.drawable.icons8_gender_neutral_user)
                .into(imageSD);
    }

    @Override
    public void setWebLink(String link) {
        this.link = link;
    }

}
