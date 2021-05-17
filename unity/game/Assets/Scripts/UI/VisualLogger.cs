using System.Collections.Generic;
using UnityEngine.UI;



public class VisualLogger : PlayerLogger
{

    private Text textObj;
    public int maximumLines = 20;

    private List<string> logs = new List<string>();

    protected override void Start()
    {
        base.Start();
        this.textObj = this.gameObject.GetComponent<Text>();
    }

    protected override void write(string text)
    {
        this.logs.Insert(0, text);

        if (this.logs.Count > this.maximumLines)
            this.logs.RemoveAt(this.logs.Count - 1);

        this.textObj.text = string.Join("\n", this.logs.ToArray());
    }

}


