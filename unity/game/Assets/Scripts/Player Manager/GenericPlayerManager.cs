/**	Gerenciador genérico e principal dos players.
 *
 *	Versão 2.1
 *		- Acompanhamento da legenda
 *			Corrigido problema na soletração quando a velocidade ultrapassava ~1.
 *
 *	Versão 2.2
 *		- Acompanhamento da legenda
 *			Corrigido problema na soletração quando o estado muda para pausado.
 *
 *	Versão 2.3
 *		- Legenda
 *			A letras acentuadas reproduzem a mesma sem o acento.
 *
 *	Versão 2.4
 *		- Legenda
 *			"Ç" reproduz "C".
 *		- Reprodução
 *			Quando não há acesso aos bundles dos sinais de pontuação, eles são ignorados.
 *			Ç adicionado como TYPE_WORD.
 *			
 *	Versão 2.5
 *		- Espera de requisição http com timeout.
 *			
 *	Log directory: http://docs.unity3d.com/Manual/LogFiles.html
 */

using UnityEngine;
using UnityEngine.Networking;
using System.Collections;
using System.Collections.Generic;
using System;
using System.Threading;
using UnityEngine.UI;
using System.Text.RegularExpressions;

public abstract class GenericPlayerManager : MonoBehaviour
{

    private const string DEFAULT_ANIMATION = "_default";
    //private const string DEFAULT_ANIMATION_MIDDLE = "_default_middle";


    protected float fadeLength = 0.4F;
    public string gloss = "";

    // Referencia para o avatar
    private GameObject AVATAR;
    // Referencia para o componente animador do avatar
    private Animation COMPONENT_ANIMATION;
    private Animation GERAL_COMPONENT_ANIMATION;
    public Text SUBTITLES;

    // Guarda os nomes das palavras já carregadas
    private HashSet<string> loadedAssetBundles = new HashSet<string>();

    // Fila de animações para reprodução
    // Utilizada para alterar velocidade e apresentar a legenda
    private Queue<AnimationReference> animQueue = new Queue<AnimationReference>();

    // Sinais de intervalo de animações: não sinaliza reprodução na UI
    private HashSet<string> intervalAnimations = new HashSet<string>();
    // Sinais ignorados na apresentação de legenda
    private HashSet<string> flags = new HashSet<string>();

    // True quando está na função Loader
    private volatile bool loading = false;
    // True quando está reproduzindo qualquer animação
    private volatile bool playing = false;
    // True quando é chamada a função de pausa
    private volatile bool paused = false;

    private volatile bool spellNow = false;

    // Se diferente de null, não está reproduzindo animação de intervalo
    private AnimationState intervalAnimationState = null;
    // Usado para pausar quando comandado
    private AnimationReference animationPlaying = null;

    // Gerenciador de animações de intervalo
    public RandomAnimations randomAnimations;
    // Gerenciados de legendas
    private Subtitle subtitles = null;

    private Coroutine animationWatcher = null;

    private bool[] lastLetterAnimations = new bool[256];

    protected readonly HashSet<string> ValidLetters = new HashSet<string>() {
            "A", "B", "C", "Ç", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9",
            ","
    };

    public virtual void Start()
    {
        // Configuração de velocidade das animações
        subtitles = new Subtitle(SUBTITLES);
        subtitles.DefaultWordSpeed = new DefaultSignSpeed();
        subtitles.DefaultFirstLetterSpeed = new DefaultSignSpeed(2.1F, 2.8F);
        subtitles.DefaultLetterSpeed = new DefaultSignSpeed(3F, 4.3F);
        subtitles.DefaultNumberSpeed = new DefaultSignSpeed(1.5F, 2.9F);

        InicialConfig();
    }

    public bool isPlayingIntervalAnimation() { return intervalAnimationState != null; }
    public bool isLoading() { return loading; }
    public bool isPlaying() { return playing; }
    public bool isPaused() { return paused; }
    public bool isRepeatable() { return !String.IsNullOrEmpty(gloss); }

