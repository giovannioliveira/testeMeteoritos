package ufrj.museunacional.testedemeteorito;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("onBoot","received");
	    Intent myIntent = new Intent(context, MainActivity.class);
	    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    context.startActivity(myIntent);
		
	}

}
