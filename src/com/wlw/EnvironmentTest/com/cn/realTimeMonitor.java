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
	
	private String TempStr[];//���ڴ���¶���ֵ
	private String LightLevelStr[];//���ڴ�Ź�ǿ��ֵ
	private String GasStr[];//���ڴ����˹��ֵ
	private String AlarmFlag[];//���ڱ�����ʶ
	private String LedFlag[];//���ڵ�״̬��ʶ
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
    public void getMessage(){//�����˵�
    	//��ȡ������󣬽�������Դ
    	String[] mTemItems = getResources().getStringArray(R.array.temperatureSpinner);
    /*	List<Map<String,Object>> data=new ArrayList<Map<String,Object>>(); 
        Map<String,Object> map=new HashMap<String, Object>(); 
        map.put("logo", R.drawable.icon); 
        map.put("text","xxXx˵������"); 
         
        Map<String, Object> map2=new HashMap<String, Object>(); 
        map2.put("logo", R.drawable.icon); 
        map2.put("text","xxx˵��xdds"); 
         
        data.add(map); 
        data.add(map2); 
         
        SimpleAdapter simpleAdapter=new SimpleAdapter(this, data, R.layout.list, new String[]{"logo","text"}, new int[]{R.id.imageView1,R.id.textView1});
    	*/
    	//�Զ��������б�����
    	List<String> Node_list = new ArrayList<String>();
    	Node_list.add("���1");
    	Node_list.add("���2");
    	Node_list.add("���3");
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
                Toast.makeText(getApplicationContext(), "��ѡ����"+spinner.getItemAtPosition(position),500).show();  
            }  
  
            @Override  
            public void onNothingSelected(AdapterView<?> parent) {  
                Toast.makeText(getApplicationContext(), "û�иı�Ĵ���", 500).show();  
            }  
  
        });
    }
    
    
    
    public void receive() {
    	if(!mainactivity.connectSuccess){//����ʧ�ܣ�����������
    		Toast.makeText(realTimeMonitor.this, "�����ӷ�����",500).show();
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
								//��������	
								switch(val.charAt(1))
								{
								case 'T'://�¶�
									TempStr = val.split(",");
									break;
								case 'L'://��ǿ
									LightLevelStr = val.split(",");
									break;
								case 'G'://����
									GasStr = val.split(",");
									break;
								case 'A'://����
									AlarmFlag = val.split(",");
									break;
								case 'D'://��״̬
									LedFlag = val.split(",");
									break;
								
								}
								Message message = new Message();// ������Ϣ��������IDֵ
								message.what = TIMER_INVALIDATE;
								Handler myHandler=new Handler();
								myHandler.sendMessage(message);// Ͷ����Ϣ
							}
							
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Toast.makeText(realTimeMonitor.this, "�������ѶϿ�",500).show();
						e.printStackTrace();
						
					}
    			}
    		}.start();
    	}
    }

    public void setView(){
    	String a = null;
    	//Tmptv.setText("�¶ȣ�");
    	a=getTemperture();
    	mTmpValuetv.setText(mTemperature);
    }
    //��ȡ��ǰ�¶�ֵ
    public String getTemperture(){
    	return mTemperature;
    }
}

