package com.ajsuneson.raidready;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NameRaidTeam extends Activity {
	
	final String TAG = "CreateRaidTeamName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_name_raid);
	}
	
	public void createTeamRoster(View v){
		
		String teamName = ((EditText) findViewById(R.id.createTeamNameInput)).getText().toString();
		teamName = teamName.trim();
		teamName = teamName.replaceAll("%", "");
		
		// Continuously loop until all double spaces are removed
		while(teamName.contains("  "))
			teamName = teamName.replaceAll("  ", " ");
		
		// Name is too long
		if (teamName.length() > 99)
		{
			Toast.makeText(this, "Your team name is too long.", Toast.LENGTH_LONG).show();
			return;
		}
		
		// Add the name of the team into the team file
		try {
			FileInputStream currentRaidTeamNames = openFileInput(GlobalO.FILE_NAME_OF_TEAMS);
			InputStreamReader isr = new InputStreamReader (currentRaidTeamNames);
			BufferedReader buffReader = new BufferedReader (isr);
			
			List<String> listedTeams = new ArrayList<String>();
			
			// Get a list of the current raid teams
			String readString = buffReader.readLine();
			while (readString != null){
				
				// No duplicate team names allowed
				if (readString.equals(teamName)){
					Toast.makeText(this, "Team Name Already Exists", Toast.LENGTH_LONG).show();
					return;
				}
				
				// If not duplicated, continue to add to the list of current teams and reading from the file
				listedTeams.add(readString);
				readString = buffReader.readLine();
			}
			
			
			// Open up the output file.
			FileOutputStream raidTeamNames = openFileOutput(GlobalO.FILE_NAME_OF_TEAMS, Context.MODE_PRIVATE);
			OutputStreamWriter osw = new OutputStreamWriter (raidTeamNames);
			
			// Write all old teams and a new line character
			for (int i = 0;i < listedTeams.size(); i++ ){
				osw.write(listedTeams.get(i));
				osw.write("\n");
			}
			
			// Write in the newly created team and flush the writer and close 
			osw.write(teamName);
			osw.flush();
			osw.close();

            // Create the File for the new team
            FileOutputStream newRaidTeamFile = openFileOutput(teamName, Context.MODE_PRIVATE);
            newRaidTeamFile.close();
			
			// Proceed to next activity
			launchTeamCreation(teamName);
		} catch (FileNotFoundException e) {
			try {
				
				// If the raid team file was not found, first time app running.
				// Create the new file, add the team, and close writer
				FileOutputStream raidTeamNames = openFileOutput(GlobalO.FILE_NAME_OF_TEAMS, Context.MODE_PRIVATE);
				OutputStreamWriter osw = new OutputStreamWriter (raidTeamNames);
				osw.write(teamName);
				osw.flush();
				osw.close();
				
				// Proceed to the next activity
				launchTeamCreation(teamName);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void launchTeamCreation(String teamName){
		Intent creationIntent = new Intent(this, CreateRaidTeam.class);
		
		if (!teamName.equals("")){

			
			creationIntent.putExtra(GlobalO.TEAM_NAME_EXTRA, teamName);
			startActivity(creationIntent);
		}
	}
}
