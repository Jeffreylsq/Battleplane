package cn.jeffrey.shoot;

import java.awt.image.BufferedImage;

public class BigPlane extends FlyingObject implements Enemy {
	
	private static BufferedImage[] images;
	
	static{
		images  = new BufferedImage[5];
		for(int i = 0 ;  i < images.length ; i++)
		{
			images[i] = loadImage("bigplane"+ i +".png");
		}
	}
	

	private int speed;

	public BigPlane()
	{
		super(69,99);
		speed = 4;
	}


	
	public void step()
	{
		y += speed;
	}
	
	
	int index = 1;
	
	public BufferedImage getImage()
	{
		if(isLife())
		{
			return images[0];
		}
		 else if(isDead())
		{
			if(index == images.length -1)
			{
				state = REMOVE; 
			}
			return images[index++];
		}
		return null;
		
	}
	
	public int getScore()
	{
		return 3;
	}
	
	
}
