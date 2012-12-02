package csci498P.video.collection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
	Cursor c;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		//TODO / to-finish really.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);
		
		helper = new MovieHelper(this);
		c = helper.getAll(null);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new MovieAdapter(this, c));

		gridview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//	            Toast.makeText(Gallery.this, "" + position, Toast.LENGTH_SHORT).show();
	        	Intent i = new Intent(Gallery.this, MovieForm.class);
	        	i.putExtra(MOVIE_ID, id);
	        	startActivity(i);
	        }
	    });
		
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
			   img.setImageURI(Uri.parse(uri));
		   }
	   }

	    // create a new ImageView for each item referenced by the Adapter 
	    public View newView(Context context, Cursor cursor, ViewGroup parent) {
	        ImageView imageView;
	        //if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
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
	            R.drawable.ball_green, R.drawable.ball_red
	    };
	}
}