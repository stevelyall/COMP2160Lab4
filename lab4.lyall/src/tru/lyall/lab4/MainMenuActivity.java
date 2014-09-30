package tru.lyall.lab4;

import java.io.FileNotFoundException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class MainMenuActivity extends ActionBarActivity implements OnItemClickListener {

	private ListView mainMenuList;
	private TextView title;
	/**
	 * Runs when the activity is created.
	 * @throws FileNotFoundException
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		Log.d("ActivityLife", "MainMenuActivity onCreate()");
		mainMenuList = (ListView) findViewById(R.id.mainMenuListView);
		mainMenuList.setOnItemClickListener(this);
		TextView title = (TextView) findViewById(R.id.welcomeMessage);
	}
	
	/**
	 * Handler for clicks on ListView items in menu. Starts appropriate activity.
	 * @param parent AdapterView
	 * @param view View
	 * @param position Index being clicked
	 */
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
