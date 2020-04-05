package com.appli.nyx.formx.utils;


public class ClusterUtils {

    public static String getCollectionPath(String parentPath, String clusterId) {
        StringBuilder builder = new StringBuilder();

        builder.append(parentPath);
        builder.append("/");
        builder.append(clusterId);
        builder.append("/");
        builder.append(clusterId);
        return builder.toString();
    }

    public static String getDocumentPath(String parentPath, String clusterId) {
        StringBuilder builder = new StringBuilder();

        builder.append(parentPath);
        builder.append("/");
        builder.append(clusterId);
        return builder.toString();
    }

    public static boolean isParentIsROOT(String path) {
        String[] rawSegments = path.split("/");
        // .collection(CLUSTER_PATH).document(SessionUtils.getUserUid()).collection(CLUSTER_DATA)
        return rawSegments.length == 3;
    }

    public static String getParentPath(String path) {
        StringBuilder builder = new StringBuilder();
        String[] rawSegments = path.split("/");

        for (int i = 0; i < rawSegments.length - 2; i++) {
            if (i > 0) {
                builder.append("/");
            }
            builder.append(rawSegments[i]);
        }

        return builder.toString();
    }
}
