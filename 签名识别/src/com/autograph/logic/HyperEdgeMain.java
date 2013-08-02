package com.autograph.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.autograph.data.PointS;

import android.os.Environment;

class NecessaryFeatrue
{
	Integer position_;
	char feature_;
	
	public NecessaryFeatrue( Integer position, char feature )
	{
		position_ = position;
		feature_ = feature;
	}
}

class HyperEdge implements Comparable< HyperEdge >
{
	short vertex[];
	char value[];
	Integer fitc;
	Integer fitw;
	char label;
	Integer Class;
	
	public HyperEdge( Integer order )
	{
		vertex = new short[ order ];
		value = new char[ order ];
	}
	
	public int compareTo( HyperEdge another )
	{
		if ( this.fitw == another.fitw )
		{
			return another.fitc - this.fitc;
		}
		return another.fitw - this.fitw;
	}
}

class Person
{
	String personName_;
	char trainSetData_[][];
	Integer trainSetSampleNumber_;
	Integer trainSetFeatureDimension_;
	Integer order_;
	Integer hyperEdgeNumberOfEachSample_;
	HyperEdge hyperEdgeAll_[];
	boolean bLearning_;
	
	public Person( String personName, Integer trainSetSampleNumbe, Integer trainSetFeatureDimension,
			Integer order, Integer hyperEdgeNumberOfEachSample )
	{
		bLearning_ = false;
		personName_ = personName;
		trainSetSampleNumber_ = trainSetSampleNumbe;
		trainSetFeatureDimension_ = trainSetFeatureDimension;
		order_ = order;
		hyperEdgeNumberOfEachSample_ = hyperEdgeNumberOfEachSample;
		trainSetData_ = new char[ trainSetSampleNumber_ ][ trainSetFeatureDimension_ ];
	}
};

public class HyperEdgeMain
{
	public ArrayList< Person > personList_;//人的签名的表

	private String personListFileName_;//存取的人的文件名
	private Integer maxsubstCnt_;
	private Integer vectorNumber_;//每个字分成多少段
	private double pi_;//圆周率
	private Integer defaultHyperEdgeNumberOfEachSample_;//默认的每个样本生成的超边数
	private Integer defaultOrder_;//默认的阶数
	private Integer defaultNumberOfMaxIteration_;//默认的学习的时候的最大迭代次数
	private float defaultSubstitutionWeight_;
	private double defaultRecognitionResultPercentage_;
	private String dataRootFileName_;//保存数据的跟文件名
	private double bNecessaryFeatruePercentage_;
	
	public void Initialization()//初始化一些变量
	{
		String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
		this.dataRootFileName_ = "/srData/";
		this.personListFileName_ = absolutePath + this.dataRootFileName_ + "personList.txt";
		this.pi_ = 3.141592653;
		this.vectorNumber_ = 50;
		this.defaultHyperEdgeNumberOfEachSample_ = 100;
		this.defaultOrder_ = 12;
		this.defaultNumberOfMaxIteration_ = 100;
		this.defaultSubstitutionWeight_ = 1.2f;
		this.defaultRecognitionResultPercentage_ = 0.005;
		this.bNecessaryFeatruePercentage_ = 0.9;
	}
	