    public virtual void setSubtitle(string text)
    {
        this.subtitles.setText(text);
    }

    public bool IsSpecialSign(string word)
    {
        return flags.Contains(word);
    }

    /* Configura as animações de intervalo */
    public void setRandomAnimations(string[] intervalAnimations)
    {
        foreach (string name in intervalAnimations)
        {
            this.intervalAnimations.Add(name);
            this.loadedAssetBundles.Add(name);
        }

        this.randomAnimations.setAnimations(intervalAnimations);
    }

    /* Define a velocidade das animacões com base no slider da GUI */
    public void setSlider(float sliderPosition)
    {
        subtitles.SliderPosition = sliderPosition;
        subtitles.updateWordSpeed();
        subtitles.updateLetterSpeed();
        subtitles.updateNumberSpeed();

        // Altera a velocidade de todas as animações em reprodução
        if (!paused)
            foreach (AnimationReference reference in this.animQueue)
                if (reference.type != Subtitle.TYPE_NONE && reference.state != null)
                    reference.state.speed = getSpeedByType(reference.type);
    }

    /* Retorna a velocidade para o tipo */
    private float getSpeedByType(short type)
    {
        switch (type)
        {
            case Subtitle.TYPE_WORD: return subtitles.WordSpeed;
            case Subtitle.TYPE_LETTER: return subtitles.LetterSpeed;
            case Subtitle.TYPE_NUMBER: return subtitles.NumberSpeed;
        }

        return 2F;
    }

    /* Para carregamento e animações */
    public void stopAll()
    {
        StopCoroutine("Loader");
        this.randomAnimations.unlockFor("Loader");
        loading = false;

        stopAnimations();
    }

    /* Para animações */
    public void stopAnimations()
    {
        this.randomAnimations.unlockFor("AnimationsWatcher");
        this.animQueue.Clear();
        this.subtitles.setText("");

        COMPONENT_ANIMATION.CrossFadeQueued(DEFAULT_ANIMATION, fadeLength, QueueMode.PlayNow);

        resetStates();
    }

    /* Repete animações */
    public void repeat()
    {
        repeat(true);
    }

    /* Repete animações se now == true ou se não estiver carregando glosa */
    public void repeat(bool now)
    {
        if (now || !this.loading)
            playNow(this.gloss);
    }

    /* Manda reproduzir animação e adiciona a file de animações a serem reproduzidas */
    private AnimationState playAnimation(short type, string name, string subtitle, float speed)
    {
        try
        {
            if(!COMPONENT_ANIMATION.GetClip(name)){
                COMPONENT_ANIMATION.AddClip(GERAL_COMPONENT_ANIMATION.GetClip(name), name); //o erro está aqui
            }

            AnimationState state = COMPONENT_ANIMATION.CrossFadeQueued(name, fadeLength, QueueMode.CompleteOthers);
            state.speed = speed - (speed * 0.2f);

            lock (this.animQueue)
            {
                this.animQueue.Enqueue(new AnimationReference(name, subtitle, state, type));
            }

            return state;
        }
        catch (NullReferenceException nre)
        {
            PlayerLogger.Log("'" + name + "' não foi encontrado!\n" + nre.ToString());
        }

        return null;
    }
    private AnimationState playAnimation(short type, string name, string subtitle)
    {
        return playAnimation(type, name, subtitle, getSpeedByType(type));
    }
    private AnimationState playAnimation(short type, string name)
    {
        return playAnimation(type, name, name);
    }

    /* Enfileira em reprodução a animação de intervalo */
    private void playDefaultAnimation()
    {
        playDefaultAnimation(false);
    }

    /* Enfileira em reprodução a animação padrão se now == true, ou reproduz imediatamente */
    private void playDefaultAnimation(bool now)
    {
        COMPONENT_ANIMATION.CrossFadeQueued(DEFAULT_ANIMATION, fadeLength, now ? QueueMode.PlayNow : QueueMode.CompleteOthers);
    }


