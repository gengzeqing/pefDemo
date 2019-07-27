package com.example.demo.templat;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @author carlosxiao
 */
@Slf4j
public class FtlStringBuilder {

    /**
     * 通过字符串模板解析出对应的文本
     *
     * @param textTemplate      模板文本
     * @param replacementValues 需要替换的值
     * @param templateName      模板名字
     * @return
     */
    private static String processTextTemplateObject(String textTemplate, Object replacementValues, String templateName) {
        if (replacementValues == null) {
            return textTemplate;
        }

        if (textTemplate == null || "".equals(textTemplate)) {
            throw new IllegalArgumentException("The textTemplate cannot be null or empty string, " +
                    "please pass in at least something in the template or do not call this method");
        }

        // setup freemarker
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);

        // Specify how templates will see the data-model
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_25));

        // get the template
        Template template;
        try {
            template = new Template(templateName, new StringReader(textTemplate), cfg);
        } catch (IOException e) {
            throw new RuntimeException("Failure while creating freemarker template", e);
        }

        Writer output = new StringWriter();
        try {
            template.process(replacementValues, output);
        } catch (TemplateException e) {
            throw new RuntimeException("Failure while processing freemarker template", e);
        } catch (IOException e) {
            throw new RuntimeException("Failure while sending freemarker output to stream", e);
        }

        return output.toString();
    }

    public static String processTextTemplate(String textTemplate, Map<String, String> replacementValues, String templateName) {
        return processTextTemplateObject(textTemplate, replacementValues, templateName);
    }

    public static String processTextTemplateFromObject(String textTemplate, Map<String, Object> replacementValues, String templateName) {
        return processTextTemplateObject(textTemplate, replacementValues, templateName);
    }
}
