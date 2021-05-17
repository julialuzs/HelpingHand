using UnityEngine;
using System.Collections;

public class ActiveOnStart : MonoBehaviour {

	public GameObject[] gameObjects;

	void Start ()
	{
		foreach (GameObject obj in gameObjects)
			obj.SetActive(true);
	}
	
}
