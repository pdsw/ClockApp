package com.miaoxiang.digitalClock.Utils;

import android.content.*;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.app.AlertDialog;

import com.miaoxiang.digitalClock.R;

public class UIUtils
{

	public UIUtils()
	{
	}

//	public static boolean changeLogCheck(Context context)
//	{
//		int i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
//		SharedPreferences sharedpreferences = context.getSharedPreferences("KEY_CHANGELOG_VERSION", 0);
//		if (sharedpreferences.getInt("KEY_CHANGELOG_VERSION_VIEWED", 0) >= i)
//			break MISSING_BLOCK_LABEL_81;
//		android.content.SharedPreferences.Editor editor = sharedpreferences.edit();
//		editor.putInt("KEY_CHANGELOG_VERSION_VIEWED", i);
//		editor.commit();
//		showChangeLogDialog(context);
//		return true;
//		android.content.pm.PackageManager.NameNotFoundException namenotfoundexception;
//		namenotfoundexception;
//		Log.w("Unable to get version code. Will not show changelog", namenotfoundexception);
//		return false;
//	}
    public static boolean changeLogCheck(Context context)
    {
        try
        {
            int i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            SharedPreferences localSharedPreferences = context.getSharedPreferences("KEY_CHANGELOG_VERSION", 0);
            if (localSharedPreferences.getInt("KEY_CHANGELOG_VERSION_VIEWED", 0) < i)
            {
                SharedPreferences.Editor localEditor = localSharedPreferences.edit();
                localEditor.putInt("KEY_CHANGELOG_VERSION_VIEWED", i);
                localEditor.commit();
                showChangeLogDialog(context);
                return true;
            }
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
            Log.w("Unable to get version code. Will not show changelog", localNameNotFoundException);
        }
        return false;
    }

	public static void sendEmail(Context context)
	{
		Intent intent = new Intent("android.intent.action.SEND");
		intent.putExtra("android.intent.extra.EMAIL", new String[] {
			"miaoxiang@miaoxiang.net"
		});
		intent.putExtra("android.intent.extra.SUBJECT", "Digital Clock Widget ");
		intent.setType("message/rfc822");
		context.startActivity(Intent.createChooser(intent, "Title"));
	}

	public static void showAboutDialog(final Context context)
	{
		View view = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.about, null);
		AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
		builder.setTitle("About");
		builder.setView(view);
		builder.setPositiveButton("Thank you", null);
		builder.setNegativeButton("Changes", new android.content.DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialoginterface, int i)
			{
				UIUtils.showChangeLogDialog(context);
			}
		});
		builder.show();
	}


	public static void showChangeLogDialog(Context context)
	{
		try
		{
			PackageInfo packageinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
			builder.setTitle((new StringBuilder("Ver. ")).append(packageinfo.versionName).append(" change log").toString());
			builder.setIcon(R.drawable.icon_small);
			builder.setMessage("+ internal bug fix\n-----------------------\nTODO: further optimize for Honeycomb");
			builder.setPositiveButton("Thanks!", null);
			builder.setCancelable(false);
			builder.show();
			//return;
		}
		catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
		{
			Log.w("Unable to get version code.", namenotfoundexception);
		}
	}

	public static void showInstructionsDialog(Context context)
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
		builder.setIcon(R.drawable.icon_small);
		builder.setTitle("To Install:");
		builder.setMessage(R.string.rules);
		builder.setNeutralButton("OK", null);
		builder.show();
	}

	public static void showIntroNotification(Context context)
	{
	}

	public static void showReadMeDialog(Context context)
	{
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
		builder.setIcon(R.drawable.icon_small);
		builder.setTitle(R.string.ChangeTitle);
		builder.setMessage(R.string.ChangeSummary);
		builder.setNeutralButton("OK", null);
		builder.show();
	}
}
