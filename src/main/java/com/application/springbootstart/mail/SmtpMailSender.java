/**
* The program provides as an Email Notification Service.
* @author  Raghuram Maturi
* @version 1.0
* @since   2017-04-01 
*/
package com.application.springbootstart.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@EnableAutoConfiguration
@Component
public class SmtpMailSender {

	
	@Autowired
    public  JavaMailSender javaMailSender;
	
	MimeMessage mail  = null;
	SimpleMailMessage message = null;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * This method provides the Email Notification servivce.
	 * sends the list of fileID's that are newly added in the last Hour.	 
	 * @param fileList
	 */	
	public void sendEmailNotification(String to, String subject, String body) throws Exception{
		try {	
			//mail = javaMailSender.createMimeMessage();
			 message = new SimpleMailMessage();
		}catch(Exception e) {
			logger.info("Exception in creating JavaMailSender: "+e);
		}
        try {
            //MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        	message.setTo(to);            
        	message.setSubject(subject);
        	message.setText(body);
            javaMailSender.send(message);
        } catch (Exception e) {
            logger.info("Exception in sending email notification to recepient.."+e);
        } finally {
        	
        }
        
    }
	
}
