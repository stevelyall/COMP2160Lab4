package tru.lyall.lab4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlayGameActivity extends Activity {

	private int spacesLeft = 9;
	private boolean xTurn = true; // flag for which turn it is
	private boolean gameOver = false; // true when game ends, for returning to menu
	private String p1Name; 
	private String p2Name;
	private MediaPlayer mp;
	
	// ImageView objects for each space on board
	private ImageView space11, space12, space13, space21, space22, space23, space31, space32, space33;
	private TextView gameText;
	private OnClickListener handler = new OnClickListener() {
		public void onClick(View v) {
			Log.i("Space clicked", "clicked"); // for debugging
			spaceClicked((ImageView)v);
		}
	};
	
	/**
	 * Sets a given spot on the game board to display an X.
	 * The ImageView is set to the appropriate drawable, as well
	 * as the ImageView's tag attribute so that CheckVictory()
	 * can reference the ImageView.
	 * @param space spot on the board to change to X
	 */
	// sets given ImageView to X or O
	private void setX(ImageView space) {
		space.setImageResource(R.drawable.x);
		space.setTag(R.drawable.x);
	}
	/**
	 * Sets a given spot on the game board to display an O.
	 * The ImageView is set to the appropriate drawable, as well
	 * as the ImageView's tag attribute so that CheckVictory()
	 * can reference the ImageView.
	 * @param space spot on the board to change to O
	 */
	private void setO(ImageView space) {
		space.setImageResource(R.drawable.o);
		space.setTag(R.drawable.o);
	}
	
	/**
	 * Checks to see whether a player has already moved to that space or not.
	 * @param space
	 * @return true is the space already has an X or O in it, false otherwise.
	 */
	private boolean isOccupied(ImageView space) {
		if (space.getTag().equals("empty")) {
			return false;
		}
		return true;
	}
	
	/**
	 * Responds to clicks on ImageViews in the PlayGameActivity. 
	 * Ends the activity if the game has finished, or alternates 
	 * between X and O player turns.
	 * @param v the space clicked on by the user
	 */
	// called when a space is clicked, alternates between placing Xs and Os on each turn
	private void spaceClicked(ImageView v) {
		if (gameOver) {
			Intent toMenu = new Intent(this,MainMenuActivity.class);
			startActivity(toMenu);
			this.finish();
		}
		else {
			// for each turn, check whether space is available to move to, make the move, and move to next turn
			if (xTurn) {
				if (isOccupied(v)) {
					Toast.makeText(getBaseContext(), "Please pick an empty space", Toast.LENGTH_SHORT).show();
				} 
				else {
					setX(v);
					spacesLeft--;
					xTurn = false;
					gameText.setText(p2Name + ", place an O:");
				}
			}
			else {
				
				if (isOccupied(v)) {
					Toast.makeText(getBaseContext(), "Please pick an empty space", Toast.LENGTH_SHORT).show();
				}
				else {
					setO(v);
					spacesLeft--;
					xTurn = true;
					gameText.setText(p1Name + ", place an X:");
				}
			}
			// check for wins
			if (checkVictory(R.drawable.x)) {
				gameOver = true;
				mp.start(); //play sound
				Toast.makeText(getBaseContext(), p1Name + " (X) wins!", Toast.LENGTH_LONG).show();
				recordWin(1);
			}
			if (checkVictory(R.drawable.o)) {
				gameOver = true;
				mp.start(); //play sound
				Toast.makeText(getBaseContext(), p2Name + " (O) wins!", Toast.LENGTH_LONG).show();
				recordWin(2);
			}
			if (spacesLeft==0) {
				gameOver = true;
				Toast.makeText(getBaseContext(), "Game ends with a tie", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	/**
	 * Saves record of X or O player's win for this game to a text file,
	 * as well as the date and time. 
	 * @param i
	 */
	private void recordWin(int i) {
		String toSave = "";
		switch (i) {
			case 1 : toSave = "X\n";
				break;
			case 2 : toSave = "O\n";
				break;
		}	
		try {
			
			FileOutputStream fout = openFileOutput("save.txt",MODE_APPEND);
			OutputStreamWriter out = new OutputStreamWriter(fout);
			out.write(toSave);
			out.flush();
			out.close();
			Log.i("IO","save.txt write success");
			Log.i("IO", "wrote " + toSave);
		}
		catch (FileNotFoundException e) {
			Log.e("IO", "can't find file when saving",e);
		}
		catch (IOException e) {
			Log.e("IO", "IOException thrown reading file",e);
		}
		
		// last played
		try {
			Date d = new Date();
			d.toString();
			String lastPlayed = d.toString();
			FileOutputStream fout = openFileOutput("lastPlayed.txt", 0);
			OutputStreamWriter out = new OutputStreamWriter(fout);
			out.write(lastPlayed);
			out.write("\n");
			out.close();
			Log.i("IO", "lastPlayed.txt write success");
			Log.i("IO", "wrote " + lastPlayed);
		} catch (FileNotFoundException e) {
			Log.e("IO", "can't find file lastPlayed.txt when saving", e);
		} catch (IOException e) {
			Log.e("IO", "IOException thrown reading file lastPlayed.txt", e);
		}
	}
	
	/**
	 * Checks to see whether the game has been won or not by a given player.
	 * @param the symbol representing the player whose moves are being checked
	 **/
	// see if a victory condition has been met
	boolean checkVictory(int mark) {	
			// check rows
			//1st row
			if (!space11.getTag().equals("empty")) {
				if (((space11.getTag().equals(mark) && space12.getTag().equals(mark)) && (space12.getTag().equals(mark) && space13.getTag().equals(mark)))) {
					Log.i("GameWin", "1st Row Win");
					return true;
				}
			}
			//2nd row
			if (!space21.getTag().equals("empty")) {
				if (((space21.getTag().equals(mark) && space22.getTag().equals(mark)) && (space22.getTag().equals(mark) && space23.getTag().equals(mark)))) {
					Log.i("GameWin", "2nd Row Win");
					return true;
				}
			}
			//3rd row
			if (!space31.getTag().equals("empty")) {
				if (((space31.getTag().equals(mark) && space32.getTag().equals(mark)) && (space32.getTag().equals(mark) && space33.getTag().equals(mark)))) {
					Log.i("GameWin", "3rd Row Win");
					return true;
				}
			}
	
			//check columns
			//1st col
			if (!space11.getTag().equals("empty")) {
				if (((space11.getTag().equals(mark) && space21.getTag().equals(mark)) && (space21.getTag().equals(mark) && space31.getTag().equals(mark)))) {
					Log.i("GameWin", "1st Column Win");
					return true;
				}
			}
			//2nd col
			if (!space12.getTag().equals("empty")) {
				if (((space12.getTag().equals(mark) && space22.getTag().equals(mark)) && (space22.getTag().equals(mark) && space32.getTag().equals(mark)))) {
					Log.i("GameWin", "2nd Column Win");
					return true;
				}
			}
			//3rd col
			if (!space13.getTag().equals("empty")) {
				if (((space13.getTag().equals(mark) && space23.getTag().equals(mark)) && (space23.getTag().equals(mark) && space33.getTag().equals(mark)))) {
					Log.i("GameWin", "3nd Column Win");
					return true;
				}
			}
	
			// two diagonals
			if (!space22.getTag().equals("empty")) {
				if ((space11.getTag().equals(mark) && space22.getTag().equals(mark)) && (space22.getTag().equals(mark) && space33.getTag().equals(mark))) {
					Log.i("GameWin", "Diagonal Win");
					return true;
				}
				if ((space31.getTag().equals(mark) && space22.getTag().equals(mark)) && (space22.getTag().equals(mark) && space13.getTag().equals(mark))) {
					return true;
				}
			}
			// no win yet
			return false;
		}
	/**
	 * Called when the activity is created. Also reads saved player names from text file.
	 * @param saved instance bundle
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_game_activity);
		// event handlers for board space clicks	
		space11 = (ImageView) findViewById(R.id.space11);
		space11.setOnClickListener(handler);
		space12 = (ImageView) findViewById(R.id.space12);
		space12.setOnClickListener(handler);
		space13 = (ImageView) findViewById(R.id.space13);
		space13.setOnClickListener(handler);
		space21 = (ImageView) findViewById(R.id.space21);
		space21.setOnClickListener(handler);
		space22 = (ImageView) findViewById(R.id.space22);
		space22.setOnClickListener(handler);
		space23 = (ImageView) findViewById(R.id.space23);
		space23.setOnClickListener(handler);
		space31 = (ImageView) findViewById(R.id.space31);
		space31.setOnClickListener(handler);
		space32 = (ImageView) findViewById(R.id.space32);
		space32.setOnClickListener(handler);
		space33 = (ImageView) findViewById(R.id.space33);
		space33.setOnClickListener(handler);
		gameText = (TextView) findViewById(R.id.gameText);
		
		// set up media player for win sound
		mp = MediaPlayer.create(this, R.raw.win);
		
		//reads player names from file
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
	    
	    // prompt player for first turn
 	    gameText.setText(p1Name + ", place an X:");
	}
	
	/**
	 * Ensures that the menu activity is started when the hardware back button is pressed.
	 */
	// go back to main menu
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent toMenu = new Intent(this,MainMenuActivity.class);
		startActivity(toMenu);
	}
}
