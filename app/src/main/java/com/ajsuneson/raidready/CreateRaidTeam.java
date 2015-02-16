package com.ajsuneson.raidready;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CreateRaidTeam extends Activity {

	public String teamName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		teamName = getIntent().getStringExtra(GlobalO.TEAM_NAME_EXTRA);
		setContentView(R.layout.activity_create_raid_team);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_raid_team, menu);
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
		retrieveRaidTeamRoster();
	}
	
	private void retrieveRaidTeamRoster(){
		List<String> listedTeams = new ArrayList<String>();
		try {
			FileInputStream fis = openFileInput(teamName);
			InputStreamReader isr = new InputStreamReader (fis);
			BufferedReader buffReader = new BufferedReader(isr);
			
			String readString = buffReader.readLine();
			
			while (readString != null){
				listedTeams.add(readString);
				readString = buffReader.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			listedTeams.removeAll(listedTeams);
		}
		listedTeams.add("Add New Member");
		DisplayCurrentTeamMembersFragment dctmf = (DisplayCurrentTeamMembersFragment) getFragmentManager().findFragmentById(R.id.displayCurrentMembersFragment);
		dctmf.setList(listedTeams);
	}
}
