using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class VoiceRecognizer : MonoBehaviour {

    public PlayerManager playerManager;

    private AndroidJavaClass _class;
    private AndroidJavaObject instance { get { return _class.GetStatic<AndroidJavaObject>("instance"); } }

    private void Start()
    {
        // Start plugin <span class="eppz inlineCode">Fragment</span>.
        _class = new AndroidJavaClass("com.plugin.speech.pluginlibrary.VoiceRecognizer");
        _class.CallStatic("start");
    }

    public void PlayVoiceRecognition()
    {
        if (Application.platform == RuntimePlatform.Android)
        {
         
            Debug.Log("Call 1 Started");

            // Pass the name of the game object which has the onActivityResult(string recognizedText) attached to it.
            // The speech recognizer intent will return the string result to onActivityResult method of "Main Camera"
            instance.CallStatic("setReturnObject", "VoiceRecognizer");
            Debug.Log("Return Object Set");

            // Setting language is optional. If you don't run this line, it will try to figure out language based on device settings
            instance.CallStatic("setLanguage", "pt_BR");
            Debug.Log("Language Set");

            // The following line sets the maximum results you want for recognition
            instance.CallStatic("setMaxResults", 3);
            Debug.Log("Max Results Set");

            // The following line sets the question which appears on intent over the microphone icon
            instance.CallStatic("changeQuestion", "Olá, fale a tradução que deseja realizar!");
            Debug.Log("Question Set");

            Debug.Log("Call 2 Started");

            // Calls the function from the jar file
            instance.Call("promptSpeechInput");

            Debug.Log("Call End");
        }
    }

   

    void onActivityResult(string recognizedText)
    {
        char[] delimiterChars = { '~' };
        string[] result = recognizedText.Split(delimiterChars);
        playerManager.resultVoiceRecognizer(result[0]);
        //You can get the number of results with result.Length
        //And access a particular result with result[i] where i is an int
        //I have just assigned the best result to UI text
        //GameObject.Find("Text").GetComponent<Text>().text = result[0];
    }
}
