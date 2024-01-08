package com.eagle.tech.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.eagle.tech.model.DRSDocuments;

/*
 * This class simulate service implementation to populate the list of object
 */
public class DRSDocumentService {

	public static List<DRSDocuments> getDRSDocumets() {
		List<DRSDocuments> exports = new ArrayList<DRSDocuments>();
		
		IntStream.range(0, 300).forEach(e->{
			DRSDocuments drs = new DRSDocuments();
			drs.setDrsCohort("2012");
			drs.setDrsCohortId(50L);
			drs.setGrantee("Municipal Gurantee");
			drs.setGrantGroupId(501L);
			drs.setGrantAwardId(100L);
			drs.setGrantNumber(
					"That is test for wrap so see how to fit into a cell. "
					+ "Its length exceeds default 256*20. This includes a "
					+ "long word abcdefghijkmnopq");
			drs.setRegion(32L);
			drs.setComission(new BigDecimal("145.78"));
			exports.add(drs);
		});
		
		return exports;
	}

}
