package csci498P.video.collection;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

public class MovieForm extends Activity {

	EditText title;
	EditText genre;
	EditText release;
	EditText barcode;
	ImageView image;
	
	MovieHelper helper;
	long movieID;
	
	String imgURI = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_form);

		title = (EditText)findViewById(R.id.title);
		genre = (EditText)findViewById(R.id.genre);
		release = (EditText)findViewById(R.id.release);
		barcode = (EditText)findViewById(R.id.barcode);
		image = (ImageView)findViewById(R.id.imageView1);
		
		//Bitmap bmp = BitmapFactory.decodeFile("/storage/emulated/0/DCIM/Camera/IMG_20120809_170510.jpg");
		//bmp = Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*.25), (int)(bmp.getHeight()*0.25), true);
		
		//image.setImageBitmap(bmp);
		
		movieID = getIntent().getLongExtra(Gallery.MOVIE_ID, -1);
	}

	@Override
	public void onPause() {

		helper.close();
		super.onPause();
	}
	
	@Override
	public void onBackPressed(){
		save();
		super.onBackPressed();
	}
	
	@Override
	public void onResume(){
		helper = new MovieHelper(this);
		if(movieID != -1) {
			load();
		}
		super.onResume();
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
		state.putString("barcode", barcode.getText().toString());
	}

	@Override
	public void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		title.setText(state.getString("title"));
		genre.setText(state.getString("genre"));
		release.setText(state.getString("release"));
		barcode.setText(state.getString("barcode"));
	}

	private void save() {
		if(movieID == -1){ 
			helper.insert(title.getText().toString(), 
					release.getText().toString(), 
					genre.getText().toString(), 
					barcode.getText().toString(),
					null);
		} else {
			helper.update(movieID, 
					title.getText().toString(), 
					release.getText().toString(), 
					genre.getText().toString(), 
					barcode.getText().toString(), 
					null);
		}
		Toast.makeText(MovieForm.this, "Saved" , Toast.LENGTH_LONG).show()
	}

	private void load() {
		Cursor c = helper.getMovieById(movieID);
		c.moveToFirst();
		//Log.w("MovieForm", String.valueOf(c.getColumnCount()));
		title.setText(helper.getTitle(c));
		release.setText(helper.getRelease(c));
		genre.setText(helper.getGenre(c));
		barcode.setText(helper.getBarcode(c));
		
		c.close();
	}
	
	
	//this piece of code handles the result of the barcode scanner
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		Log.w("MovieForm", scanResult.getContents());
		if (scanResult != null) {
			barcode.setText(scanResult.getContents());
		}
		// else continue with any other code you need in the method
		
	}

}
