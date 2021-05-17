using UnityEngine;
using System;

public class SwitchActiveStatus : MonoBehaviour, ChangeState {

	public void Switch()
	{
		gameObject.SetActive( ! gameObject.activeInHierarchy);
	}

	public bool getChangeState()
	{
		return gameObject.activeInHierarchy;
	}

}