    /**
	 * 	Returns the asset bundle named aniName.
	 *	@return WWW - request.
	 */
    protected abstract UnityWebRequest loadAssetBundle(string aniName);

    /**
	 *	Called when a bundle request causes error.
	 *	@param gloss - gloss been loaded.
	 *	@param word - bundle requested.
	 */
    public abstract void onConnectionError(string gloss, string word, UnityWebRequest request);

    /**
	 *	Listen to changes in the playing status.
	 */
    public abstract void onPlayingStateChange();

    /**
	 * Waits for response or time runs out. 
	 * Check for WWW.isDone, true if success, false if timeout.
	 * @param www - request.
	 */
    //protected abstract IEnumerator WaitForResponse(UnityWebRequest www);

    //protected abstract WWW getCheckConnectionRequest();

    /* Pause or continue animations */
    public void setPauseState(bool paused)
    {
        if (this.paused != paused)
        {
            this.paused = paused;

            lock (this.animQueue)
            {
                if (this.animationPlaying != null && this.animationPlaying.state != null)
                    this.animationPlaying.state.speed = paused ? 0F : getSpeedByType(this.animationPlaying.type);

                foreach (AnimationReference reference in this.animQueue)
                    if (reference.state != null)
                        reference.state.speed = paused ? 0F : getSpeedByType(reference.type);
            }
        }

        onPlayingStateChange();
    }

    public void setAnimationEnabled(bool enabled)
    {
        COMPONENT_ANIMATION.enabled = enabled;
    }

    /* Pause or continue animations */
    public void switchPauseState()
    {
        setPauseState(!this.paused);
    }

    /* Play if anything loading or playing */
    public bool playIfEmpty(string gloss)
    {
        if (this.loading || this.playing)
            return false;

        StartCoroutine("Loader", gloss);
        return true;
    }

    /* Stop all and play */
    public void playNow(string gloss)
    {
        stopAll();
        this.spellNow = false;
        StartCoroutine("Loader", gloss);
    }

    public void spell()
    {
        this.spellNow = true;
        StartCoroutine("Loader", gloss);
        PlayerLogger.Log("PM", "SP", " A glosa: " + gloss + " será soletrada.");


    }

    /* Reproduz animação de intervalo */
    public bool playIntervalAnimation(string name)
    {
        if (this.loading || this.playing)
            return false;

        playDefaultAnimation(true);
        this.intervalAnimationState = COMPONENT_ANIMATION.CrossFadeQueued(name, fadeLength, QueueMode.CompleteOthers);
        playDefaultAnimation(false);
        return true;
    }

    private string nextLetterAnimation(char letter)
    {
        string animation = (this.lastLetterAnimations[letter] ? "" : "d_") + letter.ToString();
        this.lastLetterAnimations[letter] = !this.lastLetterAnimations[letter];

        return animation;
    }

    private static short getType(char c)
    {
        // Se for uma letra
        if (c >= 65 && c <= 90)
            return Subtitle.TYPE_LETTER;

        // Se for um número
        else if (c >= 48 && c <= 57)
            return Subtitle.TYPE_NUMBER;

        // Se for uma vírgula
        else if (c == 44 || c == 'Ç')
            return Subtitle.TYPE_WORD;

        else
            return Subtitle.TYPE_NONE;
    }

