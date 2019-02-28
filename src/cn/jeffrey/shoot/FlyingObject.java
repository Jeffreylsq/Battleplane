package cn.jeffrey.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class FlyingObject {
	
	//Alive, Dead, remove
	
	//Alive
	public static final int LIFE = 0;
	//Dead
	public static final int DEAD = 1;
	//Remove
	public static final int REMOVE = 2;
	
	//represent object's situation 
	protected int state = LIFE;
	
	
	
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	
	FlyingObject(int x, int y , int width , int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	
	FlyingObject(int width , int height)
	{
		this.width = width;
		this.height = height;
		x = (int)(Math.random()*(World.WIDTH - width));
		y =  -height;
	}
	
	// abstract method for moving units
	public abstract void step();
	
	
	//If you want obtain flying objects pictures, we have to get pictures from arrays
	//Because we don't know which pictures we should use , we should use abstract method
	
	public abstract BufferedImage getImage();
	
	//Getting condition method
	//Check out it is alive or not 
	public  boolean isLife()
	{
		return state == LIFE;
	}
	
	public boolean isDead() 
	{
		return state == DEAD;
	}
	
	//check out it is removed or not
	public boolean isRemove()
	{
		return state == REMOVE;
	}
	
	//让飞行物去死的方法
	public void goDead() {
		state = DEAD;
	}
	
	
	
	
	
	
	
	
	//This method is used to draw picture in the windows
	public void paintObject(Graphics g)
	{
		//put pictures in the panel		
		g.drawImage(getImage(),x,y,null);
		
	}
	
	
	
	//读取图片的方法
	public static BufferedImage loadImage(String fileName) 
	{
		try {
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
		    return img;
		
		} catch (IOException e) {
			
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	
	}
	
	//检测越界的方法
	
	public boolean outOfBounds() {
		//返回真表示已经越界
	  return y > World.HEIGHT; //当前对象的y大于了窗体高
	}
	
	//检测碰撞代码， this是敌人，other：子弹，英雄机 
	
	public boolean hit(FlyingObject other) {
		
		//敌人的x 减去 子弹的宽 
		int x1 = this.x - other.width; //左侧
		int x2 = this.x + this.width;  //右侧
		int y1 = this.y - other.height; //子弹尾部击中
		int y2 = this.y + this.height; //底部
		//子弹x y
		int x = other.x;    
		int y = other.y;
		
		//判断思路：将this（敌机）的逻辑范围扩大
		//扩大原因是因为子弹也有宽和高
		//扩大后能更简单的检测碰撞
		//如果x大于x1小于x2 同时 y大于y1 小于y2
		
		
		
		return ((x >= x1 && x <= x2) && (y >= y1 && y <= y2 ));
		
		
	}
	
	
	
	
	
	
	
	
}
