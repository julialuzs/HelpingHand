using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using UnityEngine.UI;
using UnityEngine.EventSystems;

public class RegionSelector : MonoBehaviour {

	private readonly Dictionary<string, string> regions = new Dictionary<string, string> {

		{ "Padrão Nacional", "" },
		{ "Acre", "AC/" },
		{ "Alagoas", "AL/" },
		{ "Amapá", "AP/" },
		{ "Amazonas", "AM/" },
		{ "Bahia", "BA/" },
		{ "Ceará", "CE/" },
		{ "Distrito Federal", "DF/" },
		{ "Espírito Santo", "ES/" },
		{ "Goiás", "GO/" },
		{ "Maranhão", "MA/" },
		{ "Mato Grosso", "MT/" },
		{ "Mato Grosso do Sul", "MS/" },
		{ "Minas Gerais", "MG/" },
		{ "Pará", "PA/" },
		{ "Paraíba", "PB/" },
		{ "Paraná",  "PR/"},
		{ "Pernambuco", "PE/" },
		{ "Piauí", "PI/" },
		{ "Rio de Janeiro", "RJ/" },
		{ "Rio Grande do Norte", "RN/" },
		{ "Rio Grande do Sul", "RS/" },
		{ "Rondônia", "RO/" },
		{ "Roraima", "RR/" },
		{ "Santa Catarina", "SC/" },
		{ "São Paulo", "SP/" },
		{ "Sergipe", "SE/" },
		{ "Tocantins", "TO/" }

	};

	public PlayerManager manager;
	public GameObject list;
	public GameObject SampleItem;
	public Text label;

	private Region activeItem = null;
	private Region selectedItem = null;


    /*
     * Must be executed in the awake because the Fadder.cs 
     * depends on the creation of the references, used in the Start
     * method of it.
     * 
     * */
    void Awake ()
	{
		foreach (KeyValuePair<string, string> regionData in regions)
		{
			GameObject item = Instantiate(this.SampleItem) as GameObject;
			item.GetComponentInChildren<Text>().text = regionData.Key;
			item.transform.SetParent(this.list.transform);
			item.GetComponent<Button>().onClick.AddListener(delegate {
				selectItem(EventSystem.current.currentSelectedGameObject.GetComponent<Region>());
			});

			Region region = item.GetComponent<Region>();
			region.Path = regionData.Value;

			if (this.activeItem == null)
			{
				this.activeItem = region;
				this.selectedItem = region;
				region.select(true);
			}
		}
	}

	private void selectItem(Region region)
	{
		this.selectedItem.select(false);
		this.selectedItem = region;
		this.selectedItem.select(true);
	}

	public void ReselectActiveItem()
	{
		selectItem(this.activeItem);
	}

	public void OnDone()
	{
		this.activeItem = this.selectedItem;
		this.manager.setRegion(this.activeItem.Path);
		this.manager.clearLoadedBundles();

		if (selectedItem.Path == "")
		{
			this.label.text = "BR";
		}else
		{
			this.label.text = selectedItem.Path.Replace('/', ' ');

		}
	}

}

/*

void Start ()
	{
		this.group = this.gameObject.GetComponent<ToggleGroup>();

		foreach (KeyValuePair<string, string> region in regions)
		{
			GameObject item = Instantiate(this.sampleItem) as GameObject;
			item.GetComponentInChildren<Text>().text = region.Key;

			Toggle toggle = item.GetComponentInChildren<Toggle>();
			toggle.group = this.group;
			toggles.Add(toggle);

			Debug.Log(region.Key + ": " + (region.Value.Length == 0) + " but " + toggle.isOn);

			if (region.Value.Length == 0)
				this.selected = toggle;

			item.transform.SetParent(this.list.transform);
		}

		foreach (Toggle toggle in this.toggles)
		{
			toggle.isOn = false;
		}
		
		this.selected.isOn = true;
	}

	void Update ()
	{
		int i = 0;
		foreach (Toggle toggle in this.toggles)
		{
			Debug.Log("Toggle " + i++ + " : " + toggle.isOn);
			toggle.isOn = false;
			this.group.NotifyToggleOn(toggle);
		}
	}

	public void OnSelect()
	{
		if ( ! selected.isOn) {
			foreach (Toggle toggle in group.ActiveToggles())
			{
				if (toggle.isOn)
				{
					this.selected = toggle;
					this.manager.setRegion(this.regions[toggle.GetComponent<Text>().text]);
				}
			}
		}
	}

*/
