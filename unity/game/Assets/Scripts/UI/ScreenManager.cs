using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;
using LAViD.VLibras.UI;

public class ScreenManager : MonoBehaviour {

	public static string LOCK_ID = "ScreenManager";
    public GameObject avatarMale;
    public GameObject avatarFemale;
    public GameObject changeAvatarButton;
    public Sprite spriteButtonMale;
    public Sprite spriteButtonFemale;

    public GenericPlayerManager playerManager;
	public RandomAnimations randomAnimations;

	public GameObject infoScreen;
	public GameObject translateScreen;
	public GameObject dictScreen;
	public GameObject tutorialScreen;
	public GameObject makersScreen;
    public GameObject avaliationBar;
    public GameObject avaliationScreen;

	public SwitchImageAttribute textButtonImage;
	public SwitchImageAttribute pauseButtonImage;
	public SwitchImageAttribute dictButtonImage;
	public SwitchImageAttribute infoButtonImage;

	public GameObject loadingSnippet;
	public GameObject connectionErrorDialog;
    public Text connectionErrorTitle;
	public Text connectionErrorText;
    public GameObject permissionDialog;
    public Text permissionTitle;
    public Text permissionText;
	public GameObject pauseMenu;
	public GameObject repeatLayer;
	public GameObject avaliationLayer;
    public GameObject exportContainer;
    public GameObject exportLayer;
    public GameObject shareLayer;
    public GameObject progressLayer;
    public GameObject downloadLayer;
    public GameObject downloadProgressLayer;
    public GameObject infoMessageLayer;
    public GameObject subtitleLayer;
    
    public Text infoMessageTitle;
    public Text infoMessage;
    public InputField dictInput;

    public Button infoMessageButton;
    public Button writeButton;
    public Button pause_Button;
    public Button microButton;
    public Button subtitleButton;
    public Button menuButton;
    public Button dictButton;
    public Button exportButton;
    public Button avaliationButton;
    public Button repeatButton;
    

    private bool onLockExport = false;
    private bool onLockShare = false;
  
    

	public GameObject textButton;
	public GameObject pauseButton;

	public BoxCollider avatarCollider;

	public RegionSelector regionSelector;
	public SlidingHidder settingsPanel;
    public GameObject regionPanel;
	public GameObject shadow;

    private bool hasReview = false;
	private bool exit = false;
    public InputField translationAreaText;

    public void setReviewState(bool didReviewed)
    {
        this.hasReview = didReviewed;
    }

    private void Start()
    {
        closeRegionPanel(true);
        showTutorialOnInit();

    }

    private void showTutorialOnInit()
    {
        var firstTime = PlayerPrefs.GetInt("init", 0);
        if (firstTime == 0)
        {
            this.showInfoMessage("Seja bem vindo ao Vlibras!", "Ficamos felizes por nos escolher! Fique agora com um pequeno tutorial de como utilizar nosso tradutor.", delegate { switchScreen("tutorial"); });
            
            PlayerPrefs.SetInt("init", 1);
        }
    }
    public void Update()
	{
		if (Input.GetKeyDown(KeyCode.Escape)) 
		{
			if (infoScreen.activeSelf)
			{
				infoScreen.SetActive(false);
				infoButtonImage.switchAttribute(false);
			}
			else if (translateScreen.activeSelf)
			{
				translateScreen.SetActive(false);
				textButtonImage.switchAttribute(false);
			}
			else if (dictScreen.activeSelf)
			{
				dictScreen.SetActive(false);
				dictButtonImage.switchAttribute(false);
			}
			else if (tutorialScreen.activeSelf)
			{
				tutorialScreen.SetActive(false);
			}
			else if (tutorialScreen.activeSelf)
			{
				makersScreen.SetActive(false);
			}
			else if (regionPanel.activeSelf)
			{
				closeRegionPanel(true);
			}
			else if (settingsPanel.isVisible())
			{
				closeSettingsPanel();
			}
			else
			{
				if (exit) Application.Quit();

				exit = true;
			}
		}
	}

    public void setAvatar(bool active)
    {
        if (active)
        {
            avatarMale.SetActive(true);
            avatarFemale.SetActive(false);
            changeAvatarButton.GetComponent<Image>().sprite = spriteButtonFemale;
            avatarCollider = avatarMale.GetComponent<BoxCollider>();
        }
        else
        {
            avatarMale.SetActive(false);
            avatarFemale.SetActive(true);
            changeAvatarButton.GetComponent<Image>().sprite = spriteButtonMale;
            avatarCollider = avatarFemale.GetComponent<BoxCollider>();
        }
    }

    public void setAvatarButton(bool active)
    {
        this.changeAvatarButton.SetActive(active);
    }



    public bool hasActiveScreen()
	{
		return 		this.infoScreen.activeSelf
				||	this.translateScreen.activeSelf
				||	this.dictScreen.activeSelf
				||	this.tutorialScreen.activeSelf
				||	this.makersScreen.activeSelf;
	}

