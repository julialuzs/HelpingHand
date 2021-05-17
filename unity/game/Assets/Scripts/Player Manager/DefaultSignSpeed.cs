/**
 *	Configura a velocidade de reprodução de sinais com relação a uma
 *		velocidade padrão e a velocidade ajustada pelo usuário.
 */
public class DefaultSignSpeed
{
	public static float DEFAULT = 1.1F;
	public static float DEFAULT_MAX = 2F;

	// Velocidade padrão
	private float speed;
	// Velocidade máxima
	private float max;
	// Relação entre a velocidade do tipo representado e a velocidade padrão (speed)
	private float unit;

	public DefaultSignSpeed()
	{
		this.speed = DEFAULT;
		this.max = DEFAULT_MAX;
		this.unit = 1F;
	}

	public DefaultSignSpeed(float defaultSpeed, float defaultMaxSpeed)
	{
		this.speed = defaultSpeed;
		this.max = defaultMaxSpeed;
		this.unit = (this.max - this.speed) / (DEFAULT_MAX - DEFAULT);
	}

	public float Speed {
		get { return this.speed; }
		set {
			this.speed = value;
			this.unit = calculateUnit();
		}
	}
	public float Max {
		get { return this.max; }
		set {
			this.speed = value;
			this.unit = calculateUnit();
		}
	}
	public float Unit {
		get { return this.unit; }
	}


	private float calculateUnit() {
		return (this.max - this.speed) / (DEFAULT_MAX - DEFAULT);
	}

	/*
	 *	Retorna velocidade em relação ao estado do slider.
	 *	@param slider - estado do slider (valor entre "speed - max" e "max")
	 */
	public float getProportional(float slider) {
		return this.speed + (slider - DEFAULT) * this.unit;
	}
}