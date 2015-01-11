package com.miaoxiang.digitalClock.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.miaoxiang.digitalClock.R;
import com.miaoxiang.digitalClock.Utils.UberColorPickerDialog;


public class ColorPreference extends Preference
{

	private ImageView preview;
	private int selectedColor;

	public ColorPreference(Context context)
	{
		super(context);
		selectedColor = -1;
	}

	public ColorPreference(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
		selectedColor = -1;
	}

	public ColorPreference(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
		selectedColor = -1;
	}

	private void updatePreference(int i)
	{
		selectedColor = i;
		android.content.SharedPreferences.Editor editor = getEditor();
		editor.putInt(getKey(), i);
		editor.commit();
	}

	public int getColor()
	{
		return selectedColor;
	}

//	protected void onClick()
//	{
//		(new UberColorPickerDialog(getContext(), new UberColorPickerDialog.OnColorChangedListener() {
//
//			final ColorPreference this$0;
//
//			public void colorChanged(int i)
//			{
//				preview.setBackgroundColor(i);
//				updatePreference(i);
//			}
//
//
//			{
//				this$0 = ColorPreference.this;
//				super();
//			}
//		}, selectedColor)).show();
//	}
    protected void onClick()
    {
        new UberColorPickerDialog(getContext(), new UberColorPickerDialog.OnColorChangedListener()
        {
            public void colorChanged(int paramAnonymousInt)
            {
                ColorPreference.this.preview.setBackgroundColor(paramAnonymousInt);
                ColorPreference.this.updatePreference(paramAnonymousInt);
            }
        }
                , this.selectedColor).show();
    }

	protected View onCreateView(ViewGroup viewgroup)
	{
		View view = super.onCreateView(viewgroup);
		this.preview = (ImageView)view.findViewById(R.id.preview);
		this.preview.setBackgroundColor(selectedColor);
		return view;
	}

	protected void onSetInitialValue(boolean flag, Object obj)
	{
		int i;
		if (flag)
			i = getPersistedInt(50);
		else
			i = ((Integer)obj).intValue();
		if (!flag)
			persistInt(i);
		selectedColor = i;
	}


}
