package com.miaoxiang.digitalClock.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.*;

import com.miaoxiang.digitalClock.R;

public class EmptyPreference extends Preference
{

	public EmptyPreference(Context context)
	{
		super(context);
	}

	public EmptyPreference(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
	}

	public EmptyPreference(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
	}

	protected View onCreateView(ViewGroup viewgroup)
	{
		return ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.empty, null);
	}
}
