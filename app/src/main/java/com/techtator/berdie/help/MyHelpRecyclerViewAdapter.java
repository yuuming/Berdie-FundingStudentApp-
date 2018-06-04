package com.techtator.berdie.help;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techtator.berdie.Models.FBModel.FBHelp;
import com.techtator.berdie.R;
import com.techtator.berdie.help.HelpFragment.OnHelpFragmentInteractionListener;


public class MyHelpRecyclerViewAdapter extends RecyclerView.Adapter<MyHelpRecyclerViewAdapter.ViewHolder>
        //ExpandableRecyclerViewAdapter<HelpParentViewHolder, HelpChildViewHolder>
{

    private final OnHelpFragmentInteractionListener mListener;
    private final HelpPresenter presenter;
    private RecyclerView mRecycler;

    public MyHelpRecyclerViewAdapter(OnHelpFragmentInteractionListener listener, HelpPresenter presenter) {
        mListener = listener;
        this.presenter = presenter;
    }

//    public MyHelpRecyclerViewAdapter(List<HelpParentList> groups) {
//        super(groups);
//    }
//
//    @Override
//    public HelpParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType){
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_help_item,parent,false);
//        return new HelpParentViewHolder(view);
//    }
//
//    @Override
//    public HelpChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_item_child,parent, false);
//        return new HelpChildViewHolder(view);
//    }

//    public void onBindChildViewHolder(HelpChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex){
//        final FBNews fbNews = (FBNews) group.getItems().get(childIndex);
//        holder.onBind(fbNews.getBody());
//        final String TitleChild = group.getTitle();
//        holder.mHelpBodyText.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                @SuppressLint("RestrictedApi") Toast toast = Toast.makeText(getApplicationContext(), TitleChild, Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        });
//
//    }
//
//    @Override
//    public void onBindGroupViewHolder(HelpParentViewHolder holder, int flatPosition, final ExpandableGroup group){
//        holder.setParentTitle(group);
//
//        if (group.getItems() == null){
//            holder.mTitleView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view){
//                    @SuppressLint("RestrictedApi") Toast toast = Toast.makeText(getApplicationContext(), group.toString(), Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//            });
//        }
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_help_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,int position) {
        holder.mItem = presenter.getHelpList(position);
        holder.mHeaderView.setText(presenter.get(position).getHeader());
        holder.mBodyView.setText(presenter.get(position).getBody());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onHelpFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return presenter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mHeaderView;
        public final TextView mBodyView;
        public FBHelp mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mHeaderView = (TextView) view.findViewById(R.id.help_title);
            mBodyView = (TextView)view.findViewById(R.id.help_body);
        }
    }
}
