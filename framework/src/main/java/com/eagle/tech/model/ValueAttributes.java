package com.eagle.tech.model;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eagle.tech.annotation.ValueStyle;

public class ValueAttributes {

	private Field fd;
	private HorizontalAlignment hAlignment;
	private boolean wrapText;
	private HSSFColor.HSSFColorPredefined textColor;

	public ValueAttributes(Field field) {
		this.fd = field;
		extractAttributes();
	}

	private void extractAttributes() {
		ValueStyle anStyle = fd.getAnnotation(ValueStyle.class);
		hAlignment = anStyle.align();
		wrapText = anStyle.wrapText();
		textColor = anStyle.textColor();
	}

	public <T> Object getValue(T t) {
		Object o = null;
		try {
			fd.setAccessible(true);
			o = fd.get(t);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return o;
	}

	public static <T> void createRow(XSSFRow row, XSSFWorkbook wb, List<ValueAttributes> attributes, T doc) {

		IntStream.range(0, attributes.size()).forEach(index -> {
			ValueAttributes a = attributes.get(index);
			Cell c = row.createCell(index);

			CellStyle rowStyle = wb.createCellStyle();
			rowStyle.setAlignment(a.hAlignment);
			rowStyle.setWrapText(a.wrapText);

			Font font = wb.createFont();
			font.setColor(a.textColor.getIndex());
			rowStyle.setFont(font);

			c.setCellStyle(rowStyle);

			Object obj = a.getValue(doc);
			if (obj instanceof String) {
				c.setCellValue((String) obj);
			}
			if (obj instanceof Number) {
				c.setCellValue(((Number) obj).doubleValue());
			}
			if (obj instanceof Calendar) {
				c.setCellValue((Calendar) obj);
			}
			if (obj instanceof Date) {
				c.setCellValue((Date) obj);
			}
			if (obj instanceof LocalDate) {
				c.setCellValue((LocalDate) obj);
			}
			if (obj instanceof LocalDateTime) {
				c.setCellValue((LocalDateTime) obj);
			}
			if (obj instanceof RichTextString) {
				c.setCellValue((RichTextString) obj);
			}
			if (obj instanceof Boolean) {
				c.setCellValue(obj == null ? false : (Boolean) obj);
			}
		});

	}
}