	public void hideScreen()
	{
		setAvatarColliderState(true);
		randomAnimations.unlockFor(LOCK_ID);

		if (infoScreen.activeSelf)
		{
			infoScreen.SetActive(false);
			infoButtonImage.switchAttribute(false);
		}

		if (translateScreen.activeSelf)
		{
			translateScreen.SetActive(false);
			textButtonImage.switchAttribute(false);
		}

		if (dictScreen.activeSelf)
		{
            dictInput.text = "";
			dictScreen.SetActive(false);
			dictButtonImage.switchAttribute(false);
		}

		if (tutorialScreen.activeSelf)
		{
			tutorialScreen.SetActive(false);
		}

		if (makersScreen.activeSelf)
		{
			makersScreen.SetActive(false);
		}

        if (avaliationScreen.activeSelf)
        {
            avaliationScreen.SetActive(false);
        }
	}

	private void pause()
	{
		playerManager.setPauseState(true);
		setPauseMenuState(true);
	}

	public void switchScreen(GameObject screen)
	{
		bool active = screen.activeSelf;

		hideScreen();

		if (active) return;

		screen.SetActive(true);

		if (playerManager.isPlayingIntervalAnimation())
			playerManager.stopAll();

		else if (playerManager.isPlaying())
			pause();

		setAvatarColliderState(false);
		randomAnimations.lockFor(LOCK_ID);

		if (screen == infoScreen)
			infoButtonImage.switchAttribute(true);

		if (screen == translateScreen){
			textButtonImage.switchAttribute(true);
            translationAreaText.ActivateInputField();
            translationAreaText.Select();
        }

		if (screen == dictScreen)
			dictButtonImage.switchAttribute(true);

		exit = false;
	}

	public void switchScreen(string name) {
		switchScreen(getScreenByName(name));
	}

	public GameObject getScreenByName(string name)
	{
		switch (name)
		{
			case "translate": return this.translateScreen;
			case "dict": return this.dictScreen;
			case "info": return this.infoScreen;
			case "tutorial": return this.tutorialScreen;
			case "makers": return this.makersScreen;
		}

		return null;
	}

	public void openSettingsPanel()
	{
		settingsPanel.Animate(true);
		setPanelOpen(true);

		if (playerManager.isPlaying())
			pause();
	}

	public void openRegionPanel()
	{
		settingsPanel.Animate(false);
        regionPanel.SetActive(true);
		//regionPanel.Animate(true);
		setPanelOpen(true);
	}

	public void openInfoScreen()
	{
		closeSettingsPanel();
		switchScreen(infoScreen);
	}

	public void closeSettingsPanel()
	{
		settingsPanel.Animate(false);
		setPanelOpen(false);
	}

	public void closeRegionPanel(bool restoreActiveItem)
	{
		//regionPanel.Animate(false);
		setPanelOpen(false);
        regionPanel.SetActive(false);

		if (restoreActiveItem)
			regionSelector.ReselectActiveItem();
	}

	public void onPanelOutClick()
	{
		if (regionPanel.activeSelf)
			closeRegionPanel(true);

		else if (settingsPanel.isVisible())
			closeSettingsPanel();

		else setPanelOpen(false);
	}

	private void setPanelOpen(bool open)
	{
        shadow.SetActive(open);
		setAvatarColliderState( ! open);
	}

	public void setPauseMenuState(bool active)
	{
		this.pauseMenu.SetActive(active);
		setAvatarColliderState( ! active);
	}

	public void setLoadingSnippetState(bool active)
	{
        this.setButtonsState(!active);
		this.loadingSnippet.SetActive(active);

		if (active && this.pauseMenu.activeSelf)
			this.pauseMenu.SetActive(false);
	}

	public void showConnectionErrorDialog()
	{
		this.connectionErrorDialog.SetActive(true);
	}

    public void showPermissionDialog(string permissionMessage, string permissionTitle)
    {
        this.permissionDialog.SetActive(true);
        this.permissionText.text = permissionMessage;
        this.permissionTitle.text = permissionTitle;
    }

    public void showInfoMessage(string title, string message, UnityEngine.Events.UnityAction action=null)
    {
        
        this.infoMessageLayer.SetActive(true);
        this.infoMessageTitle.text = title;
        this.infoMessage.text = message;
        this.infoMessageButton.onClick.RemoveAllListeners();
        if(action != null)
            this.infoMessageButton.onClick.AddListener(action);
    }

	//public void showConnectionErrorDialog(PlayerManager.ERROR_STATUS_MESSAGE msg)//int error_code
	//{
	//	this.connectionErrorDialog.SetActive(true);
 //       this.connectionErrorTitle.text = "Conexão falhou";
	//	this.connectionErrorText.text = PlayerManager.get_connection_status_message(msg);
	//}

    public void setDictButtonEnable(bool enable)
    {
        this.dictButton.enabled = enable;
    }

