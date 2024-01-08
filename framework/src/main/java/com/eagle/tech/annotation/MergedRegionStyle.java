package com.eagle.tech.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MergedRegionStyle{
	
	String mergedColName();
	int begIndex();
	int endIndex();
	boolean bold() default true;
	boolean italic() default false;
	HorizontalAlignment alignment() default HorizontalAlignment.CENTER;
	FillPatternType fillPattern() default FillPatternType.SOLID_FOREGROUND;
	HSSFColor.HSSFColorPredefined fillBackgroundColor() default HSSFColor.HSSFColorPredefined.GOLD;
	BorderStyle borderBottom() default BorderStyle.THIN;
	BorderStyle borderLeft() default BorderStyle.THIN;
	BorderStyle borderRight() default BorderStyle.THIN;
	BorderStyle borderTop() default BorderStyle.THIN;
	VerticalAlignment verticalAlignment() default VerticalAlignment.CENTER;
	boolean wrapText() default true;

}
