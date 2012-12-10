package csci498P.video.collection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "moviecollection.db";
	private static final int SCHEMA_VERSION = 1;

	public MovieHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//commented out since this table is not used at the moment
		//db.execSQL("CREATE TABLE collections (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT," +
		//		"created_date INTEGER, updated_date INTEGER, favorite INTEGER);");
		db.execSQL("CREATE TABLE movies (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"title TEXT, release TEXT, genre TEXT, barcode TEXT, img TEXT" +
				"collection_id INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO if we get an updated DB
	}
	
	
	public void insert(String title, String release, String genre, String barcode, String img) {
		ContentValues cv=new ContentValues();
		cv.put("title", title);
		cv.put("release", release);
		cv.put("genre", genre);
		cv.put("barcode", barcode);
		cv.put("img", img);
		getWritableDatabase().insert("movies", "title", cv);
	}
	
	public void update(long id, String title, String release, String genre, String barcode, String img) {
		ContentValues cv=new ContentValues();
		String[] args={String.valueOf(id)};
		cv.put("title", title);
		cv.put("release", release);
		cv.put("genre", genre);
		cv.put("barcode", barcode);
		cv.put("img", img);
		getWritableDatabase().update("movies", cv, "_ID=?", args);
	}
	
	public Cursor getAll(String orderBy) {
		return getReadableDatabase().rawQuery("SELECT _id, title, release, genre," +
				" barcode, img FROM movies ORDER BY " + orderBy, null);
	}
	
	public Cursor getMovieById(long id){
		String[] args = {String.valueOf(id)};
		return getReadableDatabase().rawQuery("SELECT _id, title, release, genre, barcode, img FROM movies WHERE _ID=?", args);
	}
	
	public void deleteMovie(long id) {
		String[] args = {String.valueOf(id)};
		getWritableDatabase().delete("movies", "_ID=?", args);
	}

	public String getTitle(Cursor c) {
		return c.getString(1);
	}

	public String getRelease(Cursor c) {
		return c.getString(2);
	}

	public String getGenre(Cursor c) {
		return c.getString(3);
	}

	public String getBarcode(Cursor c) {
		return c.getString(4);
	}
	
	public String getImg(Cursor c) {
		return c.getString(5);
	}
	
}
