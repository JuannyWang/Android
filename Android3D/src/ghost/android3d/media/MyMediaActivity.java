/**
 * 
 */
package ghost.android3d.media;

import ghost.android3d.R;
import android.annotation.SuppressLint;
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
public class MyMediaActivity extends Activity {

	/**
	 * 当前布局中的按钮
	 */
	private Button button[];

	/**
	 * 当前活动的上下文参数
	 */
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
		this.setContentView(R.layout.media_layout);
		context=this;
		button = new Button[5];
		button[0] = (Button) this.findViewById(R.id.Play);
		button[0].setOnClickListener(new OnClickListener() {

			@SuppressLint("SdCardPath")
			@Override
			public void onClick(View v) {
				if (MyMediaTool
						.play("/sdcard/Music/Britney Spears - I Wanna Go.mp3")) {
					Toast.makeText(context, "播放音乐成功", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		button[1] = (Button) this.findViewById(R.id.Pause);
		button[1].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyMediaTool.pause();
				Toast.makeText(context, "暂停播放", Toast.LENGTH_SHORT)
				.show();
			}
		});
		button[2] = (Button) this.findViewById(R.id.Stop);
		button[2].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyMediaTool.stop();
				Toast.makeText(context, "停止播放", Toast.LENGTH_SHORT)
				.show();
			}
		});
		button[3] = (Button) this.findViewById(R.id.Add);
		button[3].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyMediaTool.addVolumn();
				Toast.makeText(context, "增大音量", Toast.LENGTH_SHORT)
				.show();
			}
		});
		button[4] = (Button) this.findViewById(R.id.Down);
		button[4].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MyMediaTool.downVolumn();
				Toast.makeText(context, "减小音量", Toast.LENGTH_SHORT)
				.show();
			}
		});
		MyMediaTool.initialization(this);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		MyMediaTool.stop();
	}

}
