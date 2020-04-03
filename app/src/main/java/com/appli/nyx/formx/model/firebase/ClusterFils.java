package com.appli.nyx.formx.model.firebase;

import com.appli.nyx.formx.model.firebase.enumeration.TypeClusterFils;

public class ClusterFils extends Cluster {

	public TypeClusterFils type;

	public String path;

	public ClusterFils(TypeClusterFils type) {
		this.type = type;
	}
}
