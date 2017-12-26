
public class GetChanelPic {
	
	public int[] GetRedChanel(int[] IniPixelsData, int width, int height) {
		int[][][] input3DData = ProcessPixelData.processOneToThreeDeminsion(IniPixelsData,width,height);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				input3DData[i][j][2] = 0;
				input3DData[i][j][3] = 0;
			}
		}
		return ProcessPixelData.convertToOneDim(input3DData, width, height);
	}
	
	public int[] GetGreenChanel(int[] IniPixelsData, int width, int height) {
		int[][][] input3DData = ProcessPixelData.processOneToThreeDeminsion(IniPixelsData,width,height);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				input3DData[i][j][1] = 0;
				input3DData[i][j][3] = 0;
			}
		}
		return ProcessPixelData.convertToOneDim(input3DData, width, height);
	}
	
	public int[] GetBlueChanel(int[] IniPixelsData, int width, int height) {
		int[][][] input3DData = ProcessPixelData.processOneToThreeDeminsion(IniPixelsData,width,height);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				input3DData[i][j][1] = 0;
				input3DData[i][j][2] = 0;
			}
		}
		return ProcessPixelData.convertToOneDim(input3DData, width, height);
	}
}
