package com.miaoxiang.digitalClock.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import com.miaoxiang.digitalClock.Utils.UIUtils;

public class ChangesPreference extends Preference
{

	public ChangesPreference(Context context)
	{
		super(context);
	}

	public ChangesPreference(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
	}

	public ChangesPreference(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
	}

	protected void onClick()
	{
		UIUtils.showChangeLogDialog(getContext());
	}
}
