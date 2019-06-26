package com.topica.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes({ "com.topica.anno.Constant" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class ConstantProcessor extends AbstractProcessor {

	private Filer filer;
	private Messager messager;

	@Override
	public void init(ProcessingEnvironment env) {
		filer = env.getFiler();
		messager = env.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		
		//iterate all annotations
		for (TypeElement anno : annotations) {
			
			Set<? extends Element> es = env.getElementsAnnotatedWith(anno);

			for (Element element : es) {
				//get modifiers of field
				Set<Modifier> modifiers = element.getModifiers();
				
				//constant need to be final and static
				if (!(modifiers.contains(Modifier.FINAL) && modifiers.contains(Modifier.STATIC))) {
					messager.printMessage(Kind.ERROR, "Field isn't static or final", element);
				}
			}
		}
		return true;
	}

}
