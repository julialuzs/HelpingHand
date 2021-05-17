using System;
using UnityEngine;
using UnityEngine.UI;

public class BarResizer : MonoBehaviour {

	public HorizontalLayoutGroup barBottomLayout;
	public GameObject microButton;

	protected void Start ()
	{
#if UNITY_IOS
		this.microButton.SetActive(false);

		if (Screen.width < 400)
			this.barBottomLayout.spacing = 8;

		// 240
		else if (Screen.width < 500)
			this.barBottomLayout.spacing = 106;

		// 320
		else if (Screen.width < 620)
			this.barBottomLayout.spacing = 132;

		// 480
		else if (Screen.width < 840)
			this.barBottomLayout.spacing = 180;

		else
			this.barBottomLayout.spacing = 210;
#else
		if (Screen.dpi < 140)
			this.barBottomLayout.spacing = 48;

		// 240
		else if (Screen.dpi < 280)
			this.barBottomLayout.spacing = 74;

		// 320
		else if (Screen.dpi < 400)
			this.barBottomLayout.spacing = 108;

		// 480
		else if (Screen.dpi < 500)
			this.barBottomLayout.spacing = 158;

		else
			this.barBottomLayout.spacing = 108;
#endif
	}

}
