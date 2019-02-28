package cn.jeffrey.shoot;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject {
	
private static BufferedImage images;
	
	static{
		
			images = loadImage("bullet.png");
		
	}
	
	protected static int speed;


	public Bullet(int x , int y)
	{
		super(x,y,8,14);
		speed = 6; 
	}

	
	public void step()
	{
		y -= speed;
	} 
	
	
	public BufferedImage getImage()
	{
		if(isLife())
		{
			return images;
		}
		else
		{
			state = REMOVE;
			return null;
		}
		
	}
	public boolean outOfBounds()
	{
		//子弹的y值小于（高于）自身的高度时为越界
		return y < - height;
	}
	
	
			


}
