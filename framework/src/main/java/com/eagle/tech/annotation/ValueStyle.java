package com.eagle.tech.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValueStyle {
	int colIndex();
	HorizontalAlignment align() default HorizontalAlignment.LEFT;
	boolean wrapText() default true;
	HSSFColor.HSSFColorPredefined textColor() default HSSFColor.HSSFColorPredefined.BLACK;
}
