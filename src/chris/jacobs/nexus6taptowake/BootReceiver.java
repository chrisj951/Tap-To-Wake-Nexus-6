package chris.jacobs.nexus6taptowake;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
 
/**
 * Author: Navid Ghahramani
 * You can contact to me with ghahramani.navid@gmail.com
 */
public class BootReceiver extends BroadcastReceiver {
 
    @Override
    public void onReceive(Context context, Intent intent) {
    	try {
			String message = "echo "+"AUTO"+" > /sys/bus/i2c/devices/1-004a/tsp";
			Process process = Runtime.getRuntime().exec(new String[] { "su", "-c", message});
			process.waitFor();
			
		} catch (IOException e1) {

		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
    }
 
}
