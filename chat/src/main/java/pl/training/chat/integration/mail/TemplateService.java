package pl.training.chat.integration.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private static final String LANGUAGE_SEPARATOR = "_";

    private final TemplateEngine templateEngine;
    @Value("${languages}")
    private final Set<String> languages;

    public String evaluate(String templateBaseName, Map<String, Object> data, String language) {
        var context = new Context();
        context.setVariables(data);
        var templateName = getTemplateName(templateBaseName, language);
        return templateEngine.process(templateName, context);
    }

    private String getTemplateName(String baseName, String language) {
        return languages.contains(language) ? baseName + LANGUAGE_SEPARATOR + language : baseName;
    }

}
