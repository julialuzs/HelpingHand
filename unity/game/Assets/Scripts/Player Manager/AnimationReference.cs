using UnityEngine;

public class AnimationReference
{
	public string name;
	public string subtitle;
	public AnimationState state;
	public short type;
	public bool playing;

	public AnimationReference(string name, string subtitle, AnimationState state, short type)
	{
		this.name = name;
		this.subtitle = subtitle;
		this.state = state;
		this.type = type;
		this.playing = false;
	}
}