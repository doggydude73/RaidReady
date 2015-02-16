package com.ajsuneson.raidready;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayCreatedTeamsFragment extends ListFragment{
	
	int mCurCheckPosition = 0;

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		List<String> test = new ArrayList<String>();
		// Populate list with our list of found teams/players
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1, test));


		if (savedInstanceState != null) {
			// Restore last state for checked position.
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
		}
	}
	
	public void setList(List<String> teamNames){
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1, teamNames));
		
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", mCurCheckPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		OpenRaidTeamList ortl = (OpenRaidTeamList)getActivity();
		String teamName = (String) l.getItemAtPosition(position);
		mCurCheckPosition = position;
		
		// TODO Make call to start the new activity that displays raid team members
        ortl.sendToCreateRaidTeam(teamName);
	}

}
