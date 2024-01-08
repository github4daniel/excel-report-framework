package com.eagle.tech.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.eagle.tech.model.AnnotatedReport;
import com.eagle.tech.model.CompositeAttributes;
import com.eagle.tech.model.HeaderAttributes;
import com.eagle.tech.model.MergedRegionAttributes;
import com.eagle.tech.model.ValueAttributes;
import com.eagle.tech.reflection.AnnotationProcessor;

public abstract class AbstractDocumentExporter<T extends AnnotatedReport> {

	protected String excelSheetName;
	protected String exportFileName;

	protected List<T> docs;

	public void generateDocumentsExport() {

		int row = 0;
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(excelSheetName);
		

		CompositeAttributes cAttributes = AnnotationProcessor.processClassAnnotation(getClassName());
		List<MergedRegionAttributes> mAttrs = cAttributes.getMergeRegionAttributesList();

		if (mAttrs != null) {
			Row region = sheet.createRow(row++);
			MergedRegionAttributes.createMergedRegion(wb, sheet, region, mAttrs);
		}
		Row header = null;
		
		List<HeaderAttributes> hAttrs = cAttributes.getHeaderAttributesList();
		sheet.setAutoFilter(new CellRangeAddress(row, row, 0, hAttrs.size()-1));
		
		
		header = sheet.createRow(row++);
		HeaderAttributes.createHeader(wb, sheet, header, hAttrs);
		sheet.createFreezePane(0, row);
		
		

		final int next = row;

		List<ValueAttributes> attributes = AnnotationProcessor.processFieldAnnotation(getClassName());

		List<T> eData = getExportData();

		if (eData != null) {
			IntStream.range(0, eData.size()).forEach(index -> {
				ValueAttributes.createRow(sheet.createRow(index + next), wb, attributes, eData.get(index));
			});
		}
		OutputStream o;

		try {
			o = new FileOutputStream(exportFileName);
			wb.write(o);
			o.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected abstract Class<? extends AnnotatedReport> getClassName();

	protected abstract List<T> getExportData();
}
