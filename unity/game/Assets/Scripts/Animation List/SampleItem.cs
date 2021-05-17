using UnityEngine;
using UnityEngine.UI;
using System.Collections;
using System.Text.RegularExpressions;

public class SampleItem : MonoBehaviour {

	public Button button;
	public Text title;

	public void StartAnimation()
	{
		PlayerManager playerManager = GameObject.FindGameObjectWithTag("PlayerManager").GetComponent<PlayerManager>();
		string text = new Regex(@"\([^()]*\)$").IsMatch(title.text) ? title.text.Replace(Regex.Match(title.text, @"\([^()]*\)$").Value,"") + "&" + Regex.Match(Regex.Match(title.text, @"\([^()]*\)$").Value, @"(?<=\().+?(?=\))").Value: title.text;
		title.text = text;
		playerManager.playDict(title.text);
	}

}
