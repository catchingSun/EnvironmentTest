package com.wlw.EnvironmentTest.com.cn;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
//	public int a=4;
	
	
	private static final String [] ip=new String[Character.MAX_VALUE];
	private final int TIMER_INVALIDATE = 1001;
	private String SOCKET_IP = "192.168.1.117";
	Socket socket = null;
	private static  int i=1;
	public Boolean connectSuccess=true;
	private Timer timer;
	private TimerTask task;
	private int SOCKET_PORT = 51706;//�������˿ں�
	private Button mTimemonitorBtn,mConctBtn,mcontrolBtn,mclosecontrolBtn;
	private EditText mconctEdit;
	private  AutoCompleteTextView iphistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //���ر�����
       // this.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
      //  this.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
      
        mconctEdit=(EditText) findViewById(R.id.connectEdit);//����IP
        mConctBtn=(Button) findViewById(R.id.conctBtn);//����
        mclosecontrolBtn=(Button) findViewById(R.id.closeconctBtn);
        mTimemonitorBtn=(Button) findViewById(R.id.TimemonitorBtn);//ʵʱ���
        mcontrolBtn=(Button) findViewById(R.id.controlBtn);//���ƽ���
    //    iphistory=(AutoCompleteTextView) findViewById(R.id.iphistory);
      //  Tmptv=(TextView) findViewById(R.id.temperature);
        mConctBtn.setOnClickListener(new click());
        mclosecontrolBtn.setOnClickListener(new click());
        mTimemonitorBtn.setOnClickListener(new click());
        mcontrolBtn.setOnClickListener(new click());
       // iphistory();

       
    }
    /*
    public void iphistory(){
    	 ip[0]="192.168.1.1";
         String in = mconctEdit.getText().toString();
         ip[i]=in;
             
    	 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,ip);
         iphistory.setAdapter(adapter);
         i++;
    }*/
    //�������¼�
    public class click implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId()==R.id.conctBtn){//����
				openConnect();
			}
			if (v.getId()==R.id.closeconctBtn){//�Ͽ����ӹرճ���
				close();
			}
			if(v.getId()==R.id.TimemonitorBtn){//����ʵʱ������
				Intent newActivity=new Intent(MainActivity.this,realTimeMonitor.class);
				startActivity(newActivity);
			}
			if(v.getId()==R.id.controlBtn){//����ʵʱ���ƽ���
				Intent newActivity=new Intent(MainActivity.this,realControl.class);
	    		startActivity(newActivity);
				}
		}
    	
    } 
    public void openConnect(){
	    String in = mconctEdit.getText().toString();
		if(in.equals("")){
			Toast.makeText(MainActivity.this, "�����������ip", Toast.LENGTH_SHORT).show();
		}
		else{
           	 try { 
           		 	InetAddress serverAddr = InetAddress.getByName(in);//TCPServer.SERVERIP 
                	Log.d("TCP", "C: Connecting..."); 
                	//socket = new Socket(serverAddr, SOCKET_PORT);  
                	socket = new Socket(serverAddr, SOCKET_PORT);
            	} catch(Exception e) { 
            		Toast.makeText(MainActivity.this, "����ʧ�ܣ����������ӷ�����", 500).show();
            		connectSuccess=false;
            	    Log.e("TCP", "S: Error", e); 
            	} finally { 
            	    try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	} 
           	 if(checkConnect()){
				Toast.makeText(MainActivity.this, "���ӳɹ�", 500).show();
			}
			else{
				Toast.makeText(MainActivity.this, "�˿��ѹر�", 500).show();
			}
        }
	}
    
  
    private void close(){//�ر������Լ�����
    	try {
    		Log.d("TCP","socket close");
    		if(socket != null)
    		{
    			socket.close();    			
    		}
	    	finish();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 Log.e("TCP", "S: Error", e); 
		} 
    }
    
    public boolean checkConnect(){//�������
    	if(socket == null){
    		connectSuccess=false;
    	}
		return connectSuccess;
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
