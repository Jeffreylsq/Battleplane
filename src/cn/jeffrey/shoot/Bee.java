package cn.jeffrey.shoot;

import java.awt.image.BufferedImage;

public class Bee extends FlyingObject implements Award{
	
   private static BufferedImage[] images;
	
	static{
		images  = new BufferedImage[5];
		for(int i = 0 ;  i < images.length ; i++)
		{
			images[i] = loadImage("bee"+ i +".png");
		}
	}
	
	
	
	private int xSpeed;
	private int ySpeed;
	private int awardType;

	public Bee()
	{
		
		super(60,50);
		xSpeed = 1;
		ySpeed = 2;
		awardType = (int)(Math.random()* 2);
		
	}



	
	public void step()
	{
		x += xSpeed;//小蜜蜂 x轴 y轴 都有移动
		y += ySpeed;
		//小蜜蜂碰撞边缘要改变方向
		//是通过修改xSpeed属性的正负号来决定的
		
		if(x <= 0 || x+width >= World.WIDTH)
		{
			xSpeed *= -1;    //切换方向
		}
		
		
		
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
			 if(index == images.length - 1)
			 {
				 state = REMOVE;
			 }
			 return images[index++];
			
		}
		return null;
	}
	
	public int getAwardType(){
		
		return awardType;	
	}
	

}
