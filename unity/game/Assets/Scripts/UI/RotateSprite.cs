using UnityEngine;
using System.Collections;

public class RotateSprite : MonoBehaviour {
	private float count = 0;
	void Update () {
		count = 2f + (count % 360);
		transform.rotation = Quaternion.AngleAxis(count, new Vector3(0, 0, -1));
	}
}
