package com.mcmaster.xuq22.activity;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;

import com.mcmaster.xuq22.R;
import com.mcmaster.xuq22.process.Generator;

public class GeneratorActivity extends Activity {
//	private static TableLayout spectrumTable;
//	private static Switch[] freqSwitches = new Switch[Common.FREQUENCY_TO_WATCH];
	private Generator generator;
	
//	private MediaPlayer mediaPlayer;
	public void toggleSwitch(View view) {
		if (((Switch) view).isChecked()) {
			if (generator == null || generator.isCancelled()){
				EditText editText = (EditText)this.findViewById(R.id.phoneNumber);
				String numberStr = editText.getText().toString();
				
				if (numberStr.length() > 0){
				Long phoneNumber = Long.parseLong(numberStr);
				byte[] arrayToSend = Arrays.copyOfRange(ByteBuffer.allocate(8).putLong(phoneNumber).array(), 3, 8);
				generator = new Generator(arrayToSend);
				this.generator.execute();	
				
				}else {
					((Switch) view).setChecked(false);
				}
			}
			
		} else {
			this.generator.cancel(true);
//			mediaPlayer.release();
//			mediaPlayer = null;
		}
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_generator);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generator, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		AudioManager audioManager;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_generator,
					container, false);
			
			//The main logic for volume seekbar, this seekbar is used to control the transmitting power
			audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);;
			
		    int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		    int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		    SeekBar volControl = (SeekBar) rootView.findViewById(R.id.volbar);
		    volControl.setMax(maxVolume);
		    volControl.setProgress(curVolume);
		    volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
		        @Override
		        public void onStopTrackingTouch(SeekBar arg0) {
		        }

		        @Override
		        public void onStartTrackingTouch(SeekBar arg0) {
		        }

		        @Override
		        public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
		        }
		    });

			return rootView;
		}
	}

}