	private boolean HasNecessaryFeatrue( Integer index, char testData[] )
	{
		char trainData[][] = this.personList_.get( index ).trainSetData_;
		if( trainData.length < 1 || trainData[ 0 ].length < 1 || testData.length < 1 )
			return false;
		
		ArrayList< NecessaryFeatrue > necessaryFeatrueList = new ArrayList< NecessaryFeatrue >();
		Integer directionNumber = 8;
		Integer necessaryFeatrueFlag[] = new Integer[ directionNumber ];//现在只有8个方向
		Integer max, maxPosition, rowNumber, lineNumber;
		Integer trainSetFeatureSampleNumber = this.personList_.get( index ).trainSetSampleNumber_ - 1;
		
		rowNumber = trainData.length;
		lineNumber = trainData[ 0 ].length - 1;
		for( Integer j = 0; j < lineNumber; j++ )
		{
			for( Integer i = 0; i < directionNumber; i++ )//重置flag数据
			{
				necessaryFeatrueFlag[ i ] = 0;
			}
			
			for( Integer i = 1; i < rowNumber; i++ )//设置flag数据
			{
				necessaryFeatrueFlag[ trainData[ i ][ j ] - '0' - 1 ]++;
			}
			
			max = necessaryFeatrueFlag[ 0 ];
			maxPosition = 0;
			for( Integer i = 1; i < necessaryFeatrueFlag.length; i++ )//寻找出现最多的方向
			{
				if( necessaryFeatrueFlag[ i ] > max )
				{
					max = necessaryFeatrueFlag[ i ];
					maxPosition = i;
				}
			}
			
			//if( ( double )max / ( this.personList_.get( index ).trainSetSampleNumber_ - 1 ) > this.bNecessaryFeatruePercentage_ )
			if( 0 == trainSetFeatureSampleNumber.toString().compareTo( max.toString() )  )
			{
				necessaryFeatrueList.add( new NecessaryFeatrue( j, ( char )( maxPosition + 1 + '0' ) ));
			}
		}
		
		for( Integer i = 0; i < necessaryFeatrueList.size(); i++ )
		{
			if( testData[ necessaryFeatrueList.get( i ).position_ ] != necessaryFeatrueList.get( i ).feature_ )
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean IsLearning()
	{
		if( 0 == this.personList_.size() )
			return false;
		
		for( Integer i = 0; i < this.personList_.size(); i++ )
		{
			if( false == this.personList_.get( i ).bLearning_ )
				return false;
		}
		return true;
	}
	
	private void CheckIsLearningAndLearning()
	{
		for( Integer i = 0; i < this.personList_.size(); i++ )
		{
			if( false == this.personList_.get( i ).bLearning_ )
			{
				this.Learning( i, this.defaultNumberOfMaxIteration_ );
			}
		}
	}
	
	public void ReadTrainDataFromFile() throws IOException//读取训练数据
	{
		personList_ = new ArrayList< Person >();
		FileReader fPersonListIn = new FileReader( this.personListFileName_ );
		BufferedReader bPersonListIn = new BufferedReader( fPersonListIn );
		String oneline = null;
		
		Integer personNumber = Integer.parseInt( bPersonListIn.readLine() );
		for( Integer i = 0; i < personNumber; i++ )
		{
			String personName = bPersonListIn.readLine();
			Integer trainSetSampleNumber = Integer.parseInt( bPersonListIn.readLine() );
			Integer trainSetFeatureDimension = Integer.parseInt( bPersonListIn.readLine() );
			Integer order = Integer.parseInt( bPersonListIn.readLine() );
			Integer hyperEdgeNumberOfEachSample = Integer.parseInt( bPersonListIn.readLine() );
			Person onePerson = new Person( personName, trainSetSampleNumber, trainSetFeatureDimension, order,
					hyperEdgeNumberOfEachSample );
			personList_.add( onePerson );
			System.out.println( "the " + ( i + 1 ) + " person: " + personList_.get( i ).personName_ + " "
					+ personList_.get( i ).trainSetSampleNumber_ + " "
					+ personList_.get( i ).trainSetFeatureDimension_ + " " 
					+ personList_.get( i ).order_ + " "
					+ personList_.get( i ).hyperEdgeNumberOfEachSample_ );
			String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
			FileReader fOnePersonIn = new FileReader( absolutePath + this.dataRootFileName_ + personList_.get( i ).personName_ + ".txt" );
			BufferedReader bOnePersonIn = new BufferedReader( fOnePersonIn );
			
			for( Integer sampleCount = 0; sampleCount < trainSetSampleNumber; sampleCount++ )
			{
				oneline = bOnePersonIn.readLine();
				for( Integer j = 0; j < oneline.length(); j++ )
				{
					personList_.get( i ).trainSetData_[ sampleCount ][ j ] = oneline.charAt( j );
				}
			}
			bOnePersonIn.close();
			fOnePersonIn.close();
		}
		
		bPersonListIn.close();
		fPersonListIn.close();
	}
	
	public void ReadTrainSetResult( Integer index ) throws IOException//读取训练数据的训练结果
	{
		Integer hyperEdgeNumber = 0;
		Integer order = 0;
		String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
		File personResultFile = new File( absolutePath + this.dataRootFileName_ + this.personList_.get( index ).personName_ + "_result.txt" );
		if( !personResultFile.exists() )
		{
			System.out.println( absolutePath + this.dataRootFileName_ + this.personList_.get( index ).personName_ + "_result.txt is not exists" );
			return;
		}

		FileReader fPersonResultIn = new FileReader( personResultFile );
		BufferedReader bPersonResultIn = new BufferedReader( fPersonResultIn );
		String splitOneline[] = null;
		
		hyperEdgeNumber = Integer.parseInt( bPersonResultIn.readLine() );
		order = Integer.parseInt( bPersonResultIn.readLine() );
		this.personList_.get( index ).hyperEdgeAll_ = new HyperEdge[ hyperEdgeNumber ];
		for( Integer i = 0; i < hyperEdgeNumber; i++ )
		{
			this.personList_.get( index ).hyperEdgeAll_[ i ] = new HyperEdge( order );
			splitOneline = bPersonResultIn.readLine().split( " " );
			for( Integer j = 0; j < order; j++ )
			{
				this.personList_.get( index ).hyperEdgeAll_[ i ].vertex[ j ] = ( short )Integer.parseInt( splitOneline[ j ] );
			}
			splitOneline = bPersonResultIn.readLine().split( " " );
			for( Integer j = 0; j < order; j++ )
			{
				this.personList_.get( index ).hyperEdgeAll_[ i ].value[ j ] = ( char )( Integer.parseInt( splitOneline[ j ] ) + 48 );
			}
			this.personList_.get( index ).hyperEdgeAll_[ i ].label = ( char )( Integer.parseInt( bPersonResultIn.readLine() ) + 48 );
		}
		bPersonResultIn.close();
		fPersonResultIn.close();
		this.personList_.get( index ).bLearning_ = true;
	}
	
	private void LeastSquareMethod( PointS pointList[], double params[] )
	{
		Integer pointNumber = pointList.length;

	    double p = 0, q = 0, xTotal = 0, yTotal = 0, c = 0, d = 0;
	    short xTemp = 0, yTemp = 0;
	    for( Integer i = 0; i < pointNumber; i++ )
	    {
	    	xTemp = pointList[ i ].x;
	    	yTemp = pointList[ i ].y;
	        p += xTemp * yTemp;

	        xTotal += xTemp;
	        yTotal += yTemp;

	        c += xTemp * xTemp;
	    }
	    q = xTotal * yTotal / pointNumber;
	    d = xTotal * xTotal / pointNumber;

	    if( Math.abs( c - d ) < 1e-6 )
	    	params[ 0 ] = 1000000;
	    else
	    	params[ 0 ] = ( p - q ) / ( c - d );
	    
	    params[ 1 ] = ( yTotal - xTotal * params[ 0 ] ) / pointNumber;
	}
	
	private boolean GetFreeman( PointS pointList[], char freeman[] )
	{
		double k;
		PointS vec = new PointS();//方向向量
		double theta;
		
		Integer perVectorCount = pointList.length / this.vectorNumber_;//每段的点的数量
		if( perVectorCount < 4 )//如果太短了
		{
	    	return false;
	    }
		
		for( Integer i = 0; i < this.vectorNumber_; i++ )
		{
			PointS pointListTemp[] = new PointS[ perVectorCount ];
			for( Integer j = 0; j < perVectorCount; j++ )
			{
				pointListTemp[ j ] = pointList[ i * perVectorCount + j ];
			}
			double params[] = new double[ 2 ];
			this.LeastSquareMethod( pointListTemp, params );//最小二乘法
			k = params[ 0 ];
			
			vec.x = ( short )( pointListTemp[ perVectorCount - 1 ].x - pointListTemp[ 0 ].x );
			vec.y = ( short )( k * pointListTemp[ perVectorCount - 1 ].x - k * pointListTemp[ 0 ].x );
			
			if( 0 == vec.x )
			{
				if( 0 == vec.y )
				{
					if( pointListTemp[ perVectorCount - 1 ].y - pointListTemp[ 0 ].y >= 0 )
					{
						freeman[ i ] = '3';
					}
					else
						freeman[ i ] = '7';
				}
				else if( vec.y > 0 )//向上
				{
					freeman[ i ] = '3';
				}
				else//向下
				{
					freeman[ i ] = '7';
				}
			}
			else if( 0 == vec.y )
			{
				if( vec.x > 0 )//向右
				{
					freeman[ i ] = '1';
				}
				else//向左
				{
					freeman[ i ] = '5';
				}
			}
			else
			{
				theta = Math.abs( Math.atan( k ) * 180 / this.pi_ );
				if( vec.x > 0 && vec.y > 0 )//第一象限
				{
					if( theta > 0 && theta < 22.5 )//向右
					{
						freeman[ i ] = '1';
					}
					else if( theta >= 22.5 && theta <= 67.5 )//向右上
					{
						freeman[ i ] = '2';
					}
					else if( theta > 67.5 && theta < 90 )//向上
					{
						freeman[ i ] = '3';
					}
				}
				else if( vec.x < 0 && vec.y > 0 )//第二象限
				{
					if( theta > 0 && theta < 22.5 )//向左
					{
						freeman[ i ] = '5';
					}
					else if( theta >= 22.5 && theta <= 67.5 )//向左上
					{
						freeman[ i ] = '4';
					}
					else if( theta > 67.5 && theta < 90 )//向上
					{
						freeman[ i ] = '3';
					}
				}
				else if( vec.x < 0 && vec.y < 0 )//第三象限
				{
					if( theta > 0 && theta < 22.5 )//向左
					{
						freeman[ i ] = '5';
					}
					else if( theta >= 22.5 && theta <= 67.5 )//向左下
					{
						freeman[ i ] = '6';
					}
					else if( theta > 67.5 && theta < 90 )//向下
					{
						freeman[ i ] = '7';
					}
				}
				else if( vec.x > 0 && vec.y < 0 )//第四象限
				{
					if( theta > 0 && theta < 22.5 )//向右
					{
						freeman[ i ] = '1';
					}
					else if( theta >= 22.5 && theta <= 67.5 )//向右下
					{
						freeman[ i ] = '8';
					}
					else if( theta > 67.5 && theta < 90 )//向下
					{
						freeman[ i ] = '7';
					}
				}
			}
		}
		
		return true;
	}
	
	public void addPerson( PointS pointList[], String personName ) throws IOException
	{
		char freeman[] = new char[ this.vectorNumber_ + 1 ];
		if( false == this.GetFreeman( pointList, freeman ) )
			return;
		freeman[ this.vectorNumber_ ] = '1';
		this.addPerson( freeman, personName );
	}
	
	private void addPerson( char personTrainData[], String personName ) throws IOException
	{
		System.out.println( "write person: " + personName );
		Integer personNumber = this.personList_.size();
		for( Integer i = 0; i < personNumber; i++ )
		{
			if( 0 == personName.compareTo( this.personList_.get( i ).personName_ ) )
			{
				String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
				FileWriter fPersonListOut = new FileWriter( absolutePath + this.dataRootFileName_ + personName + ".txt", true );
				fPersonListOut.write( "\r\n" );
				fPersonListOut.write( personTrainData );
//				char dataTemp[] = new char[ personTrainData.length ];
//				for( Integer p = 0; p < personTrainData.length; p++ )
//					dataTemp[ p ] = '0';
//				fPersonListOut.write( "\r\n" );
//				fPersonListOut.write( dataTemp );
				fPersonListOut.close();
				
				Integer trainSetSampleNumberTemp = this.personList_.get( i ).trainSetSampleNumber_;
				Integer trainSetFeatureDimensionTemp = this.personList_.get( i ).trainSetFeatureDimension_;
				char trainSetDataTemp[][] = new char[ trainSetSampleNumberTemp ][ trainSetFeatureDimensionTemp ];
				for( Integer m = 0; m < trainSetSampleNumberTemp; m++ )
				{
					for( Integer n = 0; n < trainSetFeatureDimensionTemp; n++ )
					{
						trainSetDataTemp[ m ][ n ] = this.personList_.get( i ).trainSetData_[ m ][ n ];
					}
				}
				this.personList_.get( i ).trainSetData_ =
					new char[ trainSetSampleNumberTemp + 1 ][ trainSetFeatureDimensionTemp ];
				for( Integer m = 0; m < trainSetSampleNumberTemp; m++ )
				{
					for( Integer n = 0; n < trainSetFeatureDimensionTemp; n++ )
					{
						this.personList_.get( i ).trainSetData_[ m ][ n ] = trainSetDataTemp[ m ][ n ];
					}
				}
				for( Integer n = 0; n < trainSetFeatureDimensionTemp; n++ )
				{
					this.personList_.get( i ).trainSetData_[ trainSetSampleNumberTemp ][ n ] = personTrainData[ n ];
					//this.personList_.get( i ).trainSetData_[ trainSetSampleNumberTemp + 1 ][ n ] = '0';
				}
				
				this.personList_.get( i ).trainSetSampleNumber_++;
				
				fPersonListOut = new FileWriter( this.personListFileName_ );
				fPersonListOut.write( personNumber.toString() + "\r\n" );

				for( Integer j = 0; j < personNumber; j++ )
				{
					fPersonListOut.write( this.personList_.get( j ).personName_.toString() + "\r\n" );
					fPersonListOut.write( this.personList_.get( j ).trainSetSampleNumber_.toString() + "\r\n" );
					fPersonListOut.write( this.personList_.get( j ).trainSetFeatureDimension_.toString() + "\r\n" );
					fPersonListOut.write( this.personList_.get( j ).order_.toString() + "\r\n" );
					fPersonListOut.write( this.personList_.get( j ).hyperEdgeNumberOfEachSample_.toString() + "\r\n" );
				}
				fPersonListOut.close();
				this.personList_.get( i ).bLearning_ = false;
				return;
			}
		}
		
		Person onePerson = new Person( personName, 2, personTrainData.length,
				this.defaultOrder_, this.defaultHyperEdgeNumberOfEachSample_ );
		for( Integer i = 0; i < personTrainData.length; i++ )
		{
			onePerson.trainSetData_[ 0 ][ i ] = '0';
			onePerson.trainSetData_[ 1 ][ i ] = personTrainData[ i ];
		}
		this.personList_.add( onePerson );
		String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
		FileWriter fPersonListOut = new FileWriter( absolutePath + this.dataRootFileName_ + personName + ".txt" );
		fPersonListOut.write( onePerson.trainSetData_[ 0 ] );
		fPersonListOut.write( "\r\n" );
		fPersonListOut.write( onePerson.trainSetData_[ 1 ] );
		fPersonListOut.close();
		
		fPersonListOut = new FileWriter( this.personListFileName_ );
		Integer personNumberTemp = this.personList_.size();
		fPersonListOut.write( personNumberTemp.toString() + "\r\n" );
		for( Integer j = 0; j < personNumberTemp; j++ )
		{
			fPersonListOut.write( this.personList_.get( j ).personName_.toString() + "\r\n" );
			fPersonListOut.write( this.personList_.get( j ).trainSetSampleNumber_.toString() + "\r\n" );
			fPersonListOut.write( this.personList_.get( j ).trainSetFeatureDimension_.toString() + "\r\n" );
			fPersonListOut.write( this.personList_.get( j ).order_.toString() + "\r\n" );
			fPersonListOut.write( this.personList_.get( j ).hyperEdgeNumberOfEachSample_.toString() + "\r\n" );
		}
		fPersonListOut.close();
		this.personList_.get( this.personList_.size() - 1 ).bLearning_ = false;
	}
	
	public void removePerson( String personName ) throws IOException
	{
		boolean bFind = false;
		Integer personNumber = this.personList_.size();
		for( Integer i = 0; i < personNumber; i++ )
		{
			if( 0 == personName.compareTo( this.personList_.get( i ).personName_ ) )
			{
				System.out.println( "remove person: " + personName );
				String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
				File deleteFile = new File( absolutePath + this.dataRootFileName_ + personName + ".txt" );
				if( deleteFile.isFile() && deleteFile.exists() )
				{
					if( true == deleteFile.delete() )
					{
						System.out.println( "remove person file success at: " + personName );
					}
					else
					{
						System.out.println( "remove person file failure at: " + personName );
					}
				}
				else
				{
					System.out.println( "remove person file failure at: " + personName );
				}
				deleteFile = new File( absolutePath + this.dataRootFileName_ + personName + "_result.txt" );
				if( deleteFile.isFile() && deleteFile.exists() )
				{
					if( true == deleteFile.delete() )
					{
						System.out.println( "remove person_result file success at: " + personName );
					}
					else
					{
						System.out.println( "remove person_result file failure at: " + personName );
					}
				}
				else
				{
					System.out.println( "remove person_result file failure at: " + personName );
				}
				this.personList_.remove( this.personList_.get( i ) );
				bFind = true;
				break;
			}
		}
		
		if( false == bFind )
			return;

		personNumber = this.personList_.size();
		FileWriter fPersonListOut = new FileWriter( this.personListFileName_ );
		fPersonListOut.write( personNumber.toString() + "\r\n" );
		
		for( Integer i = 0; i < personNumber; i++ )
		{
			fPersonListOut.write( this.personList_.get( i ).personName_.toString() + "\r\n" );
			fPersonListOut.write( this.personList_.get( i ).trainSetSampleNumber_.toString() + "\r\n" );
			fPersonListOut.write( this.personList_.get( i ).trainSetFeatureDimension_.toString() + "\r\n" );
			fPersonListOut.write( this.personList_.get( i ).order_.toString() + "\r\n" );
			fPersonListOut.write( this.personList_.get( i ).hyperEdgeNumberOfEachSample_.toString() + "\r\n" );
		}
		fPersonListOut.close();
	}
	
	public void InitiationHyperEdge( Integer trainSetSampleNumber, Integer order,
			Integer hyperEdgeNumberOfEachSample, Integer trainSetFeatureDimension,
			HyperEdge hyperEdgeAll[], char trainSetData[][] )
	{
		short vertexPosition = 0;
		Integer c = 0;
		Random rand = new Random();
		boolean randomTemp[] = new boolean[ trainSetFeatureDimension - 1 ];
		for( Integer i = 0; i < trainSetSampleNumber; i++ )//遍历每个类别
		{
			for( Integer j = 0; j < hyperEdgeNumberOfEachSample; j++ )//遍历每个类别所要生成的超边数
			{
				for( Integer k = 0; k < trainSetFeatureDimension - 1; k++ )
				{
					randomTemp[ k ] = false;
				}
				hyperEdgeAll[ c ] = new HyperEdge( order );
				for( Integer k = 0; k < order; k++ )//遍历每一个顶点
				{
					do
					{
						vertexPosition = ( short )( Math.abs( rand.nextInt() ) % ( trainSetFeatureDimension - 1 ) );//随机生成顶点位置
						if( false == randomTemp[ vertexPosition ] )
						{
							randomTemp[ vertexPosition ] = true;
							break;
						}
					}while( true );
					hyperEdgeAll[ c ].vertex[ k ] = vertexPosition;//存储顶点位置
					hyperEdgeAll[ c ].value[ k ] = trainSetData[ i ][ vertexPosition ];//得到这个顶点位置的值
				}
				hyperEdgeAll[ c ].fitw = 0;
				hyperEdgeAll[ c ].fitc = 0;
				hyperEdgeAll[ c ].label = trainSetData[ i ][ trainSetFeatureDimension - 1 ];
				hyperEdgeAll[ c ].Class = i;
				c++;
			}
		}
	}
	
	private Integer trainSetClassify( Integer trainSetSampleNumber, Integer order,
			Integer trainSetFeatureDimension, Integer hyperEdgeNumberOfEachSample,
			HyperEdge hyperEdgeAll[], char trainSetData[][], Integer rightAndWrongFlag[] )
	{
		char vertexValue[] = new char[ order ];
		Integer hyperEdgeNumber = trainSetSampleNumber * hyperEdgeNumberOfEachSample;
		Integer classification0 = 0, classification1 = 0;
		short vertexPosition;
		boolean labelRight[] = new boolean[ trainSetSampleNumber ];
		Integer rightResult = 0;
		char sampleLabelResult;
		boolean bSame;
		
		for( Integer i = 0; i < trainSetSampleNumber; i++ )//遍历每个类别
		{
			classification0 = 0;
			classification1 = 0;
			for( Integer j = 0; j < hyperEdgeNumber; j++ )//遍历每条超边
			{
				if( hyperEdgeAll[ j ].Class == i )
					continue;//如果是自己的类别就继续寻找
				
				for( Integer k = 0; k < order; k++ )
				{
					vertexPosition = hyperEdgeAll[ j ].vertex[ k ];//取出这个阶数获取到得顶点的位置
					vertexValue[ k ] = trainSetData[ i ][ vertexPosition ];//从自己这个类别中取出对应点的值
				}
				
				bSame = true;
				for( Integer m = 0; m < vertexValue.length; m++ )
				{
					if( vertexValue[ m ] != hyperEdgeAll[ j ].value[ m ] )
					{
						bSame = false;
						break;
					}
				}
				
				if( true == bSame )//如果这个类别的所有顶点的值与这条超边的顶点的值是一样的
				{
					if( hyperEdgeAll[ j ].label == '0' )//如果超边的标签是0类
						classification0++;//0类分类增加
					else
						classification1++;//1类分类增加
				}
			}
			
			if( classification1 > classification0
					&& classification1 > trainSetSampleNumber / 2 - 1 )//如果1类分类数量大于0类分类数量
				sampleLabelResult = '1';
			else
				sampleLabelResult = '0';
			
			if( sampleLabelResult == trainSetData[ i ][ trainSetFeatureDimension - 1 ] )//如果这个类分类出来的标签结果与我们初始给定的标签是一样的
			{
				rightResult++;
				labelRight[ i ] = true;
			}
			else
			{
				labelRight[ i ] = false;
			}
		}
		
		Integer counter = 0;
		
		for( Integer i = 0; i < trainSetSampleNumber; i++ )//遍历所有类别
		{
			if( false == labelRight[ i ] )//如果这个类分类出来的标签结果与我们初始给定的标签不一样
			{
				rightAndWrongFlag[ counter++ ] = i;//存储这个类别
			}
		}
		
		for( Integer i = 0; i < trainSetSampleNumber; i++ )//遍历所有类别
		{
			if( true == labelRight[ i ] )//如果这个类分类出来的标签结果与我们初始给定的标签不一样
			{
				rightAndWrongFlag[ counter++ ] = i;//存储这个类别
			}
		}
		
		return rightResult;
	}
	
	private void Fitness( Integer trainSetSampleNumber, Integer order, 
			Integer trainSetFeatureDimension, Integer hyperEdgeNumberOfEachSample,
			HyperEdge hyperEdgeAll[], char trainSetData[][], Integer rightAndWrongFlag[],
			Integer rightResult )
	{
		short vertexPosition = 0;
		Integer hyperEdgeNumber = trainSetSampleNumber * hyperEdgeNumberOfEachSample;
		char vertexValue[] = new char[ order ];
		for( Integer i = 0; i < hyperEdgeNumber; i++ )//遍历每条超边
		{
			for( Integer j = 0; j < trainSetSampleNumber - rightResult; j++ )//遍历分类错误的类别
			{
				for( Integer k = 0; k < order; k++ )
				{
					vertexPosition = hyperEdgeAll[ i ].vertex[ k ];//获取这条超边的顶点的位置
					vertexValue[ k ] = trainSetData[ rightAndWrongFlag[ j ] ][ vertexPosition ];//从错误类别中取出这个顶点的值
				}
				
				boolean bSame = true;
				for( Integer m = 0; m < vertexValue.length; m++ )
				{
					if( vertexValue[ m ] != hyperEdgeAll[ i ].value[ m ] )
					{
						bSame = false;
						break;
					}
				}
				
				if( true == bSame )//如果这个错误类别的所有顶点的值与这条超边的顶点的值是一样的
				{
					if( hyperEdgeAll[ i ].label == trainSetData[ rightAndWrongFlag[ j ] ][ trainSetFeatureDimension - 1 ] )
						hyperEdgeAll[ i ].fitw++;
					else
						hyperEdgeAll[ i ].fitw--;
				}
			}
			
			for( Integer j = 0; j < rightResult; j++ )//遍历分类正确的类别
			{
				for( Integer k = 0; k < order; k++ )
				{
					vertexPosition = hyperEdgeAll[ i ].vertex[ k ];
					vertexValue[ k ] = trainSetData[ rightAndWrongFlag[ trainSetSampleNumber - rightResult + j ] ][ vertexPosition ];
				}
				
				boolean bSame = true;
				for( Integer m = 0; m < vertexValue.length; m++ )
				{
					if( vertexValue[ m ] != hyperEdgeAll[ i ].value[ m ] )
					{
						bSame = false;
						break;
					}
				}
				
				if( true == bSame )
				{
					if( hyperEdgeAll[ i ].label == trainSetData[ rightAndWrongFlag[ trainSetSampleNumber - rightResult + j ] ][ trainSetFeatureDimension - 1 ] )
						hyperEdgeAll[ i ].fitc++;
					else
						hyperEdgeAll[ i ].fitc--;
				}
			}
		}
	}
	
	private void Sort( HyperEdge hyperEdgeAll[] )
	{
		Arrays.sort( hyperEdgeAll );
	}
	
	private void Substitution( Integer trainSetSampleNumber, Integer order, 
			Integer trainSetFeatureDimension, Integer hyperEdgeNumberOfEachSample,
			HyperEdge hyperEdgeAll[], char trainSetData[][],
			Integer rightResult, float weight )
	{
		short vertexPosition = 0;
		Integer hyperEdgeNumber = trainSetSampleNumber * hyperEdgeNumberOfEachSample;
		double r = ( double )( trainSetSampleNumber - rightResult ) / trainSetSampleNumber;
		Integer substCnt = ( int )( weight * r * hyperEdgeNumber );
		if( substCnt > hyperEdgeNumber )
		{
			substCnt = this.maxsubstCnt_;
			//this.maxsubstCnt_ = ( int )( 0.8 * substCnt );
		}
		System.out.println( "substCnt = " + substCnt );
		
		Random rand = new Random();
		boolean randomTemp[] = new boolean[ trainSetFeatureDimension - 1 ];
		for( Integer i = 0; i < substCnt; i++ )
		{
			for( Integer j = 0; j < trainSetFeatureDimension - 1; j++ )
			{
				randomTemp[ j ] = false;
			}
			for( Integer j = 0; j < order; j++ )
			{
				do
				{
					vertexPosition = ( short )( Math.abs( rand.nextInt() ) % ( trainSetFeatureDimension - 1 ) );//随机生成顶点位置
					if( false == randomTemp[ vertexPosition ] )
					{
						randomTemp[ vertexPosition ] = true;
						break;
					}
				}while( true );
				hyperEdgeAll[ hyperEdgeNumber - 1 - i ].vertex[ j ] = vertexPosition;
				hyperEdgeAll[ hyperEdgeNumber - 1 - i ].value[ j ] = trainSetData[ hyperEdgeAll[ hyperEdgeNumber - 1 - i ].Class ][ vertexPosition ];
			}
			hyperEdgeAll[ hyperEdgeNumber - 1 - i ].fitw = 0;
			hyperEdgeAll[ hyperEdgeNumber - 1 - i ].fitc = 0;
		}
	}
	
	private void HyEdgeAdjustment( Integer trainSetSampleNumber, Integer hyperEdgeNumberOfEachSample,
			Integer rightResult, float weight,
			HyperEdge hyperEdgeAll[], HyperEdge hyperEdgeTemp[] )
	{
		Integer hyperEdgeNumber = trainSetSampleNumber * hyperEdgeNumberOfEachSample;
		int j = 0, num;
		Integer current[] = new Integer[ 2 ];
		char label;
		num = ( int )( ( trainSetSampleNumber - rightResult ) * weight * hyperEdgeNumber / trainSetSampleNumber );
		label = hyperEdgeTemp[ 0 ].label;
		current[ label - '0' ] = 0;
		while( hyperEdgeTemp[ j ].label == label )
		{
			j++;
		}
		current[ '1' - label ] = j;
		for( int i = 0; i < num; i++ )
		{
			label = hyperEdgeAll[ hyperEdgeNumber - 1 - i ].label;
			j = current[ label - '0' ];
			while( hyperEdgeTemp[ j ].label != label )
			{
				j++;
			}
			hyperEdgeAll[ hyperEdgeNumber - 1 - i ] = hyperEdgeTemp[ j ];
			current[ label - '0' ] = j;
		}
	}
	
	public void SaveTrainSetResult( Integer index ) throws IOException
	{
		//this.CheckIsLearningAndLearning();//检查是否所有的都学习了并把没学习的学习了
		
		Integer hyperEdgeNumber = this.personList_.get( index ).trainSetSampleNumber_ * this.personList_.get( index ).hyperEdgeNumberOfEachSample_;
		Integer order = this.personList_.get( index ).order_;
		HyperEdge hyperEdgeAll[] = this.personList_.get( index ).hyperEdgeAll_;
		String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
		FileWriter fPersonResultOut = new FileWriter( absolutePath + this.dataRootFileName_ + this.personList_.get( index ).personName_ + "_result.txt" );
		fPersonResultOut.write( hyperEdgeNumber.toString() );
		fPersonResultOut.write( "\r\n" );
		fPersonResultOut.write( order.toString() );
		fPersonResultOut.write( "\r\n" );
		for( Integer i = 0; i < hyperEdgeNumber; i++ )
		{
			for( Integer j = 0; j < order; j++ )
			{
				Integer vertexTemp = ( int )hyperEdgeAll[ i ].vertex[ j ];
				fPersonResultOut.write( vertexTemp.toString() );
				fPersonResultOut.write( " " );
			}
			fPersonResultOut.write( "\r\n" );
			for( Integer j = 0; j < order; j++ )
			{
				fPersonResultOut.write( hyperEdgeAll[ i ].value[ j ] );
				fPersonResultOut.write( " " );
			}
			fPersonResultOut.write( "\r\n" );
			fPersonResultOut.write( hyperEdgeAll[ i ].label );
			fPersonResultOut.write( "\r\n" );
		}
		fPersonResultOut.close();
	}
	
	public void LearningHyperEdgeAdjustment( Integer index, Integer numberOfMaxIteration )
	{
		Integer preResult = 0, currentResult = 0;
		Integer trainSetSampleNumber = this.personList_.get( index ).trainSetSampleNumber_;
		Integer order = this.personList_.get( index ).order_;
		Integer hyperEdgeNumberOfEachSample = this.personList_.get( index ).hyperEdgeNumberOfEachSample_;
		Integer trainSetFeatureDimension = this.personList_.get( index ).trainSetFeatureDimension_;
		
		Integer rightAndWrongFlag[] = new Integer[ trainSetSampleNumber ];
		this.personList_.get( index ).hyperEdgeAll_ = new HyperEdge[ trainSetSampleNumber * hyperEdgeNumberOfEachSample ];
		
		this.InitiationHyperEdge( trainSetSampleNumber, order, hyperEdgeNumberOfEachSample, trainSetFeatureDimension,
				this.personList_.get( index ).hyperEdgeAll_, this.personList_.get( index ).trainSetData_ );
		
		currentResult = this.trainSetClassify( trainSetSampleNumber, order, trainSetFeatureDimension,
				hyperEdgeNumberOfEachSample, this.personList_.get( index ).hyperEdgeAll_,
				this.personList_.get( index ).trainSetData_, rightAndWrongFlag );
		
		Integer epch = 0;
		Integer hyperEdgeNumber = trainSetSampleNumber * hyperEdgeNumberOfEachSample;
		HyperEdge hyperEdgeTemp[] = new HyperEdge[ hyperEdgeNumber ];
		while( currentResult != trainSetSampleNumber && epch < numberOfMaxIteration )//超网络学习
		{
			System.out.println( "this is " + ( epch + 1 ) + " learning: " );
			if( currentResult < preResult )//如果本次学习结果没有前一次好
			{
				System.out.println( "currentResult < preResult" );
				for( Integer j = 0; j < hyperEdgeNumber; j++ )
				{
					this.personList_.get( index ).hyperEdgeAll_[ j ] = hyperEdgeTemp[ j ];
				}
				
				this.HyEdgeAdjustment( trainSetSampleNumber, hyperEdgeNumberOfEachSample, preResult,
						( int )( this.defaultSubstitutionWeight_ * 0.95 ), this.personList_.get( index ).hyperEdgeAll_, hyperEdgeTemp );//整合一些超边
				
				this.Fitness( trainSetSampleNumber, order, trainSetFeatureDimension, hyperEdgeNumberOfEachSample,
						this.personList_.get( index ).hyperEdgeAll_, this.personList_.get( index ).trainSetData_,
						rightAndWrongFlag, preResult );
				
				this.Sort( this.personList_.get( index ).hyperEdgeAll_ );
				
				this.Substitution( trainSetSampleNumber, order, trainSetFeatureDimension, hyperEdgeNumberOfEachSample,
						this.personList_.get( index ).hyperEdgeAll_, this.personList_.get( index ).trainSetData_,
						preResult, this.defaultSubstitutionWeight_ );
			}
			else
			{
				preResult = currentResult;
				
				this.Fitness( trainSetSampleNumber, order, trainSetFeatureDimension, hyperEdgeNumberOfEachSample,
						this.personList_.get( index ).hyperEdgeAll_, this.personList_.get( index ).trainSetData_,
						rightAndWrongFlag, currentResult );
				
				this.Sort( this.personList_.get( index ).hyperEdgeAll_ );
				
				for( int j = 0; j < hyperEdgeNumber; j++ )
				{
					hyperEdgeTemp[ j ] = this.personList_.get( index ).hyperEdgeAll_[ j ];
				}
				
				this.Substitution( trainSetSampleNumber, order, trainSetFeatureDimension, hyperEdgeNumberOfEachSample,
						this.personList_.get( index ).hyperEdgeAll_, this.personList_.get( index ).trainSetData_,
						currentResult, this.defaultSubstitutionWeight_ );   
			}
			
			currentResult = this.trainSetClassify( trainSetSampleNumber, order, trainSetFeatureDimension,
					hyperEdgeNumberOfEachSample, this.personList_.get( index ).hyperEdgeAll_,
					this.personList_.get( index ).trainSetData_, rightAndWrongFlag );
			
			System.out.println( "the TrainData of the accuray is: " + ( float )currentResult / trainSetSampleNumber );
			
			epch++;
		}
		System.out.println( "trainSetClassify rightResult at person "
				+ this.personList_.get( index ).personName_ + " is "
				+ currentResult );
	}
	
	public void Learning( Integer index )
	{
		Learning( index, this.defaultNumberOfMaxIteration_ );
	}
	
	public void Learning( Integer index, Integer numberOfMaxIteration )
	{
		Integer currentResult = 0;
		Integer trainSetSampleNumber = this.personList_.get( index ).trainSetSampleNumber_;
		Integer order = this.personList_.get( index ).order_;
		Integer hyperEdgeNumberOfEachSample = this.personList_.get( index ).hyperEdgeNumberOfEachSample_;
		Integer trainSetFeatureDimension = this.personList_.get( index ).trainSetFeatureDimension_;
		
		Integer rightAndWrongFlag[] = new Integer[ trainSetSampleNumber ];
		this.personList_.get( index ).hyperEdgeAll_ = new HyperEdge[ trainSetSampleNumber * hyperEdgeNumberOfEachSample ];
		
		this.InitiationHyperEdge( trainSetSampleNumber, order, hyperEdgeNumberOfEachSample, trainSetFeatureDimension,
				this.personList_.get( index ).hyperEdgeAll_, this.personList_.get( index ).trainSetData_ );
		
		this.maxsubstCnt_ = trainSetSampleNumber * hyperEdgeNumberOfEachSample;
		currentResult = this.trainSetClassify( trainSetSampleNumber, order, trainSetFeatureDimension,
				hyperEdgeNumberOfEachSample, this.personList_.get( index ).hyperEdgeAll_,
				this.personList_.get( index ).trainSetData_, rightAndWrongFlag );
		
		Integer epch = 0;
		while( 0 != currentResult.compareTo( trainSetSampleNumber ) && epch < numberOfMaxIteration )//超网络学习
		{
			System.out.println( "this is " + ( epch + 1 ) + " learning: " );
			
			this.Fitness( trainSetSampleNumber, order, trainSetFeatureDimension, hyperEdgeNumberOfEachSample,
					this.personList_.get( index ).hyperEdgeAll_, this.personList_.get( index ).trainSetData_,
					rightAndWrongFlag, currentResult );
			
			this.Sort( this.personList_.get( index ).hyperEdgeAll_ );
			
			this.Substitution( trainSetSampleNumber, order, trainSetFeatureDimension, hyperEdgeNumberOfEachSample,
					this.personList_.get( index ).hyperEdgeAll_, this.personList_.get( index ).trainSetData_,
					currentResult, this.defaultSubstitutionWeight_ );   
			
			currentResult = this.trainSetClassify( trainSetSampleNumber, order, trainSetFeatureDimension,
					hyperEdgeNumberOfEachSample, this.personList_.get( index ).hyperEdgeAll_,
					this.personList_.get( index ).trainSetData_, rightAndWrongFlag );
			
			System.out.println( "the TrainData of the accuray is: " + ( float )currentResult / trainSetSampleNumber );
			
			epch++;
		}
		this.personList_.get( index ).bLearning_ = true;
		
		System.out.println( "trainSetClassify rightResult at person "
				+ this.personList_.get( index ).personName_ + " is "
				+ currentResult );
	}
	
	public String Recognition( PointS pointList[] )
	{
		char freeman[] = new char[ this.vectorNumber_ + 1 ];
		this.GetFreeman( pointList, freeman );
		freeman[ this.vectorNumber_ ] = '1';
		
		this.CheckIsLearningAndLearning();//检查是否所有的都学习了并把没学习的学习了
		
		boolean recognitionResult = false;
		for( Integer i = 0; i < this.personList_.size(); i++ )
		{
			if( false == this.HasNecessaryFeatrue( i, freeman ) )
				continue;

			recognitionResult = this.Recognition( this.personList_.get( i ).trainSetSampleNumber_,
					this.personList_.get( i ).order_, this.personList_.get( i ).trainSetFeatureDimension_,
					this.personList_.get( i ).hyperEdgeNumberOfEachSample_, this.personList_.get( i ).hyperEdgeAll_,
					freeman );
			if( true == recognitionResult )
			{
				return this.personList_.get( i ).personName_;
			}
		}
		return null;
	}
	
	private boolean Recognition( Integer testSetSampleNumber, Integer order,
			Integer testSetFeatureDimension, Integer hyperEdgeNumberOfEachSample,
			HyperEdge hyperEdgeAll[], char testSetData[] )
	{
		short vertexPosition = 0;
		Integer classification0 = 0, classification1 = 0, classification2 = 0;
		Integer hyperEdgeNumber = testSetSampleNumber * hyperEdgeNumberOfEachSample;
		boolean sampleResult;
		char vertexValue[] = new char[ order ];
		
		for( Integer i = 0; i < hyperEdgeNumber; i++ )
		{
			for( Integer j = 0; j < order; j++ )
			{
				vertexPosition = hyperEdgeAll[ i ].vertex[ j ];
				vertexValue[ j ] = testSetData[ vertexPosition ];
			}
			
			boolean bSame = true;
			for( Integer j = 0; j < vertexValue.length; j++ )
			{
				if( vertexValue[ j ] != hyperEdgeAll[ i ].value[ j ] )
				{
					bSame = false;
					break;
				}
			}
			
			if( true == bSame )
			{
				if( '1' == hyperEdgeAll[ i ].label )
					classification1++;
				else
					classification0++;
			}
			else
			{
				classification2++;
			}
		}
		
		System.out.println( classification1 );
		System.out.println( classification2 );
		double resultPercentage = ( double )classification1 / ( hyperEdgeNumber - hyperEdgeNumberOfEachSample );
		System.out.println( resultPercentage );
		if( resultPercentage > this.defaultRecognitionResultPercentage_ )
			sampleResult = true;
		else
			sampleResult = false;

		return sampleResult;
	}
}