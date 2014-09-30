package tru.lyall.lab4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import tru.lyall.lab4.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EnterNamesActivity extends Activity implements OnClickListener {

	// form elements
	private TextView name1EditText, name2EditText;
	private Button saveNamesButton;
	private String p1Name;
	private String p2Name;
	
	/**
	 * Runs when activity is created. Reads player names from the save file
	 * and updates EditText to show current names.
	 * @param savedInstanceState Bundle for saved instance
	 * @throws FileNotFoundException
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_names_activity);
		name1EditText = (TextView) findViewById(R.id.name1EditText);
		name2EditText = (TextView) findViewById(R.id.name2EditText);
		saveNamesButton = (Button) findViewById(R.id.saveNamesButton);
		saveNamesButton.setOnClickListener(this);

		// checks for file with names, reads player names from file
		try {
			FileInputStream fin = openFileInput("playerNames.txt");
			Scanner s = new Scanner(fin);
			s.useDelimiter("\n");
			while (s.hasNext()) {

				p1Name = s.nextLine();
				p2Name = s.nextLine();
			}
			s.close();
			Log.i("IO", "read file success" + p1Name);

		} catch (FileNotFoundException e) {
			Log.e("IO", "Can't find saveName file", e);
			p1Name = "Player 1";
			p2Name = "Player 2";
		}
		name1EditText.setText(p1Name);
		name2EditText.setText(p2Name);
	}

	/**
	 * Handler for save button click. Gets values in EditText objects
	 * and ends activity, which will these values to a file.
	 * @param view Button clicked
	 * @see onDestroy()
	 */
	@Override
	public void onClick(View v) {
		// update player names when button is clicked, go back to menu
		p1Name = name1EditText.getText().toString();
		p2Name = name2EditText.getText().toString();
		Intent toMenu = new Intent(this, MainMenuActivity.class);
		startActivity(toMenu);
		this.finish();
	}

	/**
	 * Ensures a return to menu when the activity is destroyed when the
	 * device back button is pressed.
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent toMenu = new Intent(this, MainMenuActivity.class);
		startActivity(toMenu);
	}
	/**
	 * Called when the activity is destroyed, and saves the new player name
	 * values to a text file.
	 * 
	 * @throws FileNotFoundException
	 * @throws IoException
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			FileOutputStream fout = openFileOutput("playerNames.txt", 0);
			OutputStreamWriter out = new OutputStreamWriter(fout);
			out.write(p1Name);
			out.write("\n");
			out.write(p2Name);
			out.close();
			Log.i("IO", "playerNames.txt write success");
			Log.i("IO", "wrote " + p1Name + p2Name);
		} catch (FileNotFoundException e) {
			Log.e("IO", "can't find file when saving", e);
		} catch (IOException e) {
			Log.e("IO", "IOException thrown reading file", e);
		}
	}
}
