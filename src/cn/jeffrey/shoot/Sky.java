package cn.jeffrey.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sky extends FlyingObject {
	
	private static BufferedImage images;
	
	static{
		
			images = loadImage("background.png");
		
	}
	
	
	
	private int y1;
	private int speed;

	Sky()
	{
		super(0,0,World.WIDTH,World.HEIGHT);
		y1 = -height;
		speed = 1;
	}



	
	
	public void step()
	{
		y += speed;
		y1 += speed;
		if(y >= World.HEIGHT )
		{
			y = - World.HEIGHT;
		}
		else if( y1 >= World.HEIGHT)
		{
			y1 = -World.HEIGHT;
		}
	}
	
	public BufferedImage getImage()
	{
		return images;
	}
	
	//只有天空类重写flyingobject 父类中的paintObject方法，因为只有天空是两个图片组合的
	//重写的方法向窗体装填两个图片
	public void paintObject(Graphics g)
	{
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
		
		
	}


}
