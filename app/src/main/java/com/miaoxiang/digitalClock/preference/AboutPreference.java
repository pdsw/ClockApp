package com.miaoxiang.digitalClock.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import com.miaoxiang.digitalClock.Utils.UIUtils;

public class AboutPreference extends Preference
{

	public AboutPreference(Context context)
	{
		super(context);
	}

	public AboutPreference(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
	}

	public AboutPreference(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
	}

	protected void onClick()
	{
		UIUtils.showAboutDialog(getContext());
	}
}