    /* Enfileira soletração de palavra  */
    private string spellWord(Queue<ToPlay> toPlayQueue, string word)
    {
        string lastAnimationSubtitle = "";
        bool defaultPlayed = false;

        // A reprodução da primeira letra deve ser longa para não ser cortada no fade
        this.subtitles.updateLetterSpeed();

        for (int i = 0; i < word.Length; i++)
        {
            lastAnimationSubtitle = Subtitle.highlight(word, i);
            char anim = word[i];

            switch (word[i])
            {
                case 'Á':
                case 'Â':
                case 'À':
                case 'Ã':
                    anim = 'A';
                    break;
                case 'É':
                case 'Ê':
                    anim = 'E';
                    break;
                case 'Í':
                    anim = 'I';
                    break;
                case 'Ó':
                case 'Ô':
                case 'Õ':
                    anim = 'O';
                    break;
                case 'Ú':
                    anim = 'U';
                    break;
            }

            short type = getType(anim);
            string animName;

            try
            {
                animName = nextLetterAnimation(anim);

                // Não há animação
                if (type == Subtitle.TYPE_NONE)
                {
                    // Reproduz animação default apenas uma vez
                    if (!defaultPlayed)
                    {
                        defaultPlayed = true;
                        //toPlayQueue.Enqueue(new ToPlay(Subtitle.TYPE_WORD, DEFAULT_ANIMATION_MIDDLE, lastAnimationSubtitle, this));

                        // A reprodução da próxima letra deve ser longa para não ser cortada no fade
                        this.subtitles.updateLetterSpeed();
                    }

                    PlayerLogger.Log("Animação \"" + animName + "\" inexistente.");
                }
                else
                {
                    toPlayQueue.Enqueue(new ToPlay(type, animName, lastAnimationSubtitle, this));

                    defaultPlayed = false;
                    this.subtitles.updateLetterSpeed();
                }
            }
            catch (IndexOutOfRangeException)
            {
                Debug.Log("GPM.sW(" + word + "): Caractere '" + anim + "' inválido.");
            }

        }

        return lastAnimationSubtitle;
    }

    /* Instruções para reprodução de aninmação */
    private struct ToPlay
    {
        private short type;
        private string name;
        private string subtitle;
        private float speed;

        public ToPlay(short type, string name, string subtitle, float speed)
        {
            this.type = type;
            this.name = name;
            this.subtitle = subtitle;
            this.speed = speed;
        }
        public ToPlay(short type, string name, string subtitle, GenericPlayerManager context)
                : this(type, name, subtitle, 0F)
        {
            this.speed = context.getSpeedByType(type);
        }
        public ToPlay(short type, string name, GenericPlayerManager context)
                : this(type, name, name, context) { }

        public void play(GenericPlayerManager context)
        {
            context.playAnimation(this.type, this.name, this.subtitle, this.speed);
        }
    }

    public void clearLoadedBundles()
    {
        this.loadedAssetBundles.Clear();
    }

