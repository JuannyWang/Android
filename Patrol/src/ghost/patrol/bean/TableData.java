/**
 * 
 */
package ghost.patrol.bean;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-3-7
 */
public class TableData {

	private String[] data;

	/**
	 * 
	 * @param str1
	 *            线路
	 * @param str2
	 *            杆塔号
	 * @param str3
	 *            状态
	 * @param str4
	 *            执行人
	 * @param str5
	 *            等级
	 */
	public TableData(String str1, String str2, String str3, String str4,
			String str5) {
		data = new String[] { str1, str2, str3, str4, str5 };
	}

	/**
	 * 
	 * @param str1
	 *            姓名
	 * @param str2
	 *            班组
	 * @param str3
	 *            上级单位
	 */
	public TableData(String str1, String str2, String str3) {
		data = new String[] { str1, str2, str3 };
	}

	/**
	 * 
	 * @param str1
	 *            线路
	 * @param str2
	 *            杆塔号
	 * @param str3
	 *            缺陷部位
	 * @param str4
	 *            性质
	 */
	public TableData(String str1, String str2, String str3, String str4) {
		data = new String[] { str1, str2, str3, str4 };
	}
	
	/**
	 * 
	 * @param str1 项目
	 * @param str2 标准巡视内容
	 */
	public TableData(String str1, String str2) {
		data = new String[] { str1, str2 };
	}

	/**
	 * 返回对应值的数据
	 * 
	 * @param index
	 *            指定值坐标,如果为空则返回“空指针”
	 * @return
	 */
	public String getData(int index) {
		if (index < data.length)
			return data[index];
		return "空指针";
	}

	/**
	 * 设置对应数据位置的值
	 * 
	 * @param index
	 * @param str
	 */
	public void setData(int index, String str) {
		if (index < data.length)
			data[index] = str;
	}

}
