using UnityEngine;
using System.Collections;

public class OpenURL : MonoBehaviour {

	public void open(string url) {
		Application.OpenURL(url);
	}

}