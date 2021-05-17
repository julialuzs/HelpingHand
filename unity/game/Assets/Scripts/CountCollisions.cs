using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CountCollisions : MonoBehaviour
{
    public GameObject arm;

    float step = 0f;
    float rotationValue = 0f;
    int status = 0;

    private void LateUpdate() {
        
        switch (status)
        {
            case 1:
                AddCorrection();
                break;
            case 2:
                RemoveCorretion();
                break;
            default:
                break;
        }
    }

    void AddCorrection() {
        if(step < 1)
        {
            Quaternion rot = Quaternion.Euler(arm.transform.eulerAngles.x, arm.transform.eulerAngles.y + rotationValue, arm.transform.eulerAngles.z);
            arm.transform.rotation = Quaternion.Lerp(arm.transform.rotation, rot, step);
            step += Time.deltaTime;
        }
    }

    void RemoveCorretion() {
        if(step > 0)
        {
            Quaternion rot = Quaternion.Euler(arm.transform.eulerAngles.x, arm.transform.eulerAngles.y + rotationValue, arm.transform.eulerAngles.z);
            arm.transform.rotation = Quaternion.Lerp(arm.transform.rotation, rot, step);
            step -= Time.deltaTime;
        }
    }

    void OnTriggerEnter(Collider enter){            
        if(enter.gameObject.name == "mao_esq")
        {
            arm = GameObject.FindGameObjectWithTag("BracoL");
            rotationValue = -20f;
        }
            
        else
        {
            arm = GameObject.FindGameObjectWithTag("BracoR");
            rotationValue = 20f;
        }
        status = 1;
        
    }

    void OnTriggerExit(Collider other) {
        status = 2;
    }
}
