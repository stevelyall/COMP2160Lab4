package tru.lyall.lab4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import android.widget.Toast;

public class ShowStandingsActivity extends Activity implements OnClickListener{

	private TextView p1WinsTextView, p1NumWins, p2WinsTextView, p2NumWins;
	private Button backButton, resetStats;
	private String p1Name, p2Name;
	// counters for total number of wins since stats were last reset
	private int p1Wins;
	private int p2Wins;
	
	/** Called when the activity is first created.
	 * Reads player standings from text file, and displays
	 * them in the TextView objects.
	 * @param savedInstanceState default Bundle
	 * @throws FileNotFoundException
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.show_standings_activity);
	    p1WinsTextView = (TextView) findViewById(R.id.p1WinsTextView);
	    p2WinsTextView = (TextView) findViewById(R.id.p2WinsTextView);
	    p1NumWins = (TextView) findViewById(R.id.p1NumWins);
	    p2NumWins = (TextView) findViewById(R.id.p2NumWins);
	    backButton = (Button) findViewById(R.id.backButton);
	    backButton.setOnClickListener(this);
	    resetStats = (Button) findViewById(R.id.resetStats);
	    resetStats.setOnClickListener(this);
	    
	    	// reads player names from file
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
	
	    // update labels with player names, unless default names are used
	    if (!p1Name.equals("Player 1")) {
	    	p1WinsTextView.setText("Player 1" + " (" + p1Name + ") wins:");
	    }
	    if (!p2Name.equals("Player 2")) {
	    	p2WinsTextView.setText("Player 2" + " (" + p2Name + ") wins:");
	    }
	    
	    p1Wins = 0;
	    p2Wins = 0;
	    
	    // reads text file into string
	    try {
	    	FileInputStream fin = openFileInput("save.txt");
	    	Scanner s = new Scanner(fin);
	    	String wlString = "";
	    	while (s.hasNext()) {
	    		wlString += s.nextLine();
	    	}
	    	s.close();
	    	Log.i("IO","read file success" + wlString);
	    	// counts chars representing X or O wins, incrememt counters
		    for (int a=0;a<wlString.length();a++) {
		    	if (wlString.charAt(a)=='X') {
			    	p1Wins++;
			    }
			    if (wlString.charAt(a)=='O') {
			    	p2Wins++;
			    }
		   }
	    }
	    catch (FileNotFoundException e) {
	    	Log.e("IO","Can't find save file",e);
	    }

	   // display totals
	    p1NumWins.setText(Integer.toString(p1Wins));
	    p2NumWins.setText(Integer.toString(p2Wins));
	}

	/**
	 * Handler for button presses. Returns to main menu when back
	 * button is pressed, and resets stats counter to zero when reset
	 * is pushed.
	 * @param v Button pushed.
	 */
	@Override
	public void onClick(View v) {
		// return to main menu
		if (v.getId()==(R.id.backButton)) {
			Intent toMenu= new Intent(this, MainMenuActivity.class);
				startActivity(toMenu);
				this.finish();
		}
		// clears standings
		if (v.getId()==(R.id.resetStats)) {
			deleteFile("save.txt");
			p1NumWins.setText("0");
			p2NumWins.setText("0");
			Toast.makeText(getBaseContext(), "Stats reset", Toast.LENGTH_SHORT).show();
			Log.i("IO","save.txt deleted");
		}
	}
	
	/**
	 * Returns to main menu when the device back button is pushed.
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent toMenu = new Intent(this,MainMenuActivity.class);
		startActivity(toMenu);
	}
}
