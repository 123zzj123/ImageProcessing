import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class PlaneHistogram {
	private final int HistogramWidth = 6;
	private final int HistogramPitch = 3;
	private float scaling = 1f;
	private int maxStrWidth = 0;
	
	public BufferedImage paintPlaneHistogram(String title, int[] v) {
		int width = v.length * HistogramWidth + v.length * HistogramPitch + 50;
		int height = 255;
		scaling = calculateScale(v, height);
		
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, width, height);
		FontMetrics metrics = null;
		
		graphics.setFont(new Font(null, Font.BOLD, 30));
		graphics.setColor(Color.RED);
		
		graphics.drawString(title, (bufferedImage.getWidth() - graphics.getFontMetrics().stringWidth(title)) >> 1, 30);
		
		graphics.setColor(Color.black);
		
		graphics.drawLine(10, 0, 2, height - 15);
		graphics.drawLine(10, height - 15, width, height - 15);
		
		for(int i = 0; i < v.length; i++) {
			int x = 1 + i * (HistogramWidth + HistogramPitch);
			int y = height - 16 - (int)(v[i] * scaling);
			
			graphics.drawRect(x, y, HistogramWidth, (int)(v[i] * scaling));
			graphics.fillRect(x, y, HistogramWidth, (int)(v[i] * scaling));
		}
		
		return bufferedImage;
	}
	
	public float calculateScale(int[] v , int h){
        float scale = 1f;
        int max = Integer.MIN_VALUE;
        for(int i=0 , len=v.length ; i < len ;++i){
            if(v[i]>h && v[i]>max){
                max=v[i];
            }
        }
        if(max > h){
            scale=((int)(h*1.0f/max*1000))*1.0f/1000;
        }
        return scale;
    }
}
