package tru.lyall.lab4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainMenuActivity extends ActionBarActivity implements OnItemClickListener {

	private ListView mainMenuList;
	//player names
	private String p1Name;
	private String p2Name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		Log.d("ActivityLife", "MainMenuActivity onCreate()");
		mainMenuList = (ListView) findViewById(R.id.mainMenuListView);
		mainMenuList.setOnItemClickListener(this);
		
		// reads player names from file
		try {
			    FileInputStream fin = openFileInput("playerNames.txt");
			   	Scanner s = new Scanner(fin);
			   	s.useDelimiter("\n");
			   	String names = "";
			   	while (s.hasNext()) {
			   		
		    		p1Name = s.nextLine();
		    		p2Name = s.nextLine();
			   	}
			   	s.close();
			   	Log.i("IO","read file success" + p1Name );
			    
		 }
		catch (FileNotFoundException e) {
	    	Log.e("IO","Can't find saveName file",e);
	    	p1Name = "Player 1";
			p2Name = "Player 2";
	    }
	}
	
	// when a list item is clicked, starts activity with proper intent
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i("ClickItem", "Item " + id + "clicked. Index " + position);
		
		if (position==0) {
			Intent enterNames = new Intent(this,EnterNamesActivity.class);
			startActivity(enterNames);
			this.finish();
		}
		if (position==1) {
			Intent playGame = new Intent(this,PlayGameActivity.class);
			startActivity(playGame);
			this.finish();
		}
		if (position==2) {
			Intent showStandings = new Intent(this,ShowStandingsActivity.class) ;
			startActivity(showStandings);
			this.finish();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
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


}
