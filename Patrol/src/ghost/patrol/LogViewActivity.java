/**
 * 
 */
package ghost.patrol;

import java.util.ArrayList;
import java.util.List;

import ghost.patrol.bean.TableData;
import ghost.patrol.table.HorizontalScrollViewListener;
import ghost.patrol.table.ListAdapter4;
import ghost.patrol.table.ObservableHorizontalScrollView;
import ghost.patrol.table.ObservableScrollView;
import ghost.patrol.table.ScrollViewListener;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-3-8
 */
public class LogViewActivity extends Activity implements ScrollViewListener,
		HorizontalScrollViewListener {

	private ObservableScrollView scrollView1 = null;
	private ObservableScrollView scrollView2 = null;
	private ObservableHorizontalScrollView hscrollview1 = null;
	private ObservableHorizontalScrollView hscrollview2 = null;
	private ListView listview = null;
	private ListView listview1 = null;
	private List<TableData> list = new ArrayList<TableData>();
	private String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		setContentView(R.layout.activity_table);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		name = bundle.getString("name");
		TextView tittle = (TextView) this.findViewById(R.id.title);
		tittle.setText("查看记录：" + name);
		LinearLayout linear_layout = (LinearLayout) this
				.findViewById(R.id.linear_head);
		addHeadHelper(linear_layout, "线路");
		addHeadHelper(linear_layout, "杆塔号");
		addHeadHelper(linear_layout, "缺陷部位");
		addHeadHelper(linear_layout, "性质");
		testData();
		listview = (ListView) findViewById(R.id.listview);
		listview1 = (ListView) findViewById(R.id.listview1);
		scrollView1 = (ObservableScrollView) findViewById(R.id.scrollview1);
		scrollView1.setScrollViewListener(this);
		scrollView2 = (ObservableScrollView) findViewById(R.id.scrollview2);
		scrollView2.setScrollViewListener(this);
		hscrollview1 = (ObservableHorizontalScrollView) findViewById(R.id.horizontal_scrollview1);
		hscrollview2 = (ObservableHorizontalScrollView) findViewById(R.id.horizontal_scrollview2);
		hscrollview1.setHorizontalScrollViewListener(this);
		hscrollview2.setHorizontalScrollViewListener(this);
		ListAdapter4 adapter = new ListAdapter4(this, list, 0);
		listview.setAdapter(adapter);
		ListAdapter4 adapter1 = new ListAdapter4(this, list, 1);
		listview1.setAdapter(adapter1);
	}

	private void addHeadHelper(LinearLayout base, String text) {
		Button btn = new Button(this);
		btn.setText(text);
		btn.setWidth((int) ((100 - 0.5) * 1.5));
		base.addView(btn);
	}

	private void testData() {
		for (int i = 0; i < 10; i++) {
			list.add(new TableData("测试", "" + i, "基础", "一般"));
		}
	}

	@Override
	public void onScrollChanged(
			ObservableHorizontalScrollView horizontalScrollView, int x, int y,
			int oldx, int oldy) {
		if (horizontalScrollView == hscrollview1) {
			hscrollview2.scrollTo(x, y);
		} else if (horizontalScrollView == hscrollview2) {
			hscrollview1.scrollTo(x, y);
		}
	}

	@Override
	public void onScrollChanged(ObservableScrollView scrollView, int x, int y,
			int oldx, int oldy) {
		if (scrollView == scrollView1) {
			scrollView2.scrollTo(x, y);
		} else if (scrollView == scrollView2) {
			scrollView1.scrollTo(x, y);
		}
	}

	private void startCreateItem() {
		Intent intent = new Intent(LogViewActivity.this,
				CreateViewActivity.class);
		intent.putExtra("name", "null");
		startActivity(intent);
		overridePendingTransition(R.layout.in_from_right, R.layout.out_to_left);
	}

	/**
	 * 返回上一级界面辅助函数
	 */
	private void exitActivity() {
		LogViewActivity.this.finish();
		overridePendingTransition(R.layout.in_from_right, R.layout.out_to_left);
	}

	public void onBackPressed() {
		exitActivity();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		/**
		 * 第一个参数是组号，第二个参数是唯一的ID识别码，第三个参数是排序号，第四个参数是标题
		 */
		menu.add(1, 0, 100, "查看任务");
		if (name.equals(" 管理员")) {
			menu.add(2, 21, 90, "查看记录详情");
		} else if (name.equals(" 巡查员")) {
			menu.add(3, 31, 88, "新建记录");
			menu.add(3, 32, 87, "查看记录详情");
			menu.add(3, 33, 87, "删除记录");
		} else if (name.equals(" 演示")) {

		}
		/**
		 * 返回true表示显示菜单项
		 */
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			exitActivity();
			break;
		case 21:
		case 32:
			startCreateItem();
			break;
		}
		/**
		 * 返回true表示处理完菜单项的事件，不需要将该事件继续传播下去了
		 */
		return true;
	}

}
