package com.aoao.jinian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by asus on 2018/9/11.
 */

public class Email extends AppCompatActivity {
    private Button mButton;
    private EditText mContent,mContact;
    private ActionBar actionBar;
    public static String myEmailAccount = "1368618330@qq.com";
    public static String myEmailPassword = "bhibmqaubrmcgdgi";
    public static String myEmailSMTPHost = "smtp.qq.com";
    public static String receiveMailAccount = "193186337@qq.com";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.zhu);
        }
        mButton = (Button) findViewById(R.id.send_email);
        mContent = (EditText) findViewById(R.id.content_feedback);
        mContact = (EditText) findViewById(R.id.contact_feedback);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread (new Runnable() {
                    @Override
                    public void run() {
                        Properties props = new Properties();
                        props.setProperty("mail.transport.protocol", "smtp");
                        props.setProperty("mail.smtp.host", myEmailSMTPHost);
                        props.setProperty("mail.smtp.auth", "true");
                        Session session = Session.getInstance(props);
                        session.setDebug(true);
                        try {
                            MimeMessage mMessage = createMimeMessage(session, myEmailAccount, receiveMailAccount);
                            Transport transport = session.getTransport();
                            transport.connect(myEmailAccount, myEmailPassword);
                            transport.sendMessage(mMessage, mMessage.getAllRecipients());
                            transport.close();
                            Snackbar.make(mButton,"呀，小纪已经发送了您的反馈哦！",Snackbar.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Snackbar.make(mButton,"呀，小纪连不上网络了！",Snackbar.LENGTH_LONG).show();
                        }
                    }

                }).start();

        }});


    }

    public  MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "纪年使用者", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));
        message.setSubject("纪年使用反馈", "UTF-8");
        message.setContent("使用者联系方式："+mContact.getText().toString()+"\r\n使用者建议："+mContent.getText().toString(), "text/html;charset=UTF-8");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

}
