package com.ajsuneson.raidready;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class OpenRaidTeamList extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open_raid_team);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.open_raid_team, menu);
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
	
	public void onStart(){
		super.onStart();
		retrieveRoster();
		
	}
	
	private void retrieveRoster() {
		List<String> listedTeams = new ArrayList<String>();
		try {
			FileInputStream fis = openFileInput(GlobalO.FILE_NAME_OF_TEAMS);
			InputStreamReader isr = new InputStreamReader (fis);
			BufferedReader buffReader = new BufferedReader(isr);
			
			String readString = buffReader.readLine();
			
			while (readString != null){
				listedTeams.add(readString);
				readString = buffReader.readLine();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			listedTeams.removeAll(listedTeams);
		}
		
		DisplayCreatedTeamsFragment dctf = (DisplayCreatedTeamsFragment) getFragmentManager().findFragmentById(R.id.displayCurrentRaidTeamsFragment);
		dctf.setList(listedTeams);
	}
}
