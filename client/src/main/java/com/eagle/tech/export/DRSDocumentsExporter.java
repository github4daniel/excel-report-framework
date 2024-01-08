package com.eagle.tech.export;

import java.util.List;

import com.eagle.tech.model.AnnotatedReport;
import com.eagle.tech.model.DRSDocuments;
import com.eagle.tech.service.DRSDocumentService;

public class DRSDocumentsExporter extends AbstractDocumentExporter<DRSDocuments> {
	
	public DRSDocumentsExporter() {
		this.excelSheetName="DRS Documents Upload Report";
		this.exportFileName="DRSDocumentsUploadReport.xlsx";
	}

	@Override
	protected Class<? extends AnnotatedReport> getClassName() {
		return DRSDocuments.class;
	}

	@Override
	protected List<DRSDocuments> getExportData() {
		List<DRSDocuments> drsDocumentsExportList = DRSDocumentService.getDRSDocumets();
		return drsDocumentsExportList;
	}

}
