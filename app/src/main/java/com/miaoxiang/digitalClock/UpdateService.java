package com.miaoxiang.digitalClock;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

// Referenced classes of package com.miaoxiang.digitalClock:
//			AbstractUpdateService, ClockWidget

public class UpdateService extends AbstractUpdateService
{

	public UpdateService()
	{
	}

	public RemoteViews buildUpdate()
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean flag = prefs.getBoolean("12hour", true);
		boolean flag1 = prefs.getBoolean("ampm", false);
		String s = prefs.getString("clickShow", "Do Nothing");
		String s1 = prefs.getString("color_time", "White");
		String s2 = prefs.getString("color_date", "White");
		String s3 = prefs.getString("color_bg", "No Background");
		String s4 = prefs.getString("format_date", "EE, MM dd");
		Date date = new Date();
		String s5 = (new StringBuilder()).append(date.getHours()).toString();
		String s6 = (new StringBuilder()).append(date.getMinutes()).toString();
		if (date.getHours() > 12 && flag)
			s5 = (new StringBuilder()).append(date.getHours() - 12).toString();
		if (date.getHours() == 0 && flag)
			s5 = "12";
		if (date.getMinutes() < 10)
			s6 = (new StringBuilder("0")).append(s6).toString();
		String s7 = " AM";
		if (date.getHours() >= 12)
			s7 = " PM";
		RemoteViews remoteviews;
		boolean flag2;
		boolean flag3;
		android.app.PendingIntent pendingintent;
		if (s3.equals("Black"))
			remoteviews = new RemoteViews(getPackageName(), R.layout.main);
		else
			remoteviews = new RemoteViews(getPackageName(), R.layout.main_trasparent);
		flag2 = prefs.getBoolean("custom_color_option_time", false);
		remoteviews.setTextViewText(R.id.time, (new StringBuilder(String.valueOf(s5))).append(":").append(s6).toString());
		remoteviews.setTextColor(R.id.time, getColor(s1, true, flag2));
		if (flag1)
		{
			remoteviews.setTextViewText(R.id.apm, s7);
			remoteviews.setTextColor(R.id.apm, getColor(s1, true, flag2));
		} else
		{
			remoteviews.setTextViewText(R.id.apm, "");
		}
		flag3 = prefs.getBoolean("custom_color_option_date", false);
		remoteviews.setTextViewText(R.id.date, getDateFormatString(s4, date, flag));
		remoteviews.setTextColor(R.id.date, getColor(s2, false, flag3));
		pendingintent = getClickAction(s, this);
		remoteviews.setOnClickPendingIntent(R.id.time, pendingintent);
		remoteviews.setOnClickPendingIntent(R.id.widget, pendingintent);
		remoteviews.setOnClickPendingIntent(R.id.date, pendingintent);
		return remoteviews;
	}

	protected String getDateFormatString(String s, Date date, boolean flag)
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		if (prefs.getBoolean("hide_date", false))
			return "";
		String s1 = (new SimpleDateFormat("dd")).format(date);
		if (s.equals("EE, MM dd"))
			return (new StringBuilder(String.valueOf(getString(getDayofWeek(date.getDay()))))).append(", ").append(getString(getMonth(date.getMonth()))).append(" ").append(s1).toString();
		if (s.equals("EE, dd MM"))
			return (new StringBuilder(String.valueOf(getString(getDayofWeek(date.getDay()))))).append(", ").append(s1).append(" ").append(getString(getMonth(date.getMonth()))).toString();
		if (s.equals("MM-dd-yyyy"))
			return (new SimpleDateFormat(s)).format(date);
		if (s.equals("MM/dd/yyyy"))
			return (new SimpleDateFormat(s)).format(date);
		if (s.equals("dd-MM-yyyy"))
			return (new SimpleDateFormat(s)).format(date);
		if (s.equals("dd/MM/yyyy"))
			return (new SimpleDateFormat(s)).format(date);
		if (s.equals("yyyy-MM-dd"))
			return (new SimpleDateFormat(s)).format(date);
		else
			return "";
	}

	public void updateView(RemoteViews remoteviews)
	{
		AppWidgetManager.getInstance(this).updateAppWidget(new ComponentName(this, ClockWidget.class), remoteviews);
	}
}
