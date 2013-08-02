package ghost.patrol.table;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class ObservableHorizontalScrollView extends HorizontalScrollView {

	private HorizontalScrollViewListener horizontalScrollViewListener = null;
	public ObservableHorizontalScrollView(Context context) {
		super(context);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if(horizontalScrollViewListener != null) {
			horizontalScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
	}

	public ObservableHorizontalScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public ObservableHorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public HorizontalScrollViewListener getHorizontalScrollViewListener() {
		return horizontalScrollViewListener;
	}

	public void setHorizontalScrollViewListener(
			HorizontalScrollViewListener horizontalScrollViewListener) {
		this.horizontalScrollViewListener = horizontalScrollViewListener;
	}
}
