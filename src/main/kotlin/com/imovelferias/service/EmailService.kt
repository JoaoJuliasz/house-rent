package com.imovelferias.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class EmailService(
    private val emailSender: JavaMailSender
) {

    fun sendSimpleMessage(receiver: String) {
        val simpleMessage = SimpleMailMessage()
        simpleMessage.from = "noreply@imovelferias.com"
        simpleMessage.setTo(receiver)
        simpleMessage.subject = "Welcome to Imovel Ferias"
        simpleMessage.text = "This is a test email"
        emailSender.send(simpleMessage)
    }

}