    public void setChangeAvatarButtonEnable(bool enable)
    {
        this.changeAvatarButton.GetComponent<Button>().enabled = enable;
    }

    public void setButtonsState(bool enable)
    {
        this.infoMessageButton.enabled = enable;
        this.writeButton.enabled = enable;
        this.pause_Button.enabled = enable;
        this.microButton.enabled = enable;
        this.subtitleButton.enabled = enable;
        this.menuButton.enabled = enable;
        this.dictButton.enabled = enable;
        this.exportButton.enabled = enable;
        this.avaliationButton.enabled = enable;
        this.repeatButton.enabled = enable;
        this.changeAvatarButton.GetComponent<Button>().enabled = enable;

    }
    public void setMessageLayerState(bool active)
    {
        this.infoMessageLayer.SetActive(active);
    }
	public void setAvatarColliderState(bool active) {
		this.avatarCollider.enabled = active;
	}

	public void setRepeatLayerState(bool active) {
		this.repeatLayer.SetActive(active);
	}

    public void setAvaliationLayerState(bool active)
    {
        this.avaliationLayer.SetActive(active);

    }

    public void setAvaliationBarState(bool active)
    {
        this.avaliationBar.SetActive(active);
    }

    public void setAvaliationScreenState(bool active)
    {
        this.avaliationScreen.SetActive(active);
    }

    public void setExportContainerState(bool active)
    {
        this.exportContainer.SetActive(active);
    }

    public void setExportLayerState(bool active)
        
    {
        
        this.exportLayer.SetActive(active);
    }

    public void setProgressLayerState(bool active)
    {
        
        this.progressLayer.SetActive(active);
    }

    public void setDownloadLayerState(bool active)

    {
        
        this.downloadLayer.SetActive(active);
    }

    public void setDownloadProgressLayerState(bool active)
    {
        this.downloadProgressLayer.SetActive(active);
        
    }

    public void setShareLayerState(bool active)
    {

        this.shareLayer.SetActive(active);
    }

   

    public void setTranslateButtonActive(bool active)
	{
		this.textButton.SetActive(active);
		this.pauseButton.SetActive( ! active);
	}

    public void updateProgressDownloadSprite(float progress)
    {
        downloadProgressLayer.GetComponent<Image>().fillAmount = progress; 
    }

    public void hideAvaliationLayers()
    {
        if (avaliationScreen.activeSelf)
            avaliationScreen.SetActive(false);
        if (avaliationLayer.activeSelf)
            avaliationLayer.SetActive(false);
        if (avaliationBar.activeSelf)
            avaliationBar.SetActive(false);

    }
    
	public void changeStates(bool playing, bool paused, bool repeatable)
	{
		setTranslateButtonActive( ! playing);
		setPauseMenuState(playing && paused);
		setRepeatLayerState(!playing && repeatable);
        setChangeAvatarButtonEnable(!playing && repeatable);
        setAvaliationLayerState(!playing && repeatable && !hasReview);
        if(!onLockExport && !onLockShare)
            changeExportStates(ExportLayers.ExportLayer.All, !playing && repeatable);
        

		this.pauseButtonImage.switchAttribute(playing && paused);
	}

    public void changeExportStates(ExportLayers.ExportLayer layers, bool show_Layer)
    {
        switch (layers)
        {
            case ExportLayers.ExportLayer.Export_Layer:
                onLockShare = false;
                onLockExport = false;
                setExportContainerState(show_Layer);
                setExportLayerState(show_Layer);
                break;
            case ExportLayers.ExportLayer.Progress_Layer:
                onLockExport = true;
                onLockShare = true;
                setExportContainerState(show_Layer);
                setProgressLayerState(show_Layer);
                break;
            case ExportLayers.ExportLayer.Download_Layer:
                onLockExport = true;
                onLockShare = true;
                setExportContainerState(show_Layer);
                setDownloadLayerState(show_Layer);
                break;
            case ExportLayers.ExportLayer.Progress_Download_Layer:
                onLockExport = true;
                onLockShare = true;
                setExportContainerState(show_Layer);
                setDownloadProgressLayerState(show_Layer);

                break;
            case ExportLayers.ExportLayer.Share_Layer:
                onLockExport = true;
                onLockShare = false;
                setExportContainerState(show_Layer);
                setShareLayerState(show_Layer);
                break;
            case ExportLayers.ExportLayer.OnLockExport:
                onLockExport = show_Layer;                
                break;
            case ExportLayers.ExportLayer.OnLockShare:
                onLockShare = show_Layer;
                break;
            default:
                onLockShare = false;
                onLockExport = false;
                setExportContainerState(show_Layer);
                setExportLayerState(show_Layer);
                setRepeatLayerState(show_Layer);
                setProgressLayerState(false);
                setDownloadLayerState(false);
                setDownloadProgressLayerState(false);
                setShareLayerState(false);

                break;                
        }
    }

}
