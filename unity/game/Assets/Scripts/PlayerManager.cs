using UnityEngine;
using System.Collections;
using System;
using UnityEngine.UI;
using UnityEngine.Networking;
using LAViD.Structures;
using LAViD.VLibras.Dictionary;
using Newtonsoft.Json;



public class PlayerManager : GenericPlayerManager {


#if UNITY_IOS
	
	private string BASE_URL = Config.BASE_URL_IOS;
#else
    private string BASE_URL = Config.BASE_URL_ANDROID;
#endif

    private string SERVER_URL = Config.TRANSLATOR_URL;

    private const int VERSION = 1;

	private string[] randomAnimationNames = new string[] {
		"[RELAXAR]",
		"[BOCEJAR]",
		"[COCHILAR]",
		"[ESPREGUI_ADA]"
	};

	protected VoiceRecognition voiceRecognizer; //acho que tem que tirar
	public InputField translateScreenText;
	public ScreenManager screenManager;
	private string dictWord = null;
	private string regionPath = "";
	private int regionHash = 1;
	private Trie signs = null;

    private static string currentGloss;
    private static string currentText;
	

    
    public static string getCurrentGloss()
    {
        return currentGloss;
        
    }

    public static string getCurrentText()
    {
        return currentText;
    }

	public override void Start()
	{
#if UNITY_EDITOR
		Caching.ClearCache();
#endif
        base.setRandomAnimations(randomAnimationNames);
		base.Start();
		
		this.gameObject.GetComponent<Dictionary>().AddOnLoadListener(setSigns);
		this.voiceRecognizer = new VoiceRecognition();

		Screen.fullScreen = false;
	}

	private void setSigns() {
		this.signs = this.gameObject.GetComponent<Dictionary>().Signs;
	}

    

	public void playDict(string word)
	{
		PlayerLogger.Log("Requesting dictionary: " + word);
		this.dictWord = word;
		base.gloss = word;
        
		base.playNow(word);
        this.screenManager.setReviewState(false);
        currentGloss = word;
        this.screenManager.changeExportStates(ExportLayers.ExportLayer.OnLockExport, false);

        this.screenManager.hideScreen();
		this.screenManager.setPauseMenuState(false);
	}

	public void playTranslate()
	{
		stopTranslation();
		base.stopAll();

		string text = translateScreenText.text;
        currentText = text;
        this.screenManager.setReviewState(false);
        if (!String.IsNullOrEmpty(text))
        {
            translateScreenText.text = "";
            this.screenManager.changeExportStates(ExportLayers.ExportLayer.OnLockExport, false);
            if (Methods.checkForConnection())
            {
                StartCoroutine("translate", text);
            }
            else
            {
                base.gloss = text.ToUpper();
                this.ConnectionError(ErrorMessage.TYPE.CONNECTION, false);
            }            

            this.screenManager.setPauseMenuState(false);
        }
	}

	public void stopTranslation()
	{
		StopCoroutine("translate");
		this.randomAnimations.unlockFor("translate");
	}

	public void catchTranslationError()
	{
		this.randomAnimations.unlockFor("translate");
		base.spell();
	}

	public void setRegion(string path)
	{
		this.regionPath = String.IsNullOrEmpty(path) ? "" : path;
		this.regionHash = this.regionPath == "" ? 1 : (int)this.regionPath[0] * 255 + (int)this.regionPath[1];
	}

	public void clearRegion() {
		setRegion("");
	}

	protected override UnityWebRequest loadAssetBundle(string aniName)
	{
		UnityEngine.Debug.Log("PM.lAB: Checking " + aniName);

		//if (this.regionHash == 1 && this.signs != null && !base.IsSpecialSign(aniName))
		//{
		//	try {
		//		if (!this.signs.Contains(aniName))
		//			return null;
		//	}
		//	catch (KeyNotFoundException) {
		//		return null;
		//	}
		//}

		string address = BASE_URL + this.regionPath + UnityWebRequest.EscapeURL(aniName);
       
		PlayerLogger.Log("PM", "lAB", "Requesting bundle: " + address);
        UnityWebRequest www = UnityWebRequestAssetBundle.GetAssetBundle(address);
        www.timeout = Config.TIMEOUT;
        return www;

        //return WWW.LoadFromCacheOrDownload(address, this.regionHash);
	}

