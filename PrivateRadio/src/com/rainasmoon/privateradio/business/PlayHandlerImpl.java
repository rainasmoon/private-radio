package com.rainasmoon.privateradio.business;

import java.util.HashMap;
import java.util.List;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.widget.Toast;

import com.rainasmoon.privateradio.business.impl.MediaPlayHandler;
import com.rainasmoon.privateradio.business.impl.PrivateRadio;
import com.rainasmoon.privateradio.business.impl.TtsHandler;
import com.rainasmoon.privateradio.program.AudioProgram;
import com.rainasmoon.privateradio.program.Program;
import com.rainasmoon.privateradio.program.TextProgram;
import com.rainasmoon.privateradio.utils.Utils;

public class PlayHandlerImpl implements PlayHandler {

	TtsHandler ttsHandler = new TtsHandler(this);
	MediaPlayHandler mediaPlayHandler = new MediaPlayHandler(this);
	
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
	public void playProgram(Program program) {
		if (program.isAudio()) {
			//

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

		
		
		if (currentProgram < programs.size()) {
			Program p = (Program) programs.get(currentProgram);
			Toast.makeText(Utils.context,
					"正在播放：" + p.getDescription(), Toast.LENGTH_SHORT)
					.show();
			if (p.isAudio()) {
				mediaPlayHandler.playProgram(p);
			} else if (p.isText()) {

				ttsHandler.speak(((TextProgram) p).getArticle());
			}
		}
		else {
			//here need to load more contents...
			
			
		}
		
		if (currentProgram > programs.size() - 3) {
			radio.loadMoreContent();
		}
		
		
	}

	public void playList(List<Program> programs) throws Exception {
		this.programs = programs;
		currentProgram = 0;
		Program p = (Program) programs.get(currentProgram);
		if (p.isAudio()) {

			mediaPlayHandler.playProgram(p);
		} else if (p.isText()) {

			try {
				ttsHandler.speakChineseTts(((TextProgram) p).getArticle());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
