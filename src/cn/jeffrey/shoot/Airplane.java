package cn.jeffrey.shoot;

import java.awt.image.BufferedImage;

public class Airplane extends FlyingObject implements Enemy{
	private static BufferedImage[] images;
	
	static{
		images  = new BufferedImage[5];
		for(int i = 0 ;  i < images.length ; i++)
		{
			images[i] = loadImage("airplane"+ i +".png");
		}
	}
	private int xSpeed; 
	private int ySpeed;
//	private int speed;
	  Airplane()
	{
		super(49,36);
	//	speed = 4;
		xSpeed = 4;
		ySpeed = 4;
	}
	
	
	
	

	
	public void step()
	{
		//y += speed;  //向下移动2像素
		
        
		x += xSpeed;//小蜜蜂 x轴 y轴 都有移动
		y += ySpeed;
		if(x <= 0 || x+width >= World.WIDTH)
		{
			xSpeed *= -1;    //切换方向
		}
		
	}
	
	int index = 1;
	public BufferedImage getImage()
	{
		if(isLife())    //活着的时侯取活着的图片
		{
			return images[0];
		}
		 if(isDead())            //死了返回爆炸过程
		{
			//是否完成爆炸效果
			if(index == images.length -1)
			{
				state = REMOVE;   //爆炸完成直接退场
			}
		   return images[index++];	
		}
		
			return null;                  //表示对象将离场
		
	
	 }
	//实现接口
	public int getScore()
	{
		return 1;
	}


}