	public override void onPlayingStateChange() {
		this.screenManager.changeStates(base.isPlaying(), base.isPaused(), base.isRepeatable());
	}

	public override void onConnectionError(string gloss, string word, UnityWebRequest request)
	{
		if (gloss.Equals(this.dictWord))
		{
			this.dictWord = "";
            bool checkConnection = request.isNetworkError ? true : false;
            this.ConnectionError(ErrorMessage.TYPE.BUNDLE_DOWNLOAD, checkConnection);

            base.stopAll();
		}
	}

    private void ConnectionError(ErrorMessage.TYPE error, bool checkInternetConnection = false)
    {
        
        if (!checkInternetConnection)
        {
            this.screenManager.showInfoMessage("Tradução", ErrorMessage.Player.Get(error), catchTranslationError);
           

            return;
        }
        StartCoroutine(Methods.CheckInternetConnection((isConnected) =>
        {
            if (isConnected)
            {
                this.screenManager.showInfoMessage("Tradução", ErrorMessage.Player.Get(error), catchTranslationError);
            }
            else
            {
                this.screenManager.showInfoMessage("Tradução", ErrorMessage.Player.Get(ErrorMessage.TYPE.INTERNET), catchTranslationError);
            }
        }));
        
    }

	public void resultVoiceRecognizer(string voiceInText){

		string gloss = voiceInText;
		this.translateScreenText.text = gloss;
		PlayerLogger.Log("Voice recognizer answer: " + gloss);

		this.screenManager.switchScreen("translate");
	}

	
	//protected override WWW getCheckConnectionRequest() {
	//	return new WWW(BASE_URL);
	//}

	//public IEnumerator WaitForResponse(UnityWebRequest www, Events.RequestSuccess success, Events.RequestError error)
	//{
	//	yield return Methods.WaitForResponse(www, 20f, success, error);
	//}

	//protected override IEnumerator WaitForResponse(UnityWebRequest www) {
	//	yield return WaitForResponse(www, null, null);
	//}

	private IEnumerator translate(string gloss)
	{
		base.randomAnimations.lockFor("translate");
		this.screenManager.setLoadingSnippetState(true);
        

        string postData = JsonConvert.SerializeObject(new
        {
            text = gloss
        });
        
        UnityWebRequest glossRequest =  Methods.Post(SERVER_URL, postData, "application/json");
        glossRequest.timeout = Config.TIMEOUT;

        PlayerLogger.Log("PM", "t", "Gloss: " + gloss);
		PlayerLogger.Log("PM", "t", "Request: " + SERVER_URL);

        //yield return WaitForResponse(glossRequest);
        yield return glossRequest.SendWebRequest();

		try {
            PlayerLogger.Log("PM", "t", glossRequest.downloadHandler.text);

			if ( ! glossRequest.isDone)
			{
                
                this.ConnectionError(ErrorMessage.TYPE.TIMEOUT, true);

                PlayerLogger.Log("PM", "t", "Timeout.");
			}
            else if(glossRequest.error != null || String.IsNullOrEmpty(glossRequest.downloadHandler.text))
            {
                if (glossRequest.error != null)
                {
                    PlayerLogger.Log("PM", "t", "Request error (" + glossRequest.error + ").");
                }
                if (glossRequest.isNetworkError)
                {
                    if (glossRequest.error.ToLower().Contains("request timeout"))
                    {
                        this.ConnectionError(ErrorMessage.TYPE.TIMEOUT, true);

                    }else
                        this.ConnectionError(ErrorMessage.TYPE.UNREACHABLE, true);
                }
                else
                {
                    this.ConnectionError(ErrorMessage.TYPE.DEFAULT, false);
                }
            }
			else
			{
				PlayerLogger.Log("PM", "t", "Answer: " + glossRequest.downloadHandler.text);

				gloss = glossRequest.downloadHandler.text;

				base.gloss = gloss;
                
                base.playNow(base.gloss);                

				yield break;
			}
			
			// Allows the gloss to be played when the dialog is closed
			base.gloss = gloss.ToUpper();
            
        }
		finally
		{
            currentGloss = gloss;
            this.screenManager.setLoadingSnippetState(false);
			base.randomAnimations.unlockFor("translate");
		}
	}

}
