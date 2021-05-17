using System.Collections;
using System.Collections.Generic;
using UnityEngine;



public static class ErrorMessage
    
{

    private const string DEFAULT = "Ocorreu um erro com o serviço de {0} . Estamos trabalhando para solucioná-lo!";
    private const string NOT_CONNECTED_FAILURE = "É necessário que você esteja conectado à internet. Verifique sua conexão.";
    private const string INTERNET_CONNECTION_FAILURE = "Não foi possível obter conexão com a internet.";
    private const string CONNECTION_TIMEOUT_FAILURE = "A conexão com o serviço de {0} do VLibras foi perdida.";
    private const string UNREACHABLE_FAILURE = "Infelizmente não foi possível estabelecer conexão com o serviço de {0} do VLibras.";
    public enum TYPE
    {
        INTERNET,
        CONNECTION,       
        UNREACHABLE,
        TIMEOUT,
        BUNDLE_DOWNLOAD,
        WRITING_VIDEO,
        SHARING_VIDEO,
        PROCESSING_VIDEO,
        DOWNLOAD_VIDEO,
        DEFAULT
    }

   


    

    private static string GetError(TYPE errorType, Dictionary<TYPE, string> _dict)
    {

        // Try to get the result in the static Dictionary
        
        if (_dict.TryGetValue(errorType, out string result))
        {
            return result;
        }
        else
        {
            return _dict[TYPE.DEFAULT];
        }
    }

    public static class Export
    {
        
        private static readonly string CONNECTION_TIMEOUT_FAILURE = string.Format(ErrorMessage.CONNECTION_TIMEOUT_FAILURE, "vídeo") + " Aguarde e tente novamente!";
        private const string DOWNLOAD_FAILURE = "Erro ao efetuar download do Vídeo, tente novamente.";
        private const string WRITING_ERROR = "Ocorreu um erro ao salvar o vídeo.";
        private const string SENDING_ERROR = "Ocorreu um erro ao enviar o vídeo.";
        private const string PROCESSING_ERROR = "Infelizmente ocorreu um erro ao processar o vídeo, tente novamente!.";
        private readonly static string DEFAULT = string.Format(ErrorMessage.DEFAULT, "Vídeo");
        private readonly static string UNREACHABLE_FAILURE = string.Format(ErrorMessage.UNREACHABLE_FAILURE, "vídeo");

        private readonly static Dictionary<TYPE, string> _dict = new Dictionary<TYPE, string>
        {
            {TYPE.INTERNET, INTERNET_CONNECTION_FAILURE },
            {TYPE.CONNECTION, NOT_CONNECTED_FAILURE },
            {TYPE.TIMEOUT, CONNECTION_TIMEOUT_FAILURE },
            {TYPE.UNREACHABLE, UNREACHABLE_FAILURE },
            {TYPE.DEFAULT, DEFAULT },
            {TYPE.PROCESSING_VIDEO, PROCESSING_ERROR },
            {TYPE.WRITING_VIDEO, WRITING_ERROR },
            {TYPE.SHARING_VIDEO, SENDING_ERROR },
            {TYPE.DOWNLOAD_VIDEO, DOWNLOAD_FAILURE }

         };

        public static string Get(TYPE errorType)
        {
            return GetError(errorType, Export._dict);
            
        }

    }

    public static class Player
    {
        private const string AUX = "Usaremos português sinalizado e datilologia.";
        private static readonly string TRANSLATOR_TIMEOUT_FAILURE = string.Format(ErrorMessage.CONNECTION_TIMEOUT_FAILURE, "tradução");
        private const string INTERNET_CONNECTION_FAILURE = ErrorMessage.INTERNET_CONNECTION_FAILURE;
        private const string NOT_CONNECTED_FAILURE = "Você não está conectado à internet!";
        private const string BUNDLE_DOWNLOAD_FAILURE = "Não foi possível obter esta glosa. " + AUX;
        private readonly static string UNREACHABLE = string.Format(UNREACHABLE_FAILURE, "tradução");
        private readonly static string DEFAULT = string.Format(ErrorMessage.DEFAULT, "tradução");



        private readonly static Dictionary<TYPE, string> _dict = new Dictionary<TYPE, string>
        {
            {TYPE.INTERNET, INTERNET_CONNECTION_FAILURE },
            {TYPE.TIMEOUT, TRANSLATOR_TIMEOUT_FAILURE },
            {TYPE.BUNDLE_DOWNLOAD, BUNDLE_DOWNLOAD_FAILURE },
            {TYPE.CONNECTION, NOT_CONNECTED_FAILURE },
            {TYPE.UNREACHABLE, UNREACHABLE },
            {TYPE.DEFAULT, DEFAULT }
         };

        public static string Get(TYPE errorType)
        {
            return GetError(errorType, Player._dict);
        }
    }

    public static class Avaliation
    {
        private const string NOT_CONNECTED_FAILURE = "Para enviar uma sugestão é necessário que seu dispositivo esteja conectado à internet.";
        private const string UNREACHABLE_FAILURE = "Infelizmente não foi possível estabelecer conexão com o serviço de sugestão do VLibras.";
        private const string DEFAULT = "Infelizmente tivemos um problema ao enviar sua sugestão.Tente novamente mais tarde.";
        private readonly static Dictionary<TYPE, string> _dict = new Dictionary<TYPE, string>
        {
            {TYPE.INTERNET, INTERNET_CONNECTION_FAILURE },
            {TYPE.UNREACHABLE, UNREACHABLE_FAILURE },
            {TYPE.CONNECTION, NOT_CONNECTED_FAILURE },
            {TYPE.DEFAULT, DEFAULT }
         };

        public static string Get(TYPE errorType)
        {
            return GetError(errorType, _dict);
        }
    }
}





