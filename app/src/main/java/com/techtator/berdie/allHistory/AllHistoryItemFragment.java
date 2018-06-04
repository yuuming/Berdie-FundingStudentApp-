package com.techtator.berdie.allHistory;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techtator.berdie.Managers.UserAuthManager;
import com.techtator.berdie.R;

import java.text.DecimalFormat;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AllHistoryItemFragment extends Fragment implements AllHistoryContract.View {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MyAllHistoryItemRecyclerViewAdapter mAdapter;
    private AllHistoryPresenter mPresenter;
    private TextView mTotalAmountView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllHistoryItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AllHistoryItemFragment newInstance(int columnCount) {
        AllHistoryItemFragment fragment = new AllHistoryItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allhistoryitem_list, container, false);
        View listView = view.findViewById(R.id.allhistoryitem_list);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("All History");

        mTotalAmountView = (TextView)view.findViewById(R.id.allhistory_total_balance_textview);

        // Set the adapter
        if (listView instanceof RecyclerView) {
            if (UserAuthManager.getInstance().getUserId() == null) return view;

            mPresenter = new AllHistoryPresenter(this, UserAuthManager.getInstance().getUserId(), this);

            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            RecyclerView.ItemDecoration itemDecoration =
                    new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
            mAdapter = new MyAllHistoryItemRecyclerViewAdapter(mPresenter, mListener);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
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

    @Override
    public void notifyTotalAmountChanged(double currentBalance) {
        DecimalFormat formatter = new DecimalFormat("#,###");

        mTotalAmountView.setText("$" + formatter.format(currentBalance));
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(TransactionHistoryEntity item);
    }
}
