package com.rainasmoon.privateradio.business;

import java.util.HashMap;
import java.util.List;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.widget.Button;
import android.widget.Toast;

import com.rainasmoon.privateradio.R;
import com.rainasmoon.privateradio.business.impl.MediaPlayHandler;
import com.rainasmoon.privateradio.business.impl.PrivateRadio;
import com.rainasmoon.privateradio.business.impl.TtsHandler;
import com.rainasmoon.privateradio.program.AudioProgram;
import com.rainasmoon.privateradio.program.Program;
import com.rainasmoon.privateradio.program.TextProgram;
import com.rainasmoon.privateradio.utils.Utils;

public class PlayHandlerImpl implements PlayHandler {

	TtsHandler ttsHandler = TtsHandler.instanceTtsHandler(this);
	MediaPlayHandler mediaPlayHandler = MediaPlayHandler.instanceMediaPlayHandler(this);
	
	PrivateRadio radio;

	private List<Program> programs;
	private int currentProgram;

	public PlayHandlerImpl(PrivateRadio radio) {
		this.radio = radio;
		
	}
	
	@Override
	public boolean isLiveNow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void playProgram(Program p) {
		if (p.isAudio()) {
			mediaPlayHandler.playProgram(p);
		} else if (p.isText()) {

			ttsHandler.speak(((TextProgram) p).getArticle());
		}

	}

	@Override
	public boolean isCompleted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCompletedListenner() {
		// next();

	}

	public void next() {
		currentProgram++;

		
		if (programs == null || programs.size() == 0) {
			radio.nextChannel();
		}

		
		if (currentProgram < programs.size()) {
			Program p = (Program) programs.get(currentProgram);
			Toast.makeText(Utils.context,
					"正在播放：" + p.getDescription(), Toast.LENGTH_SHORT)
					.show();
			Button startButton = (Button) Utils.activity
					.findViewById(R.id.start_radio);
			startButton.setText(p.getDescription() + " is playing...");
			
			playProgram(p);
		}
		else {
			//here need to load more contents...
			
			
		}
		
		if (currentProgram > programs.size() - 3) {
			radio.loadMoreContent();
		}
		
		
	}

	public void playList(List<Program> programs) throws Exception {
		if (programs == null || programs.size() ==0) {
			Utils.log.info("no program in this channel...");
			return;
		}
		this.programs = programs;
		currentProgram = 0;
		Program p = (Program) programs.get(currentProgram);
				
		playProgram(p);
	}

	
}
