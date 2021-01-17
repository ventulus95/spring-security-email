package com.ventulus95.springsecurityemail;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "ventulus95@gmail.com";
    static StringBuffer key = new StringBuffer();

    public void mailSend(MailDto mailDto){
        try {
            MailHandler mailHandler = new MailHandler(javaMailSender);
            mailHandler.setTo(mailDto.getAddress());
            mailHandler.setSubject("인증메일입니다.");
            Random rnd = new Random();

            for (int i = 0; i < 6; i++) {
                int index = rnd.nextInt(3);
                switch (index) {
                    case 0:
                        key.append(((int) (rnd.nextInt(26)) + 97));
                        break;
                    case 1:
                        key.append(((int) (rnd.nextInt(26)) + 65));
                        break;
                    case 2:
                        key.append((rnd.nextInt(10)));
                        break;
                }
            }
            String htmlContent = "<p> 인증번호:" + key.toString() +"<p>";
            mailHandler.setText(htmlContent, true);
            mailHandler.setAttach(mailDto.getFile().getOriginalFilename(), mailDto.getFile());
            mailHandler.setInline("sample-img", mailDto.getFile());
            mailHandler.send();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean answer(String ans){
        return ans.equals(key.toString());
    }
}
