using UnityEngine;
using System.Collections;

public class ExportLayers{

	public enum ExportLayer
    {
        Export_Layer,
        Download_Layer,
        Progress_Layer,
        Share_Layer,
        Progress_Download_Layer,
        OnLockExport,
        OnLockShare,
        All
    }

    public enum ConnectionStatusError
    {
        INTERNET_CONNECTION_FAILURE,
        DOWNLOAD_CONNECTION_FAILURE,
        CONNECTION_TIMEOUT_FAILURE,
        ERROR_CONNECTION_FAILURE,
        DEFAULT
    }
}
