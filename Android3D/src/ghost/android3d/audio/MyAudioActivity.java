/**
 * 
 */
package ghost.android3d.audio;

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
public class MyAudioActivity extends Activity {

	/**
	 * 布局中的四个按钮
	 */
	private Button button[];
	/**
	 * 储存当前activity的上下文参数
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
		this.setContentView(R.layout.audio_layout);
		context=this;
		button = new Button[4];
		button[0] = (Button) this.findViewById(R.id.Start1);
		button[0].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyAudioTool.playAudio(context, 1, 0);
				Toast.makeText(context, "开始播放音效1", Toast.LENGTH_SHORT)
						.show();
			}
		});
		button[1] = (Button) this.findViewById(R.id.Start2);
		button[1].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyAudioTool.playAudio(context, 2, 0);
				Toast.makeText(context, "开始播放音效2", Toast.LENGTH_SHORT)
						.show();
			}
		});
		button[2] = (Button) this.findViewById(R.id.Pause1);
		button[2].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyAudioTool.pause(1);
				Toast.makeText(context, "暂停播放音效1", Toast.LENGTH_SHORT)
						.show();
			}
		});
		button[3] = (Button) this.findViewById(R.id.Pause2);
		button[3].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyAudioTool.pause(2);
				Toast.makeText(context, "暂停播放音效2", Toast.LENGTH_SHORT)
						.show();
			}
		});
		MyAudioTool.initialization(5);
		MyAudioTool.loadAudio(this, 1, R.raw.attack02);
		MyAudioTool.loadAudio(this, 2, R.raw.attack14);

	}

}
