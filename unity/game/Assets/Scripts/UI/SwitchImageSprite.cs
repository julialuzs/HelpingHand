using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class SwitchImageSprite : SwitchImageAttribute {

	public Sprite enabledImage;
	public Sprite disabledImage;


	public override void updateAttribute()
	{
		base.image.sprite = base.isEnabled ? enabledImage : disabledImage;
	}

}
