using UnityEngine;
using System.Collections;

public class Region : MonoBehaviour {

	public GameObject checkmark;
	private string path = "";

	public string Path
	{
		get { return this.path; }
		set { this.path = value; }
	}
	
	public void select(bool selected)
	{
		checkmark.SetActive(selected);
	}
	
}
