package com.tfg.futstats.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setTo(to);
        helper.setSubject(subject);

        String decoratedBody = decorateEmail(body);

        helper.setText(decoratedBody, true);
        mailSender.send(message);
    }

    private String decorateEmail(String bodyContent) {

        return """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Correo de Notificación</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f9f9f9;
                            margin: 0;
                            padding: 0;
                        }
                        .email-container {
                            background-color: rgba(0, 0, 0, 0.6);
                            color: #fff;
                            max-width: 600px;
                            margin: 2rem auto;
                            padding: 1.5rem;
                            border-radius: 8px;
                            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                        }
                        .email-header {
                            text-align: center;
                            margin-bottom: 2rem;
                        }
                        .email-header h1 {
                            font-size: 2rem;
                            margin: 0;
                            color: #4caf50;
                        }
                        .email-content {
                            font-size: 1rem;
                            line-height: 1.5;
                            margin-bottom: 2rem;
                        }
                        .email-content h2 {
                            font-size: 1.5rem;
                            margin: 0 0 1rem;
                            color: #81c784;
                        }
                        .email-content p {
                            margin: 0.5rem 0;
                        }
                        .email-footer {
                            text-align: center;
                            font-size: 0.9rem;
                            margin-top: 2rem;
                            color: #aaa;
                        }
                        .email-logo {
                            display: block;
                            margin: 0 auto 1rem;
                            max-width: 150px;
                        }
                    </style>
                </head>
                <body>
                    <div class="email-container">
                        <div class="email-header">
                            <h1>Notificación</h1>
                        </div>
                        <div class="email-content">
                            %s
                        </div>
                        <div class="email-footer">
                            <p>&copy; 2025 FutStats. Todos los derechos reservados.</p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(bodyContent);
    }
}
