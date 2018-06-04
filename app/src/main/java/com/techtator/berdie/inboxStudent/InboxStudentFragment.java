package com.techtator.berdie.inboxStudent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techtator.berdie.Models.FBModel.FBUser;
import com.techtator.berdie.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnInboxMessageFragmentInteractionListener}
 * interface.
 */
public class InboxStudentFragment extends Fragment implements InboxStudentContract.View {

    private OnInboxMessageFragmentInteractionListener mListener;
    InboxStudentPresenter presenter;
    InboxStudentRecyclerViewAdapter adapter;

    public InboxStudentFragment() {
    }

    public static InboxStudentFragment newInstance() {
        InboxStudentFragment fragment = new InboxStudentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter  = new InboxStudentPresenter(this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox_student_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Inbox");

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.inbox_list);
            adapter = new InboxStudentRecyclerViewAdapter(presenter, mListener);
            recyclerView.setAdapter(adapter);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInboxMessageFragmentInteractionListener) {
            mListener = (OnInboxMessageFragmentInteractionListener) context;
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
    public void notifyMessageChanged() {
        adapter.notifyDataSetChanged();
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
    public interface OnInboxMessageFragmentInteractionListener {
        void onInboxListFragmentInteraction(FBUser user);
    }
}
