using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

/**
 *	Gerenciador de animações de intervalo de inatividade.
 *	
 *	Não atribua as animações (nomes) diretamente do PlayerManager,
 *		utilize a função setRandomAnimations() do GenericPlayerManager.
 */
public class RandomAnimations : MonoBehaviour {

	public PlayerManager playerManager;

	// Reproduz animações a cada time segundos, sujeito à probabilidade.
	public int time = 3;
	// Probabilidade, de 0 a 1, de reproduzir animações no tempo determinado.
	public float probability = 0.3F;

	private string[] names = new string[] {};
	private int lastIndex = -1;

	// GameObjects que requisitaram o bloqueio das animações
	private HashSet<String> blockingObjects = new HashSet<String>();

	/* Bloquear animações para o GameObject object. */
	public void lockFor(String id) {
		lock (this.blockingObjects) {
			this.blockingObjects.Add(id);
		}
	}

	/* Desbloquear animações para o GameObject object. */
	public void unlockFor(String id) {
		lock (this.blockingObjects) {
			this.blockingObjects.Remove(id);
		}
	}

	/* Desbloquear animações para todos os GameObjects. */
	public void unlockAll() {
		lock (this.blockingObjects) {
			this.blockingObjects.Clear();
		}
	}

	/* Atribui as animações e inicia o processo para reproduzir. */
	public void setAnimations(string[] names)
	{
		this.names = names;
		StartCoroutine("playRandom");
	}

	/* Processo que reproduz animações a cada time segundos com a probabilidade probability.
	 * Enquanto houver GameObjects bloqueando, não reproduz nenhuma animação.
	 * Quando não houver nenhum bloqueio, espera time e reproduz animação de acordo com probability. */
	private IEnumerator playRandom()
	{
		while (true)
		{
			// Espera enquanto estiver reproduzindo animações de intervalo
			do { yield return null; }
			while (this.playerManager.isPlayingIntervalAnimation());
			
			// Se houver bloqueio, espera acabar
			while (true)
			{
				lock (this.blockingObjects) {
					if (this.blockingObjects.Count == 0)
						break;
				}

				yield return null;
			}

			// Espera time
			yield return new WaitForSeconds(this.time);

			// Se houver bloqueio, volta a esperar
			lock (this.blockingObjects) {
				if (this.blockingObjects.Count > 0)
					continue;
			}

			int index = sortIndex();

			if (index != -1 && index == this.lastIndex)
				index = sortIndex();

			if (index != -1)
				this.playerManager.playIntervalAnimation(this.names[index]);
		}
	}

	/* Escolhe uma animação de names ou nanhuma de acordo com probability. */
	private int sortIndex()
	{
		int rand = new System.Random().Next((int) (this.names.Length / this.probability));
		return rand < this.names.Length ? rand : -1;
	}

}