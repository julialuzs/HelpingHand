using UnityEngine;
using UnityEngine.UI;

public class TutorialManager : MonoBehaviour {

	public ScreenManager screenManager;

	public Text description;
	public Image translateImage;
	public Image micImage;
	public Image dictionaryImage;
	public Image subtitlesImage;
	public Image exportImage;
	public Image avaliationImage;
	public Image repeatImage;
    public Image changeAvatarImage;

	public GameObject sliderShadow;

	public Color enabledColor;
	public Color disabledColor;

	private int index = 0;
	private string[] descriptions = new string[] {
		"Tradução de Texto\n\nNessa opção você pode entrar com um texto para ser traduzido!",
		"Tradução de Fala\n\nNessa opção, o que você falar será traduzido para LIBRAS",
		"Dicionário\n\nNessa opção você pode ver e reproduzir todos os sinais disponíveis no VLibras",
		"Legendas\n\nNessa opção você pode ativar e desativar as legendas enquanto o sinal é traduzido",
		"Barra de velocidade\n\nNa barra você pode escolher a velocidade que deseja visualizar o sinal",
        "Reprodução\n\nNessa opção você pode tocar a animação do início!",
        "Compartilhar Vídeo\n\nNessa opção você pode baixar a animação e compartilhá-la!",
        "Avaliar Tradução\n\nNessa opção você pode avaliar a tradução e deixar sua sugestão!",
        "Trocar Avatar\n\n Nessa opção você pode mudar o avatar de acordo com sua preferência!",
        "Muito bem!\n\n Você agora se encontra pronto para utilizar nosso aplicativo!"
	};

	private Image[] buttons;


    protected void Start ()
	{
		if (Screen.dpi < 140)
		{
			this.description.fontSize = 14;
		}

		// 240
		else if (Screen.dpi < 280)
		{
			this.description.fontSize = 20;
		}

		// 320
		else if (Screen.dpi < 400)
		{
			this.description.fontSize = 30;
		}

		// 480
		else if (Screen.dpi < 500)
		{
			this.description.fontSize = 44;
		}
        else if(Screen.dpi < 600)
        {
            this.description.fontSize = 54;
        }

		else
		{
			this.description.fontSize = 60;
		}

		this.buttons = new Image[] {
			this.translateImage,
			this.micImage,
			this.dictionaryImage,
			this.subtitlesImage,
            this.repeatImage,
            this.exportImage,
            this.avaliationImage,
            this.changeAvatarImage
		};
        
	}

	private Image getButton() {
        int btn_index = this.index;
        if (this.index > 4)
        {
            btn_index -= 1;
        }
		return btn_index <= 7 ? this.buttons[btn_index] : null;
	}

	public void next()
	{
		if (this.index == 8)
		{
            this.description.text = this.descriptions[9];
            getButton().color = disabledColor;
            this.index += 1;
        }
        else if(this.index == 9)
        {
            this.screenManager.hideScreen();
            select(0);
        }
		else
		{
#if UNITY_IOS
			// Jump mic
			select(this.index == 0 ? 2 : this.index + 1);
#else
			select(this.index + 1);
#endif
		}
	}

	public void select(int index)
	{
        if(this.index == 4)
        {
           this.sliderShadow.SetActive(true);
        }
		else if (this.index <= 8)
			getButton().color = disabledColor;
			

		this.index = index;

        if(this.index == 4)
        {
            this.sliderShadow.SetActive(false);
        }else if (this.index <= 8)
			getButton().color = enabledColor;
			
		this.description.text = this.descriptions[index];
	}

}