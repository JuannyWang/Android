package ghost.patrol.table;

import ghost.patrol.R;
import ghost.patrol.bean.TableData;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter5 extends BaseAdapter {

	private Context con;
	private List<TableData> list;
	private LayoutInflater inflater;
	private int type = 0;

	public ListAdapter5(Context con, List<TableData> list, int type) {
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
				convertView = inflater.inflate(R.layout.item_5, null);
				viewHolder.tv[0] = (TextView) convertView
						.findViewById(R.id.textview1);
				viewHolder.tv[1] = (TextView) convertView
						.findViewById(R.id.textview2);
				viewHolder.tv[2] = (TextView) convertView
						.findViewById(R.id.textview3);
				viewHolder.tv[3] = (TextView) convertView
						.findViewById(R.id.textview4);
				viewHolder.tv[4] = (TextView) convertView
						.findViewById(R.id.textview5);
				for (int i = 0; i < 5; ++i) {
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
		public TextView tv[] = new TextView[5];
	}

}
