using UnityEngine;
using UnityEngine.UI;

public abstract class SwitchImageAttribute : MonoBehaviour {

	public Image image;
	public bool isEnabled;
	public GameObject updateReference;

	protected virtual void Start()
	{
		if (updateReference == null)
			updateAttribute();
		else
			switchAttributeByReference();
	}

	protected virtual void OnEnable()
	{
		if (updateReference != null)
			switchAttributeByReference();
	}

	public abstract void updateAttribute();

	public void switchAttribute(bool isEnabled)
	{
		this.isEnabled = isEnabled;
		updateAttribute();
	}
	public void switchAttribute()
	{
		switchAttribute( ! isEnabled);
	}

	public void switchAttributeByReference(GameObject updateReference)
	{
		ChangeState state = updateReference.GetComponent<ChangeState>();
		if (state != null) switchAttribute(state.getChangeState());
	}
	public void switchAttributeByReference()
	{
		switchAttributeByReference(updateReference);
	}

}
