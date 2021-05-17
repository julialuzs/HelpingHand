using UnityEngine;
using System.Collections;

namespace LAViD.VLibras.UI {

	public class SlidingHidder : ExchangeableVisibility {
		
		public bool slideOnX = true;
		public bool toLeft = true;
		//public bool slideOnY = false;
		//public bool toTop = false;
		public float speed = 1f;
		public bool disableWhenHidden = true;

		private Transform thisTransform;
		private Vector2 hiddenPosition;
		private Vector2 visiblePosition;

		public override void Animate(bool visible)
		{
			if (base.isVisible() != visible)
			{
				this.gameObject.SetActive(true);
				base.Animate(visible);
			}
		}

		void Start()
		{
			this.thisTransform = this.gameObject.transform;
			this.speed = Screen.width * this.speed;
			Rect obj = this.gameObject.GetComponent<RectTransform>().rect;

			this.visiblePosition = thisTransform.position;
			this.hiddenPosition = thisTransform.position;

			if (this.slideOnX) this.hiddenPosition.x += toLeft ? -obj.width : obj.width;
			//if (this.slideOnY) this.hiddenPosition.y += toTop ? -obj.height: obj.height;

			this.thisTransform.position = base.isVisible() ? this.visiblePosition : this.hiddenPosition;
			this.gameObject.SetActive(base.isVisible());
		}

		void Update()
		{
			Vector2 position = thisTransform.position;
			Vector2 objective = base.isVisible() ? this.visiblePosition : this.hiddenPosition;
			bool changed = true;

			if (this.slideOnX && Mathf.Abs(position.x - objective.x) > this.speed)
				position.x += position.x < objective.x ? this.speed : -this.speed;

			else if (position.x != objective.x)
				position.x = objective.x;

			else changed = false;

			/*if (slideOnY && Mathf.Abs(position.y - objective.y) > speed)
				position.y = position.y + (position.y < objective.y ? speed : -speed);
			else
				position.y = objective.y;*/

			if (changed)
			{
				this.thisTransform.position = position;
				this.gameObject.SetActive( ! this.disableWhenHidden
						|| (position.x != this.hiddenPosition.x /*|| position.y != hiddenPosition.y*/)
					);
			}
		}

	}

}
