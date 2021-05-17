using UnityEngine;

public class ToastAndroid
{
    public static void ShowToast(string textToShow)
    {

        if (Application.platform == RuntimePlatform.Android) {

            AndroidJavaClass toastClass =
                   new AndroidJavaClass("android.widget.Toast");

            object[] toastParams = new object[3];
            AndroidJavaClass unityActivity =
              new AndroidJavaClass("com.unity3d.player.UnityPlayer");
            toastParams[0] =
                         unityActivity.GetStatic<AndroidJavaObject>
                                   ("currentActivity");
            toastParams[1] = textToShow.ToCharArray();
            toastParams[2] = toastClass.GetStatic<int>
                                   ("LENGTH_LONG");

            AndroidJavaObject toastObject =
                            toastClass.CallStatic<AndroidJavaObject>
                                          ("makeText", toastParams);
            toastObject.Call("show");
        }
        else 
            PlayerLogger.Log("ToastAndroid", "ShowToast", "Not running on Android. Toast Message: " + textToShow);


    }
}
