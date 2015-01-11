package com.miaoxiang.digitalClock;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

// Referenced classes of package com.miaoxiang.digitalClock:
//			UpdateService

public class ClockWidget extends AppWidgetProvider
{

	public ClockWidget()
	{
	}

	public void onDeleted(Context context, int ai[])
	{
		super.onDeleted(context, ai);
		context.stopService(new Intent(context, UpdateService.class));
		Log.d("Digital Clock", "onDeleted");
	}

	public void onEnabled(Context context)
	{
		super.onEnabled(context);
		context.startService(new Intent(context, UpdateService.class));
		Log.d("Digital Clock", "onEnabled");
	}

	public void onUpdate(Context context, AppWidgetManager appwidgetmanager, int ai[])
	{
		super.onEnabled(context);
		context.startService(new Intent(context, UpdateService.class));
		Log.d("Digital Clock", "onUpdate");
	}
}
