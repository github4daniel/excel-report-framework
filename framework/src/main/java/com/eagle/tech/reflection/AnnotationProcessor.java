package com.eagle.tech.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.eagle.tech.annotation.HeaderStyle;
import com.eagle.tech.annotation.HeaderStyles;
import com.eagle.tech.annotation.MergedRegionStyles;
import com.eagle.tech.annotation.ValueStyle;
import com.eagle.tech.model.AnnotatedReport;
import com.eagle.tech.model.CompositeAttributes;
import com.eagle.tech.model.HeaderAttributes;
import com.eagle.tech.model.MergedRegionAttributes;
import com.eagle.tech.model.ValueAttributes;

public class AnnotationProcessor {

	public static <T extends AnnotatedReport> CompositeAttributes processClassAnnotation(Class<T> clz) {
		HeaderStyles headerStyles = clz.getAnnotation(HeaderStyles.class);
		HeaderStyle ans[] = headerStyles.value();
		HeaderStyle[] orderAns = new HeaderStyle[ans.length];
		for (int i = 0; i < ans.length; i++) {
			orderAns[ans[i].colIndex()] = ans[i];
		}

		List<HeaderAttributes> headerAttList = Arrays.stream(orderAns).map(HeaderAttributes::new)
				.collect(Collectors.toList());
		
		List<MergedRegionAttributes> mRegionAttList = null;
		if(clz.isAnnotationPresent(MergedRegionStyles.class)) {
			MergedRegionStyles mergedStyles = clz.getAnnotation(MergedRegionStyles.class);
			mRegionAttList = Arrays.stream(mergedStyles.value()).map(MergedRegionAttributes::new).collect(Collectors.toList());
		}
		
		return new CompositeAttributes(headerAttList, mRegionAttList);
	}

	public static <T extends AnnotatedReport> List<ValueAttributes> processFieldAnnotation(Class<T> clz) {

		Field[] fds = clz.getDeclaredFields();
		Field[] orderedFds = new Field[fds.length];

		for (int i = 0; i < fds.length; i++) {
			if (!fds[i].isAnnotationPresent(ValueStyle.class)) {
				continue;
			}
			ValueStyle an = fds[i].getAnnotation(ValueStyle.class);
			orderedFds[an.colIndex()] = fds[i];
		}

		return Arrays.stream(orderedFds).filter(Objects::nonNull).map(ValueAttributes::new)
				.collect(Collectors.toList());

	}

}
