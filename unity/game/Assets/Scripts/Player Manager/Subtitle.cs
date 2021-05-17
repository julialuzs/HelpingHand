using UnityEngine;
using System.Collections;
using System;
using UnityEngine.UI;
using System.Text.RegularExpressions;

/**
 *	Gerenciador de legendas
 *
 *	Versão 1.1
 *		- Caracteres de letras acentuadas e % são separados com "-".
 *
 *	Versão 1.1.1
 *		- "Ç" é separada por "-" na legenda.
 */
public class Subtitle {

	public const short TYPE_NONE = -1;
	public const short TYPE_WORD = 0;
	public const short TYPE_LETTER = 1;
	public const short TYPE_NUMBER = 2;

	// Configuração de velocidade de palavras
	protected DefaultSignSpeed defaultWordSpeed = new DefaultSignSpeed();
	// Configuração de velocidade da primeira letra de palavras a serem soletradas
	// Necessário que seja maior para não haver corte quando a mão está
	//		muito distante do local onde soletra para a maioria das letras
	protected DefaultSignSpeed defaultFirstLetterSpeed = new DefaultSignSpeed();
	// Configuração de velocidade das letras seguintes, ou velocidade padrão de letras
	protected DefaultSignSpeed defaultLetterSpeed = new DefaultSignSpeed();
	// Configuração de velocidade dos números
	protected DefaultSignSpeed defaultNumberSpeed = new DefaultSignSpeed();

	// Posição do slider de velocidade
	private float sliderPosition = DefaultSignSpeed.DEFAULT;

	// Velocade de palavras
	private float wordSpeed = DefaultSignSpeed.DEFAULT;
	// Velocade de letras
	private float letterSpeed = DefaultSignSpeed.DEFAULT;
	// Velocade de números
	private float numberSpeed = DefaultSignSpeed.DEFAULT;

	public static string highlightedColor = "white";

	// Referência para a legenda
	public Text subtitle;

	public Subtitle(Text subtitle) {
		this.subtitle = subtitle;
	}

	/* Alterar legenda */
	public void setText(string text) {
		this.subtitle.text = Regex.Match(text, @"^([^&]*)&?.*$").Groups[1].ToString();
	}

	public DefaultSignSpeed DefaultWordSpeed {
		get { return this.defaultWordSpeed; }
		set {
			this.defaultWordSpeed = value;
			this.wordSpeed = value.Speed;
		}
	}

	public DefaultSignSpeed DefaultFirstLetterSpeed {
		get { return this.defaultFirstLetterSpeed; }
		set { this.defaultFirstLetterSpeed = value; }
	}

	public DefaultSignSpeed DefaultLetterSpeed {
		get { return this.defaultLetterSpeed; }
		set {
			this.defaultLetterSpeed = value;
			this.letterSpeed = value.Speed;
		}
	}

	public DefaultSignSpeed DefaultNumberSpeed {
		get { return this.defaultNumberSpeed; }
		set {
			this.defaultNumberSpeed = value;
			this.numberSpeed = value.Speed;
		}
	}

	public float WordSpeed {
		get { return this.wordSpeed; }
		set { this.wordSpeed = value; }
	}

	public float LetterSpeed {
		get { return this.letterSpeed; }
		set { this.letterSpeed = value; }
	}

	public float NumberSpeed {
		get { return this.numberSpeed; }
		set { this.numberSpeed = value; }
	}

	public float SliderPosition {
		get { return this.sliderPosition; }
		set { this.sliderPosition = value; }
	}


	public void updateWordSpeed(float sliderPosition) {
		this.WordSpeed = this.DefaultWordSpeed.getProportional(sliderPosition);
	}
	public void updateWordSpeed() {
		this.WordSpeed = this.DefaultWordSpeed.getProportional(this.SliderPosition);
	}

	public void updateLetterSpeed(float sliderPosition) {
		this.LetterSpeed = this.DefaultLetterSpeed.getProportional(sliderPosition);
	}
	public void updateLetterSpeed() {
		this.LetterSpeed = this.DefaultLetterSpeed.getProportional(this.SliderPosition);
	}

	public void updateNumberSpeed(float sliderPosition) {
		this.NumberSpeed = this.DefaultNumberSpeed.getProportional(sliderPosition);
	}
	public void updateNumberSpeed() {
		this.NumberSpeed = this.DefaultNumberSpeed.getProportional(this.SliderPosition);
	}

	public static bool isSeparable(char c)
	{
		switch (c)
		{
			case '%':
			case 'Á':
			case 'Â':
			case 'À':
			case 'Ã':
			case 'É':
			case 'Ê':
			case 'Í':
			case 'Ó':
			case 'Ô':
			case 'Õ':
			case 'Ú':
			case 'Ç':	return true;
		}

		return (c >= 65 && c <= 90) || (c >= 48 && c <= 57);
	}

	/* Destaca caractere de uma string. */
	public static string highlight(string word, int index)
	{
		string subtitle = "";
		for (int i = 0; i < word.Length; i++)
		{
			if (i > 0 && isSeparable(word[i]))
				subtitle += "-";

			if (i == index)
				subtitle += "<b><color=" + highlightedColor + ">" + word[i] + "</color></b>";
			else
				subtitle += word[i];
		}

		return subtitle;
	}
}