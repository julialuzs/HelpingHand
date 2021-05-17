using UnityEngine;
using UnityEngine.UI;
using System.Collections.Generic;
using LAViD.VLibras.Dictionary;
using LAViD.Structures;
using System.Text.RegularExpressions;


[System.Serializable]
public class ItemData {

	public string animationName;
	public Button.ButtonClickedEvent thingToDo;

}

public class ListManager : MonoBehaviour {
	
	private Trie signs;

	public GameObject sampleItemObject;
	public GameObject sampleLoadingItemObject;
    public ScreenManager screenManager;

	public List<string> itemList;
	private int index = 0;
	private const int OFFSET = 20;
	private int size = 0;

	public GameObject listBlock;
	//public GameObject bar;

	public Transform contentPanel;
	public ScrollRect scrollView;
	public InputField input;
    public Button refresh;

	private bool isLoading = false;
	private GameObject loadingItem;

	private float itemHeight = 30F;

	void Start()
	{
		this.scrollView.onValueChanged.AddListener(checkScrollPosition);
		this.input.onValueChanged.AddListener(inputChanged);

		if (Screen.dpi < 140)
			this.itemHeight = 36;
		else if (Screen.dpi < 280)
			this.itemHeight = 50;
		else if (Screen.dpi < 400)
			this.itemHeight = 90;
		else if (Screen.dpi < 500)
			this.itemHeight = 120;
		else
			this.itemHeight = 90;

		this.gameObject.GetComponent<Dictionary>().AddOnLoadListener(load);
        this.refresh.onClick.AddListener(delegate {
            this.screenManager.hideScreen();
            this.screenManager.loadingSnippet.GetComponent<Button>().onClick.AddListener(this.gameObject.GetComponent<Dictionary>().stopUpdate);
            this.screenManager.showInfoMessage("Atualizar Dicionário", "Uma nova versão do dicionário será buscada, aguarde.", delegate {
                this.screenManager.setDictButtonEnable(false);
                this.gameObject.GetComponent<Dictionary>().UpdateDict(delegate {
                    this.clearList();
                    this.screenManager.setLoadingSnippetState(false);
                    this.screenManager.showInfoMessage("Dicionário Atualizado", "O dicionário foi atualizado com sucesso.");
                    this.screenManager.loadingSnippet.GetComponent<Button>().onClick.RemoveAllListeners();
                }, delegate {
                    this.screenManager.setLoadingSnippetState(false);
                    this.screenManager.showInfoMessage("Dicionário não atualizado", "Ocorreu um erro durante a atualização. Versão atual será mantida.");
                    this.screenManager.loadingSnippet.GetComponent<Button>().onClick.RemoveAllListeners();
                });
                this.screenManager.setLoadingSnippetState(true);
            });
            
            
            input.text = "";
        }) ;
    }

	private void load()
	{
		this.signs = this.gameObject.GetComponent<Dictionary>().Signs;

		this.itemList = TrieParser.StartsWith(this.signs, "");
		this.index = 0;
		this.size = itemList.Count;

		populateList();
	}

	public void checkScrollPosition(Vector2 scrollPosition)
	{
		if (scrollPosition.y <= 0.05F && ! this.isLoading)
			populateList();
	}

	public void clearList(){
		this.contentPanel.DetachChildren();
		foreach(GameObject go in GameObject.FindGameObjectsWithTag("clone"))
			Destroy(go);
	}

	public void inputChanged(string text)
	{
        this.itemList = TrieParser.StartsWith(this.signs, text.ToUpper());
		this.index = 0;
		this.size = itemList.Count;

		
		clearList();
		populateList();
		this.scrollView.verticalNormalizedPosition = 1F;
	}

	private void populateList()
	{
		int last = this.index + OFFSET;
		Regex rgx = new Regex(@"(?<1>[^-]+)&(?<2>.+)");
		if (last > size) last = this.size;

		for (int i = index; i < last; i++)
		{
			string item = itemList[i];

			GameObject newItem = Instantiate (sampleItemObject) as GameObject;
			SampleItem sampleItem = newItem.GetComponent<SampleItem>();
			sampleItem.title.text = rgx.IsMatch(item) ? rgx.Match(item).Groups["1"]+"("+rgx.Match(item).Groups["2"]+")" : item;

			LayoutElement le = newItem.GetComponent<LayoutElement>();
			le.minHeight = this.itemHeight;

			sampleItem.GetComponent<Button>().onClick.AddListener(
				delegate {
					listBlock.SetActive(false);
				}
			);

			if (contentPanel.childCount == 0)
				newItem.transform.GetChild(1).gameObject.SetActive(false);

			newItem.transform.SetParent(contentPanel);
		}

		this.index = last;
	}

}
