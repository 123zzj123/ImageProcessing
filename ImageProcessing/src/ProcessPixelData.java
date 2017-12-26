
public class ProcessPixelData {
	public static int[][][] processOneToThreeDeminsion(int[] data, int imgWidth, int imgHeight) {
		int [][][] tempData = new int[imgHeight][imgWidth][4];
		for(int row = 0; row < imgHeight; row++) {
			for(int col = 0; col < imgWidth; col++) {
				tempData[row][col][0] = (data[imgWidth * row + col]>>24) & 0xFF;//alpha
				tempData[row][col][1] = (data[imgWidth * row + col]>>16) & 0xFF;//red
				tempData[row][col][2] = (data[imgWidth * row + col]>>8) & 0xFF;//green
				tempData[row][col][3] = (data[imgWidth * row + col]) & 0xFF;//blue
			}
		}
		return tempData;
	}
	
	public static int[] convertToOneDim(int[][][] data, int imgWidth, int imgHeight) {
		int[] tempData = new int[imgWidth * imgHeight];
		for(int row = 0; row < imgHeight; row++) {
			for(int col = 0; col < imgWidth; col++) {
				tempData[imgWidth * row + col] = ((data[row][col][0]<<24) & 0xFF000000)
						| ((data[row][col][1]<<16) & 0x00FF0000)
						| ((data[row][col][2]<<8) & 0x0000FF00)
						| ((data[row][col][3]) & 0x000000FF);
			}
		}
		return tempData;
	}
}
