/**********************
********LAVID**********
***VLibras Project*****
*------------------------------------------------------------------------
*Description:
*
*This class needs an android plugin to call Dialogs Mobile APIs, working on both
* And IOS platforms.
* Export Video is responsible for making available the share video functionality.
*It works managing the button layers related to the funcionality and also making
* requests to VLibras server.
*---------------------------------------------------------------------------
*Plugin directory: 
* - Assets/Plugins/Android
*References:
* - http://docs.unity3d.com/ScriptReference/AndroidJavaClass.html
*------------------------------------------------------------------------
*Author: Thiago Filipe
*thiago.filipe@lavid.ufpb.br
***********************/


using UnityEngine;
using UnityEngine.Networking;
using System;
using System.Collections;
using System.IO;
using Newtonsoft.Json;


public class ExportVideo : MonoBehaviour {


    public ScreenManager screenManager;

    private const string SERVER_URL = Config.EXPORT_VIDEO_URL;
    private const int TIME_OUT_VIDEO_STATUS = 5; //seconds
    


    /* Strings related to the Android Dialog*/
    private string title = "Download";
    private string message = "Para compartilhar é preciso realizar o download do vídeo. Tamanho do Download: ";
    

    /* Data related to the Video*/

    private ulong videoSize = 0;
    private string videoId = "";
    private byte[] videoContent = null;

    private string pathTemp = null;
    /*Coroutine flags */

    private string avatar;
    private volatile bool videoRequestRunning = false;
    private volatile bool videoDownloadRunning = false;
    private volatile bool videoStatusRunning = false;

    private void Awake()
    {
        pathTemp = Application.temporaryCachePath + "/vlibras_video.mp4";
    }

    public void onClickExport()
    {
        PlayerLogger.Log("ExportVideo", "OnClickExport", "Starting Export...");
        
        
        switch (NativeGallery.CheckPermission())
        {
            case NativeGallery.Permission.Granted:
                Permitted();
                break;
            case NativeGallery.Permission.ShouldAsk:
                this.screenManager.showInfoMessage("Exportar Vídeo", "Para salvar e compartilhar as animações, permita que o Vlibras acesse mídia e arquivos de seu aparelho.", delegate
                {
                    if (NativeGallery.RequestPermission() == NativeGallery.Permission.Granted)
                    {
                        Permitted();
                    }
                    
                });
                break;
            case NativeGallery.Permission.Denied:
                this.screenManager.showInfoMessage("Exportar Vídeo", "Para salvar e compartilhar as animações, permita que o Vlibras acesse mídia e arquivos através das configurações do seu aparelho.", delegate {
                    if (NativeGallery.CanOpenSettings())
                        NativeGallery.OpenSettings();
                });

                break;

        }  


    }


    /* RunTime Android Permission being asked*/
    private void Permitted()
    {
        int fiveTimesCount;

        if (PlayerPrefs.HasKey("fiveTimesCount"))
        {
            fiveTimesCount = PlayerPrefs.GetInt("fiveTimesCount");
            if(fiveTimesCount < 5)
            {
                fiveTimesCount++;
                PlayerPrefs.SetInt("fiveTimesCount", fiveTimesCount);
                PlayerPrefs.Save();
                startExport();
            }
            else
            {
                fiveTimesCount = 0;
                PlayerPrefs.SetInt("fiveTimesCount", fiveTimesCount);
                PlayerPrefs.Save();
                this.screenManager.showInfoMessage("Exportar Vídeo", "A geração de vídeo pode levar algum tempo.", startExport);
            }

        }else
        {
            PlayerPrefs.SetFloat("fiveTimesCount", 0);
            PlayerPrefs.Save();
            this.screenManager.showInfoMessage("Exportar Vídeo", "A geração de vídeo pode levar algum tempo.", startExport);
        }
    }

