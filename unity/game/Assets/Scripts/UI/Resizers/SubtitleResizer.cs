using System;
using UnityEngine;
using UnityEngine.UI;

public class SubtitleResizer : MonoBehaviour {

	protected void Start ()
	{
		Text text = gameObject.GetComponent<Text>();

		if (Screen.dpi < 140)
		{
			text.fontSize = 20;
		}

		// 240
		else if (Screen.dpi < 280)
		{
			text.fontSize = 38;
		}

		// 320
		else if (Screen.dpi < 400)
		{
			text.fontSize = 56;
		}

		// 480
		else if (Screen.dpi < 500)
		{
			text.fontSize = 76;
		}

		else
		{
			text.fontSize = 56;
		}
	}

}
