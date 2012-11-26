package csci498P.video.collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class MovieForm extends Activity {

	EditText title;
	EditText genre;
	EditText release;
	EditText barcode;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_form);

		title = (EditText)findViewById(R.id.title);
		genre = (EditText)findViewById(R.id.genre);
		release = (EditText)findViewById(R.id.release);
		barcode = (EditText)findViewById(R.id.barcode);
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
	public boolean onCreateOptionsMenu(Menu menu) {
	  new MenuInflater(this).inflate(R.menu.details_option, menu);
	  return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.scan_barcode) {
			//opens the barcode scanner app
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.initiateScan();
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);
		state.putString("title", title.getText().toString());
		state.putString("genre", genre.getText().toString());
		state.putString("release", release.getText().toString());
	}

	@Override
	public void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		title.setText(state.getString("title"));
		genre.setText(state.getString("genre"));
		release.setText(state.getString("release"));
	}

	private void save() {
		//TODO
	}

//	private void load() {
//		//TODO
//	}
	
	
	//this piece of code handles the result of the barcode scanner
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {
			barcode.setText(scanResult.getContents());
		}
		// else continue with any other code you need in the method
		
	}

}