    private void ConnectionError(ErrorMessage.TYPE error, UnityEngine.Events.UnityAction onError, bool checkInternetConnection = false)
    {
        if (!checkInternetConnection)
        {
            this.screenManager.showInfoMessage("Exportar Vídeo", ErrorMessage.Export.Get(error), onError);


            return;
        }
        StartCoroutine(Methods.CheckInternetConnection((isConnected) =>
        {
            if (isConnected)
            {
                this.screenManager.showInfoMessage("Exportar Vídeo" , ErrorMessage.Export.Get(error), onError);
            }
            else
            {
            this.screenManager.showInfoMessage("Exportar Vídeo", ErrorMessage.Export.Get(ErrorMessage.TYPE.INTERNET), onError);
            }
        }));
    }

    

    /* Function that hides the export button and 
    * shows the progress bar while getting video info*/
    public void startExport()
    {
        //new AndroidToast().showToast("Gerando Video...", "SHORT");
        screenManager.changeExportStates(ExportLayers.ExportLayer.Export_Layer, false);

        screenManager.changeExportStates(ExportLayers.ExportLayer.Progress_Layer, true);
        StartCoroutine("requestVideoInfo");
        PlayerLogger.Log("ExportVideo", "OnClickExport", "Making request to server.");

    }



    /* Function that gets called by the web request
     * hiding the progress bar and showing the download
     * button*/
    public void OnFinishGetVideoInfo()
    {
        PlayerLogger.Log("ExportVideo", "OnFinishGetVideoInfo", "Video is ready to be downloaded.");
        screenManager.changeExportStates(ExportLayers.ExportLayer.Progress_Layer, false);
        screenManager.changeExportStates(ExportLayers.ExportLayer.Download_Layer, true);
        PlayerLogger.Log("ExportVideo", "OnFinishGetVideoInfo", "Hiding progress loading and showing download button");

    }

    /* Function that asks the user whether downlaod or not
     * the video by presenting a Dialog Box.**/

    public void onDownloadClick()
    {
        this.screenManager.showInfoMessage(title, message + ((videoSize / 1024f) / 1024f).ToString("0.00") + " MB.", delegate {
            StartCoroutine("videoDownload");
            screenManager.changeExportStates(ExportLayers.ExportLayer.Download_Layer, false);
            screenManager.changeExportStates(ExportLayers.ExportLayer.Progress_Download_Layer, true);
        });

    }

    /* Function that shares the video downloaded.**/

    public void OnDownloadFinished()
    {
        PlayerLogger.Log("ExportVideo", "OnDownloadFinished", "Video Downloaded, able to share it.");
        screenManager.changeExportStates(ExportLayers.ExportLayer.Progress_Download_Layer, false);
        screenManager.changeExportStates(ExportLayers.ExportLayer.Share_Layer, true);
    }

    /*Cancels the VideoRequest or VideoDownload by interrupting the coroutine and resetting values*/

    public void OnCancel()
    {
        PlayerLogger.Log("ExportVideo", "onCancel", "User canceled the video request, resetting states to default.");
        if (videoRequestRunning)
            StopCoroutine("requestVideoInfo");
        else if (videoStatusRunning)        
            StopCoroutine("VideoStatus");
        else if(videoDownloadRunning)
             StopCoroutine("videoDownload");

        videoId = "";
        videoSize = 0;
        videoContent = null;
        screenManager.changeExportStates(ExportLayers.ExportLayer.All, true);
    }

    /*With sharing layer active, gets called when the user clicks on it and makes
     * the video available to share*/

    public void OnShareVideo()
    {
        AssertFile();
        new NativeShare().AddFile(pathTemp).SetTitle("Enviar Vídeo").Share();
    }

    /*Coroutine that makes the video information request*/

