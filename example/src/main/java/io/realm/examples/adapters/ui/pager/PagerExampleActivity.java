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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.examples.adapters.R;
import io.realm.examples.adapters.model.TimeStamp;

public class PagerExampleActivity extends AppCompatActivity {

    private Realm realm;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        realm = Realm.getDefaultInstance();

        // RealmResults are "live" views, that are automatically kept up to date, even when changes happen
        // on a background thread. The RealmBaseAdapter will automatically keep track of changes and will
        // automatically refresh when a change is detected.
        RealmResults<TimeStamp> timeStamps = realm.where(TimeStamp.class).findAll();
        final MyListPagerAdapter adapter = new MyListPagerAdapter(getSupportFragmentManager(), timeStamps);

        pager = (ViewPager) findViewById(R.id.pager);

        pager.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.listview_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            final String timestamp = Long.toString(System.currentTimeMillis());
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.createObject(TimeStamp.class).setTimeStamp(timestamp);
                }
            });
            return true;
        }

        if (id == R.id.action_remove) {
            final RealmResults<TimeStamp> results = realm.where(TimeStamp.class).findAll();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.get(pager.getCurrentItem()).deleteFromRealm();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
