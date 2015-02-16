package com.ajsuneson.raidready;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ajsuneson.raidready.Utilities.DownloadWebpageTask;

public class CreateMemberDetailFragment extends Fragment{

    public static Activity thisActivity;

	/**
	 * Create a new instance of DetailsFragment, initialized to
	 * show the text at 'index'.
	 */
	public static CreateMemberDetailFragment newInstance(int index) {
		CreateMemberDetailFragment f = new CreateMemberDetailFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			// We have different layouts, and in one of them this
			// fragment's containing frame doesn't exist.  The fragment
			// may still be created from its saved state, but there is
			// no reason to try to create its view hierarchy because it
			// won't be displayed.  Note this is not needed -- we could
			// just run the code below, where we would create and return
			// the view hierarchy; it would just never be used.
			return null;
		}
		View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        Spinner spinner = (Spinner)rootView.findViewById(R.id.RealmListOptions);
        TextView dataDump = (TextView) rootView.findViewById(R.id.dataDump);

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            thisActivity = getActivity();
            new DownloadWebpageTask().execute(GlobalO.REALM_LIST_URL);
        } else {
            dataDump.setText("No network connection available.");
        }


        return rootView;
	}



}
