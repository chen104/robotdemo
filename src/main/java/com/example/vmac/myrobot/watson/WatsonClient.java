package com.example.vmac.myrobot.watson;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;


import com.example.vmac.myrobot.R;
import com.example.vmac.myrobot.app.WatsonApplication;
import com.example.vmac.myrobot.util.ContextUtil;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.conversation.v1.Conversation;
import com.ibm.watson.developer_cloud.conversation.v1.model.InputData;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechAlternative;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.RecognizeCallback;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/8/16.
 */

public class WatsonClient {
    private boolean hasAction =false;
    private static  String actionId="actionID";
    private static String tag =WatsonClient.class.getSimpleName();
    private Context appContext;
    public  Map<String,Object> conversationContext=null;
    private String workspace_id;
    private String conversation_username;
    private String conversation_password;
    private String STT_username;
    private String STT_password;
    private String TTS_username;
    private String TTS_password;
    private String analytics_APIKEY;
    public Conversation conversationService;
   private SpeechToText service;

    private TextToSpeech textToSpeech;
    private String sampleRate="16000";
    RecognizeOptions options;
    RecognizeOptions optionsOgg;
    StreamPlayer  streamPlayer;
    public Map<String, Object> getConversationContext() {
        return conversationContext;
    }

    public WatsonClient(Context mContext){
     this.appContext=mContext;
        conversation_username = mContext.getString(R.string.conversation_username);
        conversation_password = mContext.getString(R.string.conversation_password);
        workspace_id = mContext.getString(R.string.workspace_id);
        STT_username = mContext.getString(R.string.STT_username);
        STT_password = mContext.getString(R.string.STT_password);
        TTS_username = mContext.getString(R.string.TTS_username);
        TTS_password = mContext.getString(R.string.TTS_password);
        analytics_APIKEY = mContext.getString(R.string.mobileanalytics_apikey);
//        "url": "https://stream.watsonplatform.net/speech-to-text/api",
//                "username": "a5cfa288-bd34-4fe6-910f-3866dca5326a",
//                "password": "LsywvuC3pyNj"
        //init

//        "url": "https://stream.watsonplatform.net/text-to-speech/api",
//                "username": "a192cee8-5220-43bc-b210-34837bac0544",
//                "password": "GR52n7aNvP0D"
        conversationService =new Conversation(Conversation.VERSION_DATE_2017_05_26);
        conversationService.setUsernameAndPassword(conversation_username,conversation_password);

        service = new SpeechToText();
        service.setUsernameAndPassword(STT_username, STT_password);
         options = new RecognizeOptions.Builder()
//		  .continuous(true)
                .interimResults(true).inactivityTimeout(60)
                //.inactivityTimeout(5) // use this to stop listening when the speaker pauses, i.e. for 5s
                .contentType(HttpMediaType.AUDIO_PCM + "; rate=" + sampleRate)
                //  .contentType(HttpMediaType.AUDIO_WAV)
                .build();
        optionsOgg=new RecognizeOptions.Builder()
                .contentType("audio/ogg;codec=opus")
                .build();

        textToSpeech = new TextToSpeech();
        textToSpeech.setUsernameAndPassword(TTS_username, TTS_password);

    }

    public List<String> sendMessage(String inputMsg) {
        List<String> resList=new ArrayList<>();
        //构建请求信息对象
      //  conversationContext.remove("action");//清理上次上下文，可选

        InputData input = new InputData.Builder(inputMsg).build();
        com.ibm.watson.developer_cloud.conversation.v1.model.Context context=null;
        if(conversationContext!=null){
            context = (com.ibm.watson.developer_cloud.conversation.v1.model.Context) conversationContext;
        }
        MessageOptions newMessage= new MessageOptions.Builder(  workspace_id).input(input).context(context).build();
      // MessageRequest newMessage = new MessageRequest.Builder().inputText(inputMsg).context(conversationContext).build();

        //发送信息到Watson conversation服务中
        MessageResponse response=null;
        try {
           response = conversationService.message(newMessage).execute();
        }catch (Exception e){
            e.printStackTrace();

            return resList;
        }
        //Passing Context of last conversation
        //获得结果
        if (response.getContext() != null) {

            conversationContext=response.getContext();

        }
                //responseHandler.hadndleResponse(response, conversationContext );


        if (response != null) {
            if (response.getOutput() != null && response.getOutput().containsKey("text")) {
                ArrayList responseList = (ArrayList) response.getOutput().get("text");
                if (null != responseList && responseList.size() > 0) {
                        for(int i=0;i<responseList.size();i++){
                            if(TextUtils.isEmpty(responseList.get(i).toString())){
                                continue;
                            }//if
    //                        outMessage.setMessage(responseList.get(i).toString());
                            resList.add(responseList.get(i).toString());

                        }//for

                    }//if

                }//if

                Object actionid =   response.getContext().get(actionId);
                if(actionid != null&&(!TextUtils.isEmpty(actionid.toString()))){
                    ContextUtil.sentActionID(appContext,actionid.toString(),tag);
                    ((WatsonApplication)appContext).hasAction=true;
                    hasAction=true;
                }else{
                    ((WatsonApplication)appContext).hasAction=false;
                    hasAction=false;
                }
            }//if


        return resList;
    }

