

public class FrequencyPro {
	private int newWidth;
	private int newHeight;
	private Complex[][][] Data; 
	private int dataHeight;
	private int dataWidth;
	private int IniHeight;
	private int IniWidth;
	private boolean IsShowFFT;
	private boolean IsShowDFT;
	
	//傅里叶变换接口
	public int[] DFT2d(int[] IniPixelsData, int width, int height) {
		int[][][] input3DData = ProcessPixelData.processOneToThreeDeminsion(IniPixelsData,width,height);
		
		if(IsShowDFT) {
			input3DData = PreProcess(input3DData,width,height);
		}
		
		//System.out.println(width+"a");
		//System.out.println(height+"b");
		
		int[][] RedData = new int[height][width];
		int[][] GreenData = new int[height][width];
		int[][] BlueData = new int[height][width];
		
		for(int i = 0 ; i < height; i++) {
			for(int j = 0; j < width; j++) {
				RedData[i][j] = input3DData[i][j][1];
				GreenData[i][j] = input3DData[i][j][2];
				BlueData[i][j] = input3DData[i][j][3];
			}
		}
		Complex[][] RProData = new Complex[height][width];
		Complex[][] GProData = new Complex[height][width];
		Complex[][] BProData = new Complex[height][width];
		
		for(int u = 0; u < height; u++) {
			System.out.println(u+ "hhh");
			for(int v = 0; v < width; v++) {
				//System.out.println(v);
				RProData[u][v] = DFT(RedData, u, v);
				GProData[u][v] = DFT(GreenData, u, v);
				BProData[u][v] = DFT(BlueData, u, v);
			}     
		}
		
		Data = new Complex[height][width][3];
		dataWidth = width;
		dataHeight = height;
		IniWidth = width;
		IniHeight = height;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				Data[i][j][0] = RProData[i][j];
				Data[i][j][1] = GProData[i][j];
				Data[i][j][2] = BProData[i][j];
			}
		}
		
		if(IsShowDFT) {
			int[][] RedData1 = GetFourierImage(RProData, width, height);
			int[][] GreenData1 = GetFourierImage(GProData, width, height);
			int[][] BlueData1 = GetFourierImage(BProData, width, height);
			
			for(int i = 0 ; i < height; i++) {
				for(int j = 0; j < width; j++) {
					input3DData[i][j][1] = RedData1[i][j];
					input3DData[i][j][2] = GreenData1[i][j];
					input3DData[i][j][3] = BlueData1[i][j];
				}
			}
			
			return ProcessPixelData.convertToOneDim(input3DData, width, height); 
		} else {
			return null;
		}
	}
	
	//傅里叶逆变换接口
	public int[] IDFT2d(int[] IniPixelsData, int width, int height) {
		DFT2d(IniPixelsData, width, height);
		
		Complex[][] RedData = new Complex[dataHeight][dataWidth];
		Complex[][] GreenData = new Complex[dataHeight][dataWidth];
		Complex[][] BlueData = new Complex[dataHeight][dataWidth];
		
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				RedData[i][j] = Data[i][j][0];
				GreenData[i][j] = Data[i][j][1];
				BlueData[i][j] = Data[i][j][2];
			}
		}
		
		Complex[][] RProData = new Complex[dataHeight][dataWidth];
		Complex[][] GProData = new Complex[dataHeight][dataWidth];
		Complex[][] BProData = new Complex[dataHeight][dataWidth];
		
		for(int u = 0; u < dataHeight; u++) {
			System.out.println(u+ "hhh");
			for(int v = 0; v < dataWidth; v++) {
				RProData[u][v] = IDFT(RedData, u, v);
				GProData[u][v] = IDFT(GreenData, u, v);
				BProData[u][v] = IDFT(BlueData, u, v);
			}
		}
		
		int[][][] Result = new int[dataHeight][dataWidth][4];
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				Result[i][j][0] = 255;
				Result[i][j][1] = Math.abs((int)RProData[i][j].GetR());
				Result[i][j][2] = Math.abs((int)GProData[i][j].GetR());
				Result[i][j][3] = Math.abs((int)BProData[i][j].GetR());
			}
		}
		
		return ProcessPixelData.convertToOneDim(Result, dataWidth, dataHeight);
	}
	
	//限制范围
	private int getClip(int x) {
		return x > 255 ? 255 : x < 0 ? 0 : x;
	}
	
	//预处理中心化
	private int[][][] PreProcess(int[][][] data, int Width, int Height) {
		for(int i = 0; i < Height; i++) {
			for(int j = 0; j < Width; j++) {
				if((i + j)%2 != 0) {
					data[i][j][1] = -data[i][j][1];
					data[i][j][2] = -data[i][j][2];
					data[i][j][3] = -data[i][j][3];
				}
			}
		}
		return data;
	}
	
	//傅里叶变换公式
	private Complex DFT(int[][] Data, int u, int v) {
		int M = Data.length;
		int N = Data[0].length;
		Complex complex = new Complex(0, 0);
		for(int i = 0; i < M; i++) {
			for(int j = 0; j < N; j++) {
				Complex temp = new Complex(0, -2 * Math.PI * (1.0*u*i/M + 1.0*v*j/N));
				complex = complex.PLUS(temp.EXP().MultipleNum(Data[i][j]));
			}
		}
		return complex;
	}
	
	//傅里叶逆变换公式
	private Complex IDFT(Complex[][] Data, int u, int v) {
		int M = Data.length;
		int N = Data[0].length;
		Complex complex = new Complex(0, 0);
		for(int i = 0; i < M; i++) {
			for(int j = 0; j < N; j++) {
				Complex temp = new Complex(0, 2 * Math.PI * (1.0*u*i/M + 1.0*v*j/N));
				complex = complex.PLUS(temp.EXP().Multiple(Data[i][j]));
			}
		}
		complex = complex.MultipleNum(1.0/(double)(M*N));
		return complex;
	}
	
	//获取频谱图
	private int[][] GetFourierImage(Complex[][] Fourier, int width, int height) {
		double max = 0;
		double min = 0;
		double [][] abs = new double[height][width];
		int[][] data = new int[height][width];
		//取模
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				abs[i][j] = Fourier[i][j].GetAbs();
			}
		}
		//log变换
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				abs[i][j] = Math.log(abs[i][j] + 1);
			}
		}
		
		max = abs[0][0];
		min = abs[0][0];
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(abs[i][j] > max) {
					max = abs[i][j];
				}
				if(abs[i][j] < min) {
					min = abs[i][j];
				}
			}
		}
		//量化
		int level = 255;
		double intervel = (max - min)/level;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				for(int k = 0; k < level; k++) {
					if(abs[i][j] >= k * intervel && abs[i][j] <= (k+1) * intervel) {
						abs[i][j] = (k * intervel/ (max - min)) * level;
						data[i][j] = (int)abs[i][j];
						break;
					}
				}
			}
		}
		
		return data;
	}
	
	//快速傅里叶变换接口
	public int[] FFT2d(int[] IniPixelsData, int width, int height) {
		int[][][] input3DData = ProcessPixelData.processOneToThreeDeminsion(IniPixelsData,width,height);
		newWidth = Extend2Power(width);
		newHeight = Extend2Power(height);
		
		//System.out.println(newWidth);
		//System.out.println(newHeight);
		
		int[][][] ProData = new int[newHeight][newWidth][4];
		
		for(int i = 0; i < newHeight; i++) {
			for(int j = 0; j < newWidth; j++) {
				if(i < height && j < width) {
					ProData[i][j][1] = input3DData[i][j][1];
					ProData[i][j][2] = input3DData[i][j][2];
					ProData[i][j][3] = input3DData[i][j][3];
				}
				else {
					ProData[i][j][1] = 0;
					ProData[i][j][2] = 0;
					ProData[i][j][3] = 0;
				}
			}
		}
		
		if(IsShowFFT) {
			ProData = PreProcess(ProData, newWidth, newHeight);
		}

		
		int[][] RedData = new int[newHeight][newWidth];
		int[][] GreenData = new int[newHeight][newWidth];
		int[][] BlueData = new int[newHeight][newWidth];
		for(int i = 0; i < newHeight; i++) {
			for(int j = 0; j < newWidth; j++) {
				RedData[i][j] = ProData[i][j][1];
				GreenData[i][j] = ProData[i][j][2];
				BlueData[i][j] = ProData[i][j][3];
			}
		}
		
		Complex[][] ProRedData = ProcessToFFT(RedData, newWidth, newHeight);
		Complex[][] ProGreenData = ProcessToFFT(GreenData, newWidth, newHeight);
		Complex[][] ProBlueData = ProcessToFFT(BlueData, newWidth, newHeight);
		
		Data = new Complex[newHeight][newWidth][3];
		IniHeight = height;
		IniWidth = width;
		dataWidth = newWidth;
		dataHeight = newHeight;
		for(int i = 0; i < newHeight; i++) {
			for(int j = 0; j < newWidth; j++) {
				Data[i][j][0] = ProRedData[i][j];
				Data[i][j][1] = ProGreenData[i][j];
				Data[i][j][2] = ProBlueData[i][j];
			}
		}
		
		if(IsShowFFT) {
			int[][] ProRedData1 = GetFourierImage(ProRedData, newWidth, newHeight);
			int[][] ProGreenData1 = GetFourierImage(ProGreenData, newWidth, newHeight);
			int[][] ProBlueData1 = GetFourierImage(ProBlueData, newWidth, newHeight);
			
			int[][][] Result = new int[newHeight][newWidth][4];
			for(int i = 0; i < newHeight; i++) {
				for(int j = 0; j < newWidth; j++) {
					Result[i][j][0] = input3DData[0][0][0];
					Result[i][j][1] = ProRedData1[i][j];
					Result[i][j][2] = ProGreenData1[i][j];
					Result[i][j][3] = ProBlueData1[i][j];
				}
			}
			return ProcessPixelData.convertToOneDim(Result, newWidth, newHeight); 
		} else {
			return null;
		}
	}
	
	//快速傅里叶逆变换接口
	public int[] IFFT2d() {
		Complex[][] RedData = new Complex[dataHeight][dataWidth];
		Complex[][] GreenData = new Complex[dataHeight][dataWidth];
		Complex[][] BlueData = new Complex[dataHeight][dataWidth];
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				RedData[i][j] = Data[i][j][0];
				GreenData[i][j] = Data[i][j][1];
				BlueData[i][j] = Data[i][j][2];
			}
		}
		
		RedData = ProcessToIFFT(RedData, dataWidth, dataHeight);
		GreenData = ProcessToIFFT(GreenData, dataWidth, dataHeight);
		BlueData = ProcessToIFFT(BlueData, dataWidth, dataHeight);
		
		int[][] ProRData = new int[dataHeight][dataWidth];
		int[][] ProGData = new int[dataHeight][dataWidth];
		int[][] ProBData = new int[dataHeight][dataWidth];
		
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				ProRData[i][j] = (int)RedData[i][j].GetR();
				ProGData[i][j] = (int)GreenData[i][j].GetR();
				ProBData[i][j] = (int)BlueData[i][j].GetR();
			}
		}
		
		int[][] ProRData1 = new int[IniHeight][IniWidth];
		int[][] ProGData1 = new int[IniHeight][IniWidth];
		int[][] ProBData1 = new int[IniHeight][IniWidth];
		
		for(int i = 0; i < IniHeight; i++) {
			for(int j = 0; j < IniWidth; j++) {
				if((i+j)%2!=0) {
					ProRData1[i][j] = -ProRData[i][j];
					ProGData1[i][j] = -ProGData[i][j];
					ProBData1[i][j] = -ProBData[i][j];
				}
				else {
					ProRData1[i][j] = ProRData[i][j];
					ProGData1[i][j] = ProGData[i][j];
					ProBData1[i][j] = ProBData[i][j];
				}
			}
		}
		
		int[][][] Result = new int[IniHeight][IniWidth][4];
		for(int i = 0; i < IniHeight; i++) {
			for(int j = 0; j < IniWidth; j++) {
				Result[i][j][0] = 255;
				Result[i][j][1] = ProRData1[i][j];
				Result[i][j][2] = ProGData1[i][j];
				Result[i][j][3] = ProBData1[i][j];
			}
		}
		return ProcessPixelData.convertToOneDim(Result, IniWidth, IniHeight);
	}
	
	public int GetNewWidth() {
		return newWidth;
	}
	
	public int GetNewHeight() {
		return newHeight;
	}
	
	public int GetIniWidth() {
		return IniWidth;
	}
	
	public int GetIniHeight() {
		return IniHeight;
	}
	
	private Complex[][] ProcessToFFT(int[][] data, int newWidth, int newHeight) {
		Complex[][] Result = new Complex[newHeight][newWidth];
		Complex[] temp1 = new Complex[newWidth];
		for(int i = 0; i < newHeight; i++) {
			for(int j = 0; j < newWidth; j++) {
				Complex complex = new Complex(data[i][j], 0);
				temp1[j] = complex;
			}
			Result[i] = FFT(temp1);
		}
		
		Complex[] temp2 = new Complex[newHeight];
		for(int i = 0; i < newWidth; i++) {
			for(int j = 0; j < newHeight; j++) {
				Complex complex = Result[j][i];
				temp2[j] = complex;
			}
			temp2 = FFT(temp2);
			for(int k = 0; k < newHeight; k++) {
				Result[k][i] = temp2[k];
			}
		}
		
		return Result;
	}
	
	private Complex[][] ProcessToIFFT(Complex[][] data, int width, int height) {
		Complex[][] Result = new Complex[height][width];
		Complex[] temp1 = new Complex[width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j <width; j++) {
				Complex complex = new Complex(data[i][j].GetR(), data[i][j].GetI());
				temp1[j] = complex;
			}
			Result[i] = IFFT(temp1);
		}
		
		Complex[] temp2 = new Complex[height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				Complex complex = Result[j][i];
				temp2[j] = complex; 
			}
			temp2 = IFFT(temp2);
			for(int k = 0; k < height; k++) {
				Result[k][i] = temp2[k];
			}
		}
		
		return Result;
	}
	
	private Complex[] FFT(Complex[] complexs) {
		int len = complexs.length;
		//System.out.println(len);
		if(len == 1) {
			return complexs;
		}
		if(len % 2 != 0) {
			throw new RuntimeException("len is not a power of 2");
		}
		
		Complex[] even = new Complex[len/2];
		for(int i = 0; i < len/2; i++) {
			even[i] = complexs[2 * i];
		}
		Complex[] qComplexs = FFT(even);
		
		Complex[] odd = new Complex[len/2];
		for(int i = 0; i < len/2; i++) {
			odd[i] = complexs[2 * i + 1];
		}
		Complex[] rComplex = FFT(odd);
		
		Complex[] Result = new Complex[len];
		for(int k = 0; k < len/2; k++) {
			double kth = -2 * k * Math.PI / len;
			Complex wKComplex = new Complex(Math.cos(kth), Math.sin(kth));
			Result[k] = qComplexs[k].PLUS(wKComplex.Multiple(rComplex[k]));
			Result[k+len/2] = qComplexs[k].MINUS(wKComplex.Multiple(rComplex[k]));
		}
		
		return Result;
	}
	
	private Complex[] IFFT(Complex[] complexs) {
		int len = complexs.length;
		Complex[] yComplexs = new Complex[len];
		
		for(int i = 0; i < len; i++) {
			yComplexs[i] = complexs[i].Getconjugate();
		}
		
		yComplexs = FFT(yComplexs);
		
		for(int i = 0; i < len; i++) {
			yComplexs[i] = yComplexs[i].Getconjugate();
		}
		
		for(int i = 0; i < len; i++) {
			yComplexs[i] = yComplexs[i].MultipleNum(1.0/len);
		}
		
		return yComplexs;
	}
	
	private int Extend2Power(int length) {
		if(length == 1) return 1;
		int result = 1;
		while (true) {
			if(length > result && length <= result * 2) {
				return 2*result;
			}
			else {
				result *= 2;
			}
		}
	}
	
	public void InitialData() {
		Data = null;
		IniHeight = 0;
		IniWidth = 0;
		dataHeight = 0;
		dataWidth = 0;
	}
	
	public int[] filterEQ(int size,int[] IniPixelsData, int width, int height) {
		FFT2d(IniPixelsData, width, height);
		Complex[][] filter = new Complex[dataHeight][dataWidth];
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				if(i < size && j < size) {
					filter[i][j] = new Complex(1.0/(size*size), 0);
				}
				else {
					filter[i][j] = new Complex(0, 0);
				}
			}
		}
		
		Complex[] temp1 = new Complex[dataWidth];
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				Complex complex = new Complex(filter[i][j].GetR(), filter[i][j].GetI());
				temp1[j] = complex;
			}
			filter[i] = FFT(temp1);
		}
		
		Complex[] temp2 = new Complex[dataHeight];
		for(int i = 0; i < dataWidth; i++) {
			for(int j = 0; j < dataHeight; j++) {
				Complex complex = new Complex(filter[j][i].GetR(), filter[j][i].GetI());
				temp2[j] = complex;
			}
			temp2 = FFT(temp2);
			for(int k = 0; k < dataHeight; k++) {
				filter[k][i] = temp2[k];
			}
		}
		
		Complex[][] RedData = new Complex[dataHeight][dataWidth];
		Complex[][] GreenData = new Complex[dataHeight][dataWidth];
		Complex[][] BlueData = new Complex[dataHeight][dataWidth];
		
		
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				RedData[i][j] = Data[i][j][0].Multiple(filter[i][j]);
				GreenData[i][j] = Data[i][j][1].Multiple(filter[i][j]);
				BlueData[i][j] = Data[i][j][2].Multiple(filter[i][j]);
			}
		}
		
		Complex[][] ProRedData = new Complex[dataHeight][dataWidth];
		Complex[][] ProGreenData = new Complex[dataHeight][dataWidth];
		Complex[][] ProBlueData = new Complex[dataHeight][dataWidth];
		
		ProRedData = ProcessToIFFT(RedData, dataWidth, dataHeight);
		ProGreenData = ProcessToIFFT(GreenData, dataWidth, dataHeight);
		ProBlueData = ProcessToIFFT(BlueData, dataWidth, dataHeight);
		
		int[][] ProRData = new int[dataHeight][dataWidth];
		int[][] ProGData = new int[dataHeight][dataWidth];
		int[][] ProBData = new int[dataHeight][dataWidth];
		
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				ProRData[i][j] = (int)ProRedData[i][j].GetR();
				ProGData[i][j] = (int)ProGreenData[i][j].GetR();
				ProBData[i][j] = (int)ProBlueData[i][j].GetR();
			}
		}

		int[][][] Result = new int[IniHeight][IniWidth][4];
		for(int i = 0; i < IniHeight; i++) {
			for(int j = 0; j < IniWidth; j++) {
				Result[i][j][0] = 255;
				Result[i][j][1] = ProRData[i][j];
				Result[i][j][2] = ProGData[i][j];
				Result[i][j][3] = ProBData[i][j];
			}
		}
		return ProcessPixelData.convertToOneDim(Result, IniWidth, IniHeight);
	}
	
	public int[] filterLaplacian(int[] IniPixelsData, int width, int height) {
		FFT2d(IniPixelsData, width, height);
		Complex[][] filter = new Complex[dataHeight][dataWidth];
		int[][][] input3DData = ProcessPixelData.processOneToThreeDeminsion(IniPixelsData,width,height);
		
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				if(i < 3 && j < 3) {
					if(i==1&&j==1) {
						filter[i][j] = new Complex(-8, 0);
					}
					else {
						filter[i][j] = new Complex(1, 0);
					}
				}
				else {
					filter[i][j] = new Complex(0, 0);
				}
			}
		}
		
		Complex[] temp1 = new Complex[dataWidth];
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				Complex complex = new Complex(filter[i][j].GetR(), filter[i][j].GetI());
				temp1[j] = complex;
			}
			filter[i] = FFT(temp1);
		}
		
		Complex[] temp2 = new Complex[dataHeight];
		for(int i = 0; i < dataWidth; i++) {
			for(int j = 0; j < dataHeight; j++) {
				Complex complex = new Complex(filter[j][i].GetR(), filter[j][i].GetI());
				temp2[j] = complex;
			}
			temp2 = FFT(temp2);
			for(int k = 0; k < dataHeight; k++) {
				filter[k][i] = temp2[k];
			}
		}
		
		Complex[][] RedData = new Complex[dataHeight][dataWidth];
		Complex[][] GreenData = new Complex[dataHeight][dataWidth];
		Complex[][] BlueData = new Complex[dataHeight][dataWidth];
		
		
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				RedData[i][j] = Data[i][j][0].Multiple(filter[i][j]);
				GreenData[i][j] = Data[i][j][1].Multiple(filter[i][j]);
				BlueData[i][j] = Data[i][j][2].Multiple(filter[i][j]);
			}
		}
		
		Complex[][] ProRedData = new Complex[dataHeight][dataWidth];
		Complex[][] ProGreenData = new Complex[dataHeight][dataWidth];
		Complex[][] ProBlueData = new Complex[dataHeight][dataWidth];
		
		ProRedData = ProcessToIFFT(RedData, dataWidth, dataHeight);
		ProGreenData = ProcessToIFFT(GreenData, dataWidth, dataHeight);
		ProBlueData = ProcessToIFFT(BlueData, dataWidth, dataHeight);
		
		int[][] ProRData = new int[dataHeight][dataWidth];
		int[][] ProGData = new int[dataHeight][dataWidth];
		int[][] ProBData = new int[dataHeight][dataWidth];
		
		for(int i = 0; i < dataHeight; i++) {
			for(int j = 0; j < dataWidth; j++) {
				ProRData[i][j] = (int)ProRedData[i][j].GetR();
				ProGData[i][j] = (int)ProGreenData[i][j].GetR();
				ProBData[i][j] = (int)ProBlueData[i][j].GetR();
			}
		}
		
		int[][][] Result = new int[IniHeight][IniWidth][4];
		for(int i = 0; i < IniHeight; i++) {
			for(int j = 0; j < IniWidth; j++) {
				Result[i][j][0] = 255;
				Result[i][j][1] = ProRData[i][j];
				//Result[i][j][1] = getClip(Result[i][j][1]);
				Result[i][j][2] = ProGData[i][j];
				//Result[i][j][2] = getClip(Result[i][j][2]);
				Result[i][j][3] = ProBData[i][j];
				//Result[i][j][3] = getClip(Result[i][j][3]);
			}
		}
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				input3DData[i][j][1] -= Result[i][j][1];
				input3DData[i][j][1] = getClip(input3DData[i][j][1]);
				input3DData[i][j][2] -= Result[i][j][2];
				input3DData[i][j][2] = getClip(input3DData[i][j][2]);
				input3DData[i][j][3] -= Result[i][j][3]; 
				input3DData[i][j][3] = getClip(input3DData[i][j][3]);
			}
		}
		
		return ProcessPixelData.convertToOneDim(input3DData, IniWidth, IniHeight);
	}
	
	public void SetShowFFT(boolean b) {
		IsShowFFT = b;
	}
	
	public void SetShowDFT(boolean b) {
		IsShowDFT = b;
	}
}
