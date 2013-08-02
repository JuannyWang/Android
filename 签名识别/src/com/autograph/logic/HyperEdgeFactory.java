package com.autograph.logic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.autograph.data.PointS;

import android.os.Environment;

public class HyperEdgeFactory {

	private HyperEdgeMain hyperEdge;

	/**
	 * 检查文件是否存在
	 */
	public void check() {
		String local_file = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		File file = new File(local_file + "/srData/");
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(local_file + "/srData/personList.txt");
		if (!file.exists()) {
			try {
				System.out.println(local_file + "/srData/personList.txt");
				file.createNewFile();
				PrintWriter writer = new PrintWriter(file);
				writer.println(0);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 初始化
	 */
	public void initialize() {
		hyperEdge = new HyperEdgeMain();
		hyperEdge.Initialization();
		try {
			hyperEdge.ReadTrainDataFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for( Integer i = 0; i < hyperEdge.personList_.size(); i++ )
		{	
			try {
				hyperEdge.ReadTrainSetResult( i );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void learn(List<PointS> map, String name) {
		if( map.size() < 2 )
			return;
		
		List<PointS> mapOut = new ArrayList<PointS>();
		
		float k = 0, b = 0;
		int preX = map.get( 0 ).x, preY = map.get( 0 ).y, curX = 0, curY = 0;
		for( int i = 1; i < map.size(); i++ )
		{
			curX = map.get( i ).x;
			curY = map.get( i ).y;
			if( curX == preX )
			{
				if( curY >= preY )
				{
					for( int yValue = preY; yValue < curY; yValue++ )
						mapOut.add( new PointS( curX, yValue ) );
				}
				else
				{
					for( int yValue = preY; yValue > curY; yValue-- )
						mapOut.add( new PointS( curX, yValue ) );
				}
			}
			else if( curY == preY )
			{
				if( curX >= preX )
				{
					for( int xValue = preX; xValue < curX; xValue++ )
						mapOut.add( new PointS( xValue, curY ) );
				}
				else
				{
					for( int xValue = preX; xValue > curX; xValue-- )
						mapOut.add( new PointS( xValue, curY ) );
				}
			}
			else
			{
				k = ( float )( curY - preY ) / ( curX - preX );
				b = ( float )curY - k * curX;
				if( Math.abs( k ) <= 1 )
				{
					if( curX >= preX )
					{
						for( int xValue = preX; xValue < curX; xValue++ )
							mapOut.add( new PointS( xValue, ( int )( k * xValue + b ) ) );
					}
					else
					{
						for( int xValue = preX; xValue > curX; xValue-- )
							mapOut.add( new PointS( xValue, ( int )( k * xValue + b ) ) );
					}
				}
				else
				{
					if( curY >= preY )
					{
						for( int yValue = preY; yValue < curY; yValue++ )
							mapOut.add( new PointS( ( int )( ( yValue - b ) / k ), yValue ) );
					}
					else
					{
						for( int yValue = preY; yValue > curY; yValue-- )
							mapOut.add( new PointS( ( int )( ( yValue - b ) / k ), yValue ) );
					}
				}
			}
			preX = curX;
			preY = curY;
		}
		
		try {
			PointS points[] = new PointS[mapOut.size()];
			for (int i = 0; i < mapOut.size(); ++i) {
				points[i] = new PointS(mapOut.get(i).x, mapOut.get(i).y);
			}
			hyperEdge.addPerson(points, name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(String name) {
		try {
			hyperEdge.removePerson(name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void train() {
		for (Integer i = 0; i < hyperEdge.personList_.size(); i++) {
			if ( true == hyperEdge.personList_.get( i ).bLearning_ )
				continue;
			hyperEdge.Learning(i);
			try {
				hyperEdge.SaveTrainSetResult(i);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String recog(List<PointS> map) {
		if( map.size() < 2 )
			return "签名未识别";
		
		List<PointS> mapOut = new ArrayList<PointS>();
		
		float k = 0, b = 0;
		int preX = map.get( 0 ).x, preY = map.get( 0 ).y, curX = 0, curY = 0;
		for( int i = 1; i < map.size(); i++ )
		{
			curX = map.get( i ).x;
			curY = map.get( i ).y;
			if( curX == preX )
			{
				if( curY >= preY )
				{
					for( int yValue = preY; yValue < curY; yValue++ )
						mapOut.add( new PointS( curX, yValue ) );
				}
				else
				{
					for( int yValue = preY; yValue > curY; yValue-- )
						mapOut.add( new PointS( curX, yValue ) );
				}
			}
			else if( curY == preY )
			{
				if( curX >= preX )
				{
					for( int xValue = preX; xValue < curX; xValue++ )
						mapOut.add( new PointS( xValue, curY ) );
				}
				else
				{
					for( int xValue = preX; xValue > curX; xValue-- )
						mapOut.add( new PointS( xValue, curY ) );
				}
			}
			else
			{
				k = ( float )( curY - preY ) / ( curX - preX );
				b = ( float )curY - k * curX;
				if( Math.abs( k ) <= 1 )
				{
					if( curX >= preX )
					{
						for( int xValue = preX; xValue < curX; xValue++ )
							mapOut.add( new PointS( xValue, ( int )( k * xValue + b ) ) );
					}
					else
					{
						for( int xValue = preX; xValue > curX; xValue-- )
							mapOut.add( new PointS( xValue, ( int )( k * xValue + b ) ) );
					}
				}
				else
				{
					if( curY >= preY )
					{
						for( int yValue = preY; yValue < curY; yValue++ )
							mapOut.add( new PointS( ( int )( ( yValue - b ) / k ), yValue ) );
					}
					else
					{
						for( int yValue = preY; yValue > curY; yValue-- )
							mapOut.add( new PointS( ( int )( ( yValue - b ) / k ), yValue ) );
					}
				}
			}
			preX = curX;
			preY = curY;
		}
		
		PointS points[] = new PointS[mapOut.size()];
		for (int i = 0; i < mapOut.size(); ++i) {
			points[i] = new PointS(mapOut.get(i).x, mapOut.get(i).y);
		}
		String result = hyperEdge.Recognition(points);
		if (result == null)
			return "签名未识别";
		return result;
	}
}
