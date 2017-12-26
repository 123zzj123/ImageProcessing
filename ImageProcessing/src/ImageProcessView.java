import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.xml.namespace.QName;

public class ImageProcessView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BufferedImage Image;
	private MyJpanel panel;
	private JFileChooser chooser;
	private GetChanelPic getChanelPic;
	private GreyMethod greyMethod;
	private SpaceProcess spaceProcess;
	private PlaneHistogram planeHistogram;
	private FrequencyPro frequencyPro;
	private ImgCompress imgCompress;
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;
	private String imagePath;
	private String fileName;
	private boolean ToFFT = true;
	private boolean ToDFT = true;
	
	public ImageProcessView() {
		setTitle("Image Process");
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		//setLayout(new BorderLayout());
		setLayout(null);
		
		addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				if(Image != null)
				panel.setImage(panel.getImage());
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		getChanelPic = new GetChanelPic();
		greyMethod = new GreyMethod();
		spaceProcess = new SpaceProcess();
		planeHistogram = new PlaneHistogram();
		frequencyPro = new FrequencyPro();
		imgCompress = new ImgCompress();
		
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));
		
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		addFileMenu(menubar);
		
		panel = new MyJpanel();
		panel.setSize(400,400);
		add(panel);
	}
	
	public void addFileMenu(JMenuBar menubar) {
		String menustr[] = {"文件","提取","缩放","灰度化","图像空间域处理","图像变换域处理","图像压缩"};
		JMenu menu[] = new JMenu[menustr.length];
		for(int i = 0; i < menustr.length; i++) {
			menu[i] = new JMenu(menustr[i]);
			menubar.add(menu[i]);
		}
		
		addFileItem(menu[0]);
		addChanelItem(menu[1]);
		addScaleItem(menu[2]);
		addGreyItem(menu[3]);
		addSpaceProItem(menu[4]);
		addFreQProItem(menu[5]);
		addCompressItem(menu[6]);
	}
	
	public void addFileItem(JMenu menu) {
		JMenuItem menuitem_open = new JMenuItem("打开");
		JMenuItem menuitem_save = new JMenuItem("保存");
		JMenuItem menuItem_back = new JMenuItem("恢复原图");
		JMenuItem menuitem_exit = new JMenuItem("退出");
		menu.add(menuitem_open);
		menu.add(menuitem_save);
		menu.add(menuItem_back);
		menu.addSeparator();//添加分割线
		menu.add(menuitem_exit);
		menuitem_open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = chooser.showOpenDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
					String name = chooser.getSelectedFile().getPath();
					imagePath = name.substring(0, name.lastIndexOf("\\") + 1);
					fileName = name.substring(name.lastIndexOf("\\"));
					File file = new File(name);
					try {
						Image = ImageIO.read(file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					panel.setImage(Image);
					panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());
					panel.paint(getGraphics());
					repaint();
				}
			}
		});
		menuitem_save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = chooser.showSaveDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
					String name = chooser.getSelectedFile().getPath();
					if(name.length() <= 4 ||!name.substring(name.length()-4).equals(new String(".png"))) {
						name += ".png";
					}
					File file = new File(name);
					try {
						ImageIO.write(panel.getImage(), "png", file);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		menuItem_back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel.setImage(Image);
				panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());
				panel.paint(getGraphics());
				repaint();
			}
		});
		menuitem_exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}
	
	public void addChanelItem(JMenu menu) {
		JMenuItem menuitem_red = new JMenuItem("红");
		JMenuItem menuitem_green = new JMenuItem("绿");
		JMenuItem menuitem_blue = new JMenuItem("蓝");
		menu.add(menuitem_red);
		menu.add(menuitem_green);
		menu.add(menuitem_blue);
		menuitem_red.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(Image != null) {
					int[] result = getChanelPic.GetRedChanel(GetImageRGBArray(Image), Image.getWidth(), Image.getHeight());
					ShowImage(result, Image.getWidth(), Image.getHeight());
				}
			}
		});
		menuitem_green.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(Image != null) {
					int[] result = getChanelPic.GetGreenChanel(GetImageRGBArray(Image), Image.getWidth(), Image.getHeight());
					ShowImage(result, Image.getWidth(), Image.getHeight());
				}
			}
		});
		menuitem_blue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(Image != null) {
					int[] result = getChanelPic.GetBlueChanel(GetImageRGBArray(Image), Image.getWidth(), Image.getHeight());
					ShowImage(result, Image.getWidth(), Image.getHeight());
				}
			}
		});
	}

	public void addScaleItem(JMenu menu) {
		JMenuItem menuItem_copy = new JMenuItem("拷贝缩放");
		JMenuItem menuItem_BL = new JMenuItem("双线性插值");
		JMenuItem menuItem_bicubic = new JMenuItem("双三次插值");
		menu.add(menuItem_copy);
		menu.add(menuItem_BL);
		menu.add(menuItem_bicubic);
		menuItem_copy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog temp = new MyJDialog(GetImageRGBArray(panel.getImage()),panel.getImage().getWidth(),panel.getImage().getHeight());
				temp.setVisible(true);
				temp.SetID(1);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int[] result = temp.GetResult();
						if(result != null)
						ShowImage(result, temp.GetDesWidth(), temp.GetDesHeight());
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		menuItem_BL.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog temp = new MyJDialog(GetImageRGBArray(panel.getImage()),panel.getImage().getWidth(),panel.getImage().getHeight());
				temp.setVisible(true);
				temp.SetID(2);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int[] result = temp.GetResult();
						if(result != null)
						ShowImage(result, temp.GetDesWidth(), temp.GetDesHeight());
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		menuItem_bicubic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog temp = new MyJDialog(GetImageRGBArray(panel.getImage()),panel.getImage().getWidth(),panel.getImage().getHeight());
				temp.setVisible(true);
				temp.SetID(3);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int[] result = temp.GetResult();
						if(result != null)
						ShowImage(result, temp.GetDesWidth(), temp.GetDesHeight());
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	}
	
	public void addGreyItem(JMenu menu) {
		JMenuItem menuItem_1 = new JMenuItem("方法1");
		JMenuItem menuItem_2 = new JMenuItem("方法2");
		menu.add(menuItem_1);
		menu.add(menuItem_2);
		menuItem_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[] result = null;
				BufferedImage img = panel.getImage();
				if(img != null) {
					result = GetImageRGBArray(panel.getImage());
					int w = img.getWidth();
					int h = img.getHeight();
					ShowGreyImage(result, w, h);
				}
			}
		});
		menuItem_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					int[] result = greyMethod.Method1(GetImageRGBArray(img), img.getWidth(), img.getHeight());
					ShowImage(result, img.getWidth(), img.getHeight());
				}
			}
		});
	}
	
	public void addSpaceProItem(JMenu menu) {
		JMenu FilterOpe = new JMenu("滤波操作");
		JMenu HistogramOpe = new JMenu("直方图均衡化操作");
		JMenu DitherOpe = new JMenu("抖动操作");
		JMenu DItectOpe = new JMenu("边缘检测");
		JMenuItem menuItem_GS = new JMenuItem("高斯模糊");
		JMenu NoiseOpe = new JMenu("添加噪声");
		menu.add(FilterOpe);
		addFilterItem(FilterOpe);
		menu.add(HistogramOpe);
		addHistogramItem(HistogramOpe);
		menu.add(DitherOpe);
		addDitherItem(DitherOpe);
		menu.add(DItectOpe);
		addDItectItem(DItectOpe);
		menu.add(menuItem_GS);
		menu.add(NoiseOpe);
		addNoiseItem(NoiseOpe);

		menuItem_GS.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog3 temp = new MyJDialog3("请输入高斯模糊半径");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						double Size = temp.GetSize();
						if(Size > 0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.GaussianBlur(GetImageRGBArray(img), img.getWidth(), img.getHeight(), 3, Size);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
	}
	
	private void addFilterItem(JMenu menu) {
		JMenuItem menuItem_AF = new JMenuItem("均值滤波");
		JMenuItem menuItem_HMF = new JMenuItem("调和滤波");
		JMenuItem menuItem_Max = new JMenuItem("最大值滤波");
		JMenuItem menuItem_MF = new JMenuItem("中值滤波");
		JMenuItem menuItem_Min = new JMenuItem("最小值滤波");
		JMenuItem menuItem_GMF = new JMenuItem("几何均值滤波");
		JMenuItem menuItem_LF = new JMenuItem("拉普拉斯滤波");
		JMenuItem menuItem_HBF = new JMenuItem("高提升滤波");
		JMenuItem menuItem_CHM = new JMenuItem("逆谐波滤波");
		menu.add(menuItem_AF);
		menu.add(menuItem_HMF);
		menu.add(menuItem_Max);
		menu.add(menuItem_MF);
		menu.add(menuItem_Min);
		menu.add(menuItem_GMF);
		menu.add(menuItem_LF);
		menu.add(menuItem_HBF);
		menu.add(menuItem_CHM);
		menuItem_AF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog2 temp = new MyJDialog2("请输入方形均值滤波器大小");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int Size = temp.GetSize();
						if(Size > 0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.filterEQ(GetImageRGBArray(img), img.getWidth(), img.getHeight(), Size);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		menuItem_HMF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog2 temp = new MyJDialog2("请输入方形调和均值滤波器大小");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int Size = temp.GetSize();
						if(Size > 0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.filterHM(GetImageRGBArray(img), img.getWidth(), img.getHeight(), Size);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		menuItem_MF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog2 temp = new MyJDialog2("请输入方形中值滤波器大小");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int Size = temp.GetSize();
						if(Size > 0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.filterMedF(GetImageRGBArray(img), img.getWidth(), img.getHeight(), Size);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		menuItem_Max.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog2 temp = new MyJDialog2("请输入方形最大值滤波器大小");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int Size = temp.GetSize();
						if(Size > 0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.filterMax(GetImageRGBArray(img), img.getWidth(), img.getHeight(), Size);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		menuItem_Min.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog2 temp = new MyJDialog2("请输入方形最小值滤波器大小");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int Size = temp.GetSize();
						if(Size > 0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.filterMin(GetImageRGBArray(img), img.getWidth(), img.getHeight(), Size);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		menuItem_GMF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog2 temp = new MyJDialog2("请输入方形几何均值滤波器大小");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int Size = temp.GetSize();
						if(Size > 0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.filterGMF(GetImageRGBArray(img), img.getWidth(), img.getHeight(), Size);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		menuItem_LF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					int[] result = spaceProcess.filterLaplacian(GetImageRGBArray(img), img.getWidth(), img.getHeight());
					ShowImage(result, img.getWidth(), img.getHeight());
				}
			}
		});
		menuItem_HBF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog2 temp = new MyJDialog2("请输入方形均值滤波器大小");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int Size = temp.GetSize();
						if(Size > 0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.highBoostfiltering(GetImageRGBArray(img), img.getWidth(), img.getHeight(), Size);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		menuItem_CHM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog4 temp = new MyJDialog4("滤波器大小","参数Q");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int Size = (int)temp.GetSize();
						double Q = temp.GetQ();
						if(Size > 0 && Q != 0.0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.filterCHM(GetImageRGBArray(img), img.getWidth(), img.getHeight(), Size, Q);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	}
	
	private void addHistogramItem(JMenu menu) {
		JMenuItem menuItem_HE = new JMenuItem("灰度直方图均衡化");
		JMenuItem menuItem_CHE = new JMenuItem("彩色直方图均衡化");
		JMenuItem menuItem_CAHE = new JMenuItem("彩色均值直方图均衡化");
		JMenuItem menuItem_Itensity = new JMenuItem("强度直方图均衡化");
		menu.add(menuItem_HE);
		menu.add(menuItem_CHE);
		menu.add(menuItem_CAHE);
		menu.add(menuItem_Itensity);
		menuItem_HE.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					MyJDialog1 temp = new MyJDialog1();
					int[] result = spaceProcess.HistogramEQ(GetImageRGBArray(img), img.getWidth(), img.getHeight());
					temp.SetIniImage(img);
					temp.SetIniHistogram(planeHistogram.paintPlaneHistogram("原图", spaceProcess.GetIniPicCount()));
					ShowImage(result, img.getWidth(), img.getHeight());
					temp.SetProImage(panel.getImage());
					temp.SetProHistogram(planeHistogram.paintPlaneHistogram("均衡化", spaceProcess.GetProcessCount()));
					temp.setVisible(true);
					temp.Show();
				}
			}
		});
		menuItem_CHE.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					int[] result = spaceProcess.ColorHistogramEQ(GetImageRGBArray(img), img.getWidth(), img.getHeight());
					ShowImage(result, img.getWidth(), img.getHeight());
				}
			}
		});
		menuItem_CAHE.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					int[] result = spaceProcess.ColorAveHistogramEQ(GetImageRGBArray(img), img.getWidth(), img.getHeight());
					ShowImage(result, img.getWidth(), img.getHeight());
				}
			}
		});
		menuItem_Itensity.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					int[] result = spaceProcess.ItensityHistogramEQ(GetImageRGBArray(img), img.getWidth(), img.getHeight());
					ShowImage(result, img.getWidth(), img.getHeight());
				}
			}
		});
	}
	
	private void addDitherItem(JMenu menu) {
		JMenuItem menuItem_GDI1 = new JMenuItem("灰度色彩抖动1");
		JMenuItem menuItem_CDI1 = new JMenuItem("Floyd–Steinberg色彩抖动");
		JMenuItem menuItem_CDI2 = new JMenuItem("bayer色彩抖动");
		menu.add(menuItem_GDI1);
		menu.add(menuItem_CDI1);
		menu.add(menuItem_CDI2);
		menuItem_GDI1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					int[] result = spaceProcess.GreyDithering(GetImageRGBArray(img), img.getWidth(), img.getHeight());
					ShowImage(result, img.getWidth(), img.getHeight());
				}
			}
		});
		menuItem_CDI1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog2 temp = new MyJDialog2("请输入抖动颜色级别");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int Size = temp.GetSize();
						if(Size > 0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.FSDithering(GetImageRGBArray(img), img.getWidth(), img.getHeight(), Size);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		menuItem_CDI2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					int[] result = spaceProcess.bayerDithering(GetImageRGBArray(img), img.getWidth(), img.getHeight(), 2);
					ShowImage(result, img.getWidth(), img.getHeight());
				}
			}
		});
	}
	
	private void addDItectItem(JMenu menu) {
		JMenuItem menuItem_HD = new JMenuItem("sobel水平边缘检测");
		JMenuItem menuItem_VD = new JMenuItem("sobel垂直边缘检测");
		JMenuItem menuItem_Canny = new JMenuItem("Canny边缘检测算法");
		menu.add(menuItem_HD);
		menu.add(menuItem_VD);
		menu.add(menuItem_Canny);
		menuItem_HD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					int[] result = spaceProcess.HorizonDetect(GetImageRGBArray(img), img.getWidth(), img.getHeight());
					ShowImage(result, img.getWidth(), img.getHeight());
				}
			}
		});
		menuItem_VD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					int[] result = spaceProcess.VerticalDetect(GetImageRGBArray(img), img.getWidth(), img.getHeight());
					ShowImage(result, img.getWidth(), img.getHeight());
				}
			}
		});
		menuItem_Canny.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					int[] result = spaceProcess.CannyDetect(GetImageRGBArray(img), img.getWidth(), img.getHeight());
					ShowImage(result, img.getWidth(), img.getHeight());
				}
			}
		});
	}
	
	private void addNoiseItem(JMenu menu) {
		JMenuItem GSNoise = new JMenuItem("高斯噪声");
		JMenuItem ImpluseNoise = new JMenuItem("椒盐噪声");
		menu.add(GSNoise);
		menu.add(ImpluseNoise);
		GSNoise.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog4 temp = new MyJDialog4("平均值","标准差");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						double mean = temp.GetSize();
						double standardVariance = temp.GetQ();
						if(standardVariance > 0.0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.ProduceGSNoise(GetImageRGBArray(img), img.getWidth(), img.getHeight(), mean, standardVariance);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		ImpluseNoise.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog4 temp = new MyJDialog4("椒比例","盐比例");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						double PRatio = temp.GetSize();
						double SRatio = temp.GetQ();
						if(SRatio < 1.0 && PRatio < 1.0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								int[] result = spaceProcess.ProduceImpluseNoise(GetImageRGBArray(img), img.getWidth(), img.getHeight(), PRatio, SRatio);
								ShowImage(result, img.getWidth(), img.getHeight());
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	}
	
	public void addFreQProItem(JMenu menu) {
		JMenuItem menuItem_DFT = new JMenuItem("DFT");
		JMenuItem menuItem_IDFT = new JMenuItem("IDFT");
		JMenuItem menuItem_FFT = new JMenuItem("FFT");
		JMenuItem menuItem_IFFT = new JMenuItem("IFFT");
		JMenuItem menuItem_FreAF = new JMenuItem("均值滤波");
		JMenuItem menuItem_FreLF = new JMenuItem("拉普拉斯滤波");
		menu.add(menuItem_DFT);
		menu.add(menuItem_IDFT);
		menu.add(menuItem_FFT);
		menu.add(menuItem_IFFT);
		menu.add(menuItem_FreAF);
		menu.add(menuItem_FreLF);
		menuItem_DFT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(ToDFT) {
					BufferedImage img = panel.getImage();
					if(img != null) {
						frequencyPro.SetShowDFT(true);
						int[] result = frequencyPro.DFT2d(GetImageRGBArray(img), img.getWidth(), img.getHeight());
						ShowImage(result, img.getWidth(), img.getHeight());
					}
					ToDFT = false;
				}
			}
		});
		
		menuItem_IDFT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(ToDFT) {
					BufferedImage img = panel.getImage();
					if(img != null) {
						frequencyPro.SetShowDFT(false);
						int[] result = frequencyPro.IDFT2d(GetImageRGBArray(Image),Image.getWidth(),Image.getHeight());
						ShowImage(result, img.getWidth(), img.getHeight());
					}
					ToDFT = true;
				}
			}
		});
		
		menuItem_FFT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(ToFFT) {
					BufferedImage img = panel.getImage();
					if(img != null) {
						frequencyPro.SetShowFFT(true);
						int[] result = frequencyPro.FFT2d(GetImageRGBArray(img), img.getWidth(), img.getHeight());
						int w = frequencyPro.GetNewWidth();
						int h = frequencyPro.GetNewHeight();
						ShowImage(result, w, h);
						ToFFT = false;
					}
				}
			}
		});
		
		menuItem_IFFT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!ToFFT) {
					int[] result = frequencyPro.IFFT2d();
					int w = frequencyPro.GetIniWidth();
					int h = frequencyPro.GetIniHeight();
					ShowImage(result, w, h);
					frequencyPro.InitialData();
					ToFFT = true;
				}
			}
		});
		
		menuItem_FreAF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MyJDialog2 temp = new MyJDialog2("请输入方形均值滤波器大小");
				temp.setVisible(true);
				temp.addWindowListener(new WindowListener() {
					
					@Override
					public void windowOpened(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub
						int Size = temp.GetSize();
						if(Size > 0) {
							BufferedImage img = panel.getImage();
							if(img != null) {
								frequencyPro.SetShowFFT(false);
								int[] result = frequencyPro.filterEQ(Size,GetImageRGBArray(img),Image.getWidth(),Image.getHeight());
								int w = frequencyPro.GetIniWidth();
								int h = frequencyPro.GetIniHeight();
								ShowImage(result, w, h);
								frequencyPro.InitialData();
								ToFFT = true;
							}
						}
					}
					
					@Override
					public void windowClosing(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowClosed(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		menuItem_FreLF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					frequencyPro.SetShowFFT(false);
					int[] result = frequencyPro.filterLaplacian(GetImageRGBArray(Image),Image.getWidth(),Image.getHeight());
					int w = frequencyPro.GetIniWidth();
					int h = frequencyPro.GetIniHeight();
					ShowImage(result, w, h);
					frequencyPro.InitialData();
					ToFFT = true;
				}
			}
		});
	}
	
	public void addCompressItem(JMenu menu) {
		JMenuItem HuffmanCompress = new JMenuItem("哈夫曼编码压缩");
		JMenuItem HuffmanDecode = new JMenuItem("哈夫曼编码解码");
		JMenuItem ComputePSNR = new JMenuItem("计算PSNR");
		JMenuItem ComputeSSIM = new JMenuItem("计算SSIM");
		menu.add(HuffmanCompress);
		menu.add(HuffmanDecode);
		menu.add(ComputePSNR);
		menu.add(ComputeSSIM);
		HuffmanCompress.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					imgCompress.HuffmanComPress(GetImageRGBArray(img), img.getWidth(), img.getHeight());
				}
			}
		});
		HuffmanDecode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BufferedImage img = panel.getImage();
				if(img != null) {
					MyJDialog5 temp = new MyJDialog5();
					temp.SetIniImage(img);
					int[] result = imgCompress.HuffmanDecode(img.getWidth(), img.getHeight());
					ShowImage(result, img.getWidth(), img.getHeight());
					imgCompress.ComputeSNR(GetImageRGBArray(img), GetImageRGBArray(panel.getImage()), img.getWidth(), img.getHeight());
					temp.SetProImage(panel.getImage());
					temp.SetSNR(imgCompress.GetSNR());
					temp.SetRate(imgCompress.GetRate());
					temp.setVisible(true);
					temp.Show();
				}
			}
		});
		ComputePSNR.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chooser.setMultiSelectionEnabled(true);
				int result = chooser.showOpenDialog(null);
				BufferedImage OriPic = null, AnotherPic = null;
				if(result == JFileChooser.APPROVE_OPTION) {
					File[] files = chooser.getSelectedFiles();
					if(files.length != 2) {
						return;
					}
					else {
						String name1 = files[0].getPath();
						String name2 = files[1].getPath();
						File file1 = new File(name1);
						File file2 = new File(name2);
						try {
							OriPic = ImageIO.read(file1);
							AnotherPic = ImageIO.read(file2);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						int W1 = OriPic.getWidth();
						int H1 = OriPic.getHeight();
						int W2 = AnotherPic.getWidth();
						int H2 = AnotherPic.getHeight();
						if(W1 == W2 && H1 == H2) {
							double PSNR = imgCompress.ComputePSNR(GetImageRGBArray(OriPic), GetImageRGBArray(AnotherPic), W1, H1);
							MyJDialog6 temp = new MyJDialog6();
							temp.SetIniImage(OriPic);
							temp.SetAnoImage(AnotherPic);
							temp.SetLabel("PSNR:" + PSNR);
							temp.setVisible(true);
							temp.Show();
						}
					}
				}
			}
		});
		ComputeSSIM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chooser.setMultiSelectionEnabled(true);
				int result = chooser.showOpenDialog(null);
				BufferedImage OriPic = null, AnotherPic = null;
				if(result == JFileChooser.APPROVE_OPTION) {
					File[] files = chooser.getSelectedFiles();
					if(files.length != 2) {
						return;
					}
					else {
						String name1 = files[0].getPath();
						String name2 = files[1].getPath();
						File file1 = new File(name1);
						File file2 = new File(name2);
						try {
							OriPic = ImageIO.read(file1);
							AnotherPic = ImageIO.read(file2);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						int W1 = OriPic.getWidth();
						int H1 = OriPic.getHeight();
						int W2 = AnotherPic.getWidth();
						int H2 = AnotherPic.getHeight();
						if(W1 == W2 && H1 == H2) {
							double SSIM = imgCompress.ComputeSSIM(GetImageRGBArray(OriPic), GetImageRGBArray(AnotherPic), W1, H1);
							MyJDialog6 temp = new MyJDialog6();
							temp.SetIniImage(OriPic);
							temp.SetAnoImage(AnotherPic);
							temp.SetLabel("SSIM:" + SSIM);
							temp.setVisible(true);
							temp.Show();
						}
					}
				}
			}
		});
	}
	
	public int[] GetImageRGBArray(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		int[] RGBArray = new int[width * height];
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				RGBArray[width * i + j] = img.getRGB(j, i);
			}
		}
		return RGBArray;
	}
	
	public void ShowImage(int[] result, int width, int height) {
		BufferedImage TempImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				TempImage.setRGB(j, i, result[width * i + j]);
			}
		}
		panel.setImage(TempImage);
		panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());
		panel.paint(getGraphics());
		repaint();
	}
	
	public void ShowGreyImage(int[] result, int width, int height) {
		BufferedImage TempImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				TempImage.setRGB(j, i, result[i * width + j]);
			}
		}
		panel.setImage(TempImage);
		panel.getGraphics().clearRect(0, 0, panel.getWidth(), panel.getHeight());
		panel.paint(getGraphics());
		repaint();
	}
}
