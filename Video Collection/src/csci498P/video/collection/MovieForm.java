package csci498P.video.collection;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

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

}
