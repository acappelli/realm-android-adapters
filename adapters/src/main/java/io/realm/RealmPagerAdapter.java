package io.realm;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * @author Andrea Cappelli
 */

public abstract class RealmPagerAdapter<T extends RealmModel> extends FragmentStatePagerAdapter {
    @Nullable
    protected OrderedRealmCollection<T> adapterData;
    @Nullable
    private final RealmChangeListener<? extends BaseRealm> listener;

    public RealmPagerAdapter(FragmentManager fm, @Nullable OrderedRealmCollection<T> data) {
        super(fm);
        this.adapterData = data;

        this.listener = new RealmChangeListener<BaseRealm>() {
            @Override
            public void onChange(BaseRealm results) {
                notifyDataSetChanged();
            }
        };

        if (data != null) {
            addListener(data);
        }
    }

    @Override
    public int getCount() {
        if (adapterData == null) {
            return 0;
        }

        return adapterData.size();
    }

    private void addListener(@NonNull OrderedRealmCollection<T> data) {
        if (data instanceof RealmResults) {
            RealmResults realmResults = (RealmResults) data;
            realmResults.realm.handlerController.addChangeListenerAsWeakReference(listener);
        } else if (data instanceof RealmList) {
            RealmList realmList = (RealmList) data;
            realmList.realm.handlerController.addChangeListenerAsWeakReference(listener);
        } else {
            throw new IllegalArgumentException("RealmCollection not supported: " + data.getClass());
        }
    }

    private void removeListener(@NonNull OrderedRealmCollection<T> data) {
        if (data instanceof RealmResults) {
            RealmResults realmResults = (RealmResults) data;
            realmResults.realm.handlerController.removeWeakChangeListener(listener);
        } else if (data instanceof RealmList) {
            RealmList realmList = (RealmList) data;
            realmList.realm.handlerController.removeWeakChangeListener(listener);
        } else {
            throw new IllegalArgumentException("RealmCollection not supported: " + data.getClass());
        }
    }


    /**
     * Updates the data associated with the Adapter.
     *
     * Note that RealmResults and RealmLists are "live" views, so they will automatically be updated to reflect the
     * latest changes. This will also trigger {@code notifyDataSetChanged()} to be called on the adapter.
     *
     * This method is therefore only useful if you want to display data based on a new query without replacing the
     * adapter.
     *
     * @param data the new {@link OrderedRealmCollection} to display.
     */
    public void updateData(@Nullable OrderedRealmCollection<T> data) {
        if (listener != null) {
            if (adapterData != null) {
                removeListener(adapterData);
            }
            if (data != null) {
                addListener(data);
            }
        }

        this.adapterData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }
}
