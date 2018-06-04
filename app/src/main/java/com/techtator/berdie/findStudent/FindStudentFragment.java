package com.techtator.berdie.findStudent;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.techtator.berdie.R;
import com.techtator.berdie.goal.GoalItemDecoration;

public class FindStudentFragment extends Fragment implements FindStudentContract.View{

    FindStudentPresenter presenter;
    private FindStudentFragment.OnFindStudentInteractionListener mListener;
    private FindStudentRecyclerViewAdapter mAdapter;
    SearchView searchWindow;

    public FindStudentFragment() {
    }

    public static FindStudentFragment newInstance() {
        FindStudentFragment fragment = new FindStudentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new FindStudentPresenter(this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findstudent_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Student");
        searchWindow = (SearchView) view.findViewById(R.id.search_window_find_student);
        searchWindow.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null && !query.isEmpty()) {
                    presenter.searchText(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                searchWindow.setOnCloseListener(new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        presenter.restoreProfileEntityList();
                        notifyDataChanged();
                        return false;
                    }
                });
                return false;
            }
        });

        // Set the adapter
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.find_student_list);
        mAdapter = new FindStudentRecyclerViewAdapter(presenter, mListener);
        recyclerView.addItemDecoration(GoalItemDecoration.createDefaultDecoration(20));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFindStudentInteractionListener) {
            mListener = (OnFindStudentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void notifyDataChanged() {
        mAdapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFindStudentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ProfileEntity item);
    }
}
