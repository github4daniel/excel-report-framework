package gov.hhs.acf.ohs.hses.drs.export;

/**
 * @author Devon Russell
 */

import gov.hhs.acf.ohs.hses.export.common.ExportPrintFormatter;
import gov.hhs.acf.ohs.hses.export.common.ExportUtilities;
import gov.hhs.acf.ohs.hses.model.exports.ExportRequest;
import gov.hhs.acf.ohs.hses.model.exports.security.ExportsUser;
import gov.hhs.acf.ohs.hses.model.grant.DrsCorrespondence;
import gov.hhs.acf.ohs.hses.model.grant.service.DrsExportsService;
import gov.hhs.acf.ohs.hses.shared.exceptions.HsemsException;
import gov.hhs.acf.ohs.hses.view.api.ExportGenerator;
import gov.hhs.acf.ohs.hses.view.api.ServiceProvider;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;

public class DrsCorrespondenceExportGenerator implements ExportGenerator {

	private static final long serialVersionUID = 1L;

	public DrsCorrespondenceExportGenerator() {
	}

	private Workbook wb;
	private Sheet sheet;
	private int rowNumber = 0;
	private int headerCol = 0;
	private int firstRow = 0;
	private int lastRow;
	private int firstColumn = 0;
	private int lastColumn;

	@Override
	public void generateReport(OutputStream outputStream, ServiceProvider serviceProvider, ExportRequest request, ExportsUser user)
			throws HsemsException {
		createWorkSheet();
		createHeader();
		try {
			Long grantGroupId = (Long) request.getParameter();

			final DrsExportsService exportsService = (DrsExportsService) serviceProvider.getService(DrsExportsService.SERVICE_NAME);
			List<DrsCorrespondence> drsCorrespondenceList = exportsService.getGrantCorresondencesByGrantGroup(grantGroupId);

			for (DrsCorrespondence drsCorrespondence : drsCorrespondenceList) {
				try {
					if (drsCorrespondence != null) {
						createDataRow(drsCorrespondence);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			lastRow = rowNumber - 1;
		} catch (HsemsException e) {
			throw e;
		}
		sheet.setAutoFilter(new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn));
		createGrouping();

		try {
			ExportPrintFormatter.format(wb, sheet, (short) 1, -1, -1, 0, 1);
			wb.write(outputStream);
			outputStream.close();
		} catch (IOException e) {
			throw new HsemsException("Unable to generate DRS Correspondence export", e);
		}
	}

	private void createWorkSheet() {
		wb = new XSSFWorkbook();
		String sheetName = "DRS Correspondence";
		sheet = wb.createSheet(sheetName);
	}

	private void createHeader() {
		headerCol = 0;

		CellStyle headerStyle1 = ExportUtilities.createDefaultHeaderStyle(wb, HorizontalAlignment.CENTER);
		headerStyle1.setWrapText(true);

		Row header = sheet.createRow(rowNumber++);
		header.setHeightInPoints(30);

		ExportUtilities.createCell(header, headerCol++, headerStyle1, "Region"                               );
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "State"                                );
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "Grant"           + "\n" + "Number"    );
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "Grantee"         + "\n" + "Name"      );
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "DRS" 		+ "\n" + "Cohort"    );
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "Program"         + "\n" + "Specialist");
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "Correspondence"  + "\n" + "Status");
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "DRS"  		+ "\n" + "Status");
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "DRS"             + "\n" + "Notified");
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "Type"                                 );
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "Approved"        + "\n" + "Date"      );
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "Sent"            + "\n" + "Date"      );
		ExportUtilities.createCell(header, headerCol++, headerStyle1, "Received"        + "\n" + "Date"      );

		lastColumn = headerCol - 1;
	}

	private void createDataRow(DrsCorrespondence drsCorrespondence) throws ParseException {
		int dataCol = 0;
		
		CellStyle cellStyle_center = wb.createCellStyle();
		cellStyle_center.setAlignment(HorizontalAlignment.CENTER);

		CellStyle cellStyle_left = wb.createCellStyle();
		cellStyle_left.setAlignment(HorizontalAlignment.LEFT);
		
		CellStyle cellStyle_date = wb.createCellStyle();
		CreationHelper creationHelper = wb.getCreationHelper();
		cellStyle_date.setDataFormat(creationHelper.createDataFormat().getFormat("m/d/yy"));


		Row row = sheet.createRow(rowNumber++);
		
		ExportUtilities.createCell(row, dataCol++, cellStyle_center, drsCorrespondence.getRegion());
		ExportUtilities.createCell(row, dataCol++, cellStyle_center, ExportUtilities.getValue(drsCorrespondence.getState()));
		ExportUtilities.createCell(row, dataCol++, cellStyle_left, ExportUtilities.getValue(drsCorrespondence.getGrantNumber()));
		ExportUtilities.createCell(row, dataCol++, cellStyle_left, ExportUtilities.getValue(drsCorrespondence.getGranteeName()));
		ExportUtilities.createCell(row, dataCol++, cellStyle_center, ExportUtilities.getValue(drsCorrespondence.getCohort()));
		ExportUtilities.createCell(row, dataCol++, cellStyle_left, ExportUtilities.getValue(drsCorrespondence.getProgramSpecialist()));
		ExportUtilities.createCell(row, dataCol++, cellStyle_left, ExportUtilities.getValue(drsCorrespondence.getStatus()));
		ExportUtilities.createCell(row, dataCol++, cellStyle_left, ExportUtilities.getValue(drsCorrespondence.getDrsStatus()));
		ExportUtilities.createCell(row, dataCol++, cellStyle_center, ExportUtilities.getYesNoText(Boolean.TRUE.equals(drsCorrespondence.isNotified()) ? 1L : 0L));
		ExportUtilities.createCell(row, dataCol++, cellStyle_left, ExportUtilities.getValue(drsCorrespondence.getType()));
		ExportUtilities.createCell(row, dataCol++, cellStyle_date, drsCorrespondence.getApprovalDate());
		ExportUtilities.createCell(row, dataCol++, cellStyle_date, drsCorrespondence.getSentDate());
		ExportUtilities.createCell(row, dataCol++, cellStyle_date, drsCorrespondence.getReceivedDate()	);
	
	}

	private void createGrouping() {
		// Auto size columns
		for (int i = 0; i < headerCol; i++) {
			sheet.autoSizeColumn(i);
		}

		// Adjusting width of specific columns to be able to read the column headers.
		// 0 = Region, 1 = State, 2 = Grant Number, 6 = Status Type
		sheet.setColumnWidth(0, sheet.getColumnWidth(0) + 900);
		sheet.setColumnWidth(1, sheet.getColumnWidth(1) + 1000);
		sheet.setColumnWidth(2, sheet.getColumnWidth(2) + 300);
		sheet.setColumnWidth(6, sheet.getColumnWidth(6) + 600);

		// Freeze panel for row and column headers
		sheet.createFreezePane(4, 1);
	}

	@Override
	public String getFileName(ServiceProvider serviceProvider, ExportRequest request, ExportsUser user) throws HsemsException {
		return "Drs_Correspondence_Report.xlsx";
	}

}
