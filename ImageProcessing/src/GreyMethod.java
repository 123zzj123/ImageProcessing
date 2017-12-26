
public class GreyMethod {
	public int[] Method1(int[] IniPixelsData, int width, int height) {
		int[][][] input3DData = ProcessPixelData.processOneToThreeDeminsion(IniPixelsData,width,height);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int r = input3DData[i][j][1];
				int g = input3DData[i][j][2];
				int b = input3DData[i][j][3];
				int grey = (int)(0.3 * r + 0.59 * g + 0.11 * b);
				input3DData[i][j][1] = grey;
				input3DData[i][j][2] = grey;
				input3DData[i][j][3] = grey;
			}
		}
		return ProcessPixelData.convertToOneDim(input3DData, width, height);
	}
}
