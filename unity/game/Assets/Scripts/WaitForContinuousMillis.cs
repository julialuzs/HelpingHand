using UnityEngine;
using System.Collections;
using System.Diagnostics;

public class WaitForContinuousMillis {

	/*	Corotina que espera millis milésimos, mas que conta o tempo apenas
	 *		quando as animações sendo reproduzidas não estão pausadas.
	 */
	public static IEnumerator Wait(GenericPlayerManager context, long millis)
	{
		Stopwatch watch = new Stopwatch();
		watch.Start();
		
		while (true)
		{
			if (watch.ElapsedMilliseconds < millis)
			{
				UnityEngine.Debug.Log(millis);

				if (context.isPaused()) {
					if (watch.IsRunning)
						watch.Stop();
				}
				else if ( ! watch.IsRunning)
					watch.Start();
				
				yield return null;
			}
			else break;
		}
	}

}
