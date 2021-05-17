using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AvatarChange : MonoBehaviour
{
    public ScreenManager screenManager;
    private bool isMale = true;
    public static string avatarTag = "icaro";
    private GameObject avatarManager;

    private void Start()
    {
        avatarManager = GameObject.FindGameObjectWithTag("PlayerManager");
    }

    public void Change()
    {
        
        if (isMale)
        {
            avatarManager.GetComponent<PlayerManager>().RemoveAnimation();
            this.screenManager.setAvatar(false);
            avatarTag = "hozana";
            avatarManager.GetComponent<PlayerManager>().InicialConfig();
            isMale = false;
        }
        else
        {
            avatarManager.GetComponent<PlayerManager>().RemoveAnimation();
            this.screenManager.setAvatar(true);
            avatarTag = "icaro";
            avatarManager.GetComponent<PlayerManager>().InicialConfig();
            isMale = true;
        }
        GameObject.FindGameObjectWithTag(avatarTag).GetComponent<RotateGameObject>().UpdateRotate();
    }
}
