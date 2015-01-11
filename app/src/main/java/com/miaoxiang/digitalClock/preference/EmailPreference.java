package com.miaoxiang.digitalClock.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import com.miaoxiang.digitalClock.Utils.UIUtils;

public class EmailPreference extends Preference
{

	public EmailPreference(Context context)
	{
		super(context);
	}

	public EmailPreference(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
	}

	public EmailPreference(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
	}

	protected void onClick()
	{
		UIUtils.sendEmail(getContext());
	}
}