    /* Carrega animações e reproduz */
    private IEnumerator Loader(string gloss)
    {
        this.loading = true;
        this.randomAnimations.lockFor("Loader");
        // onPlayingStateChange();

        string lastAnimationSubtitle = "";
        bool spelled = false;

        Queue<ToPlay> toPlayQueue = new Queue<ToPlay>();
        toPlayQueue.Enqueue(new ToPlay(Subtitle.TYPE_NONE, DEFAULT_ANIMATION, "", this));

        bool playingStarted = false;

        String[] stringPos = gloss.Split(' ');
        foreach (string sub in stringPos)
        {
            string aniName = sub;
            if (String.IsNullOrEmpty(aniName)) continue;

            bool loaded = loadedAssetBundles.Contains(aniName);
            if (!spellNow)
            {
                if (!loaded)
                {
                    UnityWebRequest bundleRequest = loadAssetBundle(aniName);

                    if (bundleRequest != null)
                    {
                        yield return bundleRequest.SendWebRequest();

                        PlayerLogger.Log("GPM", "L", "Bundle request done (" + aniName + ").");

                        if (bundleRequest.isDone && bundleRequest.error == null)
                        {
                            AssetBundle bundle = null;
                            string bundleName = null;
                            try
                            {
                                bundle = DownloadHandlerAssetBundle.GetContent(bundleRequest);
                                bundleName = bundle.GetAllAssetNames()[0];

                            }
                            catch(Exception e)
                            {
                                PlayerLogger.Log("GPM", "L", e.Message + "\n" + e.StackTrace);
                                loaded = false;
                            }
                           

                            if (bundle != null && !String.IsNullOrEmpty(bundleName))
                            {
                                AnimationClip aniClip = bundle.LoadAsset(bundleName) as AnimationClip;
                                bundle.Unload(false);

                                if (aniClip)
                                {
                                    float frameRate = aniClip.frameRate;
                                    int frameFinal = (int)Math.Ceiling(aniClip.length * aniClip.frameRate);

                                    COMPONENT_ANIMATION.AddClip(aniClip, aniName, 15, (frameFinal - 15));
                                    COMPONENT_ANIMATION[aniName].clip.frameRate = frameFinal;
                                    GERAL_COMPONENT_ANIMATION.AddClip(aniClip, aniName, 15, (frameFinal - 15));
                                    GERAL_COMPONENT_ANIMATION[aniName].clip.frameRate = frameFinal;

                                    if (playingStarted) yield return new WaitForEndOfFrame();

                                    loadedAssetBundles.Add(aniName);
                                    loaded = true;

                                    PlayerLogger.Log("GPM", "L", "Bundle \"" + aniName + "\" loaded!");
                                }
                                else PlayerLogger.Log("GPM", "L", "Sign \"" + aniName + "\" wasn't loaded successfuly.");
                            }
                            else PlayerLogger.Log("GPM", "L", "Bundle \"" + aniName + "\" wasn't loaded successfuly.");
                        }
                        else
                        {
                            PlayerLogger.Log("GPM", "L", "Connection error.");
                            onConnectionError(gloss, aniName, bundleRequest);
                        }
                    }
                    else PlayerLogger.Log("GPM", "L", "Animation of \"" + aniName + "\" do not exist.");
                }


            }

            // Reproduz palavra
            if (loaded)
            {
                if (spelled)
                {
                    // Default
                    toPlayQueue.Enqueue(new ToPlay(Subtitle.TYPE_NONE, DEFAULT_ANIMATION, lastAnimationSubtitle, this));
                    spelled = false;
                }

                if (this.flags.Contains(aniName) || this.intervalAnimations.Contains(aniName))
                {
                    lastAnimationSubtitle = "";
                    toPlayQueue.Enqueue(new ToPlay(Subtitle.TYPE_WORD, aniName, "", this));
                }
                else
                {
                    lastAnimationSubtitle = aniName;
                    toPlayQueue.Enqueue(new ToPlay(Subtitle.TYPE_WORD, aniName, this));
                }
            }

            // Soletra palavra
            else
            {
                aniName =  Regex.Match(aniName, @"^([^&]*)&?.*$").Groups[1].ToString();
                PlayerLogger.Log("GPM", "L", "To spell: " + aniName);

                if (this.flags.Contains(aniName) || this.intervalAnimations.Contains(aniName))
                {
                   // toPlayQueue.Enqueue(new ToPlay(Subtitle.TYPE_NONE, DEFAULT_ANIMATION_MIDDLE, "", 1.6F));
                    spelled = false;
                }
                else
                {
                    // Se já houve o soletramento de alguma palavra, reproduz animação default
                    //if (spelled)
                        //toPlayQueue.Enqueue(new ToPlay(Subtitle.TYPE_NONE, DEFAULT_ANIMATION_MIDDLE, "", 1.6F));
                    //else
                    spelled = true;
                    lastAnimationSubtitle = spellWord(toPlayQueue, aniName);
                }
            }

            if (toPlayQueue.Count > 4)
            {
                playingStarted = true;

                while (toPlayQueue.Count > 0)
                {
                    toPlayQueue.Dequeue().play(this);
                    yield return new WaitForEndOfFrame();
                }
            }

            if (playingStarted)
                yield return new WaitForEndOfFrame();

            while (this.animQueue.Count > 6)
                yield return new WaitForEndOfFrame();
        }

        while (toPlayQueue.Count > 0)
        {
            toPlayQueue.Dequeue().play(this);
            yield return new WaitForEndOfFrame();
        }

        // Default
        playAnimation(Subtitle.TYPE_NONE, DEFAULT_ANIMATION, "");

        // onPlayingStateChange();
        this.randomAnimations.unlockFor("Loader");
        this.loading = false;
    }

