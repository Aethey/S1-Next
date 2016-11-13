package me.ykrank.s1next.view.adapter;

import android.app.Activity;

import me.ykrank.s1next.view.adapter.delegate.FooterProgressAdapterDelegate;
import me.ykrank.s1next.view.adapter.delegate.PmAdapterDelegate;

public final class PmRecyclerViewAdapter extends BaseRecyclerViewAdapter {

    public PmRecyclerViewAdapter(Activity activity) {
        super(activity);

        addAdapterDelegate(new PmAdapterDelegate(activity));
        addAdapterDelegate(new FooterProgressAdapterDelegate(activity));

        setHasStableIds(true);
    }
}
