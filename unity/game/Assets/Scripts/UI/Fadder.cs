using UnityEngine;

namespace LAViD.VLibras.UI {

	public class Fadder : ExchangeableVisibility {

		public float visibleAlpha = 1f;
		public float hiddenAlpha = 0f;
		public float showingSpeed = 0.01f;
		public float hiddingSpeed = 0.01f;
		public bool disableWhenHidden = true;

		private CanvasRenderer mainRenderer;
		private CanvasRenderer[] renderers;
		private bool visibilityChange = false;

		private readonly Vector3 visibleScale = new Vector3(1, 1, 0);
		private readonly Vector3 hiddenScale = new Vector3(0, 0, 0);

		public override void Animate(bool visible)
		{
			if (base.isVisible() != visible)
			{
				this.gameObject.transform.localScale = visibleScale;
				base.Animate(visible);
				this.visibilityChange = true;
			}
		}

		public void brutallySetVisible(bool visible)
		{
			base.Animate(visible);

			float alpha = visible ? visibleAlpha : hiddenAlpha;

			foreach (CanvasRenderer renderer in renderers)
				renderer.SetAlpha(alpha);

			updateScale();
		}

		private void updateScale()
		{
			if (disableWhenHidden && this.mainRenderer.GetAlpha() == hiddenAlpha)
				this.gameObject.transform.localScale = hiddenScale;

		}


		void Start()
		{
			this.mainRenderer = this.gameObject.GetComponent<CanvasRenderer>();
			this.renderers = this.gameObject.GetComponentsInChildren<CanvasRenderer>();
			this.hiddingSpeed = -hiddingSpeed;

			this.brutallySetVisible(base.isVisible());
		}

		void Update()
		{
			if (visibilityChange)
			{
				float objective = base.isVisible() ? this.visibleAlpha : this.hiddenAlpha;
				this.visibilityChange = false;

				foreach (CanvasRenderer renderer in renderers)
				{
					float alpha = renderer.GetAlpha();

					if (alpha != objective)
					{
						float speed = base.isVisible() ? this.showingSpeed : this.hiddingSpeed;
						renderer.SetAlpha(Mathf.Abs(alpha - objective) < Mathf.Abs(speed) ? objective : alpha + speed);
						this.visibilityChange = true;
					}
				}

				if (this.visibilityChange) this.updateScale();
			}
		}

	}

}