package in.celest.xash3d.dedicated;
import android.app.*;
import android.content.pm.ResolveInfo;
import android.os.*;
import android.widget.*;
import android.widget.RelativeLayout.*;
import android.text.*;
import android.view.*;
import android.content.*;

import java.util.List;

public class ConnectActivity extends Activity
{

	private boolean useHacks = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		layout.setOrientation(LinearLayout.VERTICAL);

		Switch hacks = new Switch(this);
		hacks.setText(hacks.isChecked()?R.string.l_scut_gamem2:R.string.l_scut_gamem1);
		hacks.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		hacks.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				hacks.setText(hacks.isChecked()?R.string.l_scut_gamem2:R.string.l_scut_gamem1);
				useHacks = hacks.isChecked();
			}
		});
		layout.addView(hacks);

		//make app list
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, 0);

		String s = "";
		String temp;

		if (pkgAppsList != null) for (final ResolveInfo inf : pkgAppsList)
		{
			temp = inf.activityInfo.name;
			if (temp.contains("in.celest.xash3d.")
					&& (!temp.contains("in.celest.xash3d.dedicated.")))
			{
				s += temp + "\n";

				final boolean isXash = temp.equals("in.celest.xash3d.LauncherActivity");

				Button v = new Button(this);
				v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
				v.setText(inf.loadLabel(getPackageManager()) + (isXash?" [no mod]":""));
				v.setOnClickListener(new View.OnClickListener() {
					String l = inf.activityInfo.name;
					boolean b = isXash;
					@Override
					public void onClick(View view) {
						//TODO: call launch action
						Intent intent = new Intent();
						intent.setAction("in.celest.xash3d.START");
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

						String arguments = DedicatedActivity.autostarted?DedicatedActivity.autoArgv:DedicatedStatics.getArgv(getApplicationContext());

						String temp2;

						if (useHacks) temp2 = "-dev 3 +xashds_hacks 1 +rcon_address 127.0.0.1 +rcon_password "
								+ CommandParser.parseSingleParameter(arguments, "+rcon_password");
							else temp2 = "-dev 3 +connect localhost:27015";

						intent.putExtra("argv", temp2);

						temp2 = CommandParser.parseSingleParameter(arguments, "-game");
						if (!temp2.equals("")) intent.putExtra("gamedir", temp2);

						if (!isXash) try {
							temp2 = getPackageManager().getPackageInfo(l.substring(0, l.lastIndexOf('.')), 0).applicationInfo.dataDir + "/lib";
							intent.putExtra("gamelibdir", temp2);
							startActivity(intent);
						} catch (Exception e) { Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show(); return; }

						startActivity(intent);
						finish();
					}
				});
				layout.addView(v);
			}
		}

		Button closeButton = new Button(this);
		closeButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		closeButton.setText(R.string.b_close);
		closeButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v)
				{
					finish();
				}
			});

		layout.addView(closeButton);
		
		setContentView(layout);

		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
	}
}
