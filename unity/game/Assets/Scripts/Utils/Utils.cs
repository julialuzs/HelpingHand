using System.Collections;
using UnityEngine.Networking;
using UnityEngine.Events;
using System.Text;
using UnityEngine;

public static class Methods
{

    public static Color SetAlpha(this Color color, float alpha)
    {
        return new Color(color.r, color.g, color.b, alpha);

    }

    public static bool checkForConnection()
    {
        return !(Application.internetReachability == NetworkReachability.NotReachable);
    }

    public static IEnumerator CheckInternetConnection(UnityAction<bool> action)
    {
        PlayerLogger.Log("Methods", "CheckInternetConnection", "Trying to ping at google.com");
        UnityWebRequest www = new UnityWebRequest("https://google.com");
        www.timeout = 10; //seconds
        yield return www.SendWebRequest();
        if (!www.isDone || (www.error != null))
        {
            PlayerLogger.Log("Methods", "CheckInternetConnection", "Failed to ping at google.com. Probably there's no internet connection available.");
            action(false);
        }
        else
        {
            PlayerLogger.Log("Methods", "CheckInternetConnection", "Succefully pinged at google.com. The error must be in the Vlibras service.");
            action(true);
        }
    }
    public static UnityWebRequest Post(string url, string bodyJsonString, string contentType)
    {
        var request = new UnityWebRequest(url, "POST");
        byte[] bodyRaw = Encoding.UTF8.GetBytes(bodyJsonString);
        request.uploadHandler = (UploadHandler)new UploadHandlerRaw(bodyRaw);
        request.downloadHandler = (DownloadHandler)new DownloadHandlerBuffer();
        request.SetRequestHeader("Content-Type", contentType);

        return request;

    }
}
