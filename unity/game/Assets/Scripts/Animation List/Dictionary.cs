using System;
using System.Diagnostics;
using System.IO;
using System.Collections;
using System.Threading;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.Events;
using LAViD.Structures;

namespace LAViD.VLibras.Dictionary
{
	public class Dictionary : MonoBehaviour
	{
		private readonly string DefaultDictionary = "{}";
		
        private string URL = Config.DICTIONARY_URL;
        private string path;

        private UnityAction onLoadListeners;
        private Trie signs = null;
		private volatile bool ready = false;
        private readonly int daysBetweenUpdates = 7;
       
        private UnityAction onFinish = null;
        private UnityAction onError = null;
        private Coroutine routine = null;

        private const string dateOfUpdateKey = "lastUpdate";



        public void AddOnLoadListener(UnityAction onLoad)
		{
			PlayerLogger.Log("D", "AOLL", "Registering onLoad event.");

			if (ready)
			{
				PlayerLogger.Log("D", "AOLL", "Ready. Calling onLoad event directly.");
				onLoad();
			}
			else this.onLoadListeners += onLoad;
		}

		public Trie Signs
		{
			get { return ready ? this.signs : null; }
		}

        public void UpdateDict(UnityEngine.Events.UnityAction onFinish, UnityEngine.Events.UnityAction onError)
        {
            ready = false;
            this.gameObject.GetComponent<Dictionary>().enabled = true;
            this.onFinish = onFinish;
            this.onError = onError;
            PlayerLogger.Log("Dictionary", "UpdateDict", "Trying to update the dictionary version, called by user.");
            this.routine = StartCoroutine(downloadDict());

        }

        public void stopUpdate()
        {
            PlayerLogger.Log("Dictionary", "stopUpdate", "Cancelling update of dictionary version.");
            StopCoroutine(this.routine);
        }

		private void Start()
		{
            this.path = Application.persistentDataPath + "/dictionary.json";

			PlayerLogger.Log("D", "S", "Dictionary path: " + this.path);
            

            /**
             * First time the dictionary is saved in the app
             */
 
            if (!exists() || !PlayerPrefs.HasKey(dateOfUpdateKey) || read() == DefaultDictionary)
            {
                PlayerLogger.Log("Dictionary", "Start", "Trying to update dictionary...");
                save(DefaultDictionary);
                PlayerPrefs.SetString(dateOfUpdateKey, DateTime.Now.ToString());
                PlayerPrefs.Save();
                this.routine =  StartCoroutine(downloadDict());
            }
            else
            {
                /**
                 * From now on check if X days has passed so an update will be made
                 */
                

                DateTime lastUpdate = Convert.ToDateTime(PlayerPrefs.GetString(dateOfUpdateKey));
                DateTime currentDate = DateTime.Now;
                TimeSpan diff = currentDate - lastUpdate;
                if (diff.Days >= daysBetweenUpdates)
                {
                    PlayerLogger.Log("Dictionary", "Start", "It's been " + diff.Days + " days since last update. Doing an update in the dict.");
                    this.routine = StartCoroutine(downloadDict());
                }
                else
                {
                    load(read());
                }

            }            

            
		}

        private IEnumerator downloadDict()
        {
            UnityWebRequest www = UnityWebRequest.Get(URL);
            www.timeout = Config.TIMEOUT;
            yield return www.SendWebRequest();

            if(!www.isDone || www.isNetworkError || www.isHttpError)
            {
                if(onError != null)
                {
                    onError();
                }
                PlayerLogger.Log("D", "downloadDict", "Connection error. Loading local dictionary.");

                if (www.error == null)
                    PlayerLogger.Log("D", "S", "Timeout.");
                else
                {
                    PlayerLogger.Log("D", "S", "WWW error: " + www.error);
                   
                }


                load(read());

            }
            else
            {
                if(onFinish != null)
                {
                    onFinish();

                }
                PlayerLogger.Log("D", "vRS", "Trie request done.");
                string json = www.downloadHandler.text;
                
                save(json);
                load(json);

            }
        }

		private void Update()
		{
			if (ready)
			{
				PlayerLogger.Log("D", "AOLL", "Ready. Calling onLoad events.");

				this.onLoadListeners();
                this.gameObject.GetComponent<Dictionary>().enabled = false;
			}
		}

		private void load(string json)
		{
			new Thread(
				() => {
					Thread.CurrentThread.IsBackground = true;

					Stopwatch watch = new Stopwatch();
					watch.Start();

					try {
						PlayerLogger.Log("D", "l", "Starting trie parsing.");
						this.signs = TrieParser.ParseJSON(json);						
						
					}
					catch (Exception e) {
						PlayerLogger.Log("D", "l", "Exception at trie JSON parser: " + e.ToString());
						this.signs = new Trie();
					}

					this.ready = true;
                  

					watch.Stop();
					PlayerLogger.Log("D", "l", "Parsed in " + watch.ElapsedMilliseconds + " ms.");
				}
			).Start();
		}

		private bool exists() {
			return File.Exists(this.path);
		}

		private string read()
		{
			if (!exists())
			{
				PlayerLogger.Log("D", "r", "Error! Local dictionary not found. Loading default.");
				return DefaultDictionary;
			}

			return File.ReadAllText(this.path);
		}

        private void save(string json)
        {
            File.WriteAllText(this.path, json);
            PlayerPrefs.SetString(dateOfUpdateKey, DateTime.Now.ToString());
            PlayerPrefs.Save();
        }

#if UNITY_EDITOR
			//UnityEditor.AssetDatabase.Refresh();
#endif
		}

	
}
