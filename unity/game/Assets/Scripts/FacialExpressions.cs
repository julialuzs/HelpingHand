using UnityEngine;

public class FacialExpressions : MonoBehaviour
{
	private SkinnedMeshRenderer smr;
	
	void Start()
    {
		this.smr = base.gameObject.GetComponent<SkinnedMeshRenderer>();
	}
	
	// TODO: make a more beautiful solution for Hozana expressions
    void LateUpdate()
    {
		for(int i = 0; i < 22; i++)
			this.smr.SetBlendShapeWeight(i, this.smr.GetBlendShapeWeight(i) * 0.65f);
	}
}