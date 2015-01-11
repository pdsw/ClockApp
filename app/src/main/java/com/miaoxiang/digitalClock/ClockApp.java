package com.miaoxiang.digitalClock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.miaoxiang.digitalClock.Utils.AdMob;
import com.miaoxiang.digitalClock.Utils.UIUtils;
import com.miaoxiang.digitalClock.preference.ColorPreference;

public class ClockApp extends PreferenceActivity
	implements android.content.SharedPreferences.OnSharedPreferenceChangeListener
{

	static final String BACK_COLOR = "color_bg";
	static final String DATE_COLOR = "color_date";
	static final String DATE_COLOR_CUSTOM = "custom_color_date";
	static final String DATE_COLOR_CUSTOM_OPTION = "custom_color_option_date";
	static final String DATE_FORMAT = "format_date";
	static final String INSTANT = "instant";
	static final String TAP_ACTION = "clickShow";
	static final String TIME_COLOR = "color_time";
	static final String TIME_COLOR_CUSTOM = "custom_color_time";
	static final String TIME_COLOR_CUSTOM_OPTION = "custom_color_option_time";
	private int date_color;
	private int time_color;

	public ClockApp()
	{
		time_color = -1;
		date_color = -1;
	}

	private int getColor(CharSequence charsequence)
	{
		if (charsequence.equals("Black"))
			return 0xff000000;
		if (charsequence.equals("White"))
			return -1;
		if (charsequence.equals("Gray"))
			return 0xff888888;
		if (charsequence.equals("Brown"))
			return Color.rgb(139, 69, 19);
		if (charsequence.equals("Red"))
			return 0xffff0000;
		if (charsequence.equals("Orange"))
			return Color.rgb(255, 140, 0);
		if (charsequence.equals("Yellow"))
			return -256;
		if (charsequence.equals("Green"))
			return Color.rgb(34, 139, 34);
		if (charsequence.equals("Blue"))
			return 0xff0000ff;
		if (charsequence.equals("Purple"))
			return Color.rgb(147, 112, 219);
		if (charsequence.equals("Hot Pink"))
			return Color.rgb(255, 105, 180);
		if (charsequence.equals("Deep Pink"))
			return Color.rgb(255, 20, 147);
		else
			return -1;
	}

    private void updateSummary(String paramString)
    {
        if (paramString.equals("instant"))
            if (((CheckBoxPreference)getPreferenceManager().findPreference("instant")).getSharedPreferences().getBoolean("instant", false))
            {
                getListView().setBackgroundColor(0);
                getListView().setCacheColorHint(0);
                getWindow().setFlags(1024, 1024);
            }
        do
        {
            //return;
            getListView().setBackgroundColor(Color.rgb(0, 0, 0));
            getWindow().clearFlags(1024);
            //return;
            if ((paramString.equals("clickShow")) || (paramString.equals("color_bg")) || (paramString.equals("format_date")))
            {
                ListPreference localListPreference1 = (ListPreference)getPreferenceManager().findPreference(paramString);
                localListPreference1.setSummary(localListPreference1.getEntry());
                return;
            }
            if (paramString.equals("color_time"))
            {
                ListPreference localListPreference4 = (ListPreference)getPreferenceManager().findPreference(paramString);
                localListPreference4.setSummary(localListPreference4.getEntry());
                this.time_color = getColor(localListPreference4.getEntry());
                return;
            }
            if (paramString.equals("color_date"))
            {
                ListPreference localListPreference3 = (ListPreference)getPreferenceManager().findPreference(paramString);
                localListPreference3.setSummary(localListPreference3.getEntry());
                this.date_color = getColor(localListPreference3.getEntry());
                return;
            }
            if (paramString.equals("custom_color_option_time"))
            {
                CheckBoxPreference localCheckBoxPreference2 = (CheckBoxPreference)getPreferenceManager().findPreference("custom_color_option_time");
                ColorPreference localColorPreference2 = (ColorPreference)getPreferenceManager().findPreference("custom_color_time");
                if (localCheckBoxPreference2.getSharedPreferences().getBoolean("custom_color_option_time", false))
                {
                    localColorPreference2.setEnabled(true);
                    this.time_color = localColorPreference2.getColor();
                    return;
                }
                localColorPreference2.setEnabled(false);
                return;
            }
            if (paramString.equals("custom_color_option_date"))
            {
                CheckBoxPreference localCheckBoxPreference1 = (CheckBoxPreference)getPreferenceManager().findPreference("custom_color_option_date");
                ColorPreference localColorPreference1 = (ColorPreference)getPreferenceManager().findPreference("custom_color_date");
                ListPreference localListPreference2 = (ListPreference)getPreferenceManager().findPreference("color_date");
                if (localCheckBoxPreference1.getSharedPreferences().getBoolean("custom_color_option_date", false))
                {
                    localColorPreference1.setEnabled(true);
                    localListPreference2.setEnabled(false);
                    this.date_color = localColorPreference1.getColor();
                    return;
                }
                localColorPreference1.setEnabled(false);
                localListPreference2.setEnabled(true);
                return;
            }
            if (paramString.equals("custom_color_time"))
            {
                this.time_color = ((ColorPreference)getPreferenceManager().findPreference("custom_color_time")).getColor();
                return;
            }
        }
        while (!paramString.equals("custom_color_date"));
        this.date_color = ((ColorPreference)getPreferenceManager().findPreference("custom_color_date")).getColor();
    }

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		AdView adview;
		AdRequest adrequest;
		RelativeLayout relativelayout;
		android.widget.RelativeLayout.LayoutParams layoutparams;
		try
		{
			PackageInfo packageinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			setTitle((new StringBuilder("Digital Clock Widget - ")).append(packageinfo.versionName).toString());
		}
		catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
		{
			namenotfoundexception.printStackTrace();
		}
		addPreferencesFromResource(R.xml.preferences);
		adview = new AdView(this, AdSize.BANNER, AdMob.PUBLISHER_ID);
		adrequest = new AdRequest();
		relativelayout = new RelativeLayout(this);
		layoutparams = new android.widget.RelativeLayout.LayoutParams(-1, -2);
		layoutparams.addRule(12);
		relativelayout.addView(adview, layoutparams);
		addContentView(relativelayout, new android.widget.RelativeLayout.LayoutParams(-2, -2));
		UIUtils.changeLogCheck(this);
		adview.loadAd(adrequest);
	}

	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		menu.add(0, 1, 0, "Preview").setIcon(R.drawable.preview);
		menu.add(0, 2, 0, "Instructions").setIcon(R.drawable.instructions);
		menu.add(0, 3, 0, "Read me").setIcon(R.drawable.changes);
		menu.add(0, 4, 0, "About").setIcon(R.drawable.feedback);
		return true;
	}

	public boolean onKeyDown(int i, KeyEvent keyevent)
	{
		if (keyevent.getKeyCode() == 4 || keyevent.getKeyCode() == 3 || keyevent.getKeyCode() == 26)
		{
			finish();
			return true;
		} else
		{
			return super.onKeyDown(i, keyevent);
		}
	}

	public boolean onMenuItemSelected(int i, MenuItem menuitem)
	{
		switch (menuitem.getItemId())
		{
		default:
			return super.onMenuItemSelected(i, menuitem);

		case 1: // '\001'
			View view = getLayoutInflater().inflate(R.layout.main, (ViewGroup)findViewById(R.id.widget));
			TextView textview = (TextView)view.findViewById(R.id.time);
			TextView textview1;
			Toast toast;
			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("12hour", true))
				textview.setText("    10:30    ");
			else
				textview.setText("    22:30    ");
			textview.setTextColor(time_color);
			textview1 = (TextView)view.findViewById(R.id.date);
			textview1.setText("              Tuesday, May 19              ");
			textview1.setTextColor(date_color);
			toast = new Toast(getApplicationContext());
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setGravity(16, 0, 0);
			toast.setView(view);
			toast.show();
			return true;

		case 2: // '\002'
			UIUtils.showInstructionsDialog(this);
			return true;

		case 3: // '\003'
			UIUtils.showReadMeDialog(this);
			return true;

		case 4: // '\004'
			UIUtils.showAboutDialog(this);
			return true;
		}
	}

	protected void onPause()
	{
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedpreferences, String s)
	{
		sharedpreferences.edit().commit();
		updateSummary(s);
		sendBroadcast(new Intent("MIAOXIANG_DIGITAL_CLOCK_WIDGET_SETTINGS_CHANGED"));
	}

	protected void onStart()
	{
		super.onStart();
		updateSummary("instant");
		updateSummary("clickShow");
		updateSummary("color_bg");
		updateSummary("color_time");
		updateSummary("color_date");
		updateSummary("format_date");
		updateSummary("custom_color_option_time");
		updateSummary("custom_color_option_date");
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
}
