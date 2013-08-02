package ghost.android3d;

import ghost.android3d.audio.MyAudioActivity;
import ghost.android3d.media.MyMediaActivity;
import ghost.android3d.opengl01.MyFirstTriangleActivity;
import ghost.android3d.opengl02.PointAndLineActivity;
import ghost.android3d.opengl03.ChangeTriangleActivity;
import ghost.android3d.opengl04.PerspectiveActivity;
import ghost.android3d.opengl05.SingleLightActivity;
import ghost.android3d.opengl06.FiveLightActivity;
import ghost.android3d.opengl07.ThreeBollActivity;
import ghost.android3d.opengl08.MoveLightActivity;
import ghost.android3d.opengl09.VertexActivity;
import ghost.android3d.opengl10.IndexActivity;
import ghost.android3d.opengl11.TextureTriangleActivity;
import ghost.android3d.opengl12.TextureBallActivity;
import ghost.android3d.opengl13.EarthAndMoonActivity;
import ghost.android3d.opengl14.DifferentTextureActivity;
import ghost.android3d.opengl15.ColorCubeActivity;
import ghost.android3d.opengl16.CylinderActivity;
import ghost.android3d.opengl17.LineCylinderActivity;
import ghost.android3d.opengl18.TaperActivity;
import ghost.android3d.opengl19.LineTaperActivity;
import ghost.android3d.opengl20.CirqueActivity;
import ghost.android3d.opengl21.LineCirqueActivity;
import ghost.android3d.opengl22.ParaboloidActivity;
import ghost.android3d.opengl23.LineParaboloidActivity;
import ghost.android3d.opengl24.DrumActivity;
import ghost.android3d.opengl25.LineDrumActivity;
import ghost.android3d.opengl26.HelicoidActivity;
import ghost.android3d.opengl27.LineHelicoidActivity;
import ghost.android3d.opengl28.ScaleActivity;
import ghost.android3d.opengl29.TranslateActivity;
import ghost.android3d.opengl30.RotateActivity;
import ghost.android3d.opengl31.NewEarthActivity;
import ghost.android3d.opengl32.RotateRectActivity;
import ghost.android3d.opengl33.PyramidActivity;
import ghost.android3d.opengl34.GlassActivity;
import ghost.android3d.opengl35.BlendEarthActivity;
import ghost.android3d.opengl36.FilterActivity;
import ghost.android3d.opengl37.CactusActivity;
import ghost.android3d.opengl38.FlagActivity;
import ghost.android3d.opengl39.LandActivity;
import ghost.android3d.opengl40.BasketballActivity;
import ghost.android3d.opengl41.FallBallActivity;
import ghost.android3d.opengl42.CollisionActivity;
import ghost.android3d.opengl43.FireWorkActivity;
import ghost.android3d.opengl44.TextureFireActivity;
import ghost.android3d.senser.SenserActivity;
import ghost.android3d.sqlite.MySQLiteActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StartActivity extends Activity {

	/**
	 * 用于显示其他activity的入口
	 */
	private ListView listView;
	/**
	 * 用于储存名称的映射关联
	 */
	private List<Map<String, Object>> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		listView = new ListView(this);
		list = new ArrayList<Map<String, Object>>();
		addData("音效播放", "短音效播放，支持多个同时播放，适合游戏音效", R.drawable.audioactivity,
				MyAudioActivity.class);
		addData("音乐播放", "长音乐播放，只能同时播放一个，适合背景音乐", R.drawable.mediaactivity,
				MyMediaActivity.class);
		addData("数据库操作", "简单的数据库操作，增删改查，适合游戏数据保存", R.drawable.sqlactivity,
				MySQLiteActivity.class);
		addData("第一个三角形", "使用opengl es1.0 绘制的第一个三角形",
				R.drawable.firesttriangleactivity,
				MyFirstTriangleActivity.class);
		addData("点和线的绘制", "使用opengl es1.0 绘制的四个点和两条线",
				R.drawable.pointandlineactivity, PointAndLineActivity.class);
		addData("索引法绘制三角形", "在屏幕中绘制两个有颜色的三角形，并可以控制一些渲染参数",
				R.drawable.changetriangleactivity, ChangeTriangleActivity.class);
		addData("不同的投影", "用多个排列好的六边形来展示不同的投影的不同呈现效果",
				R.drawable.perspectiveactivity, PerspectiveActivity.class);
		addData("初涉光源", "一个红黄色灯光以及一个白色材质的小球的展示效果",
				R.drawable.singlelightactivity, SingleLightActivity.class);
		addData("多个光源", "五盏不同位置灯光和一个白色材质的小球的展示效果",
				R.drawable.fivelightactivity, FiveLightActivity.class);
		addData("定位光源", "一个可以通过可拖动进度条控制位置的光源和三个白色小球的展示效果",
				R.drawable.threebollactivity, ThreeBollActivity.class);
		addData("自发光物体及运动光源", "一个本身发蓝色光的小球和两个围绕着小球旋转的点光源",
				R.drawable.movelightactivity, MoveLightActivity.class);
		addData("面法向量立方体", "一个由拥有单独的法向量的立方体在桔黄色光源下的展示效果",
				R.drawable.vertexactivity, VertexActivity.class);
		addData("平均向量立方体", "一个由平均的法向量的立方体在桔黄色光源下的展示效果",
				R.drawable.indexactivity, IndexActivity.class);
		addData("第一个纹理", "一个贴了纹理的三角形的展示效果", R.drawable.texturetriangleactivity,
				TextureTriangleActivity.class);
		addData("贴图小球", "一个贴了纹理的小球和两个围绕小球旋转的光源的展示效果",
				R.drawable.textureballactivity, TextureBallActivity.class);
		addData("简单地月星系", "利用opengl es1.0的成像技术简单的对地月星系进行展现的展示效果",
				R.drawable.earthandmoonactivity, EarthAndMoonActivity.class);
		addData("不同的纹理效果", "分别展示了三种不同的纹理效果",
				R.drawable.differenttextureactivity,
				DifferentTextureActivity.class);
		addData("彩色立方体", "在屏幕中展示两个立方体，利用视角设定进行观察的展示效果",
				R.drawable.colorcubeactivity, ColorCubeActivity.class);
		addData("圆柱体", "使用顶点法绘制圆柱体的展示效果", R.drawable.cylinderactivity,
				CylinderActivity.class);
		addData("线框圆柱体", "只绘制圆柱体的线框的展示效果", R.drawable.linecylinderactivity,
				LineCylinderActivity.class);
		addData("圆锥体", "使用顶点法绘制圆锥体的展示效果", R.drawable.taperactivity,
				TaperActivity.class);
		addData("线框圆锥体", "只绘制圆锥体的线框的展示效果", R.drawable.linetaperactivity,
				LineTaperActivity.class);
		addData("圆环", "使用顶点法绘制圆环的展示效果", R.drawable.cirqueactivity,
				CirqueActivity.class);
		addData("线框圆环", "只绘制圆环的线框的展示效果", R.drawable.linecirqueactivity,
				LineCirqueActivity.class);
		addData("抛物面", "使用顶点法绘制抛物面的展示效果", R.drawable.paraboloidactivity,
				ParaboloidActivity.class);
		addData("线框抛物面", "只绘制抛物面的线框的展示效果", R.drawable.lineparaboloidactivity,
				LineParaboloidActivity.class);
		addData("双曲面", "使用顶点法绘制双曲面的展示效果", R.drawable.drumactivity,
				DrumActivity.class);
		addData("线框双曲面", "只绘制双曲面的线框的展示效果", R.drawable.linedrumactivity,
				LineDrumActivity.class);
		addData("螺旋面", "使用顶点法绘制螺旋面的展示效果", R.drawable.helicoidactivity,
				HelicoidActivity.class);
		addData("线框螺旋面", "只绘制螺旋面的线框的展示效果", R.drawable.linehelicoidactivity,
				LineHelicoidActivity.class);
		addData("缩放小球", "绘制一个椭球体并进行缩放的展示效果", R.drawable.scaleactivity,
				ScaleActivity.class);
		addData("移动小球", "绘制一个椭球体并进行移动的展示效果", R.drawable.scaleactivity,
				TranslateActivity.class);
		addData("旋转小球", "绘制一个椭球体并进行旋转的展示效果", R.drawable.scaleactivity,
				RotateActivity.class);
		addData("符合旋转", "绘制一个带自转和公转的简单的地月星系展示效果",
				R.drawable.earthandmoonactivity, NewEarthActivity.class);
		addData("滚动的长方体", "绘制一个滚动中的长方体的展示效果", R.drawable.rotaterectactivity,
				RotateRectActivity.class);
		addData("金字塔", "绘制处在沙漠中的金字塔并且附带光照和雾化效果的展示效果",
				R.drawable.pyramidactivity, PyramidActivity.class);
		addData("混合效果1", "展示多块玻璃之间的颜色混合效果的展示效果", R.drawable.glassactivity,
				GlassActivity.class);
		addData("混合效果2", "带大气效果和光晕效果的地月信息的展示效果", R.drawable.blendearthactivity,
				BlendEarthActivity.class);
		addData("滤镜效果", "瞄准镜和几个物体混合的展示效果", R.drawable.filteractivity,
				FilterActivity.class);
		addData("标志板", "使用标志板绘制多个仙人掌的展示效果", R.drawable.cactusactivity,
				CactusActivity.class);
		addData("飘动的旗帜", "一个飘动中的旗帜的展示效果", R.drawable.flagactivity,
				FlagActivity.class);
		addData("立体地形", "灰度图形生成的3D地形的展示效果", R.drawable.landactivity,
				LandActivity.class);
		addData("镜面效果", "一个篮球运动的镜面效果的展示效果", R.drawable.basketballactivity,
				BasketballActivity.class);
		addData("加速度传感器", "加速度传感器的展示效果", R.drawable.senseractivity,
				SenserActivity.class);
		addData("下落的小球", "三个小球从平板落下的展示效果", R.drawable.fallballactivity,
				FallBallActivity.class);
		addData("小球碰撞", "两个小球正面完全弹性碰撞的展示效果", R.drawable.collisionactivity,
				CollisionActivity.class);
		addData("烟火效果", "一个三棱锥顶部释放烟火的粒子效果的展示效果", R.drawable.fireworkactivity,
				FireWorkActivity.class);
		addData("纹理烟火效果", "一个三棱锥顶部释放纹理烟火的粒子效果的展示效果", R.drawable.fireworkactivity,
				TextureFireActivity.class);

		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.vlist,
				new String[] { "title", "info", "img" }, new int[] {
						R.id.title, R.id.info, R.id.img });
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ClickListener());
		setContentView(listView);
	}

	/**
	 * 添加数据
	 * 
	 * @param title
	 *            标题
	 * @param info
	 *            内容
	 * @param drawable
	 *            图片资源ID
	 */
	private void addData(String title, String info, int drawable, Class<?> obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		map.put("info", info);
		map.put("img", drawable);
		map.put("class", obj);
		list.add(map);	
	}

	/**
	 * 列表点击事件
	 * 
	 * @author 玄雨
	 * @qq 821580467
	 * @date 2013-1-23
	 */
	private class ClickListener implements OnItemClickListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.widget.AdapterView.OnItemClickListener#onItemClick(android
		 * .widget.AdapterView, android.view.View, int, long)
		 */
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			@SuppressWarnings("unchecked")
			Intent intent = new Intent(StartActivity.this,
					(Class<MyAudioActivity>) list.get(arg2).get("class"));
			startActivity(intent);
			overridePendingTransition(R.layout.in_from_right,
					R.layout.out_to_left);
		}

	}

}
