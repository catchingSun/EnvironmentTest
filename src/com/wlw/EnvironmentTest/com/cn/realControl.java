package com.wlw.EnvironmentTest.com.cn;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;



import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

//import com.wlw.EnvironmentTest.com.cn.MainActivity;;
public class realControl extends Activity{
	MainActivity mainactivity =new MainActivity();
	sendMessage sendmessage = new sendMessage();
	
	
	private final int TIMER_INVALIDATE = 1001;
	private Timer timer;
	private TimerTask task;
	
	
	
	private String TempStr[];//用于存放温度数值
	private String LightLevelStr[];//用于存放光强数值
	private String GasStr[];//用于存放瓦斯数值
	private String AlarmFlag[];//用于报警标识
	private String LedFlag[];//用于灯状态标识
	
	private TextView mTextShow;
	
	private ToggleButton mTbtnLed1;
	private ToggleButton mTbtnLed2;
	private ToggleButton mTbtnLed3;
	private ToggleButton mTbtnLed4;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realcontrol);
        mTbtnLed1=(ToggleButton)findViewById(R.id.led1);
        mTbtnLed2=(ToggleButton)findViewById(R.id.led2);
        mTbtnLed3=(ToggleButton)findViewById(R.id.led3);
        mTbtnLed4=(ToggleButton)findViewById(R.id.led4);
        
        mTextShow=(TextView) findViewById(R.id.lightstate);
        
        mTbtnLed1.setOnClickListener(new lightStateControl());
        mTbtnLed2.setOnClickListener(new lightStateControl());
        mTbtnLed3.setOnClickListener(new lightStateControl());
        mTbtnLed4.setOnClickListener(new lightStateControl());
    }
	
	
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TIMER_INVALIDATE://查询灯状态
				if(sendmessage.dlgIndex == 0)
				{
					if(LedFlag[1].charAt(0) == 0x30)
					{
						mTbtnLed1.setChecked(false);
					}
					else if(LedFlag[1].charAt(0) == 0x31)
					{
						mTbtnLed1.setChecked(true);
					}
					
					if(LedFlag[2].charAt(0) == 0x30)
					{
						mTbtnLed2.setChecked(false);
					}
					else if(LedFlag[2].charAt(0) == 0x31)
					{
						mTbtnLed2.setChecked(true);
					}
					
					if(LedFlag[3].charAt(0) == 0x30)
					{
						mTbtnLed3.setChecked(false);
					}
					else if(LedFlag[3].charAt(0) == 0x31)
					{
						mTbtnLed3.setChecked(true);
					}
					
					if(LedFlag[4].charAt(0) == 0x30)
					{
						mTbtnLed4.setChecked(false);
					}
					else if(LedFlag[4].charAt(0) == 0x31)
					{
						mTbtnLed4.setChecked(true);
					}
				}
			}
			super.handleMessage(msg);
		
		//启动定时器执行一次查询灯状态操作
	    task = new TimerTask() {
			@Override
			public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			message.what = 1;
			myHandler.sendMessage(message);
			}
		};
		timer= new Timer();
		timer.schedule(task, 200);
		sendmessage.dlgIndex = 0;
		}
		};
		
	public class lightStateControl implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.led1){
				// 当按钮第一次被点击时候响应的事件                        
				if (mTbtnLed1.isChecked()) 
				{                                  
					sendmessage.MySend("11D11");
					mTextShow.setText("您按下了1号开关，1号灯被点亮！");
				}// 当按钮再次被点击时候响应的事件                  
				else 
				{
					sendmessage.MySend("11D00");
					mTextShow.setText("您按下了1号开关，1号灯被熄灭！");
				}
				
			}	
			if(v.getId() == R.id.led2){
				// 当按钮第一次被点击时候响应的事件                        
				if (mTbtnLed2.isChecked()) 
				{                                  
					sendmessage.MySend("22D11");
					mTextShow.setText("您按下了2号开关，2号灯被点亮！");
				}// 当按钮再次被点击时候响应的事件                  
				else 
				{
					sendmessage.MySend("22D00");
					mTextShow.setText("您按下了2号开关，2号灯被熄灭！");
				}
				
			}
			if(v.getId() == R.id.led3){
				// 当按钮第一次被点击时候响应的事件                        
				if (mTbtnLed3.isChecked()) 
				{                                  
					sendmessage.MySend("33D11");
					mTextShow.setText("您按下了3号开关，3号灯被点亮！");
				}// 当按钮再次被点击时候响应的事件                  
				else 
				{
					sendmessage.MySend("33D00");
					mTextShow.setText("您按下了3号开关，3号灯被熄灭！");
				}
				
			}
			if(v.getId() == R.id.led4){
				// 当按钮第一次被点击时候响应的事件                        
				if (mTbtnLed4.isChecked()) 
				{          
					sendmessage.MySend("44D11");
					mTextShow.setText("您按下了4号开关，4号灯被点亮！");
				}// 当按钮再次被点击时候响应的事件                  
				else 
				{
					sendmessage.MySend("44D00");
					mTextShow.setText("您按下了4号开关，4号灯被熄灭！");
				}	
			}		
		}
	}
}