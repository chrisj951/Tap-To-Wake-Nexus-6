package chris.jacobs.nexus6taptowake;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	boolean currentStatus;
	TextView statusText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		statusText = (TextView) findViewById(R.id.textOne);
		this.getStatus();
		this.setStatusText();
		
	}
	
	private void getStatus(){
		File file = new File("/sys/bus/i2c/devices/1-004a/tsp");
		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			
			if(line.equals("AUTO")){
				line = "ON";
				this.currentStatus = true;
			}else{
				this.currentStatus = false;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			statusText.setText(e.getMessage());
		} catch (IOException e) {
			statusText.setText(e.getMessage());

		}
	}
	
	private void setStatusText(){
		if(this.currentStatus){
			statusText.setText("Double Tap to Wake is ON");
		}else{
			statusText.setText("Double Tap to Wake is OFF");
		}

	}
	
	public void toggle(View button){
		String toggle = "AUTO";
		
		if(this.currentStatus){
			toggle = "OFF";
		}
		
		try {
			String message = "echo "+toggle+" > /sys/bus/i2c/devices/1-004a/tsp";
			Process process = Runtime.getRuntime().exec(new String[] { "su", "-c", message});
			process.waitFor();
			this.currentStatus = !this.currentStatus;
			this.setStatusText();
			
		} catch (IOException e1) {
			statusText.setText(e1.getMessage());

		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
