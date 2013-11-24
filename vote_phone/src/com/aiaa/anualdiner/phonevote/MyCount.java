package com.aiaa.anualdiner.phonevote;

import android.os.CountDownTimer;
import android.widget.TextView;

public class MyCount extends CountDownTimer {  
    
    private TextView tt; 

    public MyCount(long millisInFuture, long countDownInterval, TextView tt) {  
        super(millisInFuture, countDownInterval);  
        this.tt = tt;
    }  

    @Override  
    public void onFinish() {  
        tt.setText("");  

    }  

    @Override  
    public void onTick(long millisUntilFinished) {  
        tt.setText("请等待60秒(" + millisUntilFinished / 1000 + ")...");  
    }  

}  