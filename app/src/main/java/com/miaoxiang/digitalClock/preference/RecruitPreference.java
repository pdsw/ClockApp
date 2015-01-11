package com.miaoxiang.digitalClock.preference;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.util.AttributeSet;

public class RecruitPreference extends Preference
{

	public RecruitPreference(Context context)
	{
		super(context);
	}

	public RecruitPreference(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
	}

	public RecruitPreference(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
	}

	protected void onClick()
	{
		Intent intent = new Intent("android.intent.action.SEND");
		intent.putExtra("android.intent.extra.EMAIL", new String[] {
			"miaoxiang@miaoxiang.net"
		});
		intent.putExtra("android.intent.extra.SUBJECT", "Recruitment ");
		intent.setType("message/rfc822");
		getContext().startActivity(Intent.createChooser(intent, "Title"));
	}
}
