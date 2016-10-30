package com.robohorse.robopojogenerator.generator.postprocessors;

import com.robohorse.robopojogenerator.generator.common.ClassItem;
import com.robohorse.robopojogenerator.generator.common.ClassDecorator;
import com.robohorse.robopojogenerator.generator.consts.ClassTemplate;
import com.robohorse.robopojogenerator.models.GenerationModel;

import javax.inject.Inject;
import java.util.Map;

/**
 * Created by vadim on 23.10.16.
 */
public class AutoValueClassPostProcessor extends JavaPostProcessor {
    @Inject
    public AutoValueClassPostProcessor() {
    }

    @Override
    public String proceedClassBody(ClassItem classItem, GenerationModel generationModel) {
        final StringBuilder classBodyBuilder = new StringBuilder();
        final Map<String, ClassDecorator> classFields = classItem.getClassFields();
        for (String objectName : classFields.keySet()) {
            final String itemNameFormatted = generateHelper.formatClassField(objectName);
            classBodyBuilder.append(classTemplateHelper.createAutoValueField(
                    classFields.get(objectName).getJavaItem(),
                    itemNameFormatted,
                    classItem.getAnnotation()));

        }
        classBodyBuilder.append(ClassTemplate.NEW_LINE);
        classBodyBuilder.append(classTemplateHelper.createTypeAdapter(classItem));
        return classBodyBuilder.toString();
    }

    @Override
    public String createClassTemplate(ClassItem classItem, String classBody) {
        return classTemplateHelper.createClassBodyAbstract(classItem, classBody);
    }
}