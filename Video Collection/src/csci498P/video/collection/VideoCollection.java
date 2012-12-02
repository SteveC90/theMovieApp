package csci498P.video.collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class VideoCollection extends Activity implements OnClickListener{
	
	public void onClick(View v) {
		if(v.getId() == R.id.gallery) {
			startActivity(new Intent(VideoCollection.this, Gallery.class));
		} else if (v.getId() == R.id.load_create) {
			startActivity(new Intent(VideoCollection.this, LCScreen.class));
		} else if (v.getId() == R.id.extra) {
			Toast.makeText(getApplicationContext(), "Feature will be enabled in " +
					"a future release", Toast.LENGTH_LONG).show();
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_collection);
        
        Button gallery = (Button)findViewById(R.id.gallery);
        Button load_create = (Button)findViewById(R.id.load_create);
        Button extra = (Button)findViewById(R.id.extra);
        gallery.setOnClickListener(this);
        load_create.setOnClickListener(this);
        extra.setOnClickListener(this);
    }
    
}
