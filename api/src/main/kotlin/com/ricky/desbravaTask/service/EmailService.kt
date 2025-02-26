package com.ricky.desbravaTask.service

import com.ricky.desbravaTask.enums.ConfiguracaoEnum
import com.ricky.desbravaTask.exceptions.EmailErrorException
import com.ricky.desbravaTask.utils.I18n
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context


@Service
class EmailService(
    private val mailSender: JavaMailSender,
    private val templateEngine: TemplateEngine,
    private val i18n: I18n,
    private val configuracaoService: ConfiguracaoService
) {

    private fun inicializacao(helper: MimeMessageHelper) {
        val host = configuracaoService.findById(ConfiguracaoEnum.EMAIL_HOST).configuracao
        val userName = configuracaoService.findById(ConfiguracaoEnum.EMAIL_USERNAME).configuracao
        val password = configuracaoService.findById(ConfiguracaoEnum.EMAIL_PASSWORD).configuracao
        val port = configuracaoService.findById(ConfiguracaoEnum.EMAIL_PORT).configuracao

        (mailSender as JavaMailSenderImpl).host = host
        (mailSender).port = port.toInt()
        (mailSender).username = userName
        (mailSender).password = password

        helper.setFrom(userName)
    }

    fun sendEmail(to: String, cod: String) {
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        val helper: MimeMessageHelper = MimeMessageHelper(mimeMessage, "UTF-8")
        val context = Context()

        inicializacao(helper)
        context.setVariable("message", i18n.getMessage("email.body", cod));

        try {
            helper.setTo(to)
            helper.setSubject(i18n.getMessage("email.subject"))
            val htmlContent = templateEngine.process("email-template.html", context)
            helper.setText(htmlContent, true)
            mailSender.send(mimeMessage)
        } catch (e: Exception) {
            e.printStackTrace()
            throw EmailErrorException()
        }
    }
}