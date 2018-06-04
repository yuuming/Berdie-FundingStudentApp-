package com.techtator.berdie.model;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FBLiveData<T> extends LiveData<T> {
    private final DatabaseReference ref;
    private final Query query;
    ValueEventListener listener;

    FBLiveData(DatabaseReference ref) {
        this.ref = ref;
        this.query = null;
    }

    public FBLiveData(Query query) {
        this.query = query;
        this.ref = null;
    }

    public void setListener(ValueEventListener listener) {
        this.listener = listener;
        if (ref!=null) {
            ref.addValueEventListener(listener);
        } else if (query!=null) {
            query.addValueEventListener(listener);
        } else {
            Log.d(getClass().getSimpleName(), "ERRRRRORRRR!!!!! create new LiveData instance!!!");
        }
    }

    @Override
    protected void setValue(T value) {
        super.setValue(value);
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (ref!=null && listener !=null) {
            ref.addValueEventListener(listener);
        }
        if (query!=null && listener !=null) {
            query.addValueEventListener(listener);
        }
    }

    @Override
    protected void onInactive() {
        if (ref!=null && listener !=null) {
            ref.removeEventListener(listener);
        }
        if (query!=null && listener !=null) {
            query.removeEventListener(listener);
        }
    }
}
