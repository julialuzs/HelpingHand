using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class SwitchImageColor : SwitchImageAttribute {

	public Color enabledColor = new Color(0.31F, 0.75F, 0.913F, 1F);
	public Color disabledColor = new Color(1F, 1F, 1F, 1F);

	public override void updateAttribute()
	{
		base.image.color = base.isEnabled ? enabledColor : disabledColor;
	}

}
