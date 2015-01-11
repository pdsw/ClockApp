package com.miaoxiang.digitalClock.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import com.miaoxiang.digitalClock.Utils.UIUtils;

public class ReadMePreference extends Preference
{

	public ReadMePreference(Context context)
	{
		super(context);
	}

	public ReadMePreference(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
	}

	public ReadMePreference(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
	}

	protected void onClick()
	{
		UIUtils.showReadMeDialog(getContext());
	}
}
