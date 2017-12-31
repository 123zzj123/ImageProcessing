
public class Scale {
	//拷贝插值，最近邻插值
	public int[] CopyScale(int []IniPixelsData, int SrcWidth, int SrcHeight, int DesWidth, int DesHeight) {
		int[][][] input3DData = ProcessPixelData.processOneToThreeDeminsion(IniPixelsData,SrcWidth,SrcHeight);
		int[][][] outputThreeDeminsionData = new int[DesHeight][DesWidth][4];
		
		float rowRatio = ((float)SrcHeight) / ((float)DesHeight);
		float colRatio = ((float)SrcWidth) / ((float)DesWidth);
		for(int i = 0 ; i < DesHeight; i++) {
			int r = (int)(i * rowRatio);
			for(int j = 0; j < DesWidth; j++) {
				int c = (int)(j * colRatio);
				for(int k = 0; k < 4; k++) {
					outputThreeDeminsionData[i][j][k] = input3DData[r][c][k];
				}
			}
		}
		
		return ProcessPixelData.convertToOneDim(outputThreeDeminsionData, DesWidth, DesHeight);
	}
	
	//双线性插值
	public int[] BilineInterpolationScale(int []IniPixelsData, int SrcWidth, int SrcHeight, int DesWidth, int DesHeight) {
		int[][][] input3DData = ProcessPixelData.processOneToThreeDeminsion(IniPixelsData,SrcWidth,SrcHeight);
		int[][][] outputThreeDeminsionData = new int[DesHeight][DesWidth][4];

		float rowRatio = ((float)SrcHeight) / ((float)DesHeight);
		float colRatio = ((float)SrcWidth) / ((float)DesWidth);
		for(int i = 0 ; i < DesHeight; i++) {
			int r = (int)(i * rowRatio);
			double t = i * rowRatio - r;
			for(int j = 0; j < DesWidth; j++) {
				int c = (int)(j * colRatio);
				double u = j * colRatio - c;
				
				double coffiecent1 = (1.0d - t) * (1.0d - u);
				double coffiecent2 = (t) * (1.0d - u);
				double coffiecent3 = t * u;
				double coffiecent4 = (1.0d - t) * u;
				
				for(int k = 0; k < 4; k++) {
					outputThreeDeminsionData[i][j][k] = (int)(
							coffiecent1 * input3DData[getClip((int)r, SrcHeight - 1, 0)][getClip((int)c, SrcWidth - 1, 0)][k] +
							coffiecent2 * input3DData[getClip((int)(r + 1), SrcHeight - 1, 0)][getClip((int)c, SrcWidth - 1, 0)][k] +
							coffiecent3 * input3DData[getClip((int)(r + 1), SrcHeight - 1, 0)][getClip((int)(c + 1),SrcWidth - 1, 0)][k] +
							coffiecent4 * input3DData[getClip((int)r, SrcHeight - 1, 0)][getClip((int)(c + 1), SrcWidth - 1, 0)][k]
						);
				}
			}
		}
		
		return ProcessPixelData.convertToOneDim(outputThreeDeminsionData, DesWidth, DesHeight);
	}
	
	//双三次插值
	public int[] bicubicScale(int []IniPixelsData, int SrcWidth, int SrcHeight, int DesWidth, int DesHeight) {
		int[][][] input3DData = ProcessPixelData.processOneToThreeDeminsion(IniPixelsData,SrcWidth,SrcHeight);
		int[][][] outputData = new int[DesHeight][DesWidth][4];
		
		float rowRatio = ((float)SrcHeight) / ((float)DesHeight);
		float colRatio = ((float)SrcWidth) / ((float)DesWidth);
		
		for(int i = 0; i < DesHeight; i++) {
			int r = (int)(i * rowRatio);
			double u = i * rowRatio - r;
			for(int j = 0; j < DesWidth; j++) {
				int c = (int)(j * colRatio);
				double v = j * colRatio - c;
				double[] Weight_x = new double[4];
				double[] Weight_y = new double[4];
				Weight_x = IniWeight(Weight_x, u);
				Weight_y = IniWeight(Weight_y, v);
				for(int k = 0; k < 4; k++) {
					double temp = 0;
					for(int r1 = 0; r1 <= 3; r1++) {
						for(int r2 = 0; r2 <= 3; r2++) {
							int row = getClip(r+r1-1, SrcHeight-1, 0);
							int col = getClip(c+r2-1, SrcWidth-1, 0);
							temp += input3DData[row][col][k]*Weight_x[r1]*Weight_y[r2];
						}
					}
					outputData[i][j][k] = getClip((int) temp, 255, 0);
				}
			}
		}
		
		return ProcessPixelData.convertToOneDim(outputData, DesWidth, DesHeight);
	}
	
	private int getClip(int x, int max, int min) {
		return x > max ? max : x < min ? min : x;
	}
	
	private double[] IniWeight(double[] Weight, double ratio) {
		Weight[0] = bicubicBase(1 + ratio);
		Weight[1] = bicubicBase(ratio);
		Weight[2] = bicubicBase(1 - ratio);
		Weight[3] = bicubicBase(2 - ratio);
		return Weight;
	}
	
	private double bicubicBase(double ratio) {
		double r = Math.abs(ratio);
		if(r >= 0 && r <= 1) {
			return 1.5*Math.pow(r, 3) - 2.5*Math.pow(r, 2) + 1;
		}
		else if (r > 1 && r <= 2) {
			return -0.5*Math.pow(r, 3) + 2.5*Math.pow(r, 2) - 4*r + 2;
		}
		else {
			return 0;
		}
		
	}
}
