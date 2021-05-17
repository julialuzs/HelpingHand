using UnityEngine;
using UnityEngine.UI;

public class MakersScreenTextResizer : MonoBehaviour {

	protected void Start ()
	{
		Text text = gameObject.GetComponent<Text>();

		if (Screen.dpi < 140)
			text.fontSize = 22;

		// 240
		else if (Screen.dpi < 280)
			text.fontSize = 30;

		// 320
		else if (Screen.dpi < 400)
			text.fontSize = 38;

		// 480
		else if (Screen.dpi < 500)
			text.fontSize = 46;

		else
			text.fontSize = 54;
	}

}
