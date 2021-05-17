using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace LAViD.Utils
{
	public class JSONParser
	{
		private static readonly string True = "true";
		private static readonly string False = "false";

		public static int ParseStringLengthLimit = 1024;

		private string json;
		private int index;

		public JSONParser(string json)
		{
			this.json = json;
			this.index = 0;
		}

		public delegate T Casting<T>(string value);
		public delegate T Parser<T>(JSONParser json);

		private static string defaultCasting(string value)
		{
			return value;
		}

		public static int IntParseCallback(JSONParser json)
		{
			StringBuilder str = new StringBuilder();

			if (json.Current('-'))
			{
				str.Append(json.Current());
				json.Next();
			}

			while (char.IsDigit(json.Current()))
			{
				str.Append(json.Current());
				json.Next();
			}

			// PlayerLogger.Log("JP", "IPC", "Result: '" + str.ToString() + "', at " + json.Current());

			if (str.Length == 0)
				throw new FormatException("No digit found.");
			else
				return int.Parse(str.ToString());
		}

		public char Current()
		{
			return this.json[this.index];
		}

		public bool Current(char value)
		{
			return this.json[this.index].Equals(value);
		}

		public char Next()
		{
			return this.json[++this.index];
		}

		public bool Next(char value)
		{
			return Jump().Current(value);
		}

		public bool End()
		{
			return this.index >= this.json.Length;
		}

		public JSONParser Find(char value)
		{
			if (!Current(value))
				while (!End() && !Next(value)) ;

			return this;
		}

		public JSONParser FindString()
		{
			return Find('"');
		}

		public JSONParser FindList()
		{
			return Find('[');
		}

		public JSONParser FindDictionary()
		{
			return Find('{');
		}

		public JSONParser Jump(int num)
		{
			this.index += num;
			// PlayerLogger.Log("JP", "J", num + " (" + this.index + ", " + Current() + ")");
			return this;
		}

		public JSONParser Jump()
		{
			return Jump(1);
		}

		public string ParseString()
		{
			if (!Current().Equals('"')) return null;

			StringBuilder str = new StringBuilder();

			while (!Next('"'))
			{
				str.Append(Current());

				if (str.Length > ParseStringLengthLimit)
					throw new FormatException("String exceeded length limit " + ParseStringLengthLimit + ".");
			}

			// PlayerLogger.Log("JP", "PS", "Result: '" + str.ToString() + "', at " + Current());

			Jump();
			return str.ToString();
		}

		public bool Contains(string value)
		{
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < value.Length; i++)
				if (!value[i].Equals(this.json[this.index + i]))
					return false;
				else
					s.Append(this.json[this.index + i]);

			Jump(value.Length);
			return true;
		}

		public bool ParseBoolean()
		{
			if (Contains(True)) return true;
			else if (Contains(False)) return false;
			else throw new FormatException("Boolean not found.");
		}

		public List<T> ParseList<T>(Casting<T> casting)
		{
			if (!Current().Equals('[')) return null;

			// PlayerLogger.Log("JP", "PL", "Parsing at '" + Current() + "'.");

			List<T> list = new List<T>();

			while (!Current().Equals(']'))
				if (Current().Equals('"'))
					list.Add(casting(ParseString()));
				else
					Jump();

			Jump();
			return list;
		}

		public List<string> ParseList()
		{
			return ParseList<string>(defaultCasting);
		}

		public List<T> ParseList<T>(Parser<T> callback)
		{
			if (!Current().Equals('[')) return null;

			// PlayerLogger.Log("JP", "PL", "Parsing at '" + Current() + "'.");

			List<T> list = new List<T>();

			Jump();
			while (!Current().Equals(']'))
			{
				if (Current(' ') || Current(','))
					Jump();
				else
					list.Add(callback(this));
			}

			Jump();
			return list;
		}

		public Dictionary<A, B> ParseDictionary<A, B>(Casting<A> keyCasting, Parser<B> valueCallback)
		{
			if (!Current().Equals('{')) return null;

			// PlayerLogger.Log("JP", "PD", "Parsing at '" + Current() + "'.");

			Dictionary<A, B> dict = new Dictionary<A, B>();
			
			while (!Current().Equals('}'))
			{
				if (Current().Equals('"'))
				{
					A key = keyCasting(ParseString());
					B value = valueCallback(Jump(2));

					// PlayerLogger.Log("JP", "PD", "Adding '" + key + "', '" + value + "'");

					dict.Add(key, value);
				}
				else Jump();
			}

			// PlayerLogger.Log("JP", "PD", "Finish at '" + Current() + "'");

			Jump();
			return dict;
		}

		public Dictionary<A, B> ParseDictionary<A, B>(Casting<A> keyCasting, Casting<B> valueCasting)
		{
			if (!Current().Equals('{')) return null;

			// PlayerLogger.Log("JP", "PD", "Parsing at '" + Current() + "'");

			Dictionary<A, B> dict = new Dictionary<A, B>();

			while (!Current().Equals('}'))
				if (Current().Equals('"'))
					dict.Add(keyCasting(ParseString()), valueCasting(Jump(2).ParseString()));
				else
					Jump();

			Jump();
			return dict;
		}

		public Dictionary<string, string> ParseDictionary()
		{
			return ParseDictionary<string, string>(defaultCasting, defaultCasting);
		}
	}

	public class JSONObject<T>
	{
		public delegate T Creator();
		public delegate void Assigner(JSONParser json, T obj);

		private Creator create;
		private Dictionary<string, Assigner> fields = new Dictionary<string, Assigner>();

		public JSONObject() { }

		public JSONObject(Creator creator) {
			this.create = creator;
		}

		public JSONObject<T> Add(string field, Assigner assigner)
		{
			this.fields.Add(field, assigner);
			return this;
		}

		public T Parse(JSONParser json) {
			return Parse(json, this.create);
		}

		public T Parse(JSONParser json, Creator create)
		{
			if (!json.Current('{')) throw new FormatException("JSONParser is not at an object position.");

			T obj = create();

			while (!json.Current('}')) {
				if (json.Current('"'))
				{
					//PlayerLogger.Log("JO", "P", "Found a field.");

					string field = json.ParseString();

					//PlayerLogger.Log("JO", "P", "Found: '" + field + "'.");

					if (this.fields.ContainsKey(field))
						this.fields[field](json.Jump(2), obj);
				}
				else json.Jump();
			}

			json.Jump();
			return obj;
		}
	}
}
