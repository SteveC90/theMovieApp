package csci498P.video.collection;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class VideoCollection extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_collection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_video_collection, menu);
        return true;
    }
}
