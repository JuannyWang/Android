package com.cqupt.mobilestudiesdemo.dialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.cqupt.mobilestudiesdemo.entity.WSError;
import com.cqupt.mobilestudiesdemo.entity.WebServiceApi;

/**
 * Wrapper around UserTask & ProgressDialog
 * 
 * @author ap
 * 
 * @param <Input>
 * @param <Result>
 */
public abstract class LoadingDialog<Input, Result> extends
		AsyncTask<Input, WSError, Result> {

	protected ProgressDialog mProgressDialog;
	protected Activity mActivity;

	public LoadingDialog(Activity activity) {
		this.mActivity = activity;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		// 执行前初始化
		mProgressDialog = new ProgressDialog(mActivity);
		mProgressDialog.setMessage("正在加载数据，请稍后...");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	@Override
	public abstract Result doInBackground(Input... params);

	@Override
	protected void onPostExecute(Result result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		switch ((Integer) result) {
		case WebServiceApi.GET_DATA_SUCCESS:
			dataHandle("网络数据加载成功！", result);
			break;
		case WebServiceApi.NET_ERR:
			dataHandle("网络错误，请检查网络！", result);

			break;
		case WebServiceApi.GET_DATA_ERR:
			dataHandle("网络数据加载错误，请重新加载！", result);

			break;
		default:
			dataHandle("网络数据加载错误，请重新加载！", result);

			break;
		}
	}

	// 数据处理
	protected void dataHandle(String handleString, Result result) {
		mProgressDialog.dismiss();
		Toast.makeText(mActivity, handleString, Toast.LENGTH_SHORT).show();
		doStuffWithResult(result);
	}

	/**
	 * Very abstract function hopefully very meaningful name, executed when
	 * result is other than null
	 * 
	 * @param result
	 * @return
	 */
	public abstract void doStuffWithResult(Result result);

	@Override
	protected void onProgressUpdate(WSError... values) {
		Toast.makeText(mActivity, values[0].getMessage(), Toast.LENGTH_LONG)
				.show();
		this.cancel(true);
		mProgressDialog.dismiss();
		super.onProgressUpdate(values);
	}
}
