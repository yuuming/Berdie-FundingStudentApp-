package com.techtator.berdie.help;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.techtator.berdie.Models.FBModel.FBHelp;
import com.techtator.berdie.R;


public class HelpFragment extends Fragment implements HelpContract.View{
    private OnHelpFragmentInteractionListener mListener;
    private MyHelpRecyclerViewAdapter myHelpRecyclerViewAdapter;
    Button contactButton;

    public static HelpFragment newInstance(){
        HelpFragment fragment = new HelpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_help_list,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Help");

        TextView mHeaderView = (TextView) view.findViewById(R.id.help_title);
        TextView mBodyView = (TextView) view.findViewById(R.id.news_body);
        contactButton = (Button) view.findViewById(R.id.contact_button);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMailer();
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_help);
        myHelpRecyclerViewAdapter = new MyHelpRecyclerViewAdapter(mListener,new HelpPresenter(this));
        recyclerView.setAdapter(myHelpRecyclerViewAdapter);

        return view;
    }

    private void callMailer(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);

        intent.setType("text/plain");
        intent.setData(Uri.parse("mailto:xxx@yyy.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Title");
        intent.putExtra(Intent.EXTRA_TEXT,"body");

        startActivity(Intent.createChooser(intent,null));
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof HelpFragment.OnHelpFragmentInteractionListener) {
//            mListener = (HelpFragment.OnHelpFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnGoalFragmentInteractionListener");
//        }
//    }

    @Override
    public void notifyHelpDataChanged() {
        myHelpRecyclerViewAdapter.notifyDataSetChanged();

    }

    public interface OnHelpFragmentInteractionListener{
        void onHelpFragmentInteraction(FBHelp item);
    }
}
