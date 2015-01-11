package com.miaoxiang.digitalClock;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Date;

public abstract class AbstractUpdateService extends Service
{

	private Object lock;
	protected SharedPreferences prefs;
//	protected final BroadcastReceiver receiver = new BroadcastReceiver() {
//
//		final AbstractUpdateService this$0;
//
//		public void onReceive(Context context, Intent intent)
//		{
//			String s = intent.getAction();
//			if ("android.intent.action.TIME_SET".equals(s) || "android.intent.action.TIME_TICK".equals(s) || "MIAOXIANG_DIGITAL_CLOCK_WIDGET_SETTINGS_CHANGED".equals(s) || "android.intent.action.SCREEN_OFF".equals(s) || "android.intent.action.SCREEN_ON".equals(s) || "android.intent.action.USER_PRESENT".equals(s))
//			{
//				synchronized (lock)
//				{
//					Log.d("Digital Clock", "Update broadcast received.");
//					updateView(buildUpdate());
//				}
//				return;
//			} else
//			{
//				return;
//			}
//			exception;
//			obj;
//			JVM INSTR monitorexit ;
//			throw exception;
//		}
//
//
//			{
//				this$0 = AbstractUpdateService.this;
//				super();
//			}
//	};
    protected final BroadcastReceiver receiver = new BroadcastReceiver()
    {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
        {
            String str = paramAnonymousIntent.getAction();
            if (("android.intent.action.TIME_SET".equals(str)) || ("android.intent.action.TIME_TICK".equals(str)) || ("MIAOXIANG_DIGITAL_CLOCK_WIDGET_SETTINGS_CHANGED".equals(str)) || ("android.intent.action.SCREEN_OFF".equals(str)) || ("android.intent.action.SCREEN_ON".equals(str)) || ("android.intent.action.USER_PRESENT".equals(str)))
                synchronized (AbstractUpdateService.this.lock)
                {
                    Log.d("Digital Clock", "Update broadcast received.");
                    AbstractUpdateService.this.updateView(AbstractUpdateService.this.buildUpdate());
                    return;
                }
        }
    };

	public AbstractUpdateService()
	{
		lock = new Object();
	}

	public abstract RemoteViews buildUpdate();

	protected PendingIntent getClickAction(String s, Context context)
	{
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Intent intent = new Intent("android.intent.action.MAIN");
		PendingIntent pendingintent;
		if (s.equals("Bring me here"))
		{
			intent.setClassName("com.miaoxiang.digitalClock", "com.miaoxiang.digitalClock.ClockApp");
			pendingintent = PendingIntent.getActivity(context, 0, intent, 0);
		} else
		{
			if (s.equals("Bring me to alarm"))
			{
				intent.setClassName("com.htc.android.worldclock", "com.htc.android.worldclock.WorldClockTabControl");
				PendingIntent pendingintent3 = PendingIntent.getActivity(context, 0, intent, 0);
				if (isIntentAvailable(context, intent))
					return pendingintent3;
				intent.setClassName("com.android.deskclock", "com.android.deskclock.AlarmClock");
				PendingIntent pendingintent4 = PendingIntent.getActivity(context, 0, intent, 0);
				if (isIntentAvailable(context, intent))
					return pendingintent4;
				intent.setClassName("com.google.android.deskclock", "com.android.deskclock.AlarmClock");
				PendingIntent pendingintent5 = PendingIntent.getActivity(context, 0, intent, 0);
				if (isIntentAvailable(context, intent))
					return pendingintent5;
				intent.setClassName("com.motorola.blur.alarmclock", "com.motorola.blur.alarmclock.AlarmClock");
				PendingIntent pendingintent6 = PendingIntent.getActivity(context, 0, intent, 0);
				if (isIntentAvailable(context, intent))
					return pendingintent6;
				intent.setClassName("com.sec.android.app.clockpackage", "com.sec.android.app.clockpackage.ClockPackage");
				PendingIntent pendingintent7 = PendingIntent.getActivity(context, 0, intent, 0);
				if (isIntentAvailable(context, intent))
				{
					return pendingintent7;
				} else
				{
					intent.setClassName("com.android.alarmclock", "com.android.alarmclock.AlarmClock");
					return PendingIntent.getActivity(context, 0, intent, 0);
				}
			}
			if (s.equals("Bring me to calendar"))
			{
				intent.setClassName("com.htc.calendar", "com.htc.calendar.LaunchActivity");
				PendingIntent pendingintent1 = PendingIntent.getActivity(context, 0, intent, 0);
				if (isIntentAvailable(context, intent))
					return pendingintent1;
				intent.setClassName("com.google.android.calendar", "com.android.calendar.LaunchActivity");
				PendingIntent pendingintent2 = PendingIntent.getActivity(context, 0, intent, 0);
				if (isIntentAvailable(context, intent))
				{
					return pendingintent2;
				} else
				{
					intent.setClassName("com.android.calendar", "com.android.calendar.LaunchActivity");
					return PendingIntent.getActivity(context, 0, intent, 0);
				}
			}
			pendingintent = PendingIntent.getActivity(context, 0, new Intent("android.intent.action.VIEW", Uri.parse("")), 0);
		}
		return pendingintent;
	}

