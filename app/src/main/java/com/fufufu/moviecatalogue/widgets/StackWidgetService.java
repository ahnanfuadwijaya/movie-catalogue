package com.fufufu.moviecatalogue.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackFilmRemoteViewsFactory(this.getApplicationContext());
    }
}
