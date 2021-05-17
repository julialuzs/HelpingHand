using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class DependentHeightStarter : MonoBehaviour {
	
	void Start ()
	{
		LayoutElement layout = gameObject.GetComponent<LayoutElement>();

		if (Screen.height < 600)
			layout.minHeight = 32;

		else if (Screen.height <= 700)
			layout.minHeight = 40;

		// 800
		else if (Screen.height <= 900)
			layout.minHeight = 54;
		
		else if (Screen.height <= 1060)
			layout.minHeight = 66;

		// 1200
		else if (Screen.height <= 1340)
			layout.minHeight = 82;

		else
			layout.minHeight = 92;

		/*if (Screen.dpi < 140)
			layout.minHeight = 32;

		// 240
		else if (Screen.dpi < 280)
			layout.minHeight = 42;

		// 320
		else if (Screen.dpi < 400)
			layout.minHeight = 52;

		// 480
		else if (Screen.dpi < 500)
			layout.minHeight = 62;

		else
			layout.minHeight = 72;*/
	}

}
