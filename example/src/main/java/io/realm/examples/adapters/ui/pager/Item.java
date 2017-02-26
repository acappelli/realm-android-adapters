package io.realm.examples.adapters.ui.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.examples.adapters.R;
import io.realm.examples.adapters.model.TimeStamp;

/**
 * Created by andreacappelli on 13/02/17.
 */

public class Item extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Realm realm = Realm.getDefaultInstance();

        final TimeStamp timeStamp = realm.where(TimeStamp.class).equalTo("timeStamp", getArguments().getString("timestamp")).findFirst();

        TextView textView = (TextView) view.findViewById(R.id.textview);
        textView.setText(timeStamp.getTimeStamp());

    }
}
