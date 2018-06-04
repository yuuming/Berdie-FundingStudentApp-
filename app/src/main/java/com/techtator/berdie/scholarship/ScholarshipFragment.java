package com.techtator.berdie.scholarship;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.techtator.berdie.Models.FBModel.FBScholarship;
import com.techtator.berdie.R;

/**
 * Created by bominkim on 2018-04-10.
 */

public class ScholarshipFragment extends Fragment implements ScholarshipContract.View {

    private ScholarshipPresenter presenter;
    private RecyclerView recyclerView;
    private ScholarshipRecyclerViewAdapter adapter;
    private SearchView searchView;
    private OnScholarshipInteracionListener mListener;

    public static ScholarshipFragment newInstance() {
        Bundle args = new Bundle();
        ScholarshipFragment fragment = new ScholarshipFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ScholarshipFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ScholarshipFragment.OnScholarshipInteracionListener) {
            mListener = (ScholarshipFragment.OnScholarshipInteracionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnScholarshipInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new ScholarshipPresenter(this, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scholarship_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Scholarship");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_scholarship);
        adapter = new ScholarshipRecyclerViewAdapter(presenter, mListener);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        searchView = (SearchView) view.findViewById(R.id.searchView_scholarship);

        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query != null && !query.isEmpty()) {
                    presenter.searchText(query, ScholarshipFragment.this);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        presenter.restoreScholarshipList(ScholarshipFragment.this);
                        return false;
                    }
                });
//
                return false;
            }
        });
        return view;
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
    public void notifyScholarshipDataChanged() {
        adapter.notifyDataSetChanged();
    }

    public interface OnScholarshipInteracionListener {
        void onScholarshipDetail(FBScholarship item);
    }

}
