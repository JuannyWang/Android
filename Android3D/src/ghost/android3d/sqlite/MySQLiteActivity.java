/**
 * 
 */
package ghost.android3d.sqlite;

import ghost.android3d.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-1-23
 */
public class MySQLiteActivity extends Activity {

	private Button button[];
	private Context context;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.sqlite_layout);
		context = this;
		button = new Button[4];
		button[0] = (Button) this.findViewById(R.id.CreateDB);
		button[0].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MySQLiteTool.open(context, "testdb");
				Toast.makeText(context, "open SqlDB", Toast.LENGTH_SHORT)
						.show();
			}
		});
		button[1] = (Button) this.findViewById(R.id.InsertDB);
		button[1].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MySQLiteTool.insert(1, "test" + Math.random() * 1000);
				Toast.makeText(context, "insert SqlDB", Toast.LENGTH_SHORT)
						.show();
			}
		});
		button[2] = (Button) this.findViewById(R.id.UpdateDB);
		button[2].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MySQLiteTool.insert(1, "test" + Math.random() * 1000);
				Toast.makeText(context, "update SqlDB", Toast.LENGTH_SHORT)
						.show();
			}
		});
		button[3] = (Button) this.findViewById(R.id.SelectDB);
		button[3].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MySQLiteTool.search();
				Toast.makeText(context, "search SqlDB", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

}
