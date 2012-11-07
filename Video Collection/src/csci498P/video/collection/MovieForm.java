package csci498P.video.collection;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MovieForm extends Activity {

	EditText title;
	EditText genre;
	EditText release;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_form);

		title = (EditText)findViewById(R.id.title);
		genre = (EditText)findViewById(R.id.genre);
		release = (EditText)findViewById(R.id.release);

	}

	@Override
	public void onPause() {
		save();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//helper.close();
	}

	@Override
	public void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);

//		state.putString("name", name.getText().toString());
//		state.putString("address", address.getText().toString());
//		state.putString("notes", notes.getText().toString());
//		state.putInt("type", types.getCheckedRadioButtonId());
//		state.putString("feed", feed.getText().toString());
	}

	@Override
	public void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
//		name.setText(state.getString("name"));
//		address.setText(state.getString("address"));
//		notes.setText(state.getString("notes"));
//		types.check(state.getInt("type"));
//		feed.setText(state.getString("feed"));
	}

	private void save() {
		//TODO
	}

	private void load() {
		//TODO
	}

}
