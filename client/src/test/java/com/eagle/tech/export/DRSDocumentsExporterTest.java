package com.eagle.tech.export;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DRSDocumentsExporterTest {

	@Test
	void testGenerateDocumentsExport() {
		DRSDocumentsExporter d = new DRSDocumentsExporter();
		d.generateDocumentsExport();
	}

}
