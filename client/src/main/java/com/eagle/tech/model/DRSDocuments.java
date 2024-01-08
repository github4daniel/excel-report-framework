package com.eagle.tech.model;

import java.math.BigDecimal;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import com.eagle.tech.annotation.Formula;
import com.eagle.tech.annotation.HeaderStyle;
import com.eagle.tech.annotation.HeaderStyles;
import com.eagle.tech.annotation.MergedRegionStyle;
import com.eagle.tech.annotation.MergedRegionStyles;
import com.eagle.tech.annotation.ValueStyle;

@MergedRegionStyles({@MergedRegionStyle(mergedColName="Default Color", begIndex = 5, endIndex = 6),
@MergedRegionStyle(mergedColName="Override color", begIndex = 0, endIndex = 3, fillBackgroundColor = HSSFColor.HSSFColorPredefined.AQUA )})
@HeaderStyles({ @HeaderStyle(colName = "Grant Award Number", colIndex = 0),
		@HeaderStyle(colName = "Grant Group Id", colIndex = 1), @HeaderStyle(colName = "Region", colIndex = 2),
		@HeaderStyle(colName = "Grant Number", colIndex = 3), @HeaderStyle(colName = "Garantee", colIndex = 4),
		@HeaderStyle(colName = "DRS Cohort", colIndex = 5, colWidth = 256 * 10),
		@HeaderStyle(colName = "Commission", colIndex = 6, colWidth = 256 * 16) })
public class DRSDocuments implements AnnotatedReport {

	@ValueStyle(colIndex = 0)
	private Long grantAwardId;
	@ValueStyle(colIndex = 1)
	private Long grantGroupId;

	// no display if annotation not exist
	private Long drsCohortId;

	@ValueStyle(colIndex = 2, align = HorizontalAlignment.CENTER)
	private Long region;

	@ValueStyle(colIndex = 3)
	private String grantNumber;

	@ValueStyle(colIndex = 4)
	private String grantee;

	@ValueStyle(colIndex = 5, align = HorizontalAlignment.CENTER)
	private String drsCohort;

	@ValueStyle(colIndex = 6, align = HorizontalAlignment.RIGHT, textColor = HSSFColor.HSSFColorPredefined.RED)
	@Formula(formula = "SUM(A:A)-SUM(B:B)")
	private BigDecimal comission;
	

	public void setGrantAwardId(Long grantAwardId) {
		this.grantAwardId = grantAwardId;
	}

	public void setGrantGroupId(Long grantGroupId) {
		this.grantGroupId = grantGroupId;
	}

	public void setDrsCohortId(Long drsCohortId) {
		this.drsCohortId = drsCohortId;
	}

	public void setRegion(Long region) {
		this.region = region;
	}

	public void setGrantNumber(String grantNumber) {
		this.grantNumber = grantNumber;
	}

	public void setGrantee(String grantee) {
		this.grantee = grantee;
	}

	public void setDrsCohort(String drsCohort) {
		this.drsCohort = drsCohort;
	}

	public void setComission(BigDecimal comission) {
		this.comission = comission;
	}
}
