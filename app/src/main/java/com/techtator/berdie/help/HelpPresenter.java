package com.techtator.berdie.help;

import com.techtator.berdie.Models.FBModel.FBHelp;
import com.techtator.berdie.model.HelpDataModel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by bominkim on 2018-04-03.
 */

public class HelpPresenter implements HelpContract.Presenter{

    private HelpContract.View view;
    private final HelpDataModel helpDataModel;
    private final List<FBHelp> helpList;

    public HelpPresenter(final HelpContract.View v){
        this.view = v;
        helpList = new LinkedList<>();
        helpDataModel = new HelpDataModel();

        helpDataModel.setHelpList(new HelpDataModel.OnChangeListListener(){
            @Override
            public void notifyChangedList(List<FBHelp> list){
                helpList.clear();
                helpList.addAll(list);
                view.notifyHelpDataChanged();
            }
        });
    }
    @Override
    public FBHelp get(int position){
        return helpList.get(position);
    }

    public FBHelp getHelpList(int position){
        return helpList.get(position);
    }

    @Override
    public int size(){
        return helpList.size();
    }




}
