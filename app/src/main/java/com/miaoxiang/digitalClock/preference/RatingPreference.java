package com.miaoxiang.digitalClock.preference;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.preference.Preference;
import android.util.AttributeSet;

public class RatingPreference extends Preference
{

	public RatingPreference(Context context)
	{
		super(context);
	}

	public RatingPreference(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
	}

	public RatingPreference(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
	}

	public void onClick()
	{
		Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.miaoxiang.digitalClock"));
		getContext().startActivity(intent);
	}
}