	protected int getColor(String s, boolean flag, boolean flag1)
	{
		if (flag1)
		{
			prefs = PreferenceManager.getDefaultSharedPreferences(this);
			if (flag)
				return prefs.getInt("custom_color_time", -1);
			else
				return prefs.getInt("custom_color_date", -1);
		}
		if (s.equals("Black"))
			return 0xff000000;
		if (s.equals("White"))
			return -1;
		if (s.equals("Gray"))
			return 0xff888888;
		if (s.equals("Brown"))
			return Color.rgb(139, 69, 19);
		if (s.equals("Red"))
			return 0xffff0000;
		if (s.equals("Orange"))
			return Color.rgb(255, 140, 0);
		if (s.equals("Yellow"))
			return -256;
		if (s.equals("Green"))
			return Color.rgb(34, 139, 34);
		if (s.equals("Blue"))
			return 0xff0000ff;
		if (s.equals("Purple"))
			return Color.rgb(147, 112, 219);
		if (s.equals("Hot Pink"))
			return Color.rgb(255, 105, 180);
		if (s.equals("Deep Pink"))
			return Color.rgb(255, 20, 147);
		else
			return -1;
	}

	protected abstract String getDateFormatString(String s, Date date, boolean flag);

	protected int getDayofWeek(int i)
	{
		switch (i)
		{
		default:
			return -1;

		case 0: // '\0'
			return 0x7f060005;

		case 1: // '\001'
			return 0x7f060006;

		case 2: // '\002'
			return 0x7f060007;

		case 3: // '\003'
			return 0x7f060008;

		case 4: // '\004'
			return 0x7f060009;

		case 5: // '\005'
			return 0x7f06000a;

		case 6: // '\006'
			return 0x7f06000b;
		}
	}

	protected int getMonth(int i)
	{
		switch (i)
		{
		default:
			return -1;

		case 0: // '\0'
			return 0x7f060013;

		case 1: // '\001'
			return 0x7f060014;

		case 2: // '\002'
			return 0x7f060015;

		case 3: // '\003'
			return 0x7f060016;

		case 4: // '\004'
			return 0x7f060017;

		case 5: // '\005'
			return 0x7f060018;

		case 6: // '\006'
			return 0x7f060019;

		case 7: // '\007'
			return 0x7f06001a;

		case 8: // '\b'
			return 0x7f06001b;

		case 9: // '\t'
			return 0x7f06001c;

		case 10: // '\n'
			return 0x7f06001d;

		case 11: // '\013'
			return 0x7f06001e;
		}
	}

	protected int getShortDayofWeek(int i)
	{
		switch (i)
		{
		case 0: // '\0'
			return R.string.Sunday_short;

		case 1: // '\001'
			return R.string.Monday_short;

		case 2: // '\002'
			return R.string.Tuesday_short;

		case 3: // '\003'
			return R.string.Wednesday_short;

		case 4: // '\004'
			return R.string.Thursday_short;

		case 5: // '\005'
			return R.string.Friday_short;

		case 6: // '\006'
			return R.string.Saturday_short;

        default:
            return -1;
		}
	}

	public boolean isIntentAvailable(Context context, Intent intent)
	{
		return context.getPackageManager().queryIntentActivities(intent, 0x10000).size() > 0;
	}

	public IBinder onBind(Intent intent)
	{
		Log.d("Digital Clock", "onBind");
		return null;
	}

	public void onCreate()
	{
		Log.d("Digital Clock", "onCreate");
		super.onCreate();
		updateView(buildUpdate());
		IntentFilter intentfilter = new IntentFilter();
		intentfilter.addAction("android.intent.action.TIME_TICK");
		intentfilter.addAction("android.intent.action.TIME_SET");
		intentfilter.addAction("android.intent.action.TIMEZONE_CHANGED");
		intentfilter.addAction("MIAOXIANG_DIGITAL_CLOCK_WIDGET_SETTINGS_CHANGED");
		intentfilter.addAction("android.intent.action.SCREEN_ON");
		registerReceiver(receiver, intentfilter);
	}

	public void onDestroy()
	{
		Log.d("Digital Clock", "onDestroy");
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	public void onStart(Intent intent, int i)
	{
		Log.d("Digital Clock", "onStart");
		super.onStart(intent, i);
	}

	public int onStartCommand(Intent intent, int i, int j)
	{
		Log.d("Digital Clock", "onStartCommand");
		return 1;
	}

	public abstract void updateView(RemoteViews remoteviews);

}
