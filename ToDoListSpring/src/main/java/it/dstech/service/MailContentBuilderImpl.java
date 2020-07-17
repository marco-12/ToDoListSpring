package it.dstech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailContentBuilderImpl implements MailContentBuilder {
	
    private TemplateEngine templateEngine;
    
    @Autowired
    public MailContentBuilderImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }	

	@Override
	public String build(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("emailTemplate", context);
	}

}