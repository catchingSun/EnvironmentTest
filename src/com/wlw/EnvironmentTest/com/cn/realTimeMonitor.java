package com.wlw.EnvironmentTest.com.cn;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wlw.EnvironmentTest.com.cn.MainActivity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class realTimeMonitor extends Activity {
	private final int TIMER_INVALIDATE = 1001;
	
	private String TempStr[];//用于存放温度数值
	private String LightLevelStr[];//用于存放光强数值
	private String GasStr[];//用于存放瓦斯数值
	private String AlarmFlag[];//用于报警标识
	private String LedFlag[];//用于灯状态标识
	private String mTemperature;
	
	private Spinner mTempSpinner;
	private Spinner mHumSpinner;
	private Spinner mLightSpinner;
	
	
	private TextView mTextShow;
	private TextView mTmptv;
	private TextView mTmpValuetv,mTmpbtn,mHumidityBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);
        mTmpbtn=(TextView) findViewById(R.id.temperatureTv);
        mHumidityBtn=(TextView) findViewById(R.id.humidityTv);
      //  Tmptv=(TextView) findViewById(R.id.temperature);
        mTmpValuetv=(TextView) findViewById(R.id.lightTv);
        
        mTempSpinner=(Spinner) findViewById(R.id.temperatureSpinner);
        mHumSpinner=(Spinner) findViewById(R.id.humiditySpinner);
        mLightSpinner=(Spinner) findViewById(R.id.lightSpinner);
       // setView();
        getMessage();
    }
    MainActivity mainactivity=new MainActivity();
    public void getMessage(){//下拉菜单
    	//获取数组对象，建立数据源
    	String[] mTemItems = getResources().getStringArray(R.array.temperatureSpinner);
    /*	List<Map<String,Object>> data=new ArrayList<Map<String,Object>>(); 
        Map<String,Object> map=new HashMap<String, Object>(); 
        map.put("logo", R.drawable.icon); 
        map.put("text","xxXx说明操作"); 
         
        Map<String, Object> map2=new HashMap<String, Object>(); 
        map2.put("logo", R.drawable.icon); 
        map2.put("text","xxx说明xdds"); 
         
        data.add(map); 
        data.add(map2); 
         
        SimpleAdapter simpleAdapter=new SimpleAdapter(this, data, R.layout.list, new String[]{"logo","text"}, new int[]{R.id.imageView1,R.id.textView1});
    	*/
    	//自定义下拉列表数据
    	List<String> Node_list = new ArrayList<String>();
    	Node_list.add("结点1");
    	Node_list.add("结点2");
    	Node_list.add("结点3");
    	ArrayAdapter<String>_Adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Node_list);
    	_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	mTempSpinner.setAdapter(_Adapter);
    	mHumSpinner.setAdapter(_Adapter);
    	mLightSpinner.setAdapter(_Adapter);
 //   	Toast.makeText(getApplicationContext(), "main Thread"+mTempSpinner.getItemIdAtPosition(mTempSpinner.getSelectedItemPosition()), Toast.LENGTH_LONG).show();  
 
    	mTempSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {  
    		  
            @Override  
            public void onItemSelected(AdapterView<?> parent, View view,  
                    int position, long id) {  
                Spinner spinner=(Spinner) parent;  
                Toast.makeText(getApplicationContext(), "您选择了"+spinner.getItemAtPosition(position),500).show();  
            }  
  
            @Override  
            public void onNothingSelected(AdapterView<?> parent) {  
                Toast.makeText(getApplicationContext(), "没有改变的处理", 500).show();  
            }  
  
        });
    }
    
    
    
    public void receive() {
    	if(!mainactivity.connectSuccess){//连接失败，请重新连接
    		Toast.makeText(realTimeMonitor.this, "请连接服务器",500).show();
    	}
    	else{
    		new Thread() {
    			public void run(){
    				try {
						while(!mainactivity.socket.isClosed()){
							InputStream getinput = mainactivity.socket.getInputStream(); 
							byte data[] = new byte[512];
							int n = getinput.read(data);
							if(n > 0){						
								String val = new String(data);							
								//解析数据	
								switch(val.charAt(1))
								{
								case 'T'://温度
									TempStr = val.split(",");
									break;
								case 'L'://光强
									LightLevelStr = val.split(",");
									break;
								case 'G'://气体
									GasStr = val.split(",");
									break;
								case 'A'://报警
									AlarmFlag = val.split(",");
									break;
								case 'D'://灯状态
									LedFlag = val.split(",");
									break;
								
								}
								Message message = new Message();// 生成消息，并赋予ID值
								message.what = TIMER_INVALIDATE;
								Handler myHandler=new Handler();
								myHandler.sendMessage(message);// 投递消息
							}
							
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Toast.makeText(realTimeMonitor.this, "服务器已断开",500).show();
						e.printStackTrace();
						
					}
    			}
    		}.start();
    	}
    }

    public void setView(){
    	String a = null;
    	//Tmptv.setText("温度：");
    	a=getTemperture();
    	mTmpValuetv.setText(mTemperature);
    }
    //获取当前温度值
    public String getTemperture(){
    	return mTemperature;
    }
}

