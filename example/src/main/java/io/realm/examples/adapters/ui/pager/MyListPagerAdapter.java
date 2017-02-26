/*
 * Copyright 2016 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.realm.examples.adapters.ui.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmPagerAdapter;
import io.realm.examples.adapters.model.TimeStamp;

public class MyListPagerAdapter extends RealmPagerAdapter<TimeStamp> {


    public MyListPagerAdapter(FragmentManager fm, @Nullable OrderedRealmCollection<TimeStamp> data) {
        super(fm, data);
    }


    @Override
    public Fragment getItem(int position) {
        if (adapterData == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("timestamp", adapterData.get(position).getTimeStamp());
        Item item = new Item();
        item.setArguments(bundle);
        return item;
    }
}
