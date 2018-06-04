package com.techtator.berdie.allHistory;

import com.techtator.berdie.Models.FBModel.FBTransactionHistory;

public interface AllHistoryContract {
    interface View {
        void notifyDataChanged();

        void notifyTotalAmountChanged(double currentBalance);
    }

    interface Presenter {
        TransactionHistoryEntity get(int position);
        int size();
    }
}
