using UnityEngine;

namespace LAViD.VLibras.UI {

	public class ExchangeableVisibility : MonoBehaviour {

		public bool visible = false;
		
		public bool isVisible()
		{
			return this.visible;
		}

		public virtual void Animate(bool visible)
		{
			this.visible = visible;
		}

	}

}
