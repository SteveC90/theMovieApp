package csci498P.video.collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VideoCollection extends Activity {
	
	//We will have to change this block to just a method eventually with case statements I believe.
	private View.OnClickListener onGallery = new View.OnClickListener() {
		public void onClick(View v) {
			startActivity(new Intent(VideoCollection.this, Gallery.class));
		}
	};
	
	private View.OnClickListener onLCScreen = new View.OnClickListener() {
		public void onClick(View v) {
			//TODO
			//startActivity(new Intent(VideoCollection.this, LCScreen.class));
		}
	};
	
	private View.OnClickListener onExtra = new View.OnClickListener() {
		public void onClick(View v) {
			//TODO
			//Extra might be for find movie theatre location
			//startActivity(new Intent(VideoCollection.this, Extra.class));
		}
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_collection);
        
        Button gallery = (Button)findViewById(R.id.gallery);
        Button load_create = (Button)findViewById(R.id.load_create);
        Button extra = (Button)findViewById(R.id.extra);
        gallery.setOnClickListener(onGallery);
        load_create.setOnClickListener(onLCScreen);
        extra.setOnClickListener(onExtra);
    }

    //Don't know if we want a menu in the main menu screen.
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.options, menu);
//        return true;
//    }
}
