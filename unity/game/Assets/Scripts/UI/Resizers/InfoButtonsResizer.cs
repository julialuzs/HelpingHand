using UnityEngine;
using UnityEngine.UI;

public class InfoButtonsResizer : MonoBehaviour {

	void Start()
	{
		Text text = gameObject.GetComponent<Text>();

		if (Screen.dpi < 140)
			text.fontSize = 14;

		// 240
		else if (Screen.dpi < 280)
			text.fontSize = 22;

		// 320
		else if (Screen.dpi < 400)
			text.fontSize = 35;

		// 480
		else if (Screen.dpi < 500)
			text.fontSize = 48;

		else
			text.fontSize = 35;
	}
}
