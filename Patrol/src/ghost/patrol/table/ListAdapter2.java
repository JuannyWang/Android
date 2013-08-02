/**
 * 
 */
package ghost.patrol.table;

import ghost.patrol.R;
import ghost.patrol.bean.TableData;
import ghost.patrol.table.ListAdapter5.ViewHolder;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-3-8
 */
public class ListAdapter2 extends BaseAdapter {

	private Context con;
	private List<TableData> list;
	private LayoutInflater inflater;
	private int type = 0;

	public ListAdapter2(Context con, List<TableData> list, int type) {
		this.con = con;
		this.list = list;
		inflater = LayoutInflater.from(con);
		this.type = type;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			if (0 == type) {
				convertView = inflater.inflate(R.layout.item_2, null);
				viewHolder.tv[0] = (TextView) convertView
						.findViewById(R.id.textview1);
				viewHolder.tv[1] = (TextView) convertView
						.findViewById(R.id.textview2);
				for (int i = 0; i < 2; ++i) {
					viewHolder.tv[i].setText(list.get(position).getData(i));
				}
			} else if (1 == type) {
				convertView = inflater.inflate(R.layout.item, null);
				viewHolder.tv[0] = (TextView) convertView
						.findViewById(R.id.textview1);
			}
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		return convertView;
	}

	class ViewHolder {
		/** 分类名字 */
		public TextView tv[] = new TextView[2];
	}

}
