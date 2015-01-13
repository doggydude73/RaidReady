package com.ajsuneson.raidready;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayCurrentTeamMembersFragment extends ListFragment{

	boolean mDualPane;
	int mCurCheckPosition = 0;
	List<String> currentMembers = new ArrayList<String>();

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

//		String teamName = getArguments().getString(GlobalO.TEAM_NAME_EXTRA);
		
		// Open up the team file
//		FileInputStream fis = openFileInput(teamName);

		currentMembers.add("Add New Player");
		// Populate list with our list of found teams/players
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1, currentMembers));

		// Check to see if we have a frame in which to embed the details
		// fragment directly in the containing UI.
		View detailsFrame = getActivity().findViewById(R.id.displayMemberDetailFragment);
		mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

		if (savedInstanceState != null) {
			// Restore last state for checked position.
			mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
		}else{
			// Get the last position in the list and select that
		}

		if (mDualPane) {
			// In dual-pane mode, the list view highlights the selected item.
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			// Make sure our UI is in the correct state.
			//showDetails();
		}
	}
	
	public void setList(List<String> test){
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1, test));
		currentMembers = test;
		mCurCheckPosition = currentMembers.size() - 1; 
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("curChoice", mCurCheckPosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
		mCurCheckPosition = position;
		showDetails();
	}
	
	/**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails() {
        

        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            getListView().setItemChecked(mCurCheckPosition, true);

            // Check what fragment is currently shown, replace if needed.
            CreateMemberDetailFragment details = (CreateMemberDetailFragment)
                    getFragmentManager().findFragmentById(R.id.displayMemberDetailFragment);
            if (details == null || details.getShownIndex() != mCurCheckPosition) {
                // Make new fragment to show this selection.
                details = CreateMemberDetailFragment.newInstance(mCurCheckPosition);

                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (mCurCheckPosition == 0) {
                    ft.replace(R.id.displayMemberDetailFragment, details);
                }
                
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            Intent intent = new Intent();
            intent.setClass(getActivity(), CreateMemberDetailActivity.class);
            intent.putExtra("index", mCurCheckPosition);
            startActivity(intent);
        }
    }
}
