using UnityEngine;
using UnityEngine.UI;


public abstract class PlayerLogger : MonoBehaviour
{

    public static PlayerLogger instance;

    protected virtual void Start()
    {
        PlayerLogger.instance = this;
    }

    protected abstract void write(string text);

    public static void Log(string text)
    {
        if (PlayerLogger.instance != null)
            PlayerLogger.instance.write(text);

        Debug.Log(text);
    }

    public static void Log(string classSign, string methodSign, string text)
    {
        PlayerLogger.Log(classSign + "." + methodSign + ": " + text);
    }

}


