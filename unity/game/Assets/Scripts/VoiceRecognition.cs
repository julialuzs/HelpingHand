/**********************
********LAVID**********
***VLibras Project*****
*------------------------------------------------------------------------
*Description:
*
*This class needs an android plugin to call Google Speech API
*Before recognition, it verifies if there is internect connection. 
*If there is no connection, the application doesn't run and shows an error message.
*
*---------------------------------------------------------------------------
*Plugin directory: 
* - Assets/Plugins/Android
*References:
* - http://docs.unity3d.com/ScriptReference/AndroidJavaClass.html
* - https://msdn.microsoft.com/pt-br/library/system.net.webclient(v=vs.110).aspx
*
*------------------------------------------------------------------------
*Author: Claudiomar Araujo
*claudiomar.araujo@lavid.ufpb.br
***********************/

using UnityEngine;
using System.Collections;
using UnityEngine.UI;
using System;

public class VoiceRecognition {

	AndroidJavaClass unity;
	AndroidJavaObject currentActivity;

	string voiceText = "";

	public VoiceRecognition()
	{
#if !UNITY_EDITOR
		unity = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		currentActivity = unity.GetStatic<AndroidJavaObject>("currentActivity");
#endif
	}

	// Calls Google Speech from plugin method and returns recognized text
	public string callRecognition()
	{
#if !UNITY_EDITOR
			voiceText = currentActivity.Call<string>("callGoogleSpeech");
#endif

		return voiceText;
	}

}
