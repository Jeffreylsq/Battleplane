package cn.jeffrey.shoot;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {

	private static BufferedImage[] images;
	
	static{
		images  = new BufferedImage[2];
		images[0] = loadImage("hero"+ 0 +".png");
		images[1] = loadImage("hero"+ 1 +".png");
		
	}
	private int life;
	private int doubleFire; // 0 single fire, 1 double fire


	Hero()
	{
		
		super(152,500,97,124);
		life = 3;
		doubleFire = 0;
		
	}
	//英雄机移动方法， x , y 英雄机移动坐标，也是鼠标的位置
	//为了让鼠标在英雄机的中间，所以英雄机的x 时鼠标位置减英雄机宽度一半， 
	//英雄机的y 是鼠标位置减英雄机高度一般
	public void moveTo(int x, int y){
		
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	
	}
	
	
	
	
	public void step(){
		
		
	}
	
	
	int index = 0;
	public BufferedImage getImage()
	{
		return images[index++%images.length];      //不是一就是二
		
		
	}

	//英雄机的开火方法
	
	public Bullet [] shoot() {
		
		 int xStep = width/4; //获得四分之一的宽 用于射击子弹的位置
	    int yStep = 20; //爲了讓子彈出現的位置更合理
	    
	    if(doubleFire == 0)
	    {
	    	//單排
	    	Bullet[] bs = new Bullet[1]; //實例化子彈數組用來返回
	    	bs[0] = new Bullet(x+ 2*xStep,y-yStep);
	    	return bs;
	    	
	    }
	    else
	    {   //双排炮
	    	Bullet[] bs = new Bullet[2];   //实例化子弹数组
	    	bs[0] = new Bullet(x + xStep, y + yStep);
	    	bs[1] = new Bullet(x + 3*xStep, y + yStep);
	    	doubleFire --;
	    	return bs;
	    	
	    }
	}
	
	//英雄机增命， life 属性是私有的，要修改他必须是Hero类自己的操作
	public void addLife() {
		
		life++;
	}
	
	//增加火力
	
	public void addDoubleFire() {
		doubleFire += 20;
	//	Bullet.speed +=10;
	}
	
	//获得英雄机的生命
	public int getLife() {
		return life;
	}
	
	//英雄机减命
	
	public void subLife() {
		life--;
		
	}
	
	//清空火力值
	public void clearDoubleFire() {
		doubleFire = 0;
	}
	
	
	


}

