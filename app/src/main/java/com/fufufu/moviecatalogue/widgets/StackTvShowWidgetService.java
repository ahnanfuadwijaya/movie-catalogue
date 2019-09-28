package com.fufufu.moviecatalogue.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackTvShowWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackTvShowRemoteViewsFactory(this.getApplicationContext());
    }
}