    private IEnumerator requestVideoInfo()
    {
        PlayerLogger.Log(AvatarChange.avatarTag);
        videoRequestRunning = true;
        object json = new
        {
            gloss = PlayerManager.getCurrentGloss(),
            avatar = AvatarChange.avatarTag,
            caption = screenManager.subtitleLayer.activeSelf ? "on": "off"

        };
        string postBody = JsonConvert.SerializeObject(json);
        PlayerLogger.Log("Post: " + postBody);

        UnityWebRequest videoInfoRequest = Methods.Post(SERVER_URL, postBody, "application/json");
        videoInfoRequest.timeout = Config.TIMEOUT;

        PlayerLogger.Log("ExportVideo", "requestVideoInfo", "Request for: " + SERVER_URL);

        yield return videoInfoRequest.SendWebRequest();

         try
            {
                if (!videoInfoRequest.isDone)
                {
                    ConnectionError(ErrorMessage.TYPE.TIMEOUT, delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); }, true);

                    PlayerLogger.Log("ExportVideo", "requestVideoInfo", "TimeOut");

                }
                else if(videoInfoRequest.error != null || String.IsNullOrEmpty(videoInfoRequest.downloadHandler.text))
                {
                    if(videoInfoRequest.error != null)
                    {
                        PlayerLogger.Log("ExportVideo", "requestVideoInfo", "(WWW) Error: " + videoInfoRequest.error);
                    }

                    if (videoInfoRequest.isNetworkError)
                    {
                        if (videoInfoRequest.error.ToLower().Contains("request timeout"))
                        {
                            ConnectionError(ErrorMessage.TYPE.TIMEOUT, delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); });

                        }
                        else
                            ConnectionError(ErrorMessage.TYPE.UNREACHABLE, delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); }, true);
                    }
                    else
                    {
                        ConnectionError(ErrorMessage.TYPE.DEFAULT, delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); });
                    }
                }                
                else
                {
                    PlayerLogger.Log("ExportVideo", "requestVideoInfo", "Answer: " + videoInfoRequest.downloadHandler.text);
                    FileContent fileContent = JsonConvert.DeserializeObject<FileContent>(videoInfoRequest.downloadHandler.text);
                    videoId = fileContent.requestUID;
                    StartCoroutine("VideoStatus");

                }
            }
            catch (Exception e)
            {
                this.screenManager.showInfoMessage("Exportar Vídeo", ErrorMessage.Export.Get(ErrorMessage.TYPE.DEFAULT), delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); });
                PlayerLogger.Log("ExportVideo", "requestVideoInfo", "Message: " + e.Message + "\n StackTrace: " + e.StackTrace);
            }
            finally
            {
                videoRequestRunning = false;
            }
       
    }

    private IEnumerator VideoStatus()
    {
        videoStatusRunning = true;
        
        Debug.Log(SERVER_URL + "/" + UnityWebRequest.EscapeURL("status") + "/" + UnityWebRequest.EscapeURL(videoId));

        while (true)
        {
            UnityWebRequest videoStatusRequest = UnityWebRequest.Get(SERVER_URL + "/status/" + videoId);
            videoStatusRequest.timeout = Config.TIMEOUT;
            yield return videoStatusRequest.SendWebRequest();
                        
            if (!videoStatusRequest.isDone)
            {

                ConnectionError(ErrorMessage.TYPE.TIMEOUT, delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); }, true);
                PlayerLogger.Log("ExportVideo", "VideoStatus", "TimeOut");
                break;

            }
            else if (videoStatusRequest.error != null || String.IsNullOrEmpty(videoStatusRequest.downloadHandler.text))
            {
                if(videoStatusRequest.error != null)
                {
                    PlayerLogger.Log("ExportVideo", "requestVideoInfo", "(WWW) Error: " + videoStatusRequest.error);
                }
                if (videoStatusRequest.isNetworkError)
                {
                    if(videoStatusRequest.error.ToLower().Contains("request timeout"))
                        ConnectionError(ErrorMessage.TYPE.TIMEOUT, delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); });
                    else
                        ConnectionError(ErrorMessage.TYPE.UNREACHABLE, delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); }, true);

                }
                else
                {
                    ConnectionError(ErrorMessage.TYPE.DEFAULT, delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); });
                }
                break;
            }             
            else
            {
                PlayerLogger.Log("ExportVideo", "VideoStatus", "Answer: " + videoStatusRequest.downloadHandler.text);
                try
                {
                    FileContent fileContent = JsonConvert.DeserializeObject<FileContent>(videoStatusRequest.downloadHandler.text);
                    if (fileContent.status.Equals("failed"))
                    {
                        this.screenManager.showInfoMessage("Exportar Vídeo", ErrorMessage.Export.Get(ErrorMessage.TYPE.PROCESSING_VIDEO), delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); });
                        break;
                    }
                    else if (fileContent.status.Equals("generated"))
                    {
                        videoSize = ulong.Parse(fileContent.size);
                        OnFinishGetVideoInfo();
                        break;
                    }
                }
                catch (Exception e)
                {
                    this.screenManager.showInfoMessage("Exportar Vídeo", ErrorMessage.Export.Get(ErrorMessage.TYPE.DEFAULT), delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); });
                    PlayerLogger.Log("ExportVideo", "requestVideoInfo", "Message: " + e.Message + "\n StackTrace: " + e.StackTrace);
                    break;
                }
                yield return new WaitForSeconds(TIME_OUT_VIDEO_STATUS);
            }
        }// while
        videoStatusRunning = false;
    }


    private IEnumerator SaveOnDevice()
    {
        NativeGallery.SaveVideoToGallery(videoContent, "VLibras", "VLibrasVideo_" + System.DateTime.Now.ToString("dd_MM_yyyy_HH_mm_ss") + ".mp4");
        yield return null;
    }

    /*Coroutine that downloads the video and update the progress download layer*/
    
    private IEnumerator videoDownload()
    {
        videoDownloadRunning = true;
        UnityWebRequest DownloadVideo = UnityWebRequest.Get(SERVER_URL + "/download/" + videoId);

        DownloadVideo.SendWebRequest();
        while (!DownloadVideo.isDone)
        {
            screenManager.updateProgressDownloadSprite(DownloadVideo.downloadProgress);

            yield return new WaitForSeconds(1f);
        }

        try
        {
            if(DownloadVideo.error != null)
            {
                if (DownloadVideo.isNetworkError)
                {
                    ConnectionError(ErrorMessage.TYPE.DOWNLOAD_VIDEO, ErrorDownloading, true);
                }
                else
                {
                    ConnectionError(ErrorMessage.TYPE.DOWNLOAD_VIDEO, ErrorDownloading);
                }
                PlayerLogger.Log("ExportVideo", "videoDownload", "Connection error: " + DownloadVideo.error);
            }
            else if (videoSize == DownloadVideo.downloadedBytes)
            {
                videoDownloadRunning = false;

                PlayerLogger.Log("ExportVideo", "videoDownload", "Video baixado com sucesso. Bytes: " + DownloadVideo.downloadedBytes);

                videoContent = DownloadVideo.downloadHandler.data;
                File.WriteAllBytes(pathTemp, videoContent);
                OnDownloadFinished();

                StartCoroutine("SaveOnDevice");              
            }
            else
            {
                ConnectionError(ErrorMessage.TYPE.DOWNLOAD_VIDEO, ErrorDownloading);
            }


        }catch(Exception e)
        {
            PlayerLogger.Log("ExportVideo", "videoDownload", "Error: " + e.Message + "\n StackTrace: " + e.StackTrace);
            this.screenManager.showInfoMessage("Exportar Vídeo", ErrorMessage.Export.Get(ErrorMessage.TYPE.DOWNLOAD_VIDEO), delegate { screenManager.changeExportStates(ExportLayers.ExportLayer.All, true); });
        }
        finally
        {
            videoDownloadRunning = false;
        }
    }

    /* In case of error during the video download, the user gets a new chance to download the file, but
     * is also able to start a new fresh animation and export it */
    private void ErrorDownloading()
    {
        screenManager.changeExportStates(ExportLayers.ExportLayer.Progress_Download_Layer, false);
        screenManager.changeExportStates(ExportLayers.ExportLayer.Download_Layer, true);
        screenManager.changeExportStates(ExportLayers.ExportLayer.OnLockExport, false);
        screenManager.changeExportStates(ExportLayers.ExportLayer.OnLockShare, false);
    }

    private void AssertFile()
    {
        if (!File.Exists(pathTemp))
        {
            File.WriteAllBytes(pathTemp, videoContent);
        }
    }
  




}
