package com.eagle.tech.model;

import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eagle.tech.annotation.MergedRegionStyle;

public class MergedRegionAttributes {

	private MergedRegionStyle an;
	private String mergedColName;
	private HorizontalAlignment alignment;
	private boolean bold;
	private boolean italic;
	private FillPatternType fillPattern;
	private HSSFColorPredefined fillBackgroundColor;
	private BorderStyle borderBottom;
	private BorderStyle borderLeft;
	private BorderStyle borderRight;
	private BorderStyle borderTop;
	private VerticalAlignment verticalAlignment;
	private boolean wrapText;
	private int begIndex;
	private int endIndex;

	public MergedRegionAttributes(MergedRegionStyle an) {
		this.an = an;
		extractAttributes();
	}

	private void extractAttributes() {
		begIndex = an.begIndex();
		endIndex = an.endIndex();
		mergedColName = an.mergedColName();
		alignment = an.alignment();
		bold = an.bold();
		italic = an.italic();

		fillPattern = an.fillPattern();
		fillBackgroundColor = an.fillBackgroundColor();

		borderBottom = an.borderBottom();
		borderLeft = an.borderLeft();
		borderRight = an.borderRight();
		borderTop = an.borderTop();

		verticalAlignment = an.verticalAlignment();
		wrapText = an.wrapText();

	}

	public static void createMergedRegion(XSSFWorkbook wb, XSSFSheet sheet, Row region,
			List<MergedRegionAttributes> mAttrs) {
		IntStream.range(0, mAttrs.size()).forEach(index -> {
			MergedRegionAttributes a = mAttrs.get(index);

			Cell c = region.createCell(a.begIndex);

			CellStyle headerStyle = wb.createCellStyle();
			Font font = wb.createFont();
			font.setBold(a.bold);
			font.setItalic(a.italic);
			headerStyle.setFont(font);
			headerStyle.setAlignment(a.alignment);
			headerStyle.setFillPattern(a.fillPattern);
			headerStyle.setFillForegroundColor(a.fillBackgroundColor.getIndex());
			headerStyle.setBorderBottom(a.borderBottom);
			headerStyle.setBorderLeft(a.borderLeft);
			headerStyle.setBorderRight(a.borderRight);
			headerStyle.setBorderTop(a.borderTop);
			headerStyle.setVerticalAlignment(a.verticalAlignment);
			headerStyle.setWrapText(a.wrapText);
			c.setCellStyle(headerStyle);
			c.setCellValue(a.mergedColName);
			
			sheet.addMergedRegion(new CellRangeAddress(region.getRowNum(), region.getRowNum(), a.begIndex, a.endIndex));

		});
	}
}
