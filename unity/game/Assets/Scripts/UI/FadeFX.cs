using UnityEngine;
using System.Collections;
using UnityEngine.EventSystems;
using UnityEngine.UI;

public class FadeFX : MonoBehaviour, IPointerEnterHandler, IPointerExitHandler {

	private BoxCollider AVATAR_COLLIDER;

	//Cria referência para o colider do avatar e torna a barra transparente
	public void Start()
	{

		AVATAR_COLLIDER = GameObject.FindGameObjectWithTag("avatar").GetComponent<BoxCollider>();
		Deactivate();

	}

	//Listeners de eventos do mouse

	public void OnPointerEnter(PointerEventData eventData){ Activate(); }
	public void OnPointerExit(PointerEventData eventData){ Deactivate(); }

	/*
	 *	Desabilita o colider do avatar para bloquear a rotação
	 *	Em seguida retorna o visual padrão da barra de controles
	 *	e reativa a interação com os botões
	 */
	private void Activate()
	{

		AVATAR_COLLIDER.enabled = false;

		foreach(GameObject GO in GameObject.FindGameObjectsWithTag("FADENEEDED"))
			GO.GetComponent<CanvasRenderer>().SetAlpha(1f);

		foreach(GameObject GO in GameObject.FindGameObjectsWithTag("BUTTONS"))
			GO.GetComponent<Button>().interactable = true;

	}

	/*
	 *	Habilita o colider do avatar para desbloquear a rotação
	 *	Em seguida diminui o alpha dos componentes da barra de controles tornando-os transparentes
	 *	Logo após desativa a interação com os botões para impedir que fiquem em status "highlighted"
	 */
	private void Deactivate(){

		AVATAR_COLLIDER.enabled = true;

		foreach(GameObject GO in GameObject.FindGameObjectsWithTag("FADENEEDED"))
			GO.GetComponent<CanvasRenderer>().SetAlpha(.2f);

		foreach(GameObject GO in GameObject.FindGameObjectsWithTag("BUTTONS"))
			GO.GetComponent<Button>().interactable = false;

	}

}
