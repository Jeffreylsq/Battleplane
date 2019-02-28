package cn.jeffrey.shoot;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class World extends JPanel {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 700;
	
	//定义游戏状态的各种常量
	
	public static final int START = 0; //游戏未运行
	public static final int RUNNING = 1; //游戏运行
	public static final int PAUSE = 2;  //游戏暂停
	public static final int GAME_OVER = 3; //游戏结束
	
    private static BufferedImage start ; 	//启动图
    private static BufferedImage pause ;  //暂停图
    private static BufferedImage gameOver; //结束图
	
	static {//初始化图片
		
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameOver = FlyingObject.loadImage("gameover.png");
		
	}
	
	private int state = START; //定义游戏的当前状态
	
	
	
	
	Hero hero = new Hero();
	Sky sky = new Sky();
	FlyingObject[] enemies = {};
	Bullet[] bl = {};

	// 生成敌机的方法（小/大/蜜蜂）

	public FlyingObject nextOne() {
		int num = (int) (Math.random() * 100);
		if (num < 3) // 小于25的几率
		{
			return new Bee();
		} else if (num < 60) {
			return new Airplane();

		} else {
			return new BigPlane();
		}
	}

	int enterIndex = 0; // 敌人入场计数变量
	// 小/大/蜜蜂入场方式

	public void enterAction() {

		// 每运行一次这个方法 enterIndex++
		enterIndex++;
		// 每隔500毫秒出现一架敌机
		// enterIndex 为50的倍数 新敌人进入
		if (enterIndex % 30 == 0) {
			// 新敌人进场
			FlyingObject enemy = nextOne();

			// 生成之后放入到敌人数组中
			enemies = Arrays.copyOf(enemies, enemies.length + 1); // 数组要进行扩容
			enemies[enemies.length - 1] = enemy;
		}
	}

	int shootIndex = 0; //定义子弹入场的计数变量
	//子弹入场
	public void shootAction()
	{
		shootIndex ++;

		if(shootIndex % 30 == 0)
		{
			Bullet [] bs = hero.shoot();
			bl = Arrays.copyOf(bl, bl.length + bs.length);
			System.arraycopy(bs, 0, bl, bl.length - bs.length, bs.length);
		}

	}

	//飞行机移动
	public void stepAction()
	{
		sky.step(); //天空移动

		for(int i = 0 ; i < enemies.length ; i++)   //遍历数组
		{
			enemies[i].step();
		}
		for(int i = 0 ; i < bl.length ; i++)
		{
			bl[i].step();
		}



	}

	//删除越界的敌人和子弹方法
	public void outOfBoundAction() {
		//index让没有出界的数组元素， 从0位置连续放入新的数组中
		int index = 0;  //记录不越界敌人的个数，记录不越界敌人的数组下标

		//声明一个飞行物数组， 将没有越界的敌人放入这个数组

		FlyingObject [] enemyLive = new FlyingObject[enemies.length]; // 默认所有没有出界

		for(int i = 0 ; i < enemies.length ; i++)
		{
			//遍历敌人数组 检测每一个敌人是否越界 并且在场非出场

			if(!enemies[i].outOfBounds() && !enemies[i].isRemove())
			{	//当没有越界时， 将没有越界的敌人放入新数组 
				enemyLive[index] = enemies[i];
				index++;   //更新新数组的下标和没有出界的敌人数量
			}
		}

		//将筛选之后的新数组 赋给敌人数组
		enemies = Arrays.copyOf(enemyLive, index); 


		index = 0;

		Bullet [] bulletLives = new Bullet [bl.length] ;

		for(int i = 0 ; i < bl.length ; i++)
		{
			if(!bl[i].outOfBounds() && !bl[i].isRemove())
			{
				bulletLives[index] = bl[i];
				index++;
			}
		}
		bl = Arrays.copyOf(bulletLives, index);

	}

	private int scores;
	//与敌人碰撞方法

	public void bulletBangAction() {//10ms 走一次

		for(int i = 0 ;  i < bl.length ;i++) {

			Bullet b  = bl[i]; //取出子弹
			//用这发子弹遍历所有敌机
			for(int j = 0 ; j < enemies.length;j++)
			{
				FlyingObject f = enemies[j];
				if(f.hit(b)&& f.isLife() && b.isLife()) {
					//活着的子弹碰活着的敌机

					f.goDead();//敌机死
					b.goDead();//子弹死

					//击中之后要根据敌人的种类来进行得分和奖励
					if(f instanceof Enemy) {

						Enemy e = (Enemy)f;
						scores += e.getScore();  //加分
					}
					
					if(f instanceof Award){ //小蜜蜂进
						
						Award a = (Award)f;
						int type = a.getAwardType();
						//根据type的值来决定调用哪个奖励
						
						switch(type) {
						case Award.LIFE:
							hero.addLife();
							break;
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire();
							break;
						}
					}
					
					break;
				}
			 
			}
			
			
		}

	}
   //英雄机和子弹相撞的方法
	public void heroBangAction() {
		
		//遍历所有敌人
		for(int i = 0 ; i < enemies.length ;i++) {
			
			 FlyingObject f = enemies[i]; //取出一个敌人
			
			 if(f.hit(hero)&& f.isLife() && hero.isLife()) {
				
				f.goDead(); //敌机死
				hero.subLife(); //减命
				hero.clearDoubleFire(); //清空火力值
				
				
			}
		}
	}


	//检测游戏结束方法
	
	public void checkGameOverAction() {
		
		if(hero.getLife() <= 0) {   //如果英雄机没有生命
			state = GAME_OVER;  //游戏结束
		}
	}
	
	
	
	
	
	


	public void start() {

		//为了让鼠标移动时能让英雄机随之移动
		//创建一个监听器

		MouseAdapter l = new MouseAdapter() {
			//重写鼠标移动时的方法
			public void mouseMoved(MouseEvent e){
				
				if(state == RUNNING){
				 //获得鼠标 x y 坐标
				 int x = e.getX();
				 int y = e.getY();
				 //调用 moveTo方法
				 hero.moveTo(x, y);
				
				}
			}
			
			//鼠标移出窗体时
			
			public void mouseExited(MouseEvent e) {
				
				//如果运行状态鼠标移出变暂停
				if(state == RUNNING) {
					state = PAUSE;
				}		
			}
			
			//鼠标移入窗体
			public void mouseEntered(MouseEvent e) {
				
				if(state == PAUSE) {
					
					state = RUNNING;
				}
								
			}
			
			//鼠标点击时
			
			public void mouseClicked(MouseEvent e) {
				
				//根据当前状态进入不同状态需要选择结构
				
				switch(state) {
				
				case START:      //开始状态点击鼠标变运行
					state = RUNNING;
					break;
				
				case GAME_OVER:  //在游戏结束后点击，进入开始状态
					
					//重新开始之前，需要重置所有属性值
					scores = 0;
					sky = new Sky();
					hero = new Hero();
					enemies = new FlyingObject[0];
					bl = new Bullet[0];
					state = START;
					break;
				
				}
				
			}
			
		};

		//当前窗口来使用监听器来控制事件
		this.addMouseListener(l);
		this.addMouseMotionListener(l);




		// 定义计时器
		Timer timer = new Timer();
		int interval = 10; // 控制游戏速度， 值越大速度越慢

		TimerTask task = new TimerTask() {

			// 匿名内部类创建计时期执行对象
			public void run() {
				
				if(state == RUNNING) {//游戏运行时才执行
					enterAction();   //敌人进场
					shootAction();   //子弹进场
					stepAction();    //飞行物移动  
					outOfBoundAction(); //删除出界的所有元素
					bulletBangAction(); // 检测敌机和子弹相撞
					heroBangAction();   //检测英雄机与敌机相撞
					checkGameOverAction(); //检测游戏是否结束
				}
				
				
				repaint();       //让paint()重新配置 ----------------------------------------------------------最重要            
				//run 方法中依次调用业务 计数器每隔10毫秒 运行一次run 中所有内容

			}

		};
		// 每隔周期触发一次run方法
		timer.schedule(task, interval, interval);

	}

	// 这个方法的方法是将world 类中的所有单位画在窗体上，调用者不是我们，是 frame.setVisible(true) 自动调用的
	// 实际上是在重写父类的方法
	public void paint(Graphics g) {

		sky.paintObject(g);
		hero.paintObject(g);

		// 循环画敌机
		for (int i = 0; i < enemies.length; i++) {
			enemies[i].paintObject(g);
		}

		// 循环画子弹

		for (int i = 0; i < bl.length; i++) {
			bl[i].paintObject(g);
		}
		
		//开始状态 画开始图
		
		switch(state) {
		
		case START:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameOver,0,0,null);
			break;
		}
		g.drawString("SCORE: " + scores , 10, 20);
		g.drawString("LIFE: " + hero.getLife(), 10, 40);

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Fighting ");
		World w = new World();
		frame.add(w); // 将world对象放在窗体中
		w.start();

		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
}
