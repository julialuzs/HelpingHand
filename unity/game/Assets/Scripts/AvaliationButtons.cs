using System;
using System.Collections;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Networking;
using Newtonsoft.Json;

public class AvaliationButtons : MonoBehaviour
{
    
    public ScreenManager screenManager;
    public InputField avaliationScreenText;
    private string URL = Config.REVIEW_URL;
    private string HEADER = "Revisão não enviada";

    
    public void Like()
    {
        object[] parms = new object[2] { "good", "" };
        this.screenManager.hideAvaliationLayers();
        if (!Methods.checkForConnection())
        {
            OnConnectionError(ErrorMessage.Avaliation.Get(ErrorMessage.TYPE.CONNECTION), false);
        }
        else
        {
            StartCoroutine(SendReview(parms));

        }


    }

    private void OnConnectionError(string errorMsg, bool checkInternetConnection = true)
    {
 
        if (!checkInternetConnection)
        {
            this.screenManager.showInfoMessage(HEADER, errorMsg, Close);

        }
        else
        {
            StartCoroutine(Methods.CheckInternetConnection((isConnected) =>
            {
                if (isConnected)
                {
                    this.screenManager.showInfoMessage(HEADER, errorMsg, Close);
                }
                else
                {
                    this.screenManager.showInfoMessage(HEADER, ErrorMessage.Avaliation.Get(ErrorMessage.TYPE.INTERNET), Close);
                }
            }));
        }
    }

    private IEnumerator SendReview(object[] parms)
    {
        this.screenManager.hideAvaliationLayers();
        if (!Methods.checkForConnection())
        {
            OnConnectionError(ErrorMessage.Avaliation.Get(ErrorMessage.TYPE.CONNECTION),false);
            yield break;
        }
        string rate = (string)parms[0];
        string review = (string)parms[1];
        object json = null;
        string text = PlayerManager.getCurrentText();
        string gloss = PlayerManager.getCurrentGloss();
        if (String.IsNullOrEmpty(review))
            json = new
            {
                text = String.IsNullOrEmpty(text) ? gloss:text ,
                translation = gloss,
                rating = rate,

            };
        else
            json = new
            {
                text = String.IsNullOrEmpty(text) ? gloss : text,
                translation = gloss,
                rating = rate,
                review,

            };
        string postData = JsonConvert.SerializeObject(json);

        UnityWebRequest www = Methods.Post(URL, postData, "application/json");
        www.timeout = Config.TIMEOUT;
       
        PlayerLogger.Log("AvaliationButtons", "SendReview", "Sending review to server. Text: [" 
            + text + "] Gloss: [" + gloss
            + "].");

        yield return www.SendWebRequest();

        if(!www.isDone|| www.isNetworkError)
        {
            OnConnectionError(ErrorMessage.Avaliation.Get(ErrorMessage.TYPE.UNREACHABLE));
            PlayerLogger.Log("AvaliationButtons", "SendReview", "Error sending review. Error: " + www.error);
        }else if (www.isHttpError)
        {
            OnConnectionError(ErrorMessage.Avaliation.Get(ErrorMessage.TYPE.DEFAULT),false);
            PlayerLogger.Log("AvaliationButtons", "SendReview", "Error sending review. Error: " + www.error);
        }
        else
        {
            this.screenManager.setReviewState(true);
            this.screenManager.showInfoMessage("Revisão enviada", "Muito obrigado por deixar sua sugestão!");
            PlayerLogger.Log("AvaliationButtons", "SendReview", "Review sent with sucess.");
        }

        this.screenManager.changeExportStates(ExportLayers.ExportLayer.All, true);
        
    }
    public void Dislike()
    {
        this.screenManager.hideAvaliationLayers();

        if (!Methods.checkForConnection())
        {
            OnConnectionError(ErrorMessage.Avaliation.Get(ErrorMessage.TYPE.CONNECTION), false);
        }
        else
        {
            this.screenManager.setAvaliationBarState(false);
            this.screenManager.setAvaliationScreenState(true);
            this.avaliationScreenText.text = PlayerManager.getCurrentGloss();
        }
        
        

    }

    public void SubmitReview()
    {
        string text = avaliationScreenText.text;
        if (!String.IsNullOrEmpty(text))
        {
            avaliationScreenText.text = "";
            object[] parms = new object[2] { "bad", text };
            StartCoroutine("SendReview", parms);


        }
    }

    public void Open()
    {
        this.screenManager.hideAvaliationLayers();
        this.screenManager.changeExportStates(ExportLayers.ExportLayer.All,false);
        this.screenManager.setAvaliationBarState(true);
        
    }

    public void Close()
    {
        this.screenManager.hideAvaliationLayers();
        this.screenManager.changeExportStates(ExportLayers.ExportLayer.All, true);
        this.screenManager.setAvaliationLayerState(true);
    }

}