    /* Sincroniza as legendas com as animações. */
    IEnumerator AnimationsWatcher()
    {
        PlayerLogger.Log("GPM", "AW", "Starting.");

        while (true)
        {
            if (this.animQueue.Count > 0)
            {
                if (!this.playing)
                {
                    PlayerLogger.Log("GPM", "AW", "Playing.");

                    this.playing = true;
                    onPlayingStateChange();

                    this.randomAnimations.lockFor("AnimationsWatcher");
                }

                // Gets first animation
                AnimationReference reference = this.animQueue.Peek();
                // PlayerLogger.Log("GPM", "AW", "Got " + reference.name + ".");

                if (COMPONENT_ANIMATION.IsPlaying(reference.name))
                {
                    // PlayerLogger.Log("GPM", "AW", "And its playing!");

                    this.animationPlaying = this.animQueue.Dequeue();
                    this.subtitles.setText(reference.subtitle);

                    // Watches transition between current and next animation
                    while (true)
                    {
                        yield return new WaitForEndOfFrame();

                        // Gets next animation
                        AnimationReference next = this.animQueue.Count > 0 ? this.animQueue.Peek() : null;

                        if (next != null && COMPONENT_ANIMATION.IsPlaying(next.name))
                        {
                            // PlayerLogger.Log("GPM", "AW", "Next: " + next.name + ".");

                            // Waits next animation starts
                            while (!COMPONENT_ANIMATION.IsPlaying(next.name))
                                yield return new WaitForEndOfFrame();

                            // Waits half fade
                            yield return new WaitForSeconds(this.fadeLength / 2);
                            break;
                        }
                        else if (!COMPONENT_ANIMATION.IsPlaying(reference.name)) break;
                    }
                }
                // Animation played but was not tracked
                else
                {
                    if (reference.state == null)
                        this.animQueue.Dequeue();

                    yield return new WaitForEndOfFrame();
                }
            }
            else if (this.playing && !this.loading)
            {
                PlayerLogger.Log("GPM", "AW", "Not playing.");

                resetStates();

                this.subtitles.setText("");
                this.randomAnimations.unlockFor("AnimationsWatcher");
            }
            else yield return new WaitForEndOfFrame();
        }
    }

    public void InicialConfig()
    {
        GERAL_COMPONENT_ANIMATION = GameObject.FindGameObjectWithTag("avatar").GetComponent<Animation>();
        AVATAR = GameObject.FindGameObjectWithTag(AvatarChange.avatarTag);
        COMPONENT_ANIMATION = AVATAR.GetComponent<Animation>();

        // Sinais ignorados na legenda
        string[] flags = new string[] {
            "[PONTO]",
            "[INTERROGAÇÃO]",
            "[EXCLAMAÇÃO]"
        };
        foreach (string flag in flags)
            this.flags.Add(flag);

        // Duplica sinais para diferenciar quando há repetidos
        foreach (string anim in this.ValidLetters)
            COMPONENT_ANIMATION.AddClip(COMPONENT_ANIMATION[anim].clip, "d_" + anim);

        foreach (string anim in this.ValidLetters)
            this.loadedAssetBundles.Add(anim);

        // Cria novo _default chamado _default_middle para pausas dentro de uma glosa
        // Impede que a animação default seja confundida com não-reprodução na UI
        //COMPONENT_ANIMATION.AddClip(COMPONENT_ANIMATION[DEFAULT_ANIMATION].clip, DEFAULT_ANIMATION_MIDDLE);
        if (animationWatcher == null)
            animationWatcher = StartCoroutine(AnimationsWatcher());

    }

    public void resetStates()
    {
        this.playing = false;
        this.paused = false;
        this.animationPlaying = null;

        onPlayingStateChange();
    }

    public void RemoveAnimation()
    {
        foreach (AnimationState anim in GERAL_COMPONENT_ANIMATION)
        {
            if (COMPONENT_ANIMATION.GetClip(anim.name))
                COMPONENT_ANIMATION.RemoveClip(anim.name);
        }
    }
}
