package com.compath.core.api.smtp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.compath.core.api.support.error.CoreApiException;
import com.compath.core.api.support.error.ErrorType;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MailService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private final JavaMailSenderImpl mailSender;

	public void sendVerificationCode(String code, String to) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(to);
			helper.setSubject(getSubject());
			helper.setText(getMessage(code), true);

		} catch (MessagingException e) {
			throw new CoreApiException(ErrorType.BAD_REQUEST, "이메일 전송 중 오류 발생");
		}
		mailSender.send(message);
	}

	private String getSubject() {
		return "[Compath] 이메일 인증번호 안내";
	}

	private String getMessage(String code) {
		return String.format("""
			<!DOCTYPE html>
			<html lang="ko">
			<head>
			    <meta charset="UTF-8">
			    <meta name="viewport" content="width=device-width, initial-scale=1.0">
			</head>
			<body style="font-family: 'Helvetica Neue', Arial, sans-serif; background-color: #f8f9fa; margin: 0; padding: 0;">
			    <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
			        <img src="https://github.com/user-attachments/assets/ed179c54-8cc2-4ed5-9da9-db5b91b16d0f" alt="Compath 로고" style="display: block; width: 100%%; height: auto; margin-bottom: 30px;">
			        <div style="padding: 0 30px 30px;">
			            <h2 style="color: #FF3124; text-align: center; font-size: 28px; margin: 0 0 30px;">이메일 인증 안내</h2>
			            <p style="font-size: 16px; color: #333333; line-height: 1.6; margin: 0 0 20px;">
			                Compath 서비스를 이용해 주셔서 감사합니다.<br>
			                아래의 인증번호를 입력하여 이메일 인증을 완료해 주세요.
			            </p>
			            <div style="text-align: center; margin: 30px 0;">
			                <span style="display: inline-block; padding: 15px 30px; font-size: 28px; color: #ffffff; background-color: #FF3124; border-radius: 8px; font-weight: bold; letter-spacing: 2px; box-shadow: 0 2px 4px rgba(255, 49, 36, 0.3);">
			                    %s
			                </span>
			            </div>
			            <p style="font-size: 16px; color: #333333; line-height: 1.6; margin: 0 0 30px;">
			                이 인증번호는 <strong>10분</strong> 동안 유효합니다.<br>
			            </p>
			            <hr style="border: none; border-top: 1px solid #e0e0e0; margin: 0 0 30px;">
			            <p style="font-size: 14px; color: #666666; text-align: center; margin: 0 0 20px;">
			                Contact: <a href="mailto:compath.dev@gmail.com" style="color: #FF3124; text-decoration: none;">compath.dev@gmail.com</a>
			            </p>
			            <p style="font-size: 14px; color: #999999; text-align: center; margin: 0;">
			                © 2024 Compath. All rights reserved.
			            </p>
			        </div>
			    </div>
			</body>
			</html>
			""", code);
	}
}