    public String speechToText(String pathFile){
        File file =new File(pathFile);
        if(!file.exists()){
            return null;
        }
        ServiceCall<SpeechResults> str=	service.recognize(file, options);
        SpeechResults speechResults = str.execute();
        List<Transcript>  results =  speechResults.getResults();
        if(results == null || results .isEmpty()){
            return  null;
        }
        StringBuilder sb =new StringBuilder();
        for(Transcript transcript : results){
            List<SpeechAlternative> alternatives = transcript.getAlternatives();
            String stringItem = alternatives.get(0).getTranscript();
            sb.append(",");
            sb.append(stringItem );
        }
        return  sb.toString();
    }



    public List<String> speechToListText(String pathFile){
        File file =new File(pathFile);
        List<String> list =new ArrayList<String>();
        if(!file.exists()){
            return list ;
        }
        ServiceCall<SpeechResults> str=	service.recognize(file, options);
        SpeechResults speechResults = str.execute();
        List<Transcript>  results =  speechResults.getResults();
        if(results == null || results .isEmpty()){
            return  list ;
        }
        StringBuilder sb =new StringBuilder();
        for(Transcript transcript : results){
            List<SpeechAlternative> alternatives = transcript.getAlternatives();
            String stringItem = alternatives.get(0).getTranscript();
            list.add(stringItem);
        }
        return  list ;
    }


    public String speechToText(File file){
        Log.i(WatsonApplication.tag,tag+"***speechToText");
        if(!file.exists()){
            return null;
        }

        Log.i(WatsonApplication.tag,tag+"***  recognize");
        ServiceCall<SpeechResults> str=	service.recognize(file, options);
        SpeechResults speechResults = str.execute();
        Log.i(WatsonApplication.tag,tag+"***  recognize");
        List<Transcript>  results =  speechResults.getResults();
        if(results == null || results .isEmpty()){
            Log.i(WatsonApplication.tag,tag+" ***   result is null");
            return  null;
        }
        StringBuilder sb =new StringBuilder();
        for(Transcript transcript : results){
            List<SpeechAlternative> alternatives = transcript.getAlternatives();
            String stringItem = alternatives.get(0).getTranscript();
            Log.i(WatsonApplication.tag,tag+" ***  stringItem "+stringItem);
            sb.append(",");
            sb.append(stringItem );
        }
        return  sb.toString();
    }

    public String speechToTextOgg(File file){
        Log.i(WatsonApplication.tag,tag+"***speechToText");
        if(!file.exists()){
            return null;
        }

        Log.i(WatsonApplication.tag,tag+"***  recognize");
        ServiceCall<SpeechResults> str=	service.recognize(file, optionsOgg);
        SpeechResults speechResults = str.execute();
        Log.i(WatsonApplication.tag,tag+"***  recognize");
        List<Transcript>  results =  speechResults.getResults();
        if(results == null || results .isEmpty()){
            Log.i(WatsonApplication.tag,tag+" ***   result is null");
            return  null;
        }
        StringBuilder sb =new StringBuilder();
        for(Transcript transcript : results){
            List<SpeechAlternative> alternatives = transcript.getAlternatives();
            String stringItem = alternatives.get(0).getTranscript();
            Log.i(WatsonApplication.tag,tag+" ***  stringItem "+stringItem);
            sb.append(",");
            sb.append(stringItem );
        }
        return  sb.toString();
    }


    public void speechToText(InputStream inputStream, RecognizeOptions options, RecognizeCallback callback ){
        service.recognizeUsingWebSocket(inputStream,options,callback);

    }




    public List<String> speechToListText(File file){

        List<String> list =new ArrayList<String>();
        if(!file.exists()){
            return list ;
        }
        ServiceCall<SpeechResults> str=	service.recognize(file, options);
        SpeechResults speechResults = str.execute();
        List<Transcript>  results =  speechResults.getResults();
        if(results == null || results .isEmpty()){
            return  list ;
        }
        StringBuilder sb =new StringBuilder();
        for(Transcript transcript : results){
            List<SpeechAlternative> alternatives = transcript.getAlternatives();
            String stringItem = alternatives.get(0).getTranscript();
            list.add(stringItem);
        }
        return  list ;
    }

    public void textToSpeech(String text){
       streamPlayer = new StreamPlayer();
        if(text != null && !"".equals(text)) {
            if(hasAction){

                streamPlayer.playStream(textToSpeech.synthesize(text, Voice.EN_LISA).execute());

            }else{
                //Change the Voice format and choose from the available choices
                long startTime =System.currentTimeMillis();
                InputStream inputStream = textToSpeech.synthesize(text, Voice.EN_LISA).execute();
//            try {
//                int length = inputStream.available();
//                //发送
//                ContextUtil.sentActionTime(appContext,length,WatsonClient.class.getSimpleName());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

                streamPlayer.playStream(inputStream,startTime,appContext);
            }


        }
        else {
            streamPlayer.playStream(textToSpeech.synthesize("No Text Specified", Voice.EN_LISA).execute());
        }
        Log.i(WatsonApplication.tag+"."+tag,tag+"*** finish textToSpeech");
    }




}
