package com.miaoxiang.digitalClock.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import com.miaoxiang.digitalClock.Utils.UIUtils;

public class InstructionsPreference extends Preference
{

	public InstructionsPreference(Context context)
	{
		super(context);
	}

	public InstructionsPreference(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
	}

	public InstructionsPreference(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
	}

	protected void onClick()
	{
		UIUtils.showInstructionsDialog(getContext());
	}
}
