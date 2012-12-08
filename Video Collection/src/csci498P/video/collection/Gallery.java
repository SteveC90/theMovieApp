package csci498P.video.collection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
//import android.widget.Toast;

public class Gallery extends Activity{
	
	public static final String MOVIE_ID = "csci498P.video.collection.MOVIE_ID";
	
	MovieHelper helper = null;
	MovieAdapter movies = null;
	Cursor c;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		
		helper = new MovieHelper(this);
		c = helper.getAll(null);
		startManagingCursor(c);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		movies = new MovieAdapter(this, c);
		gridview.setAdapter(movies);
		registerForContextMenu(gridview);
		gridview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	Intent i = new Intent(Gallery.this, MovieForm.class);
	        	i.putExtra(MOVIE_ID, id);
	        	startActivity(i);
	        }
	    });
		
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		helper.close();
		stopManagingCursor(c);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		 new MenuInflater(this).inflate(R.menu.option, menu);
    
		 return super.onCreateOptionsMenu(menu);
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.add){
			startActivity(new Intent(Gallery.this, MovieForm.class));
			return true;
     	}
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.gridview) {
        	
            getMenuInflater().inflate(R.menu.hold_menu, menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.delete) {
			final AdapterView.AdapterContextMenuInfo info = 
	                   (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
			final long id = info.id;
			new AlertDialog.Builder(this)
				.setTitle("Are you sure you want to delete this movie? You cannot undo this action.")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						helper.deleteMovie(id);
						c.requery();
					}
				})
				.setNegativeButton("No", null).show();
		}
		return super.onContextItemSelected(item);
	}

	
	public class MovieAdapter extends CursorAdapter {
	    //private Context mContext;

	    @SuppressWarnings("deprecation")
		public MovieAdapter(Context context, Cursor c) {
	    	super(context, c);
	        //mContext = c;
	    }

	   @Override
	   public void bindView(View view, Context context, Cursor cursor) {
		   ImageView img = (ImageView) view;
		   String uri = helper.getImg(cursor);
		   if(uri == null){
			   img.setImageResource(mThumbIds[0]);
		   } else {
			   Bitmap bmp;
			try {
				bmp = MediaStore.Images.Media.getBitmap(Gallery.this.getContentResolver(), Uri.parse(uri));
				bmp = Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*.15), (int)(bmp.getHeight()*0.15), true);
				img.setImageBitmap(bmp);
			} catch (Exception e) {
				
			}
				
		   }
	   }

	    // create a new ImageView for each item referenced by the Adapter 
	    public View newView(Context context, Cursor cursor, ViewGroup parent) {
	        ImageView imageView;
	        //if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
	        //} else {
	            //imageView = (ImageView) convertView;
	        //}
	        
	        //imageView.setImageResource(mThumbIds[position]);
	        return imageView;
	    }

	    // references to our images will have to change so that it is referenced to the DB
	    private Integer[] mThumbIds = {
	            R.drawable.default_img
	    };
	}

}