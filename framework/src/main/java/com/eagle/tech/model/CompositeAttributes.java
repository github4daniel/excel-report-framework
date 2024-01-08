package com.eagle.tech.model;

import java.util.List;

public class CompositeAttributes {
	
	List<HeaderAttributes> headerAttributesList;
	List<MergedRegionAttributes> mergeRegionAttributesList;
	
	public CompositeAttributes(List<HeaderAttributes> headerAttributesList,
			List<MergedRegionAttributes> mergeRegionAttributesList) {
		
		this.headerAttributesList= headerAttributesList;
		this.mergeRegionAttributesList=mergeRegionAttributesList;
	}

	public List<HeaderAttributes> getHeaderAttributesList() {
		return headerAttributesList;
	}

	public void setHeaderAttributesList(List<HeaderAttributes> headerAttributesList) {
		this.headerAttributesList = headerAttributesList;
	}

	public List<MergedRegionAttributes> getMergeRegionAttributesList() {
		return mergeRegionAttributesList;
	}

	public void setMergeRegionAttributesList(List<MergedRegionAttributes> mergeRegionAttributesList) {
		this.mergeRegionAttributesList = mergeRegionAttributesList;
	}

}
