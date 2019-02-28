package cn.jeffrey.shoot;
//实现类具有奖励功能的接口
public interface Award {
	
	//定义获得生命的常量为0
	int LIFE = 0;
	//定义获得双重火力的常量 为1
	int DOUBLE_FIRE = 1;
	
	//获得奖励的方法
	public int getAwardType();
	
}
