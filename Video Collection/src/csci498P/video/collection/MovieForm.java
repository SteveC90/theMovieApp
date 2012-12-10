package csci498P.video.collection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MovieForm extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int MEDIA_TYPE_IMAGE = 150;
	private Uri fileUri = null;
	
	EditText title;
	EditText genre;
	EditText release;
	EditText barcode;
	ImageView image;
	
	MovieHelper helper;
	long movieID;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_form);

		title = (EditText)findViewById(R.id.title);
		genre = (EditText)findViewById(R.id.genre);
		release = (EditText)findViewById(R.id.release);
		barcode = (EditText)findViewById(R.id.barcode);
		image = (ImageView)findViewById(R.id.imageView1);
		
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
		} else if (item.getItemId() == R.id.take_pic) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
		    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
	
		    // start the image capture Intent
		    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		} else if (item.getItemId() == R.id.select_image) {
			Toast.makeText(getApplicationContext(), "Feature will be enabled in a future release", 
					Toast.LENGTH_LONG).show();
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
		if(fileUri != null) {
			state.putString("image", fileUri.toString());
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle state) {
		super.onRestoreInstanceState(state);
		title.setText(state.getString("title"));
		genre.setText(state.getString("genre"));
		release.setText(state.getString("release"));
		barcode.setText(state.getString("barcode"));
		if(state.getString("image")!=null){
			fileUri = Uri.parse(state.getString("image"));
			Bitmap bmp;
			try {
				bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
				bmp = Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*.25), (int)(bmp.getHeight()*0.25), true);
				image.setImageBitmap(bmp);
			} catch (FileNotFoundException e) {

			} catch (IOException e) {
				
			}
		}
	}

	private void save() {
		String fileString = null;
		if (fileUri!=null) {
			fileString = fileUri.toString();
		}
		
		if(movieID == -1) { 
			helper.insert(title.getText().toString(), 
					release.getText().toString(), 
					genre.getText().toString(), 
					barcode.getText().toString(),
					fileString);
		} else {
			helper.update(movieID, 
					title.getText().toString(), 
					release.getText().toString(), 
					genre.getText().toString(), 
					barcode.getText().toString(),
					fileString);
		}
		Toast.makeText(MovieForm.this, "Saved" , Toast.LENGTH_LONG).show();
	}

	private void load() {
		Cursor c = helper.getMovieById(movieID);
		c.moveToFirst();
		//Log.w("MovieForm", String.valueOf(c.getColumnCount()));
		title.setText(helper.getTitle(c));
		release.setText(helper.getRelease(c));
		genre.setText(helper.getGenre(c));
		barcode.setText(helper.getBarcode(c));
		if(helper.getImg(c)!=null) {
			fileUri = Uri.parse(helper.getImg(c));
			try {
				Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
				//bmp = Util.scaleBitmap(bmp, image.getWidth());
				bmp = Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*.25), (int)(bmp.getHeight()*0.25), true);
				image.setImageBitmap(bmp);
			} catch (Exception e) {
				
			}
		}
		c.close();
	}
	
	
	//this piece of code handles the result of the barcode scanner
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(MovieForm.this, fileUri.toString() , Toast.LENGTH_LONG).show();
				try {
					Bitmap bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
					bmp = Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*.25), (int)(bmp.getHeight()*0.25), true);
					image.setImageBitmap(bmp);
				} catch (Exception e) {
				
				}
					
			}
	    } else {
	    	//barcode
			IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
			Log.w("MovieForm", scanResult.getContents());
			if (scanResult != null) {
				barcode.setText(scanResult.getContents());
			}
	    }
		
	}
	
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	
	private static File getOutputMediaFile(int type){
	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(Environment.getExternalStorageDirectory(),
	        "IMG_"+ timeStamp + ".jpg");
	    } else {
	        return null;
	    }

	    return mediaFile;
	}

}
