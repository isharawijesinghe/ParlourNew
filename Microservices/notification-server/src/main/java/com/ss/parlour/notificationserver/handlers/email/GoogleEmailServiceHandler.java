package com.ss.parlour.notificationserver.handlers.email;

import com.ss.parlour.notificationserver.utils.bean.NotificationConst;
import com.ss.parlour.notificationserver.utils.bean.request.EmailRequestBean;
import com.ss.parlour.notificationserver.utils.bean.response.EmailResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class GoogleEmailServiceHandler implements EmailServiceHandlerI {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${application.emailProvide}")
    private String authServerUrl;

    @Override
    public EmailResponseBean sendEmailRequest(EmailRequestBean emailRequestBean){
        EmailResponseBean emailResponseBean = new EmailResponseBean();
        SimpleMailMessage simpleMailMessage = createUserRegistrationEmailRequest(emailRequestBean);
        mailSender.send(simpleMailMessage);
        emailResponseBean.setStatus(NotificationConst.SUCCESS_STATUS);
        emailResponseBean.setNarration(NotificationConst.USER_REGISTER_EMAIL_SEND_SUCCESS_NARRATION);
        return emailResponseBean;
    }


    protected SimpleMailMessage createUserRegistrationEmailRequest(EmailRequestBean emailRequestBean){
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(emailRequestBean.getReceiverEmail());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below: " + authServerUrl + "/confirm?token=" + emailRequestBean.getConfirmationToken());
        registrationEmail.setFrom("noreply@domain.com");
        return registrationEmail;
    }

}
