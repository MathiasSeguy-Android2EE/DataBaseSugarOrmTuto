package com.android2ee.android.tuto.lib.databse.sugarorm;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android2ee.android.tuto.lib.databse.sugarorm.model.Human;

public class MainActivity extends Activity {
	/**
	 * The textview that displays the whathappens
	 */
	private TextView txvWhatHappens = null;
	/**
	 * The WhatHappens messages
	 */
	private StringBuilder strWhatHappens;
	/**
	 * Carriage return
	 */
	private final String carriageReturn = "\r\n\r\n";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txvWhatHappens = (TextView) findViewById(R.id.txvWhatHappens);
		strWhatHappens = new StringBuilder(getString(R.string.whatHappens_title));
		// Insert a new record
		// -------------------
		Human george = new Human("Bush", "Georges", "blond", "green", 76, "WhoCareOfThatField",null);
		george.save();
		long idGeorge = george.getId();
		updateTxvWhatHappens(getString(R.string.success_creating_human,george,idGeorge));
		Human sadam = new Human("Sadam", "Hussein", "black", "black", 52, "WhoCareOfThatField",george);
		sadam.save();
		long idSadam = sadam.getId();
		updateTxvWhatHappens(getString(R.string.success_creating_human,sadam,idSadam));
		// update that line
		// ----------------
		george.setAge(56);
		george.save();
		updateTxvWhatHappens(getString(R.string.success_updating_human,george,idGeorge));
		// Query that line
		// ---------------
		Human sameSadam = Human.findById(Human.class, idSadam);
		long idSameSadam=sameSadam.getId();
		updateTxvWhatHappens(getString(R.string.retieve_element,sameSadam,idSameSadam));
		// Query all lines:
		// ----------------
		List<Human> humans = Human.listAll(Human.class);
		for(Human human:humans) {
			updateTxvWhatHappens(getString(R.string.retieve_element,human,human.getId()));
		}
		// And then delete it:
		// -------------------
		sadam.delete();
		updateTxvWhatHappens(getString(R.string.success_deleting_human,sadam));
		// Or delete All
		// -------------
		Human.deleteAll(Human.class);
		updateTxvWhatHappens(getString(R.string.delete_all));
		// Query that line
		// ---------------
		humans = Human.listAll(Human.class);
		if(humans.size()==0) {
			updateTxvWhatHappens(getString(R.string.retrieve_no_element));
		}
	}

	/******************************************************************************************/
	/** Displaying what happens **************************************************************************/
	/******************************************************************************************/
	/**
	 * Displays the information to the user of what's happening
	 * 
	 * @param message
	 */
	private void updateTxvWhatHappens(String message) {
		strWhatHappens.append(carriageReturn);
		strWhatHappens.append(message);
		txvWhatHappens.setText(strWhatHappens);
		Log.e("MainActivity SugarORM",message);
		// Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

	}

}
