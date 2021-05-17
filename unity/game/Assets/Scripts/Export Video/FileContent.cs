using UnityEngine;
using System.Collections;

public class FileContent {

    public string file;
    public string size;
    public string status;
    public string requestUID;
    

    public static FileContent CreateFromJSON(string jsonString)
    {
        return JsonUtility.FromJson<FileContent>(jsonString);
    }
}
