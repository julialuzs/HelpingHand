using UnityEngine;
using UnityEngine.Events;
using UnityEngine.EventSystems;
using System.Collections;

public class RotateGameObject : MonoBehaviour {

	private float RotationVariable = 4.0f;
	private float RotateLeftRight = 0.0f;
    private static Vector3 lastPosition = new Vector3(0.0f,-180.0f);
	

    void OnMouseDrag( )
	{
        transform.Rotate(0.0f, -RotateLeftRight, 0.0f);
        lastPosition = transform.localEulerAngles;
        RotateLeftRight = Input.GetAxis("Mouse X") * RotationVariable;
	}

    public void UpdateRotate()
    {
        transform.localEulerAngles = lastPosition;
    }
    
}
