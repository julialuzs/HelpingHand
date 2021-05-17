using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ReceiveResult : MonoBehaviour {
    public PlayerManager playerManager;

    void onActivityResult(string recognizedText){
        char[] delimiterChars = {'~'};
        string[] result = recognizedText.Split(delimiterChars);
        playerManager.resultVoiceRecognizer(result[0]);
        //You can get the number of results with result.Length
        //And access a particular result with result[i] where i is an int
        //I have just assigned the best result to UI text
        //GameObject.Find("Text").GetComponent<Text>().text = result[0];
    }
}